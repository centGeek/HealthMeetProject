package com.HealthMeetProject.code.infrastructure.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query("""
                select role from RoleEntity role where role.role =:role
            """)
    RoleEntity findByRole(@Param("role") String role);
}
