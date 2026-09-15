package com.alpha.locationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alpha.locationservice.Dto.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidSearchKeyException.class)
    public ResponseStructure<String> invalidSearchKeyException() {

        ResponseStructure<String> rs =
                new ResponseStructure<>();

        rs.setStatuscode(HttpStatus.BAD_REQUEST.value());
        rs.setMessage("Invalid Search Key");
        rs.setData(
                "Search key must contain at least 3 characters."
        );

        return rs;
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseStructure<String> locationNotFoundException() {

        ResponseStructure<String> rs =
                new ResponseStructure<>();

        rs.setStatuscode(HttpStatus.NOT_FOUND.value());
        rs.setMessage("Location Not Found");
        rs.setData("No matching location found.");

        return rs;
    }
}