package com.HealthMeetProject.code.domain;

import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
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
