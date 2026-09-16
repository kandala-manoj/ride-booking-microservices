package com.alpha.bookingservice.exception;

public class BookingNotAvailableException extends RuntimeException {

    public BookingNotAvailableException(String message) {
        super(message);
    }
}