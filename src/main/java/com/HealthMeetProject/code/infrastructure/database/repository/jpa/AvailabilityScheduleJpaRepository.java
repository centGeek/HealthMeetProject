package com.HealthMeetProject.code.infrastructure.database.repository.jpa;

import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface AvailabilityScheduleJpaRepository extends JpaRepository<AvailabilityScheduleEntity, Integer> {

    @Query("""
            select avail from AvailabilityScheduleEntity avail where avail.doctor.email = :email\s and avail.availableDay=true and avail.since > CURRENT_TIMESTAMP
""")
    Set<AvailabilityScheduleEntity> findAllTermsByGivenDoctor(@Param("email") String email);

    @Query("""
        select avail from AvailabilityScheduleEntity  avail where avail.availableDay=true and avail.doctor.email =:email and avail.since > CURRENT_TIMESTAMP
        """)
    List<AvailabilityScheduleEntity> findAllAvailableTermsByGivenDoctor(@Param("email") String email);
    @Query("""
                SELECT a FROM AvailabilityScheduleEntity a
                JOIN a.doctor d
                WHERE d.email = :doctorEmail
                AND (
                    (a.since > :since AND a.since < :toWhen) OR
                    (a.toWhen > :since AND a.toWhen < :toWhen) OR
                    (a.since < :since AND a.toWhen > :toWhen)
                )
            """)
    List<AvailabilityScheduleEntity> findAnyTermInGivenRangeInGivenDay(OffsetDateTime since, OffsetDateTime toWhen, String doctorEmail);
}

