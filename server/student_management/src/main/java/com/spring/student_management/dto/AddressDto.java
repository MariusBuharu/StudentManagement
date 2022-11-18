package com.spring.student_management.dto;

import com.spring.student_management.dto.parents.PrimaryDto;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class AddressDto extends PrimaryDto {
    private static final long serialVersionUID = 815990747766625843L;

    private AddressTypeDto type;
    private String country;
    private String city;
    private String addressLineOne;
    private String addressLineTwo;
}
