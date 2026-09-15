package com.alpha.customerservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alpha.customerservice.dto.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseStructure<String> customerNotFound() {

        ResponseStructure<String> rs =
                new ResponseStructure<>();

        rs.setStatuscode(
                HttpStatus.NOT_FOUND.value()
        );

        rs.setMessage("Customer Not Found");

        rs.setData("No Customer Found With Given ID");

        return rs;
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseStructure<String> emailAlreadyExists() {

        ResponseStructure<String> rs =
                new ResponseStructure<>();

        rs.setStatuscode(
                HttpStatus.CONFLICT.value()
        );

        rs.setMessage("Email Already Exists");

        rs.setData("Please Use Another Email");

        return rs;
    }

    @ExceptionHandler(PhoneAlreadyExistsException.class)
    public ResponseStructure<String> phoneAlreadyExists() {

        ResponseStructure<String> rs =
                new ResponseStructure<>();

        rs.setStatuscode(
                HttpStatus.CONFLICT.value()
        );

        rs.setMessage("Phone Number Already Exists");

        rs.setData("Please Use Another Phone Number");

        return rs;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseStructure<String>
    handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {

        ResponseStructure<String> rs =
                new ResponseStructure<>();

        rs.setStatuscode(
                HttpStatus.BAD_REQUEST.value()
        );

        rs.setMessage("Invalid Data");

        String errorMessage = exception
                .getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        rs.setData(errorMessage);

        return rs;
    }
}