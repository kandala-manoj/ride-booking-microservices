package com.alpha.customerservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerRegisterDto {

    @NotBlank(message = "Name is required")
    @Size(
        min = 2,
        max = 50,
        message = "Name must be 2 to 50 characters"
    )
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide valid email address")
    private String email;

    @Min(
        value = 6000000000L,
        message = "Invalid phone number"
    )
    @Max(
        value = 9999999999L,
        message = "Invalid phone number"
    )
    private long phone;

    public CustomerRegisterDto() {
        super();
    }

    public CustomerRegisterDto(
            String name,
            String email,
            long phone) {

        super();

        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }
}