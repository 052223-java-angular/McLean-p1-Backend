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

@CrossOrigin
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

    @PostMapping("/create")
    public ResponseEntity<?> createFavorite(@RequestBody NewFavoriteRequest req) {

        if (req.getToken() == null || req.getToken().isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        String token = req.getToken();

        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        User foundUser = userService.findUserById(userId);

        favoriteService.save(req, foundUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/read")
    public ResponseEntity<Favorite> readFavorite(@RequestBody NewFavoriteRequest req) {

        if (req.getToken() == null || req.getToken().isEmpty()) {
            throw new AccessDeniedException("No token provided!");
        }

        String token = req.getToken();

        if (tokenService.extractUserId(token) == null || tokenService.extractUserId(token).isEmpty()) {
            throw new AccessDeniedException("Invalid token!");
        }

        String userId = tokenService.extractUserId(token);
        User foundUser = userService.findUserById(userId);

        Favorite retrievedFav = favoriteService.findByUser(foundUser);

        return ResponseEntity.status(HttpStatus.OK).body(retrievedFav);
    }

}
