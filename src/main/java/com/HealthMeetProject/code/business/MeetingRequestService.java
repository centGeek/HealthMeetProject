//package com.HealthMeetProject.code.business;
//
//import com.HealthMeetProject.code.business.dao.MeetingRequestDAO;
//import com.HealthMeetProject.code.domain.Doctor;
//import com.HealthMeetProject.code.domain.MeetingRequest;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.OffsetDateTime;
//import java.time.ZoneOffset;
//import java.util.List;
//import java.util.Random;
//import java.util.Set;
//@Slf4j
//@Service
//@AllArgsConstructor
//public class MeetingRequestService {
//    private final DoctorService doctorService;
//    private final PatientService patientService;
//    private final MeetingRequestDAO meetingRequestDAO;
//
//    public List<Doctor> availableDoctors() {
//        return doctorService.findAllAvailableDoctors();
//    }
//
//    public List<MeetingRequest> availableServiceRequests() {
//        return meetingRequestDAO.findAvailable();
//    }
//
////    @Transactional
////    public void makeServiceRequest(CarServiceRequest serviceRequest) {
////        if (serviceRequest.getCar().shouldExistsInCarToBuy()) {
////            saveServiceRequestForExistingCar(serviceRequest);
////        } else {
////            saveServiceRequestForNewCar(serviceRequest);
////        }
////    }
////
////    private void saveServiceRequestForExistingCar(CarServiceRequest request) {
////        validate(request.getCar().getVin());
////
////        CarToService car = carService.findCarToService(request.getCar().getVin())
////                .orElse(findInCarToBuyAndSaveInCarToService(request.getCar()));
////        Customer customer = customerService.findCustomer(request.getCustomer().getEmail());
////
////        CarServiceRequest carServiceRequest = buildCarServiceRequest(request, car, customer);
////        Set<CarServiceRequest> existingCarServiceRequests = customer.getCarServiceRequests();
////        existingCarServiceRequests.add(carServiceRequest);
////        customerService.saveServiceRequest(customer.withCarServiceRequests(existingCarServiceRequests));
////    }
////
////    private void saveServiceRequestForNewCar(CarServiceRequest request) {
////        validate(request.getCar().getVin());
////
////        CarToService car = carService.saveCarToService(request.getCar());
////        Customer customer = customerService.saveCustomer(request.getCustomer());
////
////        CarServiceRequest carServiceRequest = buildCarServiceRequest(request, car, customer);
////        Set<CarServiceRequest> existingCarServiceRequests = customer.getCarServiceRequests();
////        existingCarServiceRequests.add(carServiceRequest);
////        customerService.saveServiceRequest(customer.withCarServiceRequests(existingCarServiceRequests));
////    }
////
////    private void validate(String carVin) {
////        Set<CarServiceRequest> serviceRequests = carServiceRequestDAO.findActiveServiceRequestsByCarVin(carVin);
////        if (serviceRequests.size() == 1) {
////            throw new ProcessingException(
////                    "There should be only one active service request at a time, car vin: [%s]".formatted(carVin)
////            );
////        }
////    }
////
////    private CarToService findInCarToBuyAndSaveInCarToService(CarToService car) {
////        CarToBuy carToBuy = carService.findCarToBuy(car.getVin());
////        return carService.saveCarToService(carToBuy);
////    }
////
////    private CarServiceRequest buildCarServiceRequest(
////            CarServiceRequest request,
////            CarToService car,
////            Customer customer
////    ) {
////        OffsetDateTime when = OffsetDateTime.of(2027, 1, 10, 10, 2, 10, 0, ZoneOffset.UTC);
////        return CarServiceRequest.builder()
////                .carServiceRequestNumber(generateCarServiceRequestNumber(when))
////                .receivedDateTime(when)
////                .customerComment(request.getCustomerComment())
////                .customer(customer)
////                .car(car)
////                .build();
////    }
////
////    private String generateCarServiceRequestNumber(OffsetDateTime when) {
////        return "%s.%s.%s-%s.%s.%s.%s".formatted(
////                when.getYear(),
////                when.getMonth().ordinal(),
////                when.getDayOfMonth(),
////                when.getHour(),
////                when.getMinute(),
////                when.getSecond(),
////                randomInt(10, 100)
////        );
////    }
////
////    @SuppressWarnings("SameParameterValue")
////    private int randomInt(int min, int max) {
////        return new Random().nextInt(max - min) + min;
////    }
////
////    @Transactional
////    public CarServiceRequest findAnyActiveServiceRequest(String carVin) {
////        Set<CarServiceRequest> serviceRequests = carServiceRequestDAO.findActiveServiceRequestsByCarVin(carVin);
////        if (serviceRequests.size() != 1) {
////            throw new ProcessingException(
////                    "There should be only one active service request at a time, car vin: [%s]".formatted(carVin));
////        }
////        return serviceRequests.stream()
////                .findAny()
////                .orElseThrow(() -> new NotFoundException("Could not find any service requests, car vin: [%s]".formatted(carVin)));
////    }
//}
