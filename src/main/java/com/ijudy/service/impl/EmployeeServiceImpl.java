package com.ijudy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ijudy.domain.Employee;
import com.ijudy.mapper.EmployeeMapper;
import com.ijudy.service.EmployeeService;

/**
 * EmployeeService simply receives an array of employees  and uses a mapper to get a list of
 * employees from the database.
 * 
 * There a number of implementations to choose from in this sample project
 * 
 * Choose one that works for your design
 * 
 */
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
	
	private EmployeeMapper mapper;

	public void setEmployeMapper(EmployeeMapper mapper) {
	   this.mapper = mapper;
	}
	  

	@Override
	public List<Employee> getEmployees(String[] emp_ids) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("I_EMP_ID_ARRAY", emp_ids);
		return this.mapper.getEmployees(params);
	}
}
