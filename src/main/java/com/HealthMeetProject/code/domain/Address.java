package com.HealthMeetProject.code.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    private Long addressId;
    private String country;
    private String city;
    private String postalCode;
    private String address;

}
