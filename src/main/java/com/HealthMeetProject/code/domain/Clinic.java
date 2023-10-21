package com.HealthMeetProject.code.domain;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
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
