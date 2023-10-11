package com.HealthMeetProject.code.domain;

import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
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
