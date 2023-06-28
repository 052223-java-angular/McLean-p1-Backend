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

@CrossOrigin
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

    @PostMapping("/create")
    public ResponseEntity<?> saveDate(@RequestBody NewDateRequest req) {

        if (req.getToken() == null || req.getToken().isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        String token = req.getToken();

        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        User foundUser = userService.findUserById(userId);

        //date service -save
        dateService.save(req, foundUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<List<Date>> getDates(@RequestBody NewDateRequest req, @PathVariable("id") String id) {

//        if (req.getToken() == null || req.getToken().isEmpty()) {
//            throw new AccessDeniedException("No token provided!");
//        }
//
//        String token = req.getToken();
//
//        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
//            throw new AccessDeniedException("Invalid token!");
//        }

        //String userId = tokenService.extractUserId(token);
        User foundUser = userService.findUserById(id);

        List<Date> retrievedDates = dateService.findByUser(foundUser);
        return ResponseEntity.status(HttpStatus.OK).body(retrievedDates);
    }

}
