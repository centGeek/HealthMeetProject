package com.HealthMeetProject.code.infrastructure.database.repository.jpa;


import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorJpaRepository extends JpaRepository<DoctorEntity, Integer> {
    @Query("""
                    select doc from DoctorEntity doc where doc.email =:email
            """)
    Optional<DoctorEntity> findByEmail(@Param("email") String email);


    @Query("""
                select emp from DoctorEntity emp
                where emp.phone = :phone
            """)
    List<DoctorEntity> findAllByPhone(@Param("phone") String phone);

}


