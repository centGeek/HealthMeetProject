package com.HealthMeetProject.code.infrastructure.database.repository.jpa;


import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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
        SELECT mre FROM MeetingRequestEntity mre
        WHERE mre.completedDateTime IS NULL
        AND mre.patient.email = :email
        """)
    List<MeetingRequestEntity> findAllActiveMeetingRequests(@Param("email") String email);
    @Query("""
    SELECT mre FROM MeetingRequestEntity mre
    WHERE mre.completedDateTime IS NOT NULL
    AND mre.completedDateTime < CURRENT_TIMESTAMP
        """)
    List<MeetingRequestEntity> findAllVisitsEndedUp();
@Query("""
        select mre from MeetingRequestEntity  mre
        WHERE mre.completedDateTime IS NOT NULL
        and mre.patient.email = :email
        """)
    List<MeetingRequestEntity> findAllCompletedServiceRequests(@Param("email") String email);
}
