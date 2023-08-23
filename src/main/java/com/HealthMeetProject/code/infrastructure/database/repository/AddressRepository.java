package com.HealthMeetProject.code.infrastructure.database.repository;

import com.HealthMeetProject.code.infrastructure.database.repository.jpa.AddressJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepository {
    private AddressJpaRepository addressJpaRepository;
}
