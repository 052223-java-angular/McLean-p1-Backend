package com.revature.p1.controllers;

import com.revature.p1.dtos.requests.NewFavoriteRequest;
import com.revature.p1.dtos.responses.Principal;
import com.revature.p1.entities.Favorite;
import com.revature.p1.entities.User;
import com.revature.p1.services.FavoriteService;
import com.revature.p1.services.JwtTokenService;
import com.revature.p1.utils.custom_exceptions.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final JwtTokenService tokenService;

    public FavoriteController(FavoriteService favoriteService, JwtTokenService tokenService) {
        this.favoriteService = favoriteService;
        this.tokenService = tokenService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createFavorite(@RequestBody NewFavoriteRequest req, @RequestHeader(name="auth-token", required=true) String token) {
        User existingUser = tokenValidator(token);

        //depends on if I want to return the saved record Favorite fav =
        favoriteService.save(req, existingUser);

        String responseHeaderKey = "auth-token";
        String responseHeaderValue = token;
        return ResponseEntity.status(HttpStatus.CREATED).header(responseHeaderKey, responseHeaderValue).build();
    }

    @GetMapping("/read")
    public ResponseEntity<Favorite> readFavorite(@RequestHeader(name="auth-token", required=true) String token) {
        User existingUser = tokenValidator(token);

        //returning favorite id, should not happen
        Favorite retrievedFav = favoriteService.findByUser(existingUser);

        String responseHeaderKey = "auth-token";
        String responseHeaderValue = token;
        return ResponseEntity.status(HttpStatus.OK).header(responseHeaderKey, responseHeaderValue).body(retrievedFav);
    }

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
