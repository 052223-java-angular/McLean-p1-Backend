package com.revature.p1.controllers;

import com.revature.p1.dtos.requests.NewLocationRequest;
import com.revature.p1.entities.Location;
import com.revature.p1.entities.User;
import com.revature.p1.services.JwtTokenService;
import com.revature.p1.services.LocationService;
import com.revature.p1.services.UserService;
import com.revature.p1.utils.custom_exceptions.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
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

        if (req.getToken() == null || req.getToken().isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        String token = req.getToken();

        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        User foundUser = userService.findUserById(userId);
        locService.save(req, foundUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/read")
    public ResponseEntity<List<Location>> getLocation(@RequestBody NewLocationRequest req) {

        if (req.getToken() == null || req.getToken().isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        String token = req.getToken();

        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        User foundUser = userService.findUserById(userId);

        List<Location> retrievedLocs = locService.findByUser(foundUser);
        return ResponseEntity.status(HttpStatus.OK).body(retrievedLocs);
    }

    //need to add one for update after auto set home loc
    //@PathVariable("urlParameter") String urlParameter

}
