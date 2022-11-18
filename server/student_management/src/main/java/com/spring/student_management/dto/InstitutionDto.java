package com.spring.student_management.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.student_management.dto.parents.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

import static com.spring.student_management.dto.utils.DtoConstants.DATE_FORMAT;


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class InstitutionDto extends BaseDto {
    private static final long serialVersionUID = -471817483815538513L;

    private List<AppUserDto> appUsers;
    private String institutionName;
    private String description;
    private AddressDto address;

    @JsonFormat(pattern = DATE_FORMAT)
    @DateTimeFormat(pattern = DATE_FORMAT)
    private LocalDate foundedDate;
}
