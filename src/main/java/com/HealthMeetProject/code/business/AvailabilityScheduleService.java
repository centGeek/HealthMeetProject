package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AvailabilityScheduleService {
    private final AvailabilityScheduleDAO availabilityScheduleDAO;
    private final AvailabilityScheduleMapper availabilityScheduleMapper;

    public List<AvailabilityScheduleDTO> findAllTermsByGivenDoctor(String email){
        return availabilityScheduleDAO.findAllTermsByGivenDoctor(email).stream().map(availabilityScheduleMapper::map).toList();
    };

    public AvailabilityScheduleDTO addTerm(OffsetDateTime since,OffsetDateTime toWhen, DoctorEntity doctorEntity) {
        if (since.plusMinutes(15).isAfter(toWhen)) {
            throw new IllegalArgumentException("Minimum planned visit is 15 minutes");
        }
        AvailabilitySchedule availabilitySchedule = availabilityScheduleDAO.addTerm(since, toWhen, doctorEntity);
        return availabilityScheduleMapper.map(availabilitySchedule);
    }


}
