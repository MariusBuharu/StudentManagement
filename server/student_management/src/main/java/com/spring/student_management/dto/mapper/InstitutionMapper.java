package com.spring.student_management.dto.mapper;

import com.spring.student_management.dto.InstitutionDto;
import com.spring.student_management.models.Institution;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InstitutionMapper {

    public static InstitutionDto toDto(Institution institution) {

        return InstitutionDto.builder()
                .id(institution.getId())
                .institutionName(institution.getInstitutionName())
                .address(institution.getAddress() != null ? AddressMapper.toDto(institution.getAddress()) : null)
                .appUsers(institution.getAppUsers() != null ?
                        AppUserMapper.toDtoList(institution.getAppUsers()) : Collections.emptyList())
                .description(institution.getDescription())
                .foundedDate(institution.getFoundedDate())
                .dateAdded(institution.getDateAdded())
                .version(institution.getVersion())
                .build();
    }

    public static Institution toModel(InstitutionDto institutionDto) {

        return Institution.builder()
                .id(institutionDto.getId())
                .institutionName(institutionDto.getInstitutionName())
                .address(institutionDto.getAddress() != null ? AddressMapper.toModel(institutionDto.getAddress()) : null)
                .appUsers(institutionDto.getAppUsers() != null ?
                        AppUserMapper.toModelList(institutionDto.getAppUsers()) : Collections.emptyList())
                .description(institutionDto.getDescription())
                .foundedDate(institutionDto.getFoundedDate())
                .version(institutionDto.getVersion())
                .build();
    }

    public static List<InstitutionDto> toDtoList(List<Institution> institutions) {
        return institutions.stream()
                .map(InstitutionMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Institution> toModelList(List<InstitutionDto> institutionDtoList) {
        return institutionDtoList.stream()
                .map(InstitutionMapper::toModel)
                .collect(Collectors.toList());
    }
}
