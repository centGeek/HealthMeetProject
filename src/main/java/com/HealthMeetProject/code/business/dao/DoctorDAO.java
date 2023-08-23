package com.HealthMeetProject.code.business.dao;


import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorDAO {
    List<Doctor> findAllAvailableDoctors();
    List<Doctor> findAllByEmail(String email);
}
