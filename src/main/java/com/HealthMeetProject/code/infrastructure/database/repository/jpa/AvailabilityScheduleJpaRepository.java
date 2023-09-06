package com.HealthMeetProject.code.infrastructure.database.repository.jpa;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface AvailabilityScheduleJpaRepository extends JpaRepository<AvailabilityScheduleEntity, Integer> {

//    @Query("""
//            select avail from AvailabilityScheduleEntity avail where avail.doctor.doctorId = :doctorId\s
//""")
//    Set<AvailabilityScheduleEntity> findAllTermsByGivenDoctor(@Param("doctorId") Integer doctorId);

    @Query("""
            select avail from AvailabilityScheduleEntity avail where avail.doctor.email = :email\s
""")
    Set<AvailabilityScheduleEntity> findAllTermsByGivenDoctor(@Param("email") String email);

    @Query("""
    SELECT avail FROM AvailabilityScheduleEntity avail WHERE YEAR(avail.since) = :year AND MONTH(avail.since) = :month AND DAY(avail.since) = :day
""")
    Set<AvailabilityScheduleEntity> findAllTermsByGivenDay(
            @Param("year") int year,
            @Param("month") int month,
            @Param("day") int day
    );
    @Query("""
        select avail from AvailabilityScheduleEntity  avail where avail.available=true and avail.doctor.email =:email
        """)
    List<AvailabilityScheduleEntity> findAllAvailableTermsByGivenDoctor(@Param("email") String email);
}

