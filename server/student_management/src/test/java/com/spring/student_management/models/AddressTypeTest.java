package com.spring.student_management.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class AddressTypeTest extends AbstractModelTest<AddressType> {

    @BeforeEach
    void setUp() {
        this.model = AddressType.builder()
                .id(1L)
                .type("Test address")
                .build();
    }

    @Test
    void setId() {
        assertEquals(1L, this.model.getId());
        this.model.setId(2L);
        assertEquals(2L, this.model.getId());
    }

    @Test
    void setType() {
        assertEquals("Test address", this.model.getType());
        this.model.setType(TEST);
        assertEquals(TEST, this.model.getType());
    }

    @Override
    @Test
    protected void testNoArgConstructor() {
        this.model = new AddressType();
        assertNotNull(this.model);
    }

    @Override
    @Test
    protected void testAllArgConstructor() {
        this.model = new AddressType(3L, TEST_CONSTRUCTOR);
        assertAll(TEST_CONSTRUCTOR, () -> {
            assertNotNull(this.model);
            assertEquals(3L, this.model.getId());
            assertEquals(TEST_CONSTRUCTOR, this.model.getType());
        });
    }
}