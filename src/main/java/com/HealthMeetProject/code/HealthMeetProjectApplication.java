package com.HealthMeetProject.code;

import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.infrastructure.database.repository.NoteRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.PatientRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.PatientJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class HealthMeetProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthMeetProjectApplication.class, args);
	}
}

