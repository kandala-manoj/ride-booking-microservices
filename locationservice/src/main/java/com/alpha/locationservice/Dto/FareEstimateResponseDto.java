package com.alpha.locationservice.Dto;

import java.util.List;

public class FareEstimateResponseDto {

    private String pickupAddress;
    private String dropAddress;

    private double distance;
    private int estimatedTime;

    private List<VehicleFareDto> vehicleOptions;

    public FareEstimateResponseDto() {
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getDropAddress() {
        return dropAddress;
    }

    public void setDropAddress(String dropAddress) {
        this.dropAddress = dropAddress;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public List<VehicleFareDto> getVehicleOptions() {
        return vehicleOptions;
    }

    public void setVehicleOptions(List<VehicleFareDto> vehicleOptions) {
        this.vehicleOptions = vehicleOptions;
    }
}