package com.HealthMeetProject.code.infrastructure.database.repository.jpa;


import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptJpaRepostitory  extends JpaRepository<Integer, DoctorEntity> {
}
