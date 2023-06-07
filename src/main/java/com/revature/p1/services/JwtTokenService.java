package com.revature.p1.services;

import com.revature.p1.dtos.responses.Principal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenService {

    //go to application.properties file for value
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    //for return - principal
    public String generateToken(Principal userPrincipal) {
        //'claiming an identity'
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userPrincipal.getId());
        claims.put("role", userPrincipal.getRole());

        //imported using io.jsonwebtoken.Jwts
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //sets expiration for 10 hours from current time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                //imported using io.jsonwebtoken.SignatureAlgorithm;
                //check oracle docs for info on signature algorithm
                //The signature algorithm can be, among others, the NIST standard DSA, using DSA and SHA-256.
                //makes signature unique - secret key
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

}
