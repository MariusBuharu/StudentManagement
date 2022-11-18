package com.spring.student_management.dto.mapper;

import com.spring.student_management.dto.AddressTypeDto;
import com.spring.student_management.models.AddressType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressTypeMapper {

    public static AddressTypeDto toDto(AddressType addressType) {
        return AddressTypeDto.builder()
                .id(addressType.getId())
                .type(addressType.getType())
                .build();
    }

    public static AddressType toModel(AddressTypeDto addressTypeDto) {
        return AddressType.builder()
                .id(addressTypeDto.getId())
                .type(addressTypeDto.getType())
                .build();
    }
}
