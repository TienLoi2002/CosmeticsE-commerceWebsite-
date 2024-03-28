package doanjavanhom4.webbanmypham.controller;

import doanjavanhom4.webbanmypham.entities.Product;
import doanjavanhom4.webbanmypham.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ShopController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/shop")
    public String index(Model model,
                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                        @RequestParam(value = "size", required = false, defaultValue = "6") int size,
                        @RequestParam(value = "sort", required = false, defaultValue = "0") int sort,
                        String q
    ) {
        Sort s = Sort.by("name").ascending();
        if (sort == 1) {
            s = Sort.by("price").descending();
        }
        if (sort == 2) {
            s = Sort.by("price").ascending();
        }
        Page<Product> products;
        Pageable pageable = PageRequest.of(page - 1, size, s);
        if (q != null && !q.trim().isEmpty()) {
            products = productRepository.searchProduct(q, pageable);
        } else {
            products = productRepository.findAll(pageable);
        }
        int totalPages = products.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("products", products);
        return "shop/index";
    }
}
