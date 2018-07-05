package com.ijudy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ijudy.domain.Employee;

@Service
public interface EmployeeService {

	/**
	 * Retrieve a list of Employees who match this criteria
	 * 
	 * @param empNumbers
	 * @return
	 */
	  public List<Employee> getEmployees(String[] empNumbers);
}
