package com.HealthMeetProject.code.domain;

import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String userName;
    private String surname;
    private String email;
    private String password;
    private Boolean active;
    private Set<RoleEntity> roles;
}