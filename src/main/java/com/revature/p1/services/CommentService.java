package com.revature.p1.services;

import com.revature.p1.dtos.requests.NewCommentRequest;
import com.revature.p1.dtos.responses.CommentResponse;
import com.revature.p1.entities.Comment;
import com.revature.p1.entities.User;
import com.revature.p1.repositories.CommentRepository;
import com.revature.p1.utils.custom_exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepo;

    public CommentService(CommentRepository commentRepo) {
        this.commentRepo = commentRepo;
    }

    public Comment createComment(NewCommentRequest req, User user) {
        return commentRepo.save(new Comment(req.getComment(), req.getCreated_at(), req.getEdited_at(), user));
    }

    public List<CommentResponse> getAllComments() {
        List<Comment> comments = commentRepo.findAll();
        List<CommentResponse> withUserId = new ArrayList<>();
        for(Comment comment : comments) {
            withUserId.add(new CommentResponse(comment));
        }
        return withUserId;
    }

    public Comment updateComment(String id, NewCommentRequest req) {
        Optional<Comment> commentOpt = commentRepo.findById(id);
        if(commentOpt.isEmpty()) {
            throw new ResourceNotFoundException("No comment with id: " + id + " found.");
        }
        Comment commy = commentOpt.get();
        commy.setComment(req.getComment());
        commy.setEdited_at(req.getEdited_at());
        return commentRepo.save(commy);
    }

    public void deleteComment(String id) {
        Optional<Comment> commentOpt = commentRepo.findById(id);
        if(commentOpt.isEmpty()) {
            throw new ResourceNotFoundException("No comment with id: " + id + " found.");
        }
        commentRepo.delete(commentOpt.get());
    }

    public String getCommentOwner(String commentId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentId + " was not found."));
        return comment.getUser().getId();
    }

}
