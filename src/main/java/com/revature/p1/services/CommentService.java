package com.revature.p1.services;

import com.revature.p1.dtos.requests.NewCommentRequest;
import com.revature.p1.entities.Comment;
import com.revature.p1.entities.User;
import com.revature.p1.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepo;

    public CommentService(CommentRepository commentRepo) {
        this.commentRepo = commentRepo;
    }

    public Comment createComment(NewCommentRequest req, User user) {
        return commentRepo.save(new Comment(req.getComment(), req.getCreated_at(), req.getEdited_at(), user));
    }

    public List<Comment> getAllComments() {
        return commentRepo.findAll();
    }

}
