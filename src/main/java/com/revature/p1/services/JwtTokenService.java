package com.revature.p1.services;

import com.revature.p1.dtos.responses.Principal;
import com.revature.p1.entities.Role;
import com.revature.p1.entities.User;
import com.revature.p1.repositories.UserRepository;
import com.revature.p1.utils.custom_exceptions.AccessDeniedException;
import com.revature.p1.utils.custom_exceptions.RoleNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

/*
In order to validate a JWT, we should check some registered claims such as:
Issuer claim (identifies the principal that issued the JWT.  The processing of this claim is generally application
specific.  The 'iss' value is a case-sensitive string containing a URI value.)
Subject claim (identifies the principal that is the subject of the JWT.  The claims in a JWT are normally statements
about the subject.)
Expiration Time claim (identifies the expiration time on or after which the JWT MUST NOT be accepted for processing)

Must important part of JWT validation is the signature.  As we have already seen that signature is generated using
payload and a secret key, anyone who is in possession of this key can generate new tokens with valid signatures.
You have to be sure that the data in that payload is legitimate and can be trusted.
---Most commonly used crupto algorithms used for generating signature are HS256 (short for HMAC-SHA256)
---And signing algorithm (short for RSA-SHA256)
 */

@Service
public class JwtTokenService {

    private UserRepository userRepo;

    public JwtTokenService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

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
        //jwts = JSON web key sets
        //BASE64URL

        //jwts.builder() returns a jwtsBuilder instance
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
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
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

    //method relies on resolution of the above method - required to validate JWT token
    public boolean validateToken(String token, Principal testValidity) {
        String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(testValidity.getUsername())); //&& !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
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
