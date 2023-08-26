package com.HealthMeetProject.code.infrastructure.database.repository.jpa;

import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailabilityScheduleJpaRepository extends JpaRepository<AvailabilityScheduleEntity, Integer> {

    @Query("""
            select avail from AvailabilityScheduleEntity avail where avail.doctor.email = :email\s
""")
    List<AvailabilityScheduleEntity> findAllTermsByGivenDoctor(@Param("email") String email);

}
