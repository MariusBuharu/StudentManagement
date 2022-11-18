package com.spring.student_management.dto.mapper;

import com.spring.student_management.dto.RoleDto;
import com.spring.student_management.models.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleMapper {

    public static RoleDto toDto(Role role) {

        return RoleDto.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .build();
    }

    public static Role toModel(RoleDto roleDto) {
        return Role.builder()
                .id(roleDto.getId())
                .roleName(roleDto.getRoleName())
                .build();
    }

    public static List<RoleDto> toDtoList(List<Role> roles) {
        return roles.stream().map(RoleMapper::toDto).collect(Collectors.toList());
    }

    public static List<Role> toModelList(List<RoleDto> roleDtoList) {
        return roleDtoList.stream().map(RoleMapper::toModel).collect(Collectors.toList());
    }
}
