package com.HealthMeetProject.code.api.controller;

import com.HealthMeetProject.code.api.dto.AvailabilityScheduleDTO;
import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
import com.HealthMeetProject.code.business.AvailabilityScheduleService;
import com.HealthMeetProject.code.business.DoctorService;
import com.HealthMeetProject.code.business.PatientService;
import com.HealthMeetProject.code.business.dao.DoctorDAO;
import com.HealthMeetProject.code.business.dao.PatientDAO;
import com.HealthMeetProject.code.domain.Doctor;
import com.HealthMeetProject.code.domain.MeetingRequest;
import com.HealthMeetProject.code.domain.Patient;
import com.HealthMeetProject.code.domain.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static com.HealthMeetProject.code.api.controller.PatientController.PATIENT;

@Controller
@AllArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorDAO doctorDAO;
    private final DoctorMapper doctorMapper;
    private final AvailabilityScheduleService availabilityScheduleService;
    private final PatientService patientService;
    private final PatientDAO patientDAO;
    public static final String DOCTOR = "/doctor";


    @GetMapping(value = PATIENT)
    public String doctorsPage(Model model) {
        String email = patientService.authenticate();
        Patient patient = patientDAO.findByEmail(email);
        List<DoctorDTO> allAvailableDoctors = doctorService.findAllAvailableDoctors().stream()
                .map(doctorMapper::map).toList();

        model.addAttribute("allAvailableDoctors", allAvailableDoctors);
        model.addAttribute("patient", patient);
        return "patient";
    }

    @GetMapping("/patient/terms/{doctorId}")
    public String showEmployeeDetails(
            @PathVariable Integer doctorId,
            Model model
    ) {
        Doctor doctor = doctorDAO.findById(doctorId).orElseThrow(() -> new EntityNotFoundException(
                "Employee entity not found, employeeId: [%s]".formatted(doctorId)
        ));
        DoctorDTO doctorDTO = doctorMapper.map(doctor);
        List<AvailabilityScheduleDTO> allTermsByGivenDoctor = availabilityScheduleService.findAllAvailableTermsByGivenDoctor(doctor.getEmail());

        model.addAttribute("allTermsByGivenDoctor", allTermsByGivenDoctor);
        model.addAttribute("doctor", doctorDTO);
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

        doctorService.conditionsToUpdate(updatedDoctorDTO, existingDoctor);
        doctorDAO.save(existingDoctor);

        redirectAttributes.addFlashAttribute("successMessage", "Patient data has been changed correctly");
        return "redirect:/logout";
    }


}
