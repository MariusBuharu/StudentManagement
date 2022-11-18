package com.spring.student_management.models;

import com.spring.student_management.models.parents.BaseModel;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;

import static com.spring.student_management.utils.DbConstants.*;



@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = ENTITY_INSTITUTION)
@Table(name = TABLE_INSTITUTION, schema = SCHEMA_PUBLIC)
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class Institution extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_INSTITUTION)
    @SequenceGenerator(name = SEQ_INSTITUTION, allocationSize = 1)
    @Column(name = ID)
    private Long id;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = TABLE_APP_USER_INSTITUTION,
            joinColumns = @JoinColumn(name = INSTITUTION_ID, nullable = false),
            inverseJoinColumns = @JoinColumn(name = APP_USER_ID, nullable = false)
    )
    private List<AppUser> appUsers;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, targetEntity = Address.class, fetch = FetchType.EAGER)
    @JoinColumn(name = ADDRESS_ID)
    private Address address;

    @Column(name = INSTITUTION_NAME, length = LENGTH_45, unique = true, nullable = false)
    private String institutionName;

    @Column(name = DESCRIPTION, length = LENGTH_500)
    private String description;

    @Column(name = FOUNDED_DATE)
    private LocalDate foundedDate;
}
