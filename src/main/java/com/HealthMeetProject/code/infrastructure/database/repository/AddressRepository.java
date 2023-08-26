package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.infrastructure.database.repository.jpa.AddressJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AddressRepository {
    private AddressJpaRepository addressJpaRepository;
}
