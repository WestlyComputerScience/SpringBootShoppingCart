package com.johnteacher.shoppingcart.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// when a request issued, this class receives the request first
// the AuthenticationEntryPoint interface is triggered when a user tries to access a protected endpoint without valid authentication

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    // when authentication fails (missing/invalid JWT), Spring Security calls this method
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // set response type + status
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        // body.put("status", HttpServletResponse.SC_UNAUTHORIZED); // build the json body
        body.put("error", "Unauthorized");
        body.put("message", "You may login and try again!");
        // body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper(); // write json to response
        mapper.writeValue(response.getOutputStream(), body);
    }
}
