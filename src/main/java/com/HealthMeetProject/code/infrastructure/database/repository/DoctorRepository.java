package com.HealthMeetProject.code.infrastructure.database.repository;


import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.domain.*;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.ReceiptEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.AvailabilityScheduleJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.NoteJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.ReceiptJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.NoteEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.ReceiptEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class DoctorRepository implements DoctorDAO {
    private DoctorJpaRepository doctorJpaRepository;
    private DoctorEntityMapper doctorEntityMapper;
    private AvailabilityScheduleJpaRepository availabilityScheduleJpaRepository;
    private NoteJpaRepository noteJpaRepository;
    private NoteEntityMapper noteEntityMapper;
    private ReceiptJpaRepository receiptJpaRepository;
    private ReceiptEntityMapper receiptEntityMapper;


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
    public void addAvailabilityTime(Doctor doctor, OffsetDateTime beginTime, OffsetDateTime endTime) {
        DoctorEntity doctorToSave = doctorEntityMapper.mapToEntity(doctor);
        Set<AvailabilityScheduleEntity> terms = doctorToSave.getTerms();
        AvailabilityScheduleEntity availabilityScheduleToSave = AvailabilityScheduleEntity.builder().since(beginTime).toWhen(endTime).doctor(doctorToSave).build();
        terms.add(availabilityScheduleToSave);
        availabilityScheduleJpaRepository.saveAndFlush(availabilityScheduleToSave);
        doctorToSave.setTerms(terms);
    }

    @Override
    public void writeNote(Note note) {
        NoteEntity noteEntity = noteEntityMapper.map(note);
        noteJpaRepository.saveAndFlush(noteEntity);
    }

    @Override
    public void issueReceipt(Receipt receipt) {
        ReceiptEntity receiptEntity = receiptEntityMapper.map(receipt);
        receiptJpaRepository.saveAndFlush(receiptEntity);
    }
}
