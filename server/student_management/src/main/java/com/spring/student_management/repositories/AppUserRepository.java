package com.spring.student_management.repositories;

import com.spring.student_management.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query(value = "SELECT u FROM AppUser u WHERE u.email = ?1")
    AppUser findByEmail(@Param("email") String email);

    @Query(value = "SELECT u.* FROM app_user_institution au\n" +
            "LEFT JOIN app_user u ON u.id = au.app_user_id\n" +
            "LEFT JOIN institution i ON i.id = au.institution_id \n" +
            "WHERE au.institution_id = ?1", nativeQuery = true)
    List<AppUser> findALlByInstitution(@Param("institutionId") long institutionId);

    @Query(value = "SELECT u FROM AppUser u WHERE u.email = ?1 OR u.username = ?1")
    Optional<AppUser> findByUserNameOrEmail(@Param("userNameOrEmail") String userNameOrEmail);

}
