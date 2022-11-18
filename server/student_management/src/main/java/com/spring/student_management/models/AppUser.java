package com.spring.student_management.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.student_management.models.parents.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

import static com.spring.student_management.dto.utils.DtoConstants.DATE_FORMAT;
import static com.spring.student_management.utils.DbConstants.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = ENTITY_APP_USER)
@Table(name = TABLE_APP_USER, schema = SCHEMA_PUBLIC)
public class AppUser extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_APP_USER)
    @SequenceGenerator(name = SEQ_APP_USER, allocationSize = 1)
    @Column(name = ID)
    private Long id;

    @ManyToOne(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinColumn(name = ROLE_ID, nullable = false)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = Address.class, fetch = FetchType.EAGER)
    @JoinColumn(name = ADDRESS_ID)
    private Address address;

    @Column(name = FIRST_NAME, length = LENGTH_45, nullable = false)
    private String firstName;

    @Column(name = LAST_NAME, length = LENGTH_45, nullable = false)
    private String lastName;

    @Column(name = EMAIL, length = LENGTH_100, unique = true, nullable = false)
    private String email;

    @Column(name = USERNAME, length = LENGTH_45, unique = true, nullable = false)
    private String username;

    @Column(name = PHONE_NUMBER, length = 11)
    private String phoneNumber;

    @Column(name = PASSWORD, length = LENGTH_100, nullable = false)
    private String password;

    @Column(name = DOB)
    @JsonFormat(pattern = DATE_FORMAT)
    @DateTimeFormat(pattern = DATE_FORMAT)
    private LocalDate dob;

    @Column(name = IS_ACTIVE, nullable = false, columnDefinition = "BOOLEAN NOT NULL DEFAULT TRUE")
    private boolean isActive;

}
