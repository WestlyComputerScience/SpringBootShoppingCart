package com.johnteacher.shoppingcart.security.jwt;

import com.johnteacher.shoppingcart.security.user.ShopUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final ShopUserDetailsService shopUserDetailsService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)) { // text is not empty and validated
                String username = jwtUtils.getUsernameFromToken(jwt); // extract username ("email")
                UserDetails userDetails = shopUserDetailsService.loadUserByUsername(username); // get our userDetailService to load this user and check database using username
                Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // authenticate user using password and username
                // now auth is the row of our user
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage() + " : Invalid or expired token, you may login and try again");
            return;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(e.getMessage());
            return;
        }
        filterChain.doFilter(request, response); // pass in the request and response
    }

    private String parseJwt(HttpServletRequest request) {
        // capture request from client, request from header and
        // check the header for the headerAuth, if any string that starts with Bearer, then extract Bearer,
        // remove Bearer and grab the rest of the token
        String headerAuthorization = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuthorization) && headerAuthorization.startsWith("Bearer ")) {
            return headerAuthorization.substring(7);
        }
        return null;
    }

}
