package com.alpha.bookingservice.dto;

public class continueBookinggDTO {
	private String pickupAddress;
	private String dropAddress;
	private double fare;
	public continueBookinggDTO(String pickupAddress, String dropAddress, double fare) {
		super();
		this.pickupAddress = pickupAddress;
		this.dropAddress = dropAddress;
		this.fare = fare;
	}
	public continueBookinggDTO() {
		super();
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
	public double getFare() {
		return fare;
	}
	public void setFare(double fare) {
		this.fare = fare;
	}
	

}
