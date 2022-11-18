package com.spring.student_management.models;

import com.spring.student_management.models.parents.BaseModel;
import com.spring.student_management.models.parents.PrimaryModel;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;

import static com.spring.student_management.utils.DbConstants.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = ENTITY_ADDRESS)
@Table(name = TABLE_ADDRESS, schema = SCHEMA_PUBLIC)
public class Address implements PrimaryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_ADDRESS)
    @SequenceGenerator(name = SEQ_ADDRESS, schema = SCHEMA_PUBLIC, allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = ADDRESS_TYPE_ID, nullable = false, unique = true)
    private AddressType type;

    @Column(name = COUNTRY, length = LENGTH_45, nullable = false)
    private String country;
    @Column(name = CITY, length = LENGTH_45, nullable = false)
    private String city;
    @Column(name = ADDRESS_LINE_ONE, length = LENGTH_500, nullable = false)
    private String addressLineOne;
    @Column(name = ADDRESS_LINE_TWO, length = LENGTH_500)
    private String addressLineTwo;
}
