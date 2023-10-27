package com.HealthMeetProject.code.business.dao;


import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.api.DoctorDTOs;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DoctorDAO {
    List<Doctor> findAllAvailableDoctors();

    DoctorDTOs findAllDoctors();

    Optional<Doctor> findByEmail(String email);


    void writeNote(NoteEntity note);

    void issueReceipt(Receipt receipt, Set<MedicineEntity> set);

    void register(DoctorDTO doctor);

    Optional<Doctor> findById(Integer id);

    boolean findAnyTermInGivenRangeInGivenDay(LocalDateTime since, LocalDateTime toWhen, String doctorEmail);


        void save(Doctor doctor);
}
