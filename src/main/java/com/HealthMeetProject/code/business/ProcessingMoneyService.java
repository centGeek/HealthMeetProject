package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProcessingMoneyService {
    public BigDecimal sumTotalCosts(List<MeetingRequest> allCompletedServiceRequestsByEmail, List<MedicineEntity> medicines) {
        BigDecimal totalCostsMeetings = allCompletedServiceRequestsByEmail.stream()
                .map(request -> request.getDoctor().getEarningsPerVisit())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCostsMedicines = medicines.stream()
                .map(MedicineEntity::getApproxPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalCostsMedicines.add(totalCostsMeetings);
    }
}
