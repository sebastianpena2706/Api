package com.credibaco.assessment.card.api.client.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> NoSuchElementException(NullPointerException e) {
        // Aquí puedes realizar el manejo de la excepción, por ejemplo, retornar un mensaje de error
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Se ha producido una NoSuchElementException");
    }
}