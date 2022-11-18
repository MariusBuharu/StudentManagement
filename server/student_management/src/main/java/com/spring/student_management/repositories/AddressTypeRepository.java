package com.spring.student_management.repositories;

import com.spring.student_management.models.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressTypeRepository extends JpaRepository<AddressType, Long> {
}
