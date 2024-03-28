package doanjavanhom4.webbanmypham.controller.admin;

import doanjavanhom4.webbanmypham.entities.Comment;
import doanjavanhom4.webbanmypham.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/comment")
public class AdminCommentController {
    @Autowired
    private CommentRepository commentRepository;


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
        Page<Comment> comments;
        if (keyword != null && !keyword.trim().isEmpty()) {
            comments = commentRepository.searchComment(keyword.trim(), pageable);
        } else {
            comments = commentRepository.findAll(pageable);
        }
        model.addAttribute("listComments", comments);
        int totalPages = comments.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("keyword", keyword);
        return "admin/comment/index";
    }

    @GetMapping("/delete/comment/{idComment}")
    public String deleteCommentByAdmin(@PathVariable("idComment") Integer idComment) {
        Comment comment = commentRepository.findById(idComment).orElseThrow(() -> new RuntimeException("Invalid id "));
        commentRepository.delete(comment);
        return "redirect:/admin/comment";
    }

}
