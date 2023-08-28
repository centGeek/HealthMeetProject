//package com.HealthMeetProject.code.api.controller;
//
//import com.HealthMeetProject.code.infrastructure.database.repository.DoctorRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//@AllArgsConstructor
//public class DoctorRegistryController {
//
//    @Controller
//    public class DoctorController {
//
//        @Autowired
//        private DoctorRepository doctorRepository; // Implement this repository
//
//        @Autowired
//        private PasswordEncoder passwordEncoder;
//
//        @GetMapping("/register")
//        public String showRegistrationForm() {
//            return "register-doctor";
//        }
//
//        @PostMapping("/register")
//        public String registerDoctor(@RequestParam String name, @RequestParam String surname,
//                                     @RequestParam String email, @RequestParam String phone,
//                                     Model model) {
//            // Create and save a new DoctorEntity
//            DoctorEntity doctor = new DoctorEntity();
//            doctor.setName(name);
//            doctor.setSurname(surname);
//            doctor.setEmail(email);
//            doctor.setPhone(phone);
//
//            // You can set other properties here as needed
//            // doctor.setSpecialization(Specialization.SOME_SPECIALIZATION);
//            // doctor.setSalaryFor15minMeet(BigDecimal.valueOf(100));
//
//            doctorRepository.save(doctor);
//
//            model.addAttribute("message", "Doctor registered successfully!");
//            return "register-doctor";
//        }
//    }
//
//
//}
