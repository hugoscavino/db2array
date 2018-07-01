package com.ijudy.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ijudy.domain.Employee;
import com.ijudy.mapper.EmployeeMapper;
import com.ijudy.service.EmployeeService;

/**
 * EmployeeService simply receives an array of emp numbers and uses a mapper to get a list of
 * employees from the database.
 */
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
	
	private EmployeeMapper mapper;

	public void setEmployeMapper(EmployeeMapper mapper) {
	   this.mapper = mapper;
	}
	  
	public List<Employee> getEmployees(List<String> empNumbers) {
	  return this.mapper.getEmployees(empNumbers);
	}
}
