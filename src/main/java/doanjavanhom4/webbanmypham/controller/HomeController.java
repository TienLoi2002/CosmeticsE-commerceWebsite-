package doanjavanhom4.webbanmypham.controller;

import doanjavanhom4.webbanmypham.common.UserRole;
import doanjavanhom4.webbanmypham.entities.Customer;
import doanjavanhom4.webbanmypham.repositories.CustomerRepository;
import doanjavanhom4.webbanmypham.repositories.ProductRepository;
import doanjavanhom4.webbanmypham.service.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;


    @RequestMapping("/default")
    public String defaultAfterLogin(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String firstAuthority = Objects.requireNonNull(authentication.getAuthorities().stream().findFirst().orElse(null)).toString();
        Customer customer = customerRepository.findByEmail(authentication.getName());
        if (firstAuthority.equals("ADMIN")) {
            session.setAttribute("name_admin", customer.getName());
            session.setAttribute("id_admin", customer.getId());
            session.setAttribute("role_admin", customer.getRole());

            return "redirect:/admin/home";
        }
        session.setAttribute("name", customer.getName());
        session.setAttribute("id", customer.getId());
        session.setAttribute("role", customer.getRole());
        return "redirect:/";
    }

    @GetMapping
    public String home(Model model, HttpSession httpSession) {
        System.out.println(httpSession.getAttribute("test"));
        model.addAttribute("products", productRepository.findAll());
        return "home/index";
    }

    @GetMapping("/login")
    public String login() {
        return "home/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customer", new Customer());
        return "home/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult, Model model) {
        System.out.println(bindingResult);

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error
                    -> model.addAttribute(error.getField() + "_error", error.getDefaultMessage()));
            return "home/register";
        }

        customer.setRole(UserRole.USER);
        customer.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));
        System.out.println(customer);
        customerService.save(customer);
        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, Principal principal) throws ServletException {
        if (principal != null) {
            request.logout();
        }
        return "redirect:/";
    }

    @GetMapping("about")
    public String about() {
        return "home/about";
    }

    @GetMapping("contact")
    public String contact() {

        return "home/contact";
    }
}
