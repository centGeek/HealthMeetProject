package com.HealthMeetProject.code.infrastructure.database.repository;


import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Specialization;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.AvailabilityScheduleJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class DoctorRepository implements DoctorDAO {
    private DoctorJpaRepository doctorJpaRepository;
    private DoctorEntityMapper doctorEntityMapper;
    private AvailabilityScheduleJpaRepository availabilityScheduleJpaRepository;

    @Override
    public List<Doctor> findAllAvailableDoctors() {
        return doctorJpaRepository.findAll().stream()
                .map(doctorEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<Doctor> findByEmail(String email) {
        return doctorJpaRepository.findByEmail(email)
                .map(doctorEntityMapper::mapFromEntity);
    }

    @Override
    public List<Doctor> findAllBySpecialization(Specialization specialization) {
        return doctorJpaRepository.findAllBySpecialization(specialization).stream()
                .map(doctorEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public void addAvailabilityTime(Doctor doctor,  OffsetDateTime beginTime, OffsetDateTime endTime) {
        DoctorEntity doctorToSave = doctorEntityMapper.mapToEntity(doctor);
        Set<AvailabilityScheduleEntity> terms = doctorToSave.getTerms();
        AvailabilityScheduleEntity availabilityScheduleToSave = AvailabilityScheduleEntity.builder().since(beginTime).toWhen(endTime).doctor(doctorToSave).build();
        terms.add(availabilityScheduleToSave);
        availabilityScheduleJpaRepository.saveAndFlush(availabilityScheduleToSave);
        doctorToSave.setTerms(terms);
    }


    /*@Override
    public List<Doctor> findAllByGivenDay(int year, int month, int day) {
        return doctorJpaRepository.findAllByTermsSinceIsLessThanEqualAndTermsToWhenIsGreaterThanEqual(OffsetDateTime.of()).stream()
                .map(doctorEntityMapper::mapFromEntity).toList();
    }*/
}
