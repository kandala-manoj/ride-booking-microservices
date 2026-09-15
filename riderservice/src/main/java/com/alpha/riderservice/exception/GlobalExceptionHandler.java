package com.alpha.riderservice.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alpha.riderservice.dto.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {


    // ===============================
    // RIDER NOT FOUND
    // ===============================

    @ExceptionHandler(RiderNotFoundException.class)
    public ResponseStructure<String> riderNotFound(
            RiderNotFoundException exception) {

        ResponseStructure<String> rs = new ResponseStructure<>();

        rs.setStatuscode(HttpStatus.NOT_FOUND.value());
        rs.setMessage("Rider Not Found");
        rs.setData("No rider found with the given ID");

        return rs;
    }


    // ===============================
    // VALIDATION ERROR
    // ===============================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseStructure<List<String>> validationException(
            MethodArgumentNotValidException exception) {

        List<String> errors = new ArrayList<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.add(error.getField() + ": " + error.getDefaultMessage())
                );

        ResponseStructure<List<String>> rs = new ResponseStructure<>();

        rs.setStatuscode(HttpStatus.BAD_REQUEST.value());
        rs.setMessage("Invalid Rider Data");
        rs.setData(errors);

        return rs;
    }
}