package com.alpha.bookingservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.bookingservice.dto.ContinueBookingdto;
import com.alpha.bookingservice.dto.ResponseStructure;
import com.alpha.bookingservice.entity.Booking;
import com.alpha.bookingservice.service.BookingService;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/booking/continuebooking")
    public ResponseStructure<Booking> continueBooking(
            @RequestBody ContinueBookingdto bookingdto) {

        return bookingService.continueBooking(bookingdto);
    }

    @GetMapping("/booking/ride-requests/{riderId}")
    public List<String> getRideRequests(
            @PathVariable int riderId) {

        return bookingService.getRideRequestsByRider(riderId);
    }

    @PostMapping("/booking/accept/{bookingId}/{riderId}")
    public String acceptBooking(
            @PathVariable int bookingId,
            @PathVariable int riderId) {
        return bookingService.acceptBooking(bookingId, riderId);
    }

    @PostMapping("/booking/start/{bookingId}/{riderId}")
    public String startRide(
            @PathVariable int bookingId,
            @PathVariable int riderId) {
        return bookingService.startRide(bookingId, riderId);
    }
    
    @PostMapping("/booking/complete/{bookingId}/{riderId}")
    public String completeRide(
            @PathVariable int bookingId,
            @PathVariable int riderId) {

        return bookingService.completeRide(bookingId, riderId);
    }
    
    
}