package org.dbclient.server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> sss(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return new ResponseEntity<>("ups its wrong", HttpStatus.BAD_REQUEST);
    }

}
