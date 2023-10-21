package com.HealthMeetProject.code.infrastructure.database.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserEntityTest {
    @Test
    public void equalsTest() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setId(4);
        UserEntity addressEntity2 = new UserEntity();
        addressEntity2.setId(3);
        Assertions.assertNotEquals(addressEntity2.hashCode(), userEntity1.hashCode());
        Assertions.assertNotEquals(userEntity1, addressEntity2);
    }
}