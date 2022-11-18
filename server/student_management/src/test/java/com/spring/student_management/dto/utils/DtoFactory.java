package com.spring.student_management.dto.utils;

import com.spring.student_management.dto.AppUserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.lang.Boolean.*;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DtoFactory {

    public static AppUserDto createUserDto(){
        return  AppUserDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Smith")
                .email("john@email.test")
                .username("Johnny")
                .isActive(TRUE)
                .password("password123")
                .build();
    }
}
