package doanjavanhom4.webbanmypham.controller.admin;

import doanjavanhom4.webbanmypham.entities.Customer;
import doanjavanhom4.webbanmypham.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/customer")
public class AdminCustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/create")
    public String create(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "admin/customer/create";
    }

    @GetMapping("")
    public String index(Model model,
                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                        @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                        @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
                        @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir,
                        @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Customer> customers;
        if (keyword != null && !keyword.trim().isEmpty()) {
            customers = customerRepository.search(keyword.trim(), pageable);
        } else {
            customers = customerRepository.findAll(pageable);
        }
        model.addAttribute("listCustomers", customers);
        int totalPages = customers.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        return "admin/customer/index";
    }

    @GetMapping("/delete/{idCustomer}")
    public String deleteCustomerByAdmin(@PathVariable("idCustomer") Integer idCustomer) {
        Customer customer = customerRepository.findById(idCustomer).orElseThrow(() -> new RuntimeException("Invalid customer id"));
        customerRepository.delete(customer);
        return "redirect:/admin/customer";
    }

    @GetMapping("/edit/{idCustomer}")
    public String showForm(Model model,
                           @PathVariable Integer idCustomer,
                           Principal principal,
                           RedirectAttributes redirectAttributes) {
        if (principal.getName() == null) {
            throw new RuntimeException("Please login");
        }
        Customer customer = customerRepository.findById(idCustomer).orElseThrow(() -> new RuntimeException("Invalid id customer"));
        model.addAttribute("customer", customer);
        return "/admin/customer/edit";
    }

    @PostMapping("/edit/{idCustomer}")
    public String editCustomerById(@PathVariable Integer idCustomer,
                                   @ModelAttribute("customer") Customer updateCustomer,
                                   Principal principal,
                                   RedirectAttributes redirectAttributes,
                                   BindingResult bindingResult) {
        Customer customer = customerRepository.findById(idCustomer).orElseThrow(() -> new RuntimeException("Invalid customer id"));
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String phonePattern = "^\\d+$";
        if (principal.getName() == null) {
            throw new RuntimeException("Vui lòng đăng nhập tài khoản có quyền là Admin");
        }
        //Check trùng
        if (!customer.getEmail().equals(updateCustomer.getEmail()) && customerRepository.existsByEmail(updateCustomer.getEmail())) {
            bindingResult.rejectValue("email", "duplicate", "Email đã tồn tại trong database, vui lòng nhập email khác");
            return "/admin/customer/edit";
        }
        //Kiểm tran email đúng định dạng
        if (!Pattern.matches(emailPattern, updateCustomer.getEmail())) {
            bindingResult.rejectValue("email", "invalid", "Email không đúng định dạng, vui lòng thử lại email khác");
            return "/admin/customer/edit";
        }
        if (!Pattern.matches(phonePattern, updateCustomer.getPhone())) {
            bindingResult.rejectValue("phone", "invalid", "Số điện thoại không hợp lệ, vui lòng nhập số điện thoại khác");
            return "/admin/customer/edit";
        }
        if (!customer.getPhone().equals(updateCustomer.getPhone()) && customerRepository.existsByPhone(updateCustomer.getPhone())) {
            bindingResult.rejectValue("phone", "duplicate", "Số điện thoại đã tồn tại trong database, vui lòng nhập số điện thoại khác");
            return "/admin/customer/edit";
        }

        customer.setEmail(updateCustomer.getEmail());
        customer.setName(updateCustomer.getName());
        customer.setGender(updateCustomer.getGender());
        customer.setRole(updateCustomer.getRole());
        customer.setPhone(updateCustomer.getPhone());
        customerRepository.save(customer);
        return "redirect:/admin/customer";
    }


    @PostMapping("/create")
    public String createUser(@ModelAttribute("customer") Customer customer, BindingResult result) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            result.rejectValue("email", "duplicate", "Email đã tồn tại trong database, vui lòng nhập email khác");
            return "admin/customer/create";
        }
        if (customerRepository.existsByPhone(customer.getPhone())) {
            result.rejectValue("phone", "duplicate", "Số diện thọại đã tồn tại trong database, vui lòng nhập số điện thoại khác");
            return "admin/customer/create";
        }
        if (!customer.getPhone().matches("\\d+")) {
            result.rejectValue("phone", "invalid", "Số điện thoại chỉ được chứa số");
            return "admin/customer/create";
        }
        String hashedPassword = BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt());
        customer.setPassword(hashedPassword);
        customerRepository.save(customer);
        return "redirect:/admin/customer";
    }
}
