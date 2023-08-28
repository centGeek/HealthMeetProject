package com.HealthMeetProject.code.infrastructure.database.repository.jpa;


import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRequestJpaRepository extends JpaRepository<MeetingRequestEntity, Integer> {

    @Query("""
        select met from MeetingRequestEntity met where met.patient.email= :email
""")
    List<MeetingRequestEntity> findAllByPatientEmail(@Param("email") String email);

    @Query("""
        select met from MeetingRequestEntity met where met.doctor.email= :email
""")
    List<MeetingRequestEntity> findAllByDoctorEmail(@Param("email") String email);

    @Query("""
        SELECT csr FROM MeetingRequestEntity csr
        WHERE csr.completedDateTime IS NULL
        AND csr.patient.email = :email
        """)
    List<MeetingRequestEntity> findAllActiveMeetingRequests(@Param("email") String email);
}
