package com.spring.student_management.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.student_management.dto.parents.BaseDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.Period;

import static com.spring.student_management.dto.utils.DtoConstants.DATE_FORMAT;



@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class AppUserDto extends BaseDto {
    private static final long serialVersionUID = -6139898218655876698L;

    private AddressDto address;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String phoneNumber;
    private String password;
    private RoleDto role;
    @JsonFormat(pattern = DATE_FORMAT)
    @DateTimeFormat(pattern = DATE_FORMAT)
    private LocalDate dob;

    private Boolean isActive;

    public int getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }
}
