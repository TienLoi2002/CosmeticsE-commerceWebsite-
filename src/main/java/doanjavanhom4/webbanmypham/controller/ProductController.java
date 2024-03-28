package doanjavanhom4.webbanmypham.controller;

import doanjavanhom4.webbanmypham.entities.Comment;
import doanjavanhom4.webbanmypham.entities.Product;
import doanjavanhom4.webbanmypham.repositories.CommentRepository;
import doanjavanhom4.webbanmypham.repositories.CustomerRepository;
import doanjavanhom4.webbanmypham.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.DecimalFormat;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/product")
    public String index(Model model) {

        return "product/index";
    }

    @GetMapping("/product/{productId}")
    public String getDetailIdProduct(@PathVariable("productId") Integer productId, Model model) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Invalid product id"));
        if (product != null) {
            int totalCommentsForProduct = commentRepository.getTotalCommentsForProduct(productId);
            double rating1Percentage = (commentRepository.getCountByRating(productId, 1) * 100.0) / totalCommentsForProduct;
            double rating2Percentage = (commentRepository.getCountByRating(productId, 2) * 100.0) / totalCommentsForProduct;
            double rating3Percentage = (commentRepository.getCountByRating(productId, 3) * 100.0) / totalCommentsForProduct;
            double rating4Percentage = (commentRepository.getCountByRating(productId, 4) * 100.0) / totalCommentsForProduct;
            double rating5Percentage = (commentRepository.getCountByRating(productId, 5) * 100.0) / totalCommentsForProduct;
            if (Double.isNaN(rating1Percentage)) {
                rating1Percentage = 0.0;
            }

            if (Double.isNaN(rating2Percentage)) {
                rating2Percentage = 0.0;
            }

            if (Double.isNaN(rating3Percentage)) {
                rating3Percentage = 0.0;
            }

            if (Double.isNaN(rating4Percentage)) {
                rating4Percentage = 0.0;
            }

            if (Double.isNaN(rating5Percentage)) {
                rating5Percentage = 0.0;
            }
            List<Comment> listComments = commentRepository.findAllByProductId(productId);
            model.addAttribute("totalComment", totalCommentsForProduct);
            model.addAttribute("averageRating", commentRepository.getAverageRatingForProduct(productId));
            model.addAttribute("product", product);
            model.addAttribute("products", productRepository.findAll());
            model.addAttribute("listComments", listComments);
            model.addAttribute("comment", new Comment());
            model.addAttribute("rating1Percentage", decimalFormat.format(rating1Percentage));
            model.addAttribute("rating2Percentage", decimalFormat.format(rating2Percentage));
            model.addAttribute("rating3Percentage", decimalFormat.format(rating3Percentage));
            model.addAttribute("rating4Percentage", decimalFormat.format(rating4Percentage));
            model.addAttribute("rating5Percentage", decimalFormat.format(rating5Percentage));
            return "product/index";
        } else {
            return "home/index";
        }
    }
}
