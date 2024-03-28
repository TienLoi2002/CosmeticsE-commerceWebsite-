package doanjavanhom4.webbanmypham.service;

import doanjavanhom4.webbanmypham.entities.Comment;
import doanjavanhom4.webbanmypham.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CommentServiceImp implements ICommentService{
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public Optional<Comment> getCommentById(int idComment) {
        return null;
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
