package com.revature.p1.controllers;

import com.revature.p1.dtos.requests.NewLocationRequest;
import com.revature.p1.dtos.responses.Principal;
import com.revature.p1.entities.Location;
import com.revature.p1.entities.User;
import com.revature.p1.services.JwtTokenService;
import com.revature.p1.services.LocationService;
import com.revature.p1.services.RoleService;
import com.revature.p1.services.UserService;
import com.revature.p1.utils.custom_exceptions.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locService;
    private final JwtTokenService tokenService;
    private final UserService userService;

    public LocationController(LocationService locService, JwtTokenService tokenService, UserService userService) {
        this.locService = locService;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLocation(@RequestBody NewLocationRequest req) {

        //---a token is valid when:
        //------1 - of type jwt
        //------2 - its signature is correct(nobody has changed a content of token)
        //------3 - its not expired   <<<<have not checked for expiration yet
        //------4 - it contains roles and scopes information

        // Checks if the user provides a token
        if (req.getToken() == null || req.getToken().isEmpty()) {
            // TODO: create a custom token exception class
            throw new RuntimeException("No token provided!");
        }

        String token = req.getToken();

        // Check if the token is valid
        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new RuntimeException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        User foundUser = userService.findUserById(userId);
        locService.save(req, foundUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/read")
    public ResponseEntity<List<Location>> getLocation(@RequestHeader(name="auth-token", required=true) String token) {
        User existingUser = tokenValidator(token);
        List<Location> retrievedLocs = locService.findByUser(existingUser);
        String responseHeaderKey = "auth-token";
        String responseHeaderValue = token;
        return ResponseEntity.status(HttpStatus.OK).header(responseHeaderKey, responseHeaderValue).body(retrievedLocs);
    }

    //need to add one for update after auto set home loc
    //@PathVariable("urlParameter") String urlParameter

    //--------helper---------
    private User tokenValidator(String token) {
        String username = tokenService.extractUsername(token);
        Principal testValidity = new Principal();
        testValidity.setUsername(username);
        if(!tokenService.validateToken(token, testValidity)) {
            //add to exception controller
            throw new AccessDeniedException("Access denied.");
        }
        String userId = tokenService.extractUserId(token);
        User existingUser = new User(userId);
        return existingUser;
    }

}
