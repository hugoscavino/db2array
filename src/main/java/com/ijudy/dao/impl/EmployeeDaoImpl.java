package com.ijudy.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ijudy.dao.EmployeeDao;
import com.ijudy.domain.Employee;

public class EmployeeDaoImpl extends SqlSessionDaoSupport implements EmployeeDao{

	public List<Employee> getEmployees(List<String> empNumbers) {
		return  getSqlSession().selectList("org.mybatis.spring.sample.mapper.EmployeeMapper.getEmployees", empNumbers);
	}
	
}
