package com.spring.student_management.dto.mapper;

import com.spring.student_management.dto.AddressDto;
import com.spring.student_management.models.Address;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressMapper {

    public static AddressDto toDto(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .type(address.getType() != null ? AddressTypeMapper.toDto(address.getType()) : null)
                .country(address.getCountry())
                .city(address.getCity())
                .addressLineOne(address.getAddressLineOne())
                .addressLineTwo(address.getAddressLineTwo())
                .build();
    }

    public static Address toModel(AddressDto addressDto) {
        return Address.builder()
                .id(addressDto.getId())
                .type(addressDto.getType() != null ? AddressTypeMapper.toModel(addressDto.getType()) : null)
                .country(addressDto.getCountry())
                .city(addressDto.getCity())
                .addressLineOne(addressDto.getAddressLineOne())
                .addressLineTwo(addressDto.getAddressLineTwo())
                .build();
    }

    public static List<AddressDto> toDtoList(List<Address> addressList) {
        return addressList.stream()
                .map(AddressMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Address> toModelList(List<AddressDto> addressDtoList) {
        return addressDtoList.stream()
                .map(AddressMapper::toModel)
                .collect(Collectors.toList());
    }
}
