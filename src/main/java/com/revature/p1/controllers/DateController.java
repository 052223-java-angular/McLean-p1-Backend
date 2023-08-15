package com.revature.p1.controllers;

import com.revature.p1.dtos.requests.NewDateRequest;
import com.revature.p1.entities.Date;
import com.revature.p1.entities.User;
import com.revature.p1.services.DateService;
import com.revature.p1.services.JwtTokenService;
import com.revature.p1.services.UserService;
import com.revature.p1.utils.custom_exceptions.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(exposedHeaders = {"auth-token"})
@RestController
@RequestMapping("/dates")
public class DateController {

    private final UserService userService;
    private final JwtTokenService tokenService;
    private final DateService dateService;

    public DateController(UserService userService, JwtTokenService tokenService, DateService dateService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.dateService = dateService;
    }

    @PostMapping("/date")
    public ResponseEntity<?> saveDate(@RequestBody NewDateRequest req, @RequestHeader(name = "auth-token", required=true) String token) {

        if (token == null || token.isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        User foundUser = userService.findUserById(userId);

        dateService.save(req, foundUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/dates")
    public ResponseEntity<List<Date>> getDates(@RequestHeader(name = "auth-token", required=true) String token) {

        if (token == null || token.isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        User foundUser = userService.findUserById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(dateService.findByUser(foundUser));
    }

    @DeleteMapping("/date/{id}")
    public ResponseEntity<?> deleteDate(@PathVariable(name="id") String id, @RequestHeader(name = "auth-token", required=true) String token) {

        if (token == null || token.isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        //User foundUser = userService.findUserById(userId);

        dateService.deleteDateById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
