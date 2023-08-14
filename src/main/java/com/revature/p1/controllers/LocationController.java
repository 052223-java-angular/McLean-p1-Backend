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

@CrossOrigin(exposedHeaders = {"auth-token"})
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

    @PostMapping("/location")
    public ResponseEntity<?> createLocation(@RequestBody NewLocationRequest req, @RequestHeader(name = "auth-token", required=true) String token) {

        if (token == null || token.isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        User foundUser = userService.findUserById(userId);
        locService.save(req, foundUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getLocation(@RequestHeader(name = "auth-token", required=true) String token) {

        if (token == null || token.isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        User foundUser = userService.findUserById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(locService.findByUser(foundUser));
    }

    @PutMapping("/locations/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable(name="id") String id, @RequestBody NewLocationRequest req, @RequestHeader(name = "auth-token", required=true) String token) {

        if (token == null || token.isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        User foundUser = userService.findUserById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(locService.updateLocation(id, req, foundUser));
    }

}
