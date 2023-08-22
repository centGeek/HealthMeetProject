package com.HealthMeetProject.code.infrastructure.database.repository.jpa;

import com.HealthMeetProject.code.infrastructure.database.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJpaRepostitory  extends JpaRepository<Integer, AddressEntity> {

  

    
}
