package com.revature.p1.controllers;

import com.revature.p1.dtos.requests.NewLoginRequest;
import com.revature.p1.dtos.requests.NewUserRequest;
import com.revature.p1.dtos.responses.Principal;
import com.revature.p1.services.JwtTokenService;
import com.revature.p1.services.LocationService;
import com.revature.p1.services.UserService;
import com.revature.p1.utils.custom_exceptions.ResourceConflictException;
import com.revature.p1.utils.custom_exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(exposedHeaders = {"auth-token"})
@RestController
@RequestMapping("/auth")
public class AuthController {
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
        if(!userService.isValidUsername(req.getUsername())) {
            //need to find a better regex
            throw new ResourceConflictException("Username must contain between 8 and 18 characters.");
        }
        if(!userService.isUniqueUsername(req.getUsername())) {
            //thrown exception matches handler with tag ResourceConflictException.class
            throw new ResourceConflictException("Username is not unique");
        }
        if(!userService.isValidPassword(req.getPassword())) {
            //need to find a better regex
            throw new ResourceConflictException("Password must contain between 8 and 18 characters.");
        }
        if(!userService.isSamePassword(req.getPassword(), req.getConfirmPassword())) {
            throw new ResourceConflictException("Passwords must match.");
        }

        userService.registerUser(req);


        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Principal> login(@RequestBody NewLoginRequest req) {
        //userService to call login method
        //might need to return role type to front end?
        Principal principal = userService.login(req);

        //return a jwt token in header
        String jwtToken = tokenService.generateToken(principal);
        principal.setToken(jwtToken);

        //return status with body
        return ResponseEntity.status(HttpStatus.OK).body(principal);
    }


}
