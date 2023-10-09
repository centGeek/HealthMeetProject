package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import lombok.*;

import java.util.Set;
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserData {
    private int id;
    private String userName;
    private String password;
    private String email;
    private Boolean active;
    private Set<RoleEntity> roles;
}
