package com.HealthMeetProject.code.infrastructure.database.repository.jpa;


import com.HealthMeetProject.code.infrastructure.database.entity.MeetingRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
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
            SELECT mre FROM MeetingRequestEntity mre
            WHERE mre.completedDateTime IS NULL
            AND mre.patient.email = :email
            """)
    List<MeetingRequestEntity> findAllActiveMeetingRequestsByPatient(@Param("email") String email);

    @Query("""
              SELECT mre FROM MeetingRequestEntity mre
                    WHERE mre.completedDateTime IS NULL
                    AND mre.doctor.email = :email
            """)
    List<MeetingRequestEntity> findAllActiveMeetingRequestsByDoctor(@Param("email") String email);

    @Query("""
            SELECT mre FROM MeetingRequestEntity mre
            WHERE mre.completedDateTime IS NOT NULL
            AND mre.completedDateTime < CURRENT_TIMESTAMP
                """)
    List<MeetingRequestEntity> findAllVisitsEndedUp();

    @Query("""
            SELECT mre FROM MeetingRequestEntity mre
            WHERE  mre.visitStart > CURRENT_TIMESTAMP
            AND mre.patient.email = :email
                """)
    List<MeetingRequestEntity> findAllUpcomingVisitsByPatient(@Param("email") String email);
    @Query("""
            SELECT mre FROM MeetingRequestEntity mre
            WHERE  mre.visitStart > CURRENT_TIMESTAMP
            AND mre.doctor.email = :email
                """)
    List<MeetingRequestEntity> findAllUpcomingVisitsByDoctor(@Param("email") String email);

    @Query("""
            select mre from MeetingRequestEntity  mre
            WHERE mre.completedDateTime IS NOT NULL
            and mre.patient.email = :email
            """)
    List<MeetingRequestEntity> findAllCompletedMeetingRequestsByPatient(@Param("email") String email);


    @Query("""
              SELECT mre FROM MeetingRequestEntity mre
                    WHERE mre.completedDateTime IS NOT NULL
                    AND mre.doctor.email = :email
                    and mre.visitEnd < CURRENT_TIMESTAMP
            """)
    List<MeetingRequestEntity> completedMeetingRequestsByDoctor(String email);

    @Query("""
                SELECT mre FROM MeetingRequestEntity mre
                WHERE mre.doctor.email = :email
                AND mre.visitStart = :since
                AND mre.visitEnd = :toWhen
            """)
    MeetingRequestEntity findIfMeetingRequestExistsWithTheSameDateAndDoctor(
            OffsetDateTime since, OffsetDateTime toWhen, String email
    );

    @Query("""
              SELECT mre FROM MeetingRequestEntity mre
                    WHERE mre.completedDateTime IS NOT NULL
                    AND mre.doctor.email = :doctorEmail
                    AND mre.patient.email = :patientEmail
                    and mre.visitEnd < CURRENT_TIMESTAMP
            """)
    List<MeetingRequestEntity> findAllEndedUpVisits(@Param("doctorEmail") String doctorEmail, @Param("patientEmail") String patientEmail);
}
