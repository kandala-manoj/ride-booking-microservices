package com.alpha.riderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.riderservice.dto.ResponseStructure;
import com.alpha.riderservice.dto.RiderRegisterDto;
import com.alpha.riderservice.entity.Rider;
import com.alpha.riderservice.service.RiderService;

import jakarta.validation.Valid;

@RestController
public class RiderController {

    @Autowired
    private RiderService riderservice;


    // ===============================
    // REGISTER RIDER
    // ===============================

    @PostMapping("/rider/registerrider")
    public ResponseStructure<Rider> registerRider(
            @RequestBody @Valid RiderRegisterDto dto) {

        return riderservice.registerNewRider(dto);
    }


    // ===============================
    // GET RIDER BY ID
    // ===============================

    @GetMapping("/rider/getrider")
    public ResponseStructure<Rider> getRiderById(
            @RequestParam int id) {

        return riderservice.getRiderById(id);
    }


    // ===============================
    // DELETE RIDER BY ID
    // ===============================

    @DeleteMapping("/rider/deleterider")
    public ResponseStructure<String> deleteRiderById(
            @RequestParam int id) {

        return riderservice.deleteRiderById(id);
    }


    // ===============================
    // GET RIDE REQUESTS FOR RIDER
    // ===============================

    @GetMapping("/ride-requests/{riderId}")
    public List<String> getRideRequests(
            @PathVariable int riderId) {

        return riderservice.getRideRequestsByRider(riderId);
    }
}