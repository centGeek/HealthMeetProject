package com.HealthMeetProject.code.api.dto.mapper;

import com.HealthMeetProject.code.api.dto.UserData;
import com.HealthMeetProject.code.infrastructure.database.entity.UserEntity;
import com.HealthMeetProject.code.util.PatientDTOFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserEntityMapperTest {
    private UserEntityMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(UserEntityMapper.class);
    }

    @Test
    public void shouldMapPatientEntityToPatient() {
        // given
        UserData userData1 = PatientDTOFixtures.userDataPatient();
        UserData userData2 = PatientDTOFixtures.userDataPatient();
        //when
        UserEntity user = mapper.mapToEntity(userData1);

        // then
        assertions(userData1,user);
        assertEquals(userData1, userData2);
    }
    private static void assertions(UserData userData, UserEntity userEntity) {
        assertEquals(userData.getEmail(), userEntity.getEmail());
        assertEquals(userData.getPassword(), userEntity.getPassword());
        assertEquals(userData.getActive(), userEntity.getActive());
        assertEquals(userData.getRoles(), userEntity.getRoles());
        assertEquals(userData.getId(), userEntity.getId());
        assertEquals(userData.getUserName(), userEntity.getUserName());
    }
}