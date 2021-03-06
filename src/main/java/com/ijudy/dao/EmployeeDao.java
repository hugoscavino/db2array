/**
 *    Copyright 2010-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.ijudy.dao;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ijudy.domain.Employee;

@Component
public interface EmployeeDao {

	/**
	 * Using a more high level Spring SimpleJdbcCall
	 * 
	 * @param empNumbers
	 * @return
	 * @throws SQLException
	 */
	public List<Employee> getEmployeesSimpleJdbcCall(String[] empNumbers) throws SQLException;

	/**
	 * Using the simple CallableStatment
	 * 
	 * @param empNumbers String[]
	 * @return List<Employee> that meet that criteria
	 * @throws SQLException
	 */
	public List<Employee> getEmployeesCallableStatment(String[] empNumbers) throws SQLException;
	
	/**
	 * Using the low level CallableStatementCreator
	 * 
	 * @param empNumbers
	 * @return
	 * @throws SQLException
	 */
	public List<Employee> getEmployeesCallableStatementCreator(String[] empNumbers) throws SQLException;
	
}
