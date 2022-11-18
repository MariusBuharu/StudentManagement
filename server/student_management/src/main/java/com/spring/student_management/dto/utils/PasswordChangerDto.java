package com.spring.student_management.dto.utils;

import lombok.*;

/**
 * Created at 11/13/2022 by Darius
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class PasswordChangerDto {

    private String oldPassword;

    private String newPassword;

    private String confirmedPassword;
}
