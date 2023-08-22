package com.HealthMeetProject.code.infrastructure.database.repository.jpa;


import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientJpaRepostitory extends JpaRepository<Integer, PatientEntity> {


}