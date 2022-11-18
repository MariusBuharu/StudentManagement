package com.spring.student_management.models;

import com.spring.student_management.models.parents.PrimaryModel;
import com.spring.student_management.utils.DbConstants;
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
@Entity(name = ENTITY_ADDRESS_TYPE)
@Table(name = TABLE_ADDRESS_TYPE, schema = SCHEMA_PUBLIC)
@SuperBuilder
public class AddressType implements PrimaryModel {

    @Id
    private Long id;
    @Column(name = TYPE, length = DbConstants.LENGTH_65, unique = true, nullable = false)
    private String type;
}
