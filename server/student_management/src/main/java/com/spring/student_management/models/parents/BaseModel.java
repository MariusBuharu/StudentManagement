package com.spring.student_management.models.parents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

import static com.spring.student_management.utils.DbConstants.DATE_ADDED;
import static com.spring.student_management.utils.DbConstants.VERSION;



@Getter
@Setter
@ToString
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
public abstract class BaseModel implements PrimaryModel {
    @Column(name = DATE_ADDED, updatable = false, columnDefinition = "TIMESTAMP NOT NULL DEFAULT NOW()::TIMESTAMP")
    protected final LocalDateTime dateAdded = LocalDateTime.now();
    @Version
    @Column(name = VERSION, nullable = false, columnDefinition = "INT NOT NULL DEFAULT 0")
    protected int version;
}
