package doanjavanhom4.webbanmypham.controller.admin;

import doanjavanhom4.webbanmypham.entities.Product;
import doanjavanhom4.webbanmypham.repositories.CategoryRepository;
import doanjavanhom4.webbanmypham.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

    @GetMapping("")
    public String index(Model model, String keyword) {
        if(keyword != null && !keyword.trim().isEmpty())
        {
            model.addAttribute("products", productRepository.searchProductAdmin(keyword.trim()));
        }else
        {
            model.addAttribute("products", productRepository.findAll());
        }
        return "admin/product/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());

        model.addAttribute("product", new Product());
        return "admin/product/create";
    }

    @PostMapping("/create")
    public String save(@Valid @ModelAttribute("product") Product product, BindingResult result, Model model, @RequestPart("file") MultipartFile file) {
        if (result.hasErrors()) {
            System.out.println(result);
            model.addAttribute("categories", categoryRepository.findAll());
            return "admin/product";
        }
        if (file.isEmpty()) {

            model.addAttribute("error", "Vui lòng chọn một file để tải lên.");
            return "admin/product";
        }
        try {
            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Path filePath =  Paths.get("C:\\Users\\NguyenTienLoi\\Desktop\\webbanmypham\\src\\main\\resources\\static\\images\\products", originalFileName);

            Files.copy( file.getInputStream(),  filePath);

            model.addAttribute("message", "Tải lên file thành công: " + originalFileName);
            product.setImg(originalFileName);
            productRepository.save(product);
            return "redirect:/admin/product";

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi xảy ra khi tải lên file.");
            System.out.println(e.getMessage());
            return "admin/product/create";
        }


    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Product product = productRepository.findById(id).orElse(null);

        if (product != null) {
            model.addAttribute("categories", categoryRepository.findAll());

            model.addAttribute("product", product);
            return "admin/product/edit";
        } else {
            return "not-found";

        }
    }


    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("product")Product productUpdate, BindingResult result, Model model, @RequestPart("file") MultipartFile file ){
        if (result.hasErrors()) {
            System.out.println(result);
            model.addAttribute("categories", categoryRepository.findAll());
            return "admin/product";
        }
        if (file.isEmpty()) {

            model.addAttribute("error", "Vui lòng chọn một file để tải lên.");
            return "admin/product";
        }
        try {
            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Path filePath =  Paths.get("C:\\Users\\NguyenTienLoi\\Desktop\\webbanmypham\\src\\main\\resources\\static\\images\\products", originalFileName);

            Files.copy( file.getInputStream(),  filePath);

            model.addAttribute("message", "Tải lên file thành công: " + originalFileName);
            productUpdate.setImg(originalFileName);
            productRepository.findAll().stream()
                    .filter(product -> product.getId() == productUpdate.getId())
                    .findFirst()
                    .ifPresent(product -> {
                        productRepository.save(productUpdate);
                    });
            return "redirect:/admin/product";

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi xảy ra khi tải lên file.");
            System.out.println(e.getMessage());
            return "admin/product";
        }

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Product product = productRepository.findById(id).orElse(null);

        assert product != null;
        productRepository.delete(product);
        return "redirect:/admin/product";
    }
}
