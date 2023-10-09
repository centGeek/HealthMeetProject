package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PatientHistoryDTO {
    private List<MeetingRequest> allUpcomingVisits;
    private List<Boolean> canCancelMeetingList;
    private List<MeetingRequest> allCompletedServiceRequests;
    private List<NoteEntity> notes;
    private BigDecimal totalCosts;
    private List<Receipt> receipts;
    private List<MedicineEntity> medicinesFromLastVisit;
}
