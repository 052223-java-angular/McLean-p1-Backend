package com.revature.p1.controllers;

import com.revature.p1.dtos.requests.NewLocationRequest;
import com.revature.p1.entities.Location;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {
    //dependency injection

//      ----------http_servlet------------
//      USE @REQUESTHEADER(NAME = "AUTHORIZATION") string token) AS A PARAMETER TO THESE METHODS TO CHECK ID OF LOGGED IN USER
//      TOKEN SERVICE WILL HAVE METHODS TO EXTRACT USER ID OR OTHER INFO TO ALLOW PROCESSING OF REQUESTS
//      -----probably want to leave out jwt user_id extraction until later----
    @PostMapping("/create")
    public ResponseEntity<?> createLocation(@RequestBody NewLocationRequest req, @RequestHeader(name = "authorization") String token) {
        //need to check token
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<List<Location>> getLocation(@RequestBody GetLocationsById req) {
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }

}
