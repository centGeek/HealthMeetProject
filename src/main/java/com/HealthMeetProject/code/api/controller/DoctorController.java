package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.exception.NotFoundException;
import com.HealthMeetProject.code.infrastructure.database.entity.DoctorEntity;
import com.HealthMeetProject.code.infrastructure.database.repository.jpa.DoctorJpaRepository;
import com.HealthMeetProject.code.infrastructure.database.repository.mapper.DoctorEntityMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.HealthMeetProject.code.api.controller.PatientController.PATIENT;

@Controller
@AllArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorJpaRepository doctorJpaRepository;
    private final DoctorMapper doctorMapper;
    private final DoctorEntityMapper doctorEntityMapper;
    private final AvailabilityScheduleService availabilityScheduleService;
    public static final String DOCTOR = "/doctor";


    @GetMapping(value = PATIENT)
    public String doctorsPage(Model model) {
        List<DoctorDTO> allAvailableDoctors = doctorService.findAllAvailableDoctors().stream()
                .map(doctorMapper::map).toList();

        model.addAttribute("allAvailableDoctors", allAvailableDoctors);
        return "patient";
    }

    @GetMapping("/patient/terms/{doctorId}")
    public String showEmployeeDetails(
            @PathVariable Integer doctorId,
            Model model
    ) {
        DoctorEntity doctor = doctorJpaRepository.findById(doctorId).orElseThrow(() -> new EntityNotFoundException(
                "Employee entity not found, employeeId: [%s]".formatted(doctorId)
        ));
        List<AvailabilityScheduleDTO> allTermsByGivenDoctor = availabilityScheduleService.findAllAvailableTermsByGivenDoctor(doctor.getEmail());
        model.addAttribute("allTermsByGivenDoctor", allTermsByGivenDoctor);
        model.addAttribute("doctor", doctor);
        return "availability_terms";
    }
    @GetMapping("/doctor/{doctorId}/edit")
    public String showEditDoctorForm(
            @PathVariable Integer doctorId,
            Model model) {

        Doctor existingDoctor = doctorService.findById(doctorId).orElseThrow(
                () -> new NotFoundException("Doctor with given id is not found"));
        DoctorDTO doctorDTO = doctorMapper.map(existingDoctor);
        if (existingDoctor == null) {
            return "redirect:/doctors";
        }

        model.addAttribute("doctor", doctorDTO);
        return "edit-doctor";
    }
    @PatchMapping("/doctor/{doctorId}/edit")
    public String updateDoctor(
            @PathVariable Integer doctorId,
            @ModelAttribute("doctor") DoctorDTO updatedDoctorDTO,
            RedirectAttributes redirectAttributes) {

        Doctor existingDoctor = doctorService.findById(doctorId).orElseThrow(
                () -> new NotFoundException("Doctor with given id is not found"));

        conditionsToUpdate(updatedDoctorDTO, existingDoctor);
        DoctorEntity doctorEntity = doctorEntityMapper.mapToEntity(existingDoctor);
        doctorJpaRepository.save(doctorEntity);

        redirectAttributes.addFlashAttribute("successMessage", "Dane lekarza zostały zaktualizowane pomyślnie.");
        return "redirect:/doctor/" + doctorId + "/edit";
    }

    private static void conditionsToUpdate(DoctorDTO updatedDoctorDTO, Doctor existingDoctor) {
        if (updatedDoctorDTO.getName() != null) {
            existingDoctor.setName(updatedDoctorDTO.getName());
        }
        if (updatedDoctorDTO.getSurname() != null) {
            existingDoctor.setSurname(updatedDoctorDTO.getSurname());
        }
        if (updatedDoctorDTO.getSpecialization() != null) {
            existingDoctor.setSpecialization(updatedDoctorDTO.getSpecialization());
        }
        if (updatedDoctorDTO.getEmail() != null) {
            existingDoctor.setEmail(updatedDoctorDTO.getEmail());
            existingDoctor.getUser().setEmail(updatedDoctorDTO.getEmail());
        }
        if (updatedDoctorDTO.getPhone() != null) {
            existingDoctor.setPhone(updatedDoctorDTO.getPhone());
        }
        if (updatedDoctorDTO.getEarningsPerVisit() != null) {
            existingDoctor.setEarningsPerVisit(updatedDoctorDTO.getEarningsPerVisit());
        }
    }
}
