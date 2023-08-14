package com.revature.p1.controllers;

import com.revature.p1.dtos.requests.NewNoteRequest;
import com.revature.p1.entities.Note;
import com.revature.p1.entities.User;
import com.revature.p1.services.JwtTokenService;
import com.revature.p1.services.NoteService;
import com.revature.p1.services.UserService;
import com.revature.p1.utils.custom_exceptions.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(exposedHeaders = {"auth-token"})
@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;
    private final JwtTokenService tokenService;

    public NoteController(NoteService noteService, UserService userService, JwtTokenService tokenService) {
        this.noteService = noteService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/note")
    public ResponseEntity<?> createNote(@RequestBody NewNoteRequest req, @RequestHeader(name = "auth-token") String token) {

        if(token == null || token.isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        if(tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        //User foundUser = userService.findUserById(userId);

        noteService.save(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<List<Note>> getNotesByUser(@PathVariable(name="id") String dateId, @RequestHeader(name = "auth-token") String token) {

        if(token == null || token.isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        if(tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        //User foundUser = userService.findUserById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(noteService.getNotesByDate(dateId));
    }

}
