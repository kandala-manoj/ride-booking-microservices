package com.alpha.riderservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vehicleId;

    private String vehicleNumber;

    // BIKE, AUTO, CAB
    private String vehicleType;

    private String vehicleBrand;

    private String vehicleModel;

    private String vehicleColor;

    // Number of passengers
    private int vehicleCapacity;

    private String registrationNumber;

    private String insuranceNumber;

    private String pollutionCertificate;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "rider_id")
    private Rider rider;

    public Vehicle() {
        super();
    }

    public Vehicle(
            String vehicleNumber,
            String vehicleType,
            String vehicleBrand,
            String vehicleModel,
            String vehicleColor,
            int vehicleCapacity,
            String registrationNumber,
            String insuranceNumber,
            String pollutionCertificate,
            Rider rider) {

        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
        this.vehicleColor = vehicleColor;
        this.vehicleCapacity = vehicleCapacity;
        this.registrationNumber = registrationNumber;
        this.insuranceNumber = insuranceNumber;
        this.pollutionCertificate = pollutionCertificate;
        this.rider = rider;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(
            String vehicleNumber) {

        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(
            String vehicleType) {

        this.vehicleType = vehicleType;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(
            String vehicleBrand) {

        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(
            String vehicleModel) {

        this.vehicleModel = vehicleModel;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(
            String vehicleColor) {

        this.vehicleColor = vehicleColor;
    }

    public int getVehicleCapacity() {
        return vehicleCapacity;
    }

    public void setVehicleCapacity(
            int vehicleCapacity) {

        this.vehicleCapacity = vehicleCapacity;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(
            String registrationNumber) {

        this.registrationNumber =
                registrationNumber;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(
            String insuranceNumber) {

        this.insuranceNumber = insuranceNumber;
    }

    public String getPollutionCertificate() {
        return pollutionCertificate;
    }

    public void setPollutionCertificate(
            String pollutionCertificate) {

        this.pollutionCertificate =
                pollutionCertificate;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }
}