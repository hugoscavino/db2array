package com.ijudy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ijudy.domain.Employee;

@Service
public interface EmployeeService {

	  public List<Employee> getEmployees(List<String> empNumbers);
}
