package com.HealthMeetProject.code.infrastructure.database.repository.jpa;


import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorJpaRepository extends JpaRepository<DoctorEntity,Integer> {

    Optional<DoctorEntity> findByEmail(String email);

}
