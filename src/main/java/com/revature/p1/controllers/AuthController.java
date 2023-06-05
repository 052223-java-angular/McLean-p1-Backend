package com.revature.p1.controllers;

import com.revature.p1.dtos.requests.NewUserRequest;
import com.revature.p1.services.UserService;
import com.revature.p1.utils.custom_exceptions.ResourceConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    //dependency injection from AuthService to AuthController
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    //there is also @GetMapping("/all"), @PutMapping("/update"), @DeleteMapping("/delete")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody NewUserRequest req) {
        //^^dto = data transfer object
        //check for valid username
        //check for unique username
        //check for valid password
        //check password confirmation

        if(!userService.isUniqueUsername(req.getUsername())) {
            throw new ResourceConflictException("Username is not unique");
        }


        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ExceptionHandler({ResourceConflictException.class})
    public ResponseEntity<Map<String, Object>> handleResourceConflictException(ResourceConflictException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", new Date(System.currentTimeMillis()));
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
    }

}
