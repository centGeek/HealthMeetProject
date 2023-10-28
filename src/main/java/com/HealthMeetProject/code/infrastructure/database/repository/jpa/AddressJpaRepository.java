package com.HealthMeetProject.code.infrastructure.database.repository.jpa;

import com.HealthMeetProject.code.infrastructure.database.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressJpaRepository extends JpaRepository<AddressEntity, Integer> {


}
