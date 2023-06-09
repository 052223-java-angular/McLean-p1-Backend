package com.revature.p1.controllers;

import com.revature.p1.dtos.requests.JwtValidator;
import com.revature.p1.dtos.requests.NewLocationRequest;
import com.revature.p1.dtos.responses.Principal;
import com.revature.p1.entities.Location;
import com.revature.p1.entities.Role;
import com.revature.p1.entities.User;
import com.revature.p1.services.JwtTokenService;
import com.revature.p1.services.LocationService;
import com.revature.p1.services.RoleService;
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

//      USE @REQUESTHEADER(NAME = "AUTHORIZATION") string token) AS A PARAMETER TO THESE METHODS TO CHECK ID OF LOGGED IN USER
//      TOKEN SERVICE WILL HAVE METHODS TO EXTRACT USER ID OR OTHER INFO TO ALLOW PROCESSING OF REQUESTS

    @PostMapping("/create")
    public ResponseEntity<?> createLocation(@RequestBody NewLocationRequest req, @RequestHeader(name = "authorization") String token) {
        //need to check token
        //---a token is valid when:
        //------1 - of type jwt
        //------2 - its signature is correct(nobody has changed a content of token)
        //------3 - its not expired
        //------4 - it contains roles and scopes information

        //probably add to helper function
        String userId = tokenService.extractUserId(token);
        String username = tokenService.extractUsername(token);
        String role = tokenService.extractUserRole(token);
        Role fullRole = roleService.findByName(role);
        JwtValidator testValidity = new JwtValidator(userId, username, fullRole);
        if(!tokenService.validateToken(token, testValidity)) {
            //throw some exception
        }

        //must validate user identity before processing service request
        //location needs name(home-auto), long, lat, User
        Location newLoc = locService.save(req, User);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<List<Location>> getLocation(@RequestBody GetLocationsById req) {
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }

}
