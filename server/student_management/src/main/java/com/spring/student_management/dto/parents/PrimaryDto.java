package com.spring.student_management.dto.parents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@SuperBuilder
public class PrimaryDto implements Serializable {

    private static final long serialVersionUID = 1240598547573359025L;
    protected Long id;
}
