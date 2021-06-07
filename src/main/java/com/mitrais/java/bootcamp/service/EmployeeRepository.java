package com.mitrais.java.bootcamp.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mitrais.java.bootcamp.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
