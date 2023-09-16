package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.exception.ProcessingException;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.AvailabilityScheduleEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
    public class AvailabilityScheduleService {
    private final AvailabilityScheduleDAO availabilityScheduleDAO;
    private final AvailabilityScheduleEntityMapper availabilityScheduleEntityMapper;
    private final AvailabilityScheduleMapper availabilityScheduleMapper;
    private final DoctorService doctorService;

    public List<AvailabilityScheduleDTO> findAllTermsByGivenDoctor(String email){
        return availabilityScheduleDAO.findAllTermsByGivenDoctor(email).stream().map(availabilityScheduleMapper::mapToDTO).toList();
    };

    public AvailabilityScheduleDTO addTerm(OffsetDateTime since,OffsetDateTime toWhen, DoctorEntity doctorEntity) {
        if(!doctorService.findAnyTermInGivenRangeInGivenDay(since, toWhen, doctorEntity.getEmail())){
            throw new ProcessingException("You can not add date[%s - %s], because this date cover with other date".formatted(since, toWhen));
        }
        if(since.isBefore(OffsetDateTime.now())){
            throw new ProcessingException("You can not add date[%s - %s], because this date isn't entirely in the future".formatted(since, toWhen));
        }
        if (since.plusMinutes(15).isAfter(toWhen)) {
            throw new IllegalArgumentException("Minimum planned visit is 15 minutes");
        }
        if(since.plusHours(10).isBefore(toWhen)){
            throw new IllegalArgumentException("Maximum working day is 10 hours");
        }
        AvailabilitySchedule availabilitySchedule = availabilityScheduleDAO.addTerm(since, toWhen, doctorEntity);
        return availabilityScheduleMapper.mapToDTO(availabilitySchedule);
    }


    public List<AvailabilityScheduleDTO> findAllAvailableTermsByGivenDoctor(String email) {
         return availabilityScheduleDAO.findAllAvailableTermsByGivenDoctor(email).stream().map(availabilityScheduleMapper::mapToDTO).toList();
    }
    public void save(AvailabilitySchedule availabilitySchedule) {
        AvailabilityScheduleEntity availabilityScheduleEntity = availabilityScheduleEntityMapper.mapToEntity(availabilitySchedule);
        availabilityScheduleDAO.save(availabilityScheduleEntity);
    }

}
