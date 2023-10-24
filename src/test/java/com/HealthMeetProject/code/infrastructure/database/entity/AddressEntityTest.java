package com.HealthMeetProject.code.infrastructure.database.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AddressEntityTest {
    @Test
    public void equalsTest() {
        AddressEntity addressEntity1 = new AddressEntity();
        addressEntity1.setAddressId(3);
        AddressEntity addressEntity2 = new AddressEntity();
        addressEntity2.setAddressId(3);
        Assertions.assertEquals(addressEntity1, addressEntity2);
        addressEntity2.setAddressId(5);

        Assertions.assertNotEquals(addressEntity2.hashCode(), addressEntity1.hashCode());
    }
}