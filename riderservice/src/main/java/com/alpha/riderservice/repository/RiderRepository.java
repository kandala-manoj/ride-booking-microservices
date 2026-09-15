package com.alpha.riderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.riderservice.entity.Rider;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Integer> {

    Rider findByEmail(String email);

    Rider findByPhone(long phone);

    Rider findByDrivingLicenseNumber(
            String drivingLicenseNumber);
}