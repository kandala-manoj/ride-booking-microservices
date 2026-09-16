package com.alpha.bookingservice.service;

import java.time.LocalDateTime;
import com.alpha.bookingservice.dto.PaymentRequestDto;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.alpha.bookingservice.dto.ContinueBookingdto;
import com.alpha.bookingservice.dto.ResponseStructure;
import com.alpha.bookingservice.entity.Booking;
import com.alpha.bookingservice.exception.BookingAlreadyAcceptedException;
import com.alpha.bookingservice.exception.BookingNotAvailableException;
import com.alpha.bookingservice.exception.BookingNotFoundException;
import com.alpha.bookingservice.exception.LocationServiceException;
import com.alpha.bookingservice.repository.BookingRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;


    // =========================================================
    // CREATE BOOKING
    // =========================================================

    public ResponseStructure<Booking> continueBooking(
            ContinueBookingdto bookingdto) {

        // -----------------------------------------------------
        // 1. CREATE BOOKING
        // -----------------------------------------------------

        Booking booking = new Booking();

        booking.setCustomer_id(0);
        booking.setRider_id(0);

        booking.setPickupLatitude(
                bookingdto.getPickupLatitude()
        );

        booking.setPickupLongitude(
                bookingdto.getPickupLongitude()
        );

        booking.setPickupAddress(
                bookingdto.getPickupAddress()
        );

        booking.setDropLatitude(
                bookingdto.getDropLatitude()
        );

        booking.setDropLongitude(
                bookingdto.getDropLongitude()
        );

        booking.setDropAddress(
                bookingdto.getDropAddress()
        );

        booking.setDistance(
                bookingdto.getDistance()
        );

        booking.setFare(
                bookingdto.getEstimatedFare()
        );

        booking.setBookingStatus("CREATED");

        booking.setOtp(
                (int) (Math.random() * 9000) + 1000
        );

        booking.setCreatedAt(
                LocalDateTime.now()
        );


        // -----------------------------------------------------
        // 2. SAVE BOOKING IN POSTGRESQL
        // -----------------------------------------------------

        booking = bookingRepository.save(booking);
        

        int bookingId =
                booking.getBooking_id();

        System.out.println(
                "Booking created with ID: "
                        + bookingId
        );


        // -----------------------------------------------------
        // 3. STORE BOOKING IN REDIS
        // -----------------------------------------------------

        String bookingKey =
                "BOOKING:" + bookingId;

        redisTemplate.opsForHash().put(
                bookingKey,
                "booking_id",
                String.valueOf(bookingId)
        );

        redisTemplate.opsForHash().put(
                bookingKey,
                "pickupAddress",
                booking.getPickupAddress()
        );

        redisTemplate.opsForHash().put(
                bookingKey,
                "pickupLatitude",
                String.valueOf(
                        booking.getPickupLatitude()
                )
        );

        redisTemplate.opsForHash().put(
                bookingKey,
                "pickupLongitude",
                String.valueOf(
                        booking.getPickupLongitude()
                )
        );

        redisTemplate.opsForHash().put(
                bookingKey,
                "dropAddress",
                booking.getDropAddress()
        );

        redisTemplate.opsForHash().put(
                bookingKey,
                "dropLatitude",
                String.valueOf(
                        booking.getDropLatitude()
                )
        );

        redisTemplate.opsForHash().put(
                bookingKey,
                "dropLongitude",
                String.valueOf(
                        booking.getDropLongitude()
                )
        );

        redisTemplate.opsForHash().put(
                bookingKey,
                "distance",
                String.valueOf(
                        booking.getDistance()
                )
        );

        redisTemplate.opsForHash().put(
                bookingKey,
                "fare",
                String.valueOf(
                        booking.getFare()
                )
        );

        redisTemplate.opsForHash().put(
                bookingKey,
                "otp",
                String.valueOf(
                        booking.getOtp()
                )
        );

        redisTemplate.opsForHash().put(
                bookingKey,
                "bookingStatus",
                "CREATED"
        );

        redisTemplate.opsForHash().put(
                bookingKey,
                "createdAt",
                booking.getCreatedAt().toString()
        );


        System.out.println(
                "Booking details stored in Redis: "
                        + bookingKey
        );


        // -----------------------------------------------------
        // 4. CALL LOCATION SERVICE
        // -----------------------------------------------------

        String url =
                "http://localhost:8083/location/nearby-riders"
                + "?latitude="
                + bookingdto.getPickupLatitude()
                + "&longitude="
                + bookingdto.getPickupLongitude()
                + "&vehicleType="
                + bookingdto.getVehicleType();


        System.out.println(
                "Calling Location Service: "
                        + url
        );


        // -----------------------------------------------------
        // 5. GET NEARBY RIDERS
        // -----------------------------------------------------

        String[] nearbyRiders;

        try {

            nearbyRiders =
                    restTemplate.getForObject(
                            url,
                            String[].class
                    );

        } catch (RestClientException e) {

            throw new LocationServiceException(
                    "Location Service is not available",
                    e
            );
        }


        List<String> riders =
                nearbyRiders != null
                        ? Arrays.asList(nearbyRiders)
                        : List.of();


        System.out.println(
                "Nearby Riders: "
                        + riders
        );


        // -----------------------------------------------------
        // 6. STORE BOOKING FOR EACH RIDER
        // -----------------------------------------------------

        for (String rider : riders) {

            String riderId =
                    rider.split("_")[0];

            String riderRequestKey =
                    "RIDER_REQUEST:" + riderId;

            redisTemplate.opsForList().rightPush(
                    riderRequestKey,
                    String.valueOf(bookingId)
            );

            System.out.println(
                    "Booking "
                            + bookingId
                            + " sent to rider "
                            + riderId
            );
        }


        // -----------------------------------------------------
        // 7. STORE RIDERS FOR THIS BOOKING
        // -----------------------------------------------------

        String bookingRidersKey =
                "BOOKING_RIDERS:" + bookingId;

        for (String rider : riders) {

            String riderId =
                    rider.split("_")[0];

            redisTemplate.opsForSet().add(
                    bookingRidersKey,
                    riderId
            );
        }


        // -----------------------------------------------------
        // 8. PRINT INFORMATION
        // -----------------------------------------------------

        System.out.println("--------------------------------");

        System.out.println(
                "Booking ID: "
                        + bookingId
        );

        System.out.println(
                "Nearby Riders: "
                        + riders
        );

        System.out.println(
                "Booking stored for riders."
        );

        System.out.println("--------------------------------");


        // -----------------------------------------------------
        // 9. RETURN RESPONSE
        // -----------------------------------------------------

        ResponseStructure<Booking> response =
                new ResponseStructure<>();

        response.setStatuscode(201);
        response.setMessage(
                "Booking created. Waiting for rider."
        );
        response.setData(booking);

        return response;
    }


    // =========================================================
    // GET RIDE REQUESTS
    // =========================================================

    public List<String> getRideRequestsByRider(
            int riderId) {

        String key =
                "RIDER_REQUEST:" + riderId;

        List<String> requests =
                redisTemplate.opsForList()
                        .range(key, 0, -1);

        return requests != null
                ? requests
                : List.of();
    }


    // =========================================================
    // ACCEPT BOOKING
    // =========================================================

    public String acceptBooking(
            int bookingId,
            int riderId) {

        String bookingRidersKey =
                "BOOKING_RIDERS:" + bookingId;


        // -----------------------------------------------------
        // 1. CHECK RIDER RECEIVED BOOKING
        // -----------------------------------------------------

        Boolean exists =
                redisTemplate.opsForSet()
                        .isMember(
                                bookingRidersKey,
                                String.valueOf(riderId)
                        );

        if (!Boolean.TRUE.equals(exists)) {

            throw new BookingNotAvailableException(
                    "Booking is not available for this rider"
            );
        }


        // -----------------------------------------------------
        // 2. GET BOOKING
        // -----------------------------------------------------

        Booking booking =
                bookingRepository.findById(bookingId)
                        .orElseThrow(() ->
                                new BookingNotFoundException(
                                        "Booking not found with ID: "
                                                + bookingId
                                )
                        );


        // -----------------------------------------------------
        // 3. CHECK BOOKING STATUS
        // -----------------------------------------------------

        if (!"CREATED".equals(
                booking.getBookingStatus())) {

            throw new BookingAlreadyAcceptedException(
                    "Booking already accepted by another rider"
            );
        }


        // -----------------------------------------------------
        // 4. ASSIGN RIDER
        // -----------------------------------------------------

        booking.setRider_id(riderId);

        booking.setBookingStatus(
                "ACCEPTED"
        );

        booking.setAcceptedAt(
                LocalDateTime.now()
        );

        bookingRepository.save(booking);
        
        
        String notificationUrl =
                "http://localhost:8086/notification/send";

        String message =
                "Booking " + bookingId
                + " accepted by rider " + riderId;

        restTemplate.postForObject(
                notificationUrl,
                message,
                String.class
        );


        // -----------------------------------------------------
        // 5. GET ALL RIDERS
        // -----------------------------------------------------

        Set<String> riders =
                redisTemplate.opsForSet()
                        .members(
                                bookingRidersKey
                        );


        // -----------------------------------------------------
        // 6. REMOVE BOOKING FROM OTHER RIDERS
        // -----------------------------------------------------

        if (riders != null) {

            for (String rider : riders) {

                if (rider.equals(
                        String.valueOf(riderId))) {

                    continue;
                }

                String riderRequestKey =
                        "RIDER_REQUEST:" + rider;

                redisTemplate.opsForList()
                        .remove(
                                riderRequestKey,
                                0,
                                String.valueOf(bookingId)
                        );
            }
        }


        // -----------------------------------------------------
        // 7. DELETE TEMPORARY MAPPING
        // -----------------------------------------------------

        redisTemplate.delete(
                bookingRidersKey
        );


        return "Booking "
                + bookingId
                + " accepted by rider "
                + riderId;
    }
    public String startRide(int bookingId, int riderId) {

        // 1. Find booking
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new BookingNotFoundException(
                                "Booking " + bookingId + " not found"));

        // 2. Check rider
        if (booking.getRider_id() != riderId) {
            throw new BookingNotAvailableException(
                    "Booking " + bookingId
                    + " is not assigned to rider " + riderId);
        }

        // 3. Check booking status
        if (!"ACCEPTED".equals(booking.getBookingStatus())) {
            throw new BookingNotAvailableException(
                    "Booking " + bookingId
                    + " cannot be started. Current status: "
                    + booking.getBookingStatus());
        }

        // 4. Change status
        booking.setBookingStatus("STARTED");

        // 5. Set start time
        booking.setStartedAt(LocalDateTime.now());

        // 6. Save booking
        bookingRepository.save(booking);

        // 7. Send notification
        String notificationUrl =
                "http://localhost:8086/notification/send";

        String message =
                "Ride started for booking "
                + bookingId
                + " by rider "
                + riderId;

        restTemplate.postForObject(
                notificationUrl,
                message,
                String.class
        );

        // 8. Return response
        return "Ride started for booking "
                + bookingId
                + " by rider "
                + riderId;
    }
    
    public String startRide1(int bookingId, int riderId) {

        // Find booking
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new BookingNotFoundException(
                                "Booking " + bookingId + " not found"));

        // Check rider
        if (booking.getRider_id() != riderId) {
            throw new BookingNotAvailableException(
                    "Booking " + bookingId
                    + " is not assigned to rider " + riderId);
        }

        // Check booking status
        if (!"ACCEPTED".equals(booking.getBookingStatus())) {
            throw new BookingNotAvailableException(
                    "Booking " + bookingId
                    + " cannot be started. Current status: "
                    + booking.getBookingStatus());
        }

        // Change status to STARTED
        booking.setBookingStatus("STARTED");

        // Set start time
        booking.setStartedAt(LocalDateTime.now());

        // Save booking
        bookingRepository.save(booking);

        return "Ride started for booking "
                + bookingId + " by rider " + riderId;
    }
    
    
    
    
    public String completeRide(int bookingId, int riderId) {

        // Find booking
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new BookingNotFoundException(
                                "Booking " + bookingId + " not found"));

        // Check rider
        if (booking.getRider_id() != riderId) {
            throw new BookingNotAvailableException(
                    "Booking " + bookingId
                    + " is not assigned to rider " + riderId);
        }

        // Check booking status
        if (!"STARTED".equals(booking.getBookingStatus())) {
            throw new BookingNotAvailableException(
                    "Booking " + bookingId
                    + " cannot be completed. Current status: "
                    + booking.getBookingStatus());
        }

        // Change status to COMPLETED
        booking.setBookingStatus("COMPLETED");

        // Set completion time
        booking.setCompletedAt(LocalDateTime.now());

        // Save booking
        bookingRepository.save(booking);
        
        //  payment      
        
        PaymentRequestDto paymentRequest = new PaymentRequestDto();

        paymentRequest.setBookingId(bookingId);
        paymentRequest.setRiderId(riderId);
        paymentRequest.setAmount(booking.getFare());
        paymentRequest.setPaymentMethod("UPI");

        String paymentUrl =
                "http://localhost:8087/payment/pay";

        restTemplate.postForObject(
                paymentUrl,
                paymentRequest,
                Object.class
        );
        
        
     // Send notification
        String notificationUrl =
                "http://localhost:8086/notification/send";

        String message =
                "Ride completed for booking "
                + bookingId
                + " by rider "
                + riderId;

        restTemplate.postForObject(
                notificationUrl,
                message,
                String.class
        );

        return "Ride completed for booking "
                + bookingId + " by rider " + riderId;
    }
}