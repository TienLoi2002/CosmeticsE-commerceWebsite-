package doanjavanhom4.webbanmypham.controller.admin;

import doanjavanhom4.webbanmypham.entities.Category;
import doanjavanhom4.webbanmypham.repositories.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("")
    public String index(Model model, @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        if(keyword != null && !keyword.trim().isEmpty())
        {
            model.addAttribute("categories", categoryRepository.search(keyword.trim()));
        }else
        {
            model.addAttribute("categories", categoryRepository.findAll());
        }

        return "admin/category/index";
    }

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("category", new Category());

        return "admin/category/create";
    }

    @PostMapping("/create")
    public String save(@Valid @ModelAttribute("category") Category updateCategory, BindingResult result, Model model) {
        if (result.hasErrors()) {

            return "admin/category/create";
        }

        categoryRepository.save(updateCategory);

        return "redirect:/admin/category";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            model.addAttribute("category", category);
            return "admin/category/edit";
        } else {
            return "not-found";
        }
    }
    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("category") Category updateCategory, BindingResult result, Model model) {
        if (result.hasErrors()) {
//            model.addAttribute("book", new Book());
            return "admin/category/edit";
        }
        Category category = categoryRepository.findById(updateCategory.getId()).orElse(null);
        category.setName(updateCategory.getName());
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Integer id) {
        Category category = categoryRepository.findById(id).orElse(null);

        assert category != null;
        categoryRepository.delete(category);
        return "redirect:/admin/category";
    }
}
