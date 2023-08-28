package com.HealthMeetProject.code.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Clinic {
    private Long clinicId;
    private String clinicName;
    private String country;
    private String postalCode;
    private String address;

}
