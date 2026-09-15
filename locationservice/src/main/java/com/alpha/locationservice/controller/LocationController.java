package com.alpha.locationservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alpha.locationservice.Dto.FareEstimateRequestDto;
import com.alpha.locationservice.Dto.FareEstimateResponseDto;
import com.alpha.locationservice.Dto.LocationResDto;
import com.alpha.locationservice.Dto.ResponseStructure;
import com.alpha.locationservice.Dto.RiderLocationRequest;
import com.alpha.locationservice.service.LocationService;


@RestController
public class LocationController {
	@Autowired
	private LocationService locationService;
	
	@GetMapping("/location/finddestinationlocation")
	public ResponseStructure<List<LocationResDto>> findDestinationLocation(@RequestParam String skey) {
		return locationService.findDestinationLocation(skey);
		
	}
	
	@PostMapping("/location/fare-estimate")
	public ResponseStructure<FareEstimateResponseDto> estimateFare(
	        @RequestBody FareEstimateRequestDto dto) {

	    return locationService.estimateFare(dto);
	}
	
	
	@PostMapping("/location/update")
	public String updateLocation(@RequestBody RiderLocationRequest request) {

	    locationService.updateLocation(request);

	    return "Rider location updated successfully";
	}
	
	@GetMapping("/location/nearby-riders")
	public List<String> findNearbyRiders(
	        @RequestParam double latitude,
	        @RequestParam double longitude,
	        @RequestParam String vehicleType) {

	    return locationService.findNearbyRiders(
	            latitude,
	            longitude,
	            vehicleType
	    );
	}}
	
	
	
	
	

   
