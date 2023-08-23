package com.HealthMeetProject.code.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clinic {
    private Long addressId;
    private String clinicName;
    private String country;
    private String postalCode;
    private String address;

}
