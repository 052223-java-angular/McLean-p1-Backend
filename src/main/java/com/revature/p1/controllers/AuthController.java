package com.revature.p1.controllers;

import com.revature.p1.dtos.requests.NewLoginRequest;
import com.revature.p1.dtos.requests.NewUserRequest;
import com.revature.p1.dtos.responses.Principal;
import com.revature.p1.services.JwtTokenService;
import com.revature.p1.services.UserService;
import com.revature.p1.utils.custom_exceptions.ResourceConflictException;
import com.revature.p1.utils.custom_exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    //dependency injection from UserService to AuthController
    private final UserService userService;
    private final JwtTokenService tokenService;

    public AuthController(UserService userService, JwtTokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    //there is also @GetMapping("/all"), @PutMapping("/update"), @DeleteMapping("/delete")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody NewUserRequest req) {
        //^^dto = data transfer object (the NewUserRequest part)
        //check for valid username
        //check for unique username
        //check for valid password
        //check password confirmation

        if(!userService.isUniqueUsername(req.getUsername())) {
            //thrown exception matches handler with tag ResourceConflictException.class
            throw new ResourceConflictException("Username is not unique");
        }

        userService.registerUser(req);


        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Principal> login(@RequestBody NewLoginRequest req) {
        //userService to call login method
        Principal principal = userService.login(req);

        //return a jwt token
        String jwtToken = tokenService.generateToken(principal);
        principal.setToken(jwtToken);

        //return status with body
        return ResponseEntity.status(HttpStatus.OK).body(principal);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<Map<String, Object>> handleResourceConflictException(ResourceConflictException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", new Date(System.currentTimeMillis()));
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", new Date(System.currentTimeMillis()));
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
    }

}
