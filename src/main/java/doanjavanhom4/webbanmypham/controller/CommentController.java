package doanjavanhom4.webbanmypham.controller;

import doanjavanhom4.webbanmypham.entities.Comment;
import doanjavanhom4.webbanmypham.entities.Customer;
import doanjavanhom4.webbanmypham.entities.Product;
import doanjavanhom4.webbanmypham.repositories.CommentRepository;
import doanjavanhom4.webbanmypham.repositories.CustomerRepository;
import doanjavanhom4.webbanmypham.repositories.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/product/comments/{productId}")
    public String createComment(@PathVariable("productId") Integer productId,
                                @ModelAttribute("comment") Comment comment,
                                Principal principal) {
        System.out.println(principal.getName());
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Product not found with id: " + productId);
        }
        Product product = productOptional.get();

        Customer customer = customerRepository.findByEmail("123@gmail.com");
        if (customer == null) {
            throw new RuntimeException("Customer not found with id: " + productId);
        }
        comment.setProduct(product);
        comment.setCustomer(customer);
        comment.setContent(comment.getContent());
        comment.setStar(comment.getStar());
        commentRepository.save(comment);
        return "redirect:/product/{productId}";
    }

    @ResponseBody
    @PostMapping("/api/comment")
    public String createComment(HttpServletRequest httpServletRequest, Principal principal, RedirectAttributes redirectAttributes) {

        if (principal == null) {
            redirectAttributes.addFlashAttribute("returnPage", "/product/" + httpServletRequest.getParameter("productId"));
            return "redirect:/login";
        }
        if(redirectAttributes.getFlashAttributes().containsKey("returnPage"))
        {
            String returnUrl = (String) redirectAttributes.getFlashAttributes().get("returnUrl");
            System.out.println("log: " + returnUrl);
            redirectAttributes.getFlashAttributes().remove("returnUrl");
            return "redirect:" + returnUrl;
        }
        Optional<Product> productOptional = productRepository.findById(Integer.valueOf(httpServletRequest.getParameter("productId")));
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Product not found with id: " + httpServletRequest.getParameter("content"));
        }
        Product product = productOptional.get();
//        if (principal == null) {
//            throw new RuntimeException("You are not logged into your account, please login before comment");
//        }
        System.out.println("Show log principal: " + principal.getName());
        String email = principal.getName();
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new RuntimeException("Customer not found with id: " + httpServletRequest.getParameter("content"));
        }
        Comment comment = new Comment();
        comment.setProduct(product);
        comment.setCustomer(customer);
        comment.setContent((String) httpServletRequest.getParameter("content"));
        comment.setStar(Integer.valueOf(httpServletRequest.getParameter("star")));
        commentRepository.save(comment);
        return customer.getName();
    }

    @GetMapping("product/edit/{productId}")
    public String showFormUpdateComment(Model model,
                                        @PathVariable Integer productId,
                                        @RequestParam Integer commentId,
                                        Principal principal, RedirectAttributes redirectAttributes) {
        System.out.println("Log product id" + principal);
        if (principal == null) {
//            redirectAttributes.addAttribute("returnUrl", "/product/" + productId);
            redirectAttributes.addFlashAttribute("returnUrl", "/product/" + productId);
            return "redirect:/login";
        }
        if (redirectAttributes.getFlashAttributes().containsKey("returnUrl")) {
            String returnUrl = (String) redirectAttributes.getFlashAttributes().get("returnUrl");
            System.out.println("log: " + returnUrl);
            redirectAttributes.getFlashAttributes().remove("returnUrl");
            return "redirect:" + returnUrl;
        }
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Invalid id product");
        }
        Product product = productOptional.get();
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (!optionalComment.isPresent()) {
            throw new RuntimeException("Invalid id comment");
        }
        Comment comment = optionalComment.get();
        model.addAttribute("comment", comment);
        return "product/showFormUpdateComment";
    }

    @PostMapping("/product/{productId}/edit")
    public String updateComment(@PathVariable Integer productId,
                                @RequestParam Integer commentId,
                                @ModelAttribute("comment") Comment updatedComment, Principal principal,
                                RedirectAttributes redirectAttributes) {
        if (principal == null) {
            redirectAttributes.addAttribute("returnUrl", "/product/" + productId);
            return "redirect:/login";
        }
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Invalid product id");
        }
        Product product = productOptional.get();

        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (!commentOptional.isPresent()) {
            throw new RuntimeException("Invalid comment id");
        }
        Comment comment = commentOptional.get();

        comment.setContent(updatedComment.getContent());
        comment.setStar(updatedComment.getStar());

        commentRepository.save(comment);

        return "redirect:/product/{productId}";
    }

    @PostMapping("/product/removeComment/{productId}")
    public String removeComment(@PathVariable Integer productId,
                                @RequestParam Integer commentId,
                                Principal principal, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            redirectAttributes.addAttribute("returnUrl", "/product/" + productId);
            return "redirect:/login";
        }
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Comment is not exits in this product");
        }
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (!optionalComment.isPresent()) {
            throw new RuntimeException("Comment is not found in database");
        }
        Comment comment = optionalComment.get();
        if (!comment.getProduct().getId().equals(productId)) {
            throw new RuntimeException("Invalid Product ID");
        }
        commentRepository.delete(comment);
        return "redirect:/product/{productId}";
    }


}

