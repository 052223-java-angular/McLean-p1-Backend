package com.revature.p1.services;

import com.revature.p1.dtos.responses.Principal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
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

    //Claims type imported from io.jsonwebtoken.Claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    //Function imported from java.util.function.Function
    //method relies on resolution of the above method
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //method relies on resolution of the above method
    //:: is called Method Reference.  It is basically a reference to a single method (it refers to an existing method by name)
    //getSubject is getting in Claims
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //method relies on resolution of the above method - required to validate JWT token against the user principal
    //why does it take in Principal?????
    public boolean validateToken(String token, Principal userPrincipal) {
        String tokenUsername = extractUsername(token);
        return tokenUsername.equals(userPrincipal.getUsername());
    }

    //seems to be casting returned Claim from extractAllClaims method to String
    //using from Claims -> <T> T get(String var1, Class<T> var2)
    public String extractUserId(String token) {
        return (String) extractAllClaims(token).get("id");
    }

    public String extractUserRole(String token) {
        return (String) extractAllClaims(token).get("role");
    }

}
