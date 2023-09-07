package com.HealthMeetProject.code.infrastructure.database.repository.jpa;

import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineJpaRepository extends JpaRepository<MedicineEntity, Integer> {
}
