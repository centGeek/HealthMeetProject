package com.HealthMeetProject.code.api.controller;//package com.HealthMeetProject.code.api.controller;
//
//import com.HealthMeetProject.code.api.dto.DoctorDTO;
//import com.HealthMeetProject.code.api.dto.mapper.DoctorMapper;
//import com.HealthMeetProject.code.business.DoctorService;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;

import com.HealthMeetProject.code.business.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientController {
    public static final String PATIENT = "/patient";
    public final DoctorService doctorService;

//
//    @GetMapping(value = PATIENT)
//    public String show(Model model) {
//        List<DoctorDTO> allAvailableDoctors = doctorService.findAllAvailableDoctors().stream()
//                .map(doctorMapper::map).toList();
//            model.addAttribute("allAvailableDoctors", allAvailableDoctors);
//            return "patient";
//    }
//}
}