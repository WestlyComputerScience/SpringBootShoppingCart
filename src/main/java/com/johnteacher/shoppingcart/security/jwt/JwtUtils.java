package com.johnteacher.shoppingcart.security.jwt;

/*
Notes: JWT stands for JSON Web Token. It's a compact, URL-safe way to represent data between 2 parties. Commonly
       used for authentication and authorization in web apps.
 - core idea:
 - - Instead of storing session data on the server, you give the client a signed token that proves who they are
 - - user logs in, sever creates a JWT
 - - Client stores it (usually in local storage or cookies)
 - - client sends it with request
 - - server verifies it -> no database lookup needed for session
 - - Looks like: xxxxx.yyyyy.zzzzz

 */

import com.johnteacher.shoppingcart.security.user.ShopUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {
    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.token.expirationInMils}")
    private int expirationTime;

    public String generateTokenForUser(Authentication authentication) {
        ShopUserDetails userPrinciple = (ShopUserDetails) authentication.getPrincipal();

        List<String> roles = userPrinciple.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        // any info you want from user is grabbed with .claim()
        return Jwts.builder().setSubject(userPrinciple.getEmail()).claim("id", userPrinciple.getId())
                .claim("roles", roles).setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime() + expirationTime))
                .signWith(key(), SignatureAlgorithm.HS256).compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameFromToken(String token) { // extract username from token
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) throws JwtException {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new JwtException(e.getMessage());
        }
    }

}
