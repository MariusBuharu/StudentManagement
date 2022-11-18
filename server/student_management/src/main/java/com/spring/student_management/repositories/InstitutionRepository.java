package com.spring.student_management.repositories;

import com.spring.student_management.models.AppUser;
import com.spring.student_management.models.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    @Query(value = "SELECT i from Institution i left join fetch i.appUsers")
    List<AppUser> findInstitutionUsers();
    void deleteByInstitutionName(@Param("institutionName") String institutionName);
}
