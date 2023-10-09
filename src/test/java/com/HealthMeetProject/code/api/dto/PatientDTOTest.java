package com.HealthMeetProject.code.api.dto;

import com.HealthMeetProject.code.domain.Address;
import com.HealthMeetProject.code.infrastructure.security.RoleEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PatientDTOTest {

    @Test
    public void testEquals() {
        Address address1 = new Address(1L, "Street1", "City1", "Zip1", "yes");
        Address address2 = new Address(2L, "Street2", "City2", "Zip2", "yes");
        UserData userData1 = new UserData(1, "Username1", "Password1", "email@gmail.com", true, Set.of(RoleEntity.builder().id(1).role("PATIENT").build()));
        UserData userData2 = new UserData(2, "Username2", "Password2", "email@gmail.com",true, Set.of(RoleEntity.builder().id(1).role("PATIENT").build()));

        PatientDTO patient1 = PatientDTO.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("1234567890")
                .phone("123-456-789")
                .address(address1)
                .user(userData1)
                .build();

        PatientDTO patient2 = PatientDTO.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("1234567890")
                .phone("123-456-789")
                .address(address1)
                .user(userData1)
                .build();

        PatientDTO patient3 = PatientDTO.builder()
                .name("Jane")
                .surname("Doe")
                .email("jane.doe@example.com")
                .pesel("9876543210")
                .phone("987-654-321")
                .address(address2)
                .user(userData2)
                .build();

        assertEquals(patient1, patient2);
        assertNotEquals(patient1, patient3);
    }

    @Test
    public void testCanEqual() {
        Address address1 = new Address(1L, "Street1", "City1", "Zip1", "yes");
        UserData userData1 = new UserData(1, "Username1", "Password1", "email@gmail.com", true, Set.of(RoleEntity.builder().id(1).role("PATIENT").build()));

        PatientDTO patient1 = PatientDTO.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("1234567890")
                .phone("123-456-789")
                .address(address1)
                .user(userData1)
                .build();

        PatientDTO patient2 = PatientDTO.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("1234567890")
                .phone("123-456-789")
                .address(address1)
                .user(userData1)
                .build();

        assertTrue(patient1.canEqual(patient2));
    }

    @Test
    public void testHashCode() {
        Address address1 = new Address(1L, "Street1", "City1", "Zip1", "yes");
        Address address2 = new Address(2L, "Street2", "City2", "Zip2", "yes");
        UserData userData1 = new UserData(1, "Username1", "Password1", "email@gmail.com", true, Set.of(RoleEntity.builder().id(1).role("PATIENT").build()));
        UserData userData2 = new UserData(2, "Username2", "Password2", "email@gmail.com",true, Set.of(RoleEntity.builder().id(1).role("PATIENT").build()));


        PatientDTO patient1 = PatientDTO.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("1234567890")
                .phone("123-456-789")
                .address(address1)
                .user(userData1)
                .build();

        PatientDTO patient2 = PatientDTO.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("1234567890")
                .phone("123-456-789")
                .address(address1)
                .user(userData1)
                .build();

        PatientDTO patient3 = PatientDTO.builder()
                .name("Jane")
                .surname("Doe")
                .email("jane.doe@example.com")
                .pesel("9876543210")
                .phone("987-654-321")
                .address(address2)
                .user(userData2)
                .build();

        int hashCode1 = patient1.hashCode();
        int hashCode2 = patient2.hashCode();
        int hashCode3 = patient3.hashCode();

        assertEquals(hashCode1, hashCode2);
        Assertions.assertNotEquals(hashCode1, hashCode3);
    }
}
