package com.ijudy.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ijudy.domain.Employee;
import com.ijudy.mapper.EmployeeMapper;

/**
 * EmployeeService simply receives an array of emp numbers and uses a mapper to get a list of
 * employees from the database.
 */
@Transactional
public class EmployeeService {
	  private final EmployeeMapper mapper;

	  public EmployeeService(EmployeeMapper mapper) {
	    this.mapper = mapper;
	  }

	  public List<Employee> getEmployees(List<String> empNumbers) {
	    return this.mapper.getEmployees(empNumbers);
	  }
}
