package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import lombok.*;

import java.util.Objects;
import java.util.Set;
@Getter
@Setter
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData that = (UserData) o;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
