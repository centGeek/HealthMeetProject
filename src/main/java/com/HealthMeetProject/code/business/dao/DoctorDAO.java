package com.HealthMeetProject.code.business.dao;


import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.domain.Specialization;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface DoctorDAO {
    List<Doctor> findAllAvailableDoctors();

    Optional<Doctor> findByEmail(String email);

    List<Doctor> findAllBySpecialization(Specialization specialization);

    void addAvailabilityTime(Doctor doctor, OffsetDateTime beginTime,OffsetDateTime endTime);

    void writeNote(Note note);

    void issueReceipt(Receipt receipt);

    void register(DoctorDTO doctor);
}
