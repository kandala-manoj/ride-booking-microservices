package com.alpha.bookingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alpha.bookingservice.dto.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleBookingNotFound(
            BookingNotFoundException ex) {

        ResponseStructure<String> response =
                new ResponseStructure<>();

        response.setStatuscode(404);
        response.setMessage(ex.getMessage());
        response.setData(null);

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BookingAlreadyAcceptedException.class)
    public ResponseEntity<ResponseStructure<String>> handleAlreadyAccepted(
            BookingAlreadyAcceptedException ex) {

        ResponseStructure<String> response =
                new ResponseStructure<>();

        response.setStatuscode(409);
        response.setMessage(ex.getMessage());
        response.setData(null);

        return new ResponseEntity<>(
                response,
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(BookingNotAvailableException.class)
    public ResponseEntity<ResponseStructure<String>> handleNotAvailable(
            BookingNotAvailableException ex) {

        ResponseStructure<String> response =
                new ResponseStructure<>();

        response.setStatuscode(400);
        response.setMessage(ex.getMessage());
        response.setData(null);

        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(LocationServiceException.class)
    public ResponseEntity<ResponseStructure<String>> handleLocationService(
            LocationServiceException ex) {

        ResponseStructure<String> response =
                new ResponseStructure<>();

        response.setStatuscode(503);
        response.setMessage(ex.getMessage());
        response.setData(null);

        return new ResponseEntity<>(
                response,
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStructure<String>> handleOtherExceptions(
            Exception ex) {

        ResponseStructure<String> response =
                new ResponseStructure<>();

        response.setStatuscode(500);
        response.setMessage("Something went wrong");
        response.setData(null);

        return new ResponseEntity<>(
                response,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}