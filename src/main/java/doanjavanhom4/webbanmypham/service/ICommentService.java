package doanjavanhom4.webbanmypham.service;

import doanjavanhom4.webbanmypham.entities.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ICommentService {
    public Optional<Comment> getCommentById(int idComment);
    List<Comment> getAllComments();
}
