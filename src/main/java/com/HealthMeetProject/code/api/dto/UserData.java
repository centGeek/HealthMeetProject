package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserData {
    private int id;
    private String name;
    private String userName;
    private String password;
    private String email;
    private Boolean active;
    private Set<RoleEntity> roles;
}
