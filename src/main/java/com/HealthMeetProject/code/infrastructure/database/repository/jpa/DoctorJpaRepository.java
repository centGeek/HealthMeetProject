package com.HealthMeetProject.code.infrastructure.database.repository.jpa;


import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.Specialization;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorJpaRepository extends JpaRepository<DoctorEntity, Integer> {

    Optional<DoctorEntity> findByEmail(String email);


    @Query("""
                select emp from DoctorEntity emp
                where emp.specialization = :specialization
            """)
    List<DoctorEntity> findAllBySpecialization(@Param("specialization") Specialization specialization);

    List<DoctorEntity> findAllByTermsSinceIsLessThanEqualAndTermsToWhenIsGreaterThanEqual(
            OffsetDateTime givenDayStart,
            OffsetDateTime givenDayEnd
    );

}


