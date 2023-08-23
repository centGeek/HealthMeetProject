package com.HealthMeetProject.code.infrastructure.database.repository.jpa;


import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientJpaRepository extends JpaRepository<PatientEntity, Integer> {


}