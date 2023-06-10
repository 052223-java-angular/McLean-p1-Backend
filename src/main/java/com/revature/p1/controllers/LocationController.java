package com.revature.p1.controllers;

import com.revature.p1.dtos.requests.NewLocationRequest;
import com.revature.p1.dtos.responses.Principal;
import com.revature.p1.entities.Location;
import com.revature.p1.entities.Role;
import com.revature.p1.entities.User;
import com.revature.p1.services.JwtTokenService;
import com.revature.p1.services.LocationService;
import com.revature.p1.services.RoleService;
import com.revature.p1.utils.custom_exceptions.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locService;
    private final JwtTokenService tokenService;
    private final RoleService roleService;

    public LocationController(LocationService locService, JwtTokenService tokenService, RoleService roleService) {
        this.locService = locService;
        this.tokenService = tokenService;
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLocation(@RequestBody NewLocationRequest req, @RequestHeader(name = "Authorization", required=true) String token) {

        System.out.println(token);
        //printing null name
        System.out.println(req.getName());
        //need to check token
        //---a token is valid when:
        //------1 - of type jwt
        //------2 - its signature is correct(nobody has changed a content of token)
        //------3 - its not expired   <<<<have not checked for expiration yet
        //------4 - it contains roles and scopes information
        String userId = tokenService.extractUserId(token);
        String username = tokenService.extractUsername(token);
        String role = tokenService.extractUserRole(token);
        Role fullRole = roleService.findByName(role);
        User validUser = new User(fullRole, userId, username);

        Principal testValidity = new Principal(validUser);

        if(!tokenService.validateToken(token, testValidity)) {
            //add to exception controller
            throw new AccessDeniedException("Access denied.");
        }

        //Location newLoc =
        locService.save(req, validUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/read")
    public ResponseEntity<List<Location>> getLocation(@RequestHeader(name="Authorization") String token) {
        String username = tokenService.extractUsername(token);
        Principal testValidity = new Principal();
        testValidity.setUsername(username);
        if(!tokenService.validateToken(token, testValidity)) {
            //add to exception controller
            throw new AccessDeniedException("Access denied.");
        }
        String userId = tokenService.extractUserId(token);
        User existingUser = new User(userId);
        List<Location> retrievedLocs = locService.findByUser(existingUser);
        return ResponseEntity.status(HttpStatus.OK).body(retrievedLocs);
    }

    //need to add one for update after auto set home loc
    //@PathVariable("urlParameter") String urlParameter

}
