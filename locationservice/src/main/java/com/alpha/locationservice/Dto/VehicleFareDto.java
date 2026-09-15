package com.alpha.locationservice.Dto;

public class VehicleFareDto {

    private String vehicleType;

    private double baseFare;
    private double perKmCharge;
    private double estimatedFare;

    public VehicleFareDto() {
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }

    public double getPerKmCharge() {
        return perKmCharge;
    }

    public void setPerKmCharge(double perKmCharge) {
        this.perKmCharge = perKmCharge;
    }

    public double getEstimatedFare() {
        return estimatedFare;
    }

    public void setEstimatedFare(double estimatedFare) {
        this.estimatedFare = estimatedFare;
    }
}