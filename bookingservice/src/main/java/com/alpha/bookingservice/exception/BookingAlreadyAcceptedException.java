package com.alpha.bookingservice.exception;

public class BookingAlreadyAcceptedException extends RuntimeException {

    public BookingAlreadyAcceptedException(String message) {
        super(message);
    }
}