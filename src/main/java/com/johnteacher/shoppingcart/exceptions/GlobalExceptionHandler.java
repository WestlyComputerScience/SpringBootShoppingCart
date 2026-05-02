package com.johnteacher.shoppingcart.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // for any request going to any controller, the request will pass here, check if that controller is authenticated or not.
                  // aka, advices the controller before it does anything
public class GlobalExceptionHandler {

    // General note: all exceptions can be defined and handled here then!

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        String message = "You don't have permission to access this resource";
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }
}
