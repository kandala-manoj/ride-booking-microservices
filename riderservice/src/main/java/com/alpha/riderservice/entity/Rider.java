package com.alpha.riderservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rider_id;

    private String name;

    private long phone;

    private String email;

    private double walletBalance;

    private String drivingLicenseNumber;

    // AVAILABLE, BUSY, OFFLINE, INACTIVE
    private String status;

    private double averageRating;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne(
            mappedBy = "rider",
            cascade = CascadeType.ALL
    )
    private Vehicle vehicle;

    public Rider() {
        super();
    }

    public Rider(
            String name,
            long phone,
            String email,
            double walletBalance,
            String drivingLicenseNumber,
            String status,
            double averageRating,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Vehicle vehicle) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.walletBalance = walletBalance;
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.status = status;
        this.averageRating = averageRating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.vehicle = vehicle;
    }

    public int getRiderId() {
        return rider_id;
    }

    public void setRiderId(int rider_id) {
        this.rider_id = rider_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(
            String drivingLicenseNumber) {

        this.drivingLicenseNumber =
                drivingLicenseNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(
            LocalDateTime updatedAt) {

        this.updatedAt = updatedAt;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}