package com.HealthMeetProject.code.infrastructure.database.repository;


import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.MeetingRequestJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.PatientJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.VisitInvoiceJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.MeetingRequestEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.PatientEntityMapper;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.VisitInvoiceEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PatientRepository implements PatientDAO {

    private final PatientJpaRepository patientJpaRepository;
    private final VisitInvoiceJpaRepository visitInvoiceJpaRepository;
    private final MeetingRequestJpaRepository meetingRequestJpaRepository;

    private final PatientEntityMapper patientEntityMapper;
    private final VisitInvoiceEntityMapper visitInvoiceEntityMapper;
    private final MeetingRequestEntityMapper meetingRequestEntityMapper;



    @Override
    public void issueInvoice(Patient patient) {
        PatientEntity patientToSave = patientEntityMapper.mapToEntity(patient);
        PatientEntity patientSaved = patientJpaRepository.saveAndFlush(patientToSave);

        patient.getInvoices().stream()
                .filter(invoice -> Objects.isNull(invoice.getInvoiceId()))
                .map(visitInvoiceEntityMapper::mapToEntity)
                .forEach(invoiceEntity -> {
                    invoiceEntity.setPatient(patientSaved);
                    visitInvoiceJpaRepository.saveAndFlush(invoiceEntity);
                });
    }
    @Override
    public void saveMeetingRequest(Patient patient) {
        List<MeetingRequestEntity> meetingRequests = patient.getMeetingRequests().stream()
                .filter(serviceRequest -> Objects.isNull(serviceRequest.getMeetingId()))
                .map(meetingRequestEntityMapper::mapToEntity)
                .toList();

        meetingRequests
                .forEach(request -> request.setPatient(patientEntityMapper.mapToEntity(patient)));
        meetingRequestJpaRepository.saveAllAndFlush(meetingRequests);

    }

    @Override
    public Patient savePatient(Patient patient) {
        PatientEntity toSave = patientEntityMapper.mapToEntity(patient);
        PatientEntity saved = patientJpaRepository.save(toSave);
        return patientEntityMapper.mapFromEntity(saved);
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        return patientJpaRepository.findByEmail(email).map(patientEntityMapper::mapFromEntity);
    }

}