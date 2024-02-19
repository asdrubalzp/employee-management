package com.asdrubalzp.employeemanagement.repositories;

import com.asdrubalzp.employeemanagement.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
