package com.alpha.riderservice.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alpha.riderservice.dto.ResponseStructure;
import com.alpha.riderservice.dto.RiderRegisterDto;
import com.alpha.riderservice.dto.VehicleDto;
import com.alpha.riderservice.entity.Rider;
import com.alpha.riderservice.entity.Vehicle;
import com.alpha.riderservice.exception.EmailAlreadyExistsException;
import com.alpha.riderservice.exception.LicenseAlreadyExistsException;
import com.alpha.riderservice.exception.PhoneAlreadyExistsException;
import com.alpha.riderservice.exception.RiderNotFoundException;
import com.alpha.riderservice.repository.RiderRepository;

@Service
public class RiderService {

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private RestTemplate restTemplate;


    // =========================================================
    // REGISTER RIDER
    // =========================================================

    public ResponseStructure<Rider> registerNewRider(
            RiderRegisterDto dto) {

        // Check email
        if (riderRepository.findByEmail(
                dto.getEmail()) != null) {

            throw new EmailAlreadyExistsException();
        }

        // Check phone
        if (riderRepository.findByPhone(
                dto.getPhone()) != null) {

            throw new PhoneAlreadyExistsException();
        }

        // Check driving license
        if (riderRepository.findByDrivingLicenseNumber(
                dto.getDrivingLicenseNumber()) != null) {

            throw new LicenseAlreadyExistsException();
        }


        // Create Rider
        Rider rider = new Rider();

        rider.setName(dto.getName());

        rider.setPhone(dto.getPhone());

        rider.setEmail(dto.getEmail());

        /*
         * These values are controlled by backend.
         */
        rider.setWalletBalance(0.0);

        rider.setDrivingLicenseNumber(
                dto.getDrivingLicenseNumber()
        );

        /*
         * Newly registered rider is offline.
         */
        rider.setStatus("OFFLINE");

        /*
         * New rider has no rating yet.
         */
        rider.setAverageRating(0.0);

        rider.setCreatedAt(
                LocalDateTime.now()
        );

        rider.setUpdatedAt(
                LocalDateTime.now()
        );


        // =====================================================
        // CREATE VEHICLE
        // =====================================================

        VehicleDto vehicleDto =
                dto.getVehicle();

        Vehicle vehicle = new Vehicle();

        vehicle.setVehicleNumber(
                vehicleDto.getVehicleNumber()
        );

        vehicle.setVehicleType(
                vehicleDto.getVehicleType()
        );

        vehicle.setVehicleBrand(
                vehicleDto.getVehicleBrand()
        );

        vehicle.setVehicleModel(
                vehicleDto.getVehicleModel()
        );

        vehicle.setVehicleColor(
                vehicleDto.getVehicleColor()
        );

        vehicle.setVehicleCapacity(
                vehicleDto.getVehicleCapacity()
        );

        vehicle.setRegistrationNumber(
                vehicleDto.getRegistrationNumber()
        );

        vehicle.setInsuranceNumber(
                vehicleDto.getInsuranceNumber()
        );

        vehicle.setPollutionCertificate(
                vehicleDto.getPollutionCertificate()
        );


        // =====================================================
        // CONNECT RIDER AND VEHICLE
        // =====================================================

        rider.setVehicle(vehicle);

        vehicle.setRider(rider);


        // =====================================================
        // SAVE
        // =====================================================

        /*
         * Because Rider has:
         *
         * cascade = CascadeType.ALL
         *
         * Vehicle will also be saved.
         */
        Rider savedRider =
                riderRepository.save(rider);


        // =====================================================
        // RESPONSE
        // =====================================================

        ResponseStructure<Rider> rs =
                new ResponseStructure<>();

        rs.setStatuscode(
                HttpStatus.CREATED.value()
        );

        rs.setMessage(
                "Rider Registered Successfully"
        );

        rs.setData(savedRider);

        return rs;
    }


    // =========================================================
    // GET RIDER BY ID
    // =========================================================

    public ResponseStructure<Rider> getRiderById(
            int id) {

        Rider rider =
                riderRepository.findById(id)
                        .orElseThrow(
                                RiderNotFoundException::new
                        );


        ResponseStructure<Rider> rs =
                new ResponseStructure<>();

        rs.setStatuscode(
                HttpStatus.OK.value()
        );

        rs.setMessage(
                "Rider Found"
        );

        rs.setData(rider);

        return rs;
    }


    // =========================================================
    // DEACTIVATE RIDER
    // =========================================================

    public ResponseStructure<String> deleteRiderById(
            int id) {

        Rider rider =
                riderRepository.findById(id)
                        .orElseThrow(
                                RiderNotFoundException::new
                        );


        /*
         * We are not physically deleting the rider.
         *
         * Instead, we mark the rider as INACTIVE.
         */
        rider.setStatus("INACTIVE");

        rider.setUpdatedAt(
                LocalDateTime.now()
        );

        riderRepository.save(rider);


        ResponseStructure<String> rs =
                new ResponseStructure<>();

        rs.setStatuscode(
                HttpStatus.OK.value()
        );

        rs.setMessage(
                "Rider Deactivated Successfully"
        );

        rs.setData("INACTIVE");

        return rs;
    }


    // =========================================================
    // GO ONLINE
    // =========================================================

    public ResponseStructure<String> goOnline(
            int id) {

        Rider rider =
                riderRepository.findById(id)
                        .orElseThrow(
                                RiderNotFoundException::new
                        );


        /*
         * Inactive rider cannot become available.
         */
        if ("INACTIVE".equals(
                rider.getStatus())) {

            ResponseStructure<String> rs =
                    new ResponseStructure<>();

            rs.setStatuscode(
                    HttpStatus.BAD_REQUEST.value()
            );

            rs.setMessage(
                    "Inactive Rider Cannot Go Online"
            );

            rs.setData("INACTIVE");

            return rs;
        }


        /*
         * Rider becomes available.
         */
        rider.setStatus("AVAILABLE");

        rider.setUpdatedAt(
                LocalDateTime.now()
        );

        riderRepository.save(rider);


        ResponseStructure<String> rs =
                new ResponseStructure<>();

        rs.setStatuscode(
                HttpStatus.OK.value()
        );

        rs.setMessage(
                "Rider Is Now Available"
        );

        rs.setData("AVAILABLE");

        return rs;
    }


    // =========================================================
    // GO OFFLINE
    // =========================================================

    public ResponseStructure<String> goOffline(
            int id) {

        Rider rider =
                riderRepository.findById(id)
                        .orElseThrow(
                                RiderNotFoundException::new
                        );


        /*
         * Rider becomes offline.
         */
        rider.setStatus("OFFLINE");

        rider.setUpdatedAt(
                LocalDateTime.now()
        );

        riderRepository.save(rider);


        ResponseStructure<String> rs =
                new ResponseStructure<>();

        rs.setStatuscode(
                HttpStatus.OK.value()
        );

        rs.setMessage(
                "Rider Is Now Offline"
        );

        rs.setData("OFFLINE");

        return rs;
    }


    // =========================================================
    // GET RIDE REQUESTS
    // =========================================================

    public List<String> getRideRequestsByRider(
            int riderId) {

        /*
         * Rider Service calls Booking Service
         * using REST API.
         */

    	String url =
    	        "http://localhost:8084/booking/ride-requests/"
    	        + riderId;


        String[] requests =
                restTemplate.getForObject(
                        url,
                        String[].class
                );


        if (requests == null) {
            return List.of();
        }


        return Arrays.asList(requests);
    }
}