package org.example.coremicroservice.exception.handler;

import org.example.coremicroservice.dto.response.ErrorResponseDto;
import org.example.coremicroservice.exception.UserCredentialNotFoundException;
import org.example.coremicroservice.exception.ValidationException;
import org.example.coremicroservice.exception.constant.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    ResponseEntity<ErrorResponseDto> exceptionHandler(UserCredentialNotFoundException e) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.FORBIDDEN);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
    ResponseEntity<ErrorResponseDto> exceptionHandler(NoHandlerFoundException e) {
        ErrorResponseDto response = new ErrorResponseDto(ErrorMessage.NOT_FOUND.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDto> validationHandler(ValidationException e) {
        return new ResponseEntity<>(new ErrorResponseDto(e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

}