package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Note;
import com.HealthMeetProject.code.domain.Receipt;
import com.HealthMeetProject.code.infrastructure.database.entity.MedicineEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.NoteEntity;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record PatientHistoryDTO(List<MeetingRequest> allUpcomingVisits, List<Boolean> canCancelMeetingList, List<MeetingRequest> allCompletedServiceRequests,
                                List<NoteEntity> notes, BigDecimal totalCosts, List<Receipt> receipts, List<MedicineEntity> medicinesFromLastVisit) {
}
