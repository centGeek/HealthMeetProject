package com.HealthMeetProject.code.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Long addressId;
    private String country;
    private String postalCode;
    private String address;

}
