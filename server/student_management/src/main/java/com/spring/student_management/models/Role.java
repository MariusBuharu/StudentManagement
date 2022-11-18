package com.spring.student_management.models;

import com.spring.student_management.models.parents.PrimaryModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.spring.student_management.utils.DbConstants.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = ENTITY_ROLE)
@Table(name = TABLE_ROLE, schema = SCHEMA_PUBLIC)
public class Role implements PrimaryModel {
    @Id
    private Long id;

    @Column(name = ROLE_NAME, length = LENGTH_45, nullable = false)
    private String roleName;
}
