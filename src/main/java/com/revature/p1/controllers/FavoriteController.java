package com.revature.p1.controllers;

import com.revature.p1.dtos.requests.NewFavoriteRequest;
import com.revature.p1.entities.Favorite;
import com.revature.p1.entities.User;
import com.revature.p1.services.FavoriteService;
import com.revature.p1.services.JwtTokenService;
import com.revature.p1.services.UserService;
import com.revature.p1.utils.custom_exceptions.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(exposedHeaders = {"auth-token"})
@RestController
@RequestMapping("/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final JwtTokenService tokenService;
    private final UserService userService;

    public FavoriteController(FavoriteService favoriteService, JwtTokenService tokenService, UserService userService) {
        this.favoriteService = favoriteService;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/favorite")
    public ResponseEntity<?> createFavorite(@RequestBody NewFavoriteRequest req, @RequestHeader(name = "auth-token", required=true) String token) {

        if (token == null || token.isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        User foundUser = userService.findUserById(userId);

        favoriteService.save(req, foundUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/favorite")
    public ResponseEntity<Favorite> readFavorite(@RequestHeader(name = "auth-token", required=true) String token) {

        if (token == null || token.isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        User foundUser = userService.findUserById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(favoriteService.findByUser(foundUser));
    }

    @PutMapping("/favorite")
    public ResponseEntity<Favorite> updateFavorite(@RequestBody NewFavoriteRequest req, @RequestHeader(name = "auth-token", required=true) String token) {

        if (token == null || token.isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        User foundUser = userService.findUserById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(favoriteService.update(req, foundUser));
    }

}
