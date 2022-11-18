package com.spring.student_management.dto;

import com.spring.student_management.dto.utils.DtoFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class InstitutionDtoTest {

    private static final long TEST_ID = 1L;
    private InstitutionDto institutionDto;
    private final AppUserDto userDto = DtoFactory.createUserDto();
    private static final String INSTITUTION_NAME = "Test institution";
    private static final String INSTITUTION_ADDRESS = "Junit5 test street, building Spring Boot";
    private static final String INSTITUTION_DESCRIPTION = "Spring boot institution test";
    public static final String TEST_TYPE = "Test type";
    private static final LocalDate FOUNDED_DATE = LocalDate.of(1998, Month.OCTOBER, 22);

    private final AddressDto addressDto = AddressDto.builder().id(TEST_ID)
            .country("Test country")
            .city("Test city")
            .addressLineOne(INSTITUTION_ADDRESS)
            .addressLineTwo("test line two")
            .type(AddressTypeDto.builder().id(TEST_ID).type(TEST_TYPE).build())
            .build();

    @BeforeEach
    void setUp() {
        institutionDto = InstitutionDto.builder()
                .id(1L)
                .institutionName(INSTITUTION_NAME)
                .address(addressDto)
                .description(INSTITUTION_DESCRIPTION)
                .foundedDate(FOUNDED_DATE)
                .appUsers(Collections.singletonList(userDto))
                .dateAdded(LocalDateTime.now())
                .build();
    }

    @Test
    void getId() {
        assertNotNull(institutionDto.getId());
    }

    @Test
    void getAppUsers() {
        assertTrue(institutionDto.getAppUsers().size() == 1 &&
                institutionDto.getAppUsers().contains(userDto));
    }

    @Test
    void getInstitutionName() {
        assertEquals(INSTITUTION_NAME, institutionDto.getInstitutionName());
    }

    @Test
    void getDescription() {
        assertEquals(INSTITUTION_DESCRIPTION, institutionDto.getDescription());
    }

    @Test
    void getAddress() {
        assertAll("institutionAddress", () -> {
            final AddressDto address = institutionDto.getAddress();
            assertNotNull(address);
            assertEquals(TEST_ID, address.getId());
            assertEquals(INSTITUTION_ADDRESS, address.getAddressLineOne());
            assertAll("addressType", () -> {
                final AddressTypeDto addressType = address.getType();
                assertNotNull(addressType);
                assertEquals(TEST_ID, addressType.getId());
                assertEquals(TEST_TYPE, addressType.getType());
            });
        });
    }

    @Test
    void getFoundedDate() {
        assertEquals(FOUNDED_DATE, institutionDto.getFoundedDate());
    }

    @Test
    void getDateAdded() {
        assertNotNull(institutionDto.getDateAdded());
    }
}