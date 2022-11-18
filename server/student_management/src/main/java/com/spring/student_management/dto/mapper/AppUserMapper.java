package com.spring.student_management.dto.mapper;

import com.spring.student_management.dto.AppUserDto;
import com.spring.student_management.models.AppUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppUserMapper {

    public static AppUser toModel(AppUserDto userDto) {
        return AppUser.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .username(userDto.getUsername())
                .phoneNumber(userDto.getPhoneNumber())
                .dob(userDto.getDob())
                .isActive(userDto.getIsActive() == null || userDto.getIsActive())
                .role(userDto.getRole() != null ? RoleMapper.toModel(userDto.getRole()) : null)
                .address(userDto.getAddress() != null ? AddressMapper.toModel(userDto.getAddress()) : null)
                .version(userDto.getVersion())
                .build();
    }

    public static AppUserDto toDto(AppUser appUser) {
        return AppUserDto.builder()
                .id(appUser.getId())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .email(appUser.getEmail())
                .password(appUser.getPassword())
                .username(appUser.getUsername())
                .phoneNumber(appUser.getPhoneNumber())
                .dob(appUser.getDob())
                .isActive(appUser.isActive())
                .role(appUser.getRole() != null ? RoleMapper.toDto(appUser.getRole()) : null)
                .address(appUser.getAddress() != null ? AddressMapper.toDto(appUser.getAddress()) : null)
                .version(appUser.getVersion())
                .dateAdded(appUser.getDateAdded())
                .build();
    }

    public static List<AppUserDto> toDtoList(List<AppUser> appUserList) {
        return appUserList.stream()
                .map(AppUserMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<AppUser> toModelList(List<AppUserDto> appUserDtoList) {
        return appUserDtoList.stream()
                .map(AppUserMapper::toModel)
                .collect(Collectors.toList());
    }
}
