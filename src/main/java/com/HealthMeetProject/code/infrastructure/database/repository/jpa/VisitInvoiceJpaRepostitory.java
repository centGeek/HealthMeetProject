package com.HealthMeetProject.code.infrastructure.database.repository.jpa;


import com.HealthMeetProject.code.infrastructure.database.entity.VisitInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitInvoiceJpaRepostitory  extends JpaRepository<Integer, VisitInvoiceEntity> {

}
