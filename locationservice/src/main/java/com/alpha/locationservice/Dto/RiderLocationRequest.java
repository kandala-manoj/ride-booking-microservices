package com.alpha.locationservice.Dto;

public class RiderLocationRequest {

    private Integer rider;

    private String vehicleType;

    private double currentLatitude;

    private double longitude;

    public RiderLocationRequest() {
    }

    public Integer getRider() {
        return rider;
    }

    public void setRider(Integer rider) {
        this.rider = rider;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}