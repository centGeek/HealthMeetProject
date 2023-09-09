package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.mapper.AvailabilityScheduleMapper;
import com.HealthMeetProject.code.business.dao.AvailabilityScheduleDAO;
import com.HealthMeetProject.code.domain.AvailabilitySchedule;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.infrastructure.database.entity.AvailabilityScheduleEntity;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.AvailabilityScheduleJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.AvailabilityScheduleEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AvailabilityScheduleService {
    private final AvailabilityScheduleDAO availabilityScheduleDAO;
    private final AvailabilityScheduleJpaRepository availabilityScheduleJpaRepository;
    private final AvailabilityScheduleEntityMapper availabilityScheduleEntityMapper;
    private final AvailabilityScheduleMapper availabilityScheduleMapper;

    public List<AvailabilityScheduleDTO> findAllTermsByGivenDoctor(String email){
        return availabilityScheduleDAO.findAllTermsByGivenDoctor(email).stream().map(availabilityScheduleMapper::map).toList();
    };

    public AvailabilityScheduleDTO addTerm(OffsetDateTime since,OffsetDateTime toWhen, DoctorEntity doctorEntity) {
        if (since.plusMinutes(15).isAfter(toWhen)) {
            throw new IllegalArgumentException("Minimum planned visit is 15 minutes");
        }
        if(since.plusHours(10).isBefore(toWhen)){
            throw new IllegalArgumentException("Maximum working day is 10 hours");
        }
        AvailabilitySchedule availabilitySchedule = availabilityScheduleDAO.addTerm(since, toWhen, doctorEntity);
        return availabilityScheduleMapper.map(availabilitySchedule);
    }


    public List<AvailabilityScheduleDTO> findAllAvailableTermsByGivenDoctor(String email) {
         return availabilityScheduleDAO.findAllAvailableTermsByGivenDoctor(email).stream().map(availabilityScheduleMapper::map).toList();
    }

    public void save(AvailabilityScheduleDTO availabilityScheduleDTO) {
        AvailabilitySchedule availabilitySchedule = availabilityScheduleMapper.map(availabilityScheduleDTO);
        AvailabilityScheduleEntity availabilityScheduleEntity = availabilityScheduleEntityMapper.map(availabilitySchedule);
        availabilityScheduleJpaRepository.save(availabilityScheduleEntity);
    }
}
