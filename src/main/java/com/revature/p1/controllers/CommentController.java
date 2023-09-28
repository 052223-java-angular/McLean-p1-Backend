package com.revature.p1.controllers;

import com.revature.p1.dtos.requests.NewCommentRequest;
import com.revature.p1.dtos.responses.CommentResponse;
import com.revature.p1.entities.Comment;
import com.revature.p1.entities.User;
import com.revature.p1.services.CommentService;
import com.revature.p1.services.JwtTokenService;
import com.revature.p1.services.UserService;
import com.revature.p1.utils.custom_exceptions.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(exposedHeaders = {"auth-token"})
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final JwtTokenService tokenService;

    public CommentController(CommentService commentService, UserService userService, JwtTokenService tokenService) {
        this.commentService = commentService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/comment")
    public ResponseEntity<Comment> createComment(@RequestBody NewCommentRequest req, @RequestHeader(name = "auth-token") String token) {

        if (token == null || token.isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        User foundUser = userService.findUserById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(req, foundUser));
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@RequestHeader(name = "auth-token") String token) {

        if (token == null || token.isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        //User foundUser = userService.findUserById(userId);

        //should later change to get by created_at sorted
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllComments());
    }

    //update+delete
    @PutMapping("/comment/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable(name="id") String id, @RequestBody NewCommentRequest req, @RequestHeader(name = "auth-token") String token) {

        if (token == null || token.isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        //User foundUser = userService.findUserById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(id, req));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable(name="id") String id, @RequestHeader(name = "auth-token") String token) {

        if(token == null || token.isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        if(tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        User foundUser = userService.findUserById(userId);

        String commentOwnerId = commentService.getCommentOwner(id);
        if(!commentOwnerId.equals(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized to delete this post.");
        }

        commentService.deleteComment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
