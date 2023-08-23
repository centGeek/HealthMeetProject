package com.HealthMeetProject.code.infrastructure.database.repository;


import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class DoctorRepository implements DoctorDAO {
    private DoctorJpaRepository doctorJpaRepository;
    private DoctorEntityMapper doctorEntityMapper;

    @Override
    public List<Doctor> findAllAvailableDoctors() {
        return doctorJpaRepository.findAll().stream()
                .map(doctorEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public List<Doctor> findAllByEmail(String email) {
        return doctorJpaRepository.findByEmail(email).stream()
                .map(doctorEntityMapper::mapFromEntity)
                .toList();
    }
}
