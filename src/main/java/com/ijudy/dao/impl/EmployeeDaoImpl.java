package com.ijudy.dao.impl;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.ijudy.dao.EmployeeDao;
import com.ijudy.domain.Employee;

public class EmployeeDaoImpl implements EmployeeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Employee> getEmployees(String[] empNumbers) throws SQLException {

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate.getDataSource())
				.withProcedureName("GET_BY_EMPNUMBERS");

		List<Employee> list = new ArrayList<Employee>();

		Connection conn = jdbcTemplate.getDataSource().getConnection();

		Array empArray = conn.createArrayOf("CHAR", empNumbers);

		SqlParameterSource I_EMP_ID_ARRAY = new MapSqlParameterSource().addValue("I_EMP_ID_ARRAY", empArray);
		Map<String, Object> out = jdbcCall.execute(I_EMP_ID_ARRAY);
		return list;
	}

	@Override
	public List<Employee> getEmployeesCallableStatment(String[] empNumbers) throws SQLException {
		final String procedureCall = "{call IJUDY.GET_BY_EMPNUMBERS(?,?,?,?)}";
		Connection connection = null;
		List<Employee> list = new ArrayList<Employee>();

		try {

			// Get Connection instance from dataSource
			connection = jdbcTemplate.getDataSource().getConnection();
			CallableStatement callableSt = connection.prepareCall(procedureCall);
			
			Array EMP_ARRAY = connection.createArrayOf("VARCHAR", empNumbers);
			
			callableSt.setArray(1, EMP_ARRAY);
			callableSt.registerOutParameter(2, Types.CHAR);
			callableSt.registerOutParameter(3, Types.INTEGER);
			callableSt.registerOutParameter(4, Types.VARCHAR);

			// Call Stored Procedure
			ResultSet resultset = callableSt.executeQuery();
			if (resultset != null) {
				while (resultset.next()) {
					Employee emp = new Employee();
					
					String EMPNO = resultset.getString("EMPNO");
					emp.setEmpNo(EMPNO);
					
					String FIRSTNME = resultset.getString("FIRSTNME");
					emp.setFirstName(FIRSTNME);
					
					String LASTNAME = resultset.getString("LASTNAME");
					emp.setLastName(LASTNAME);
					
					list.add(emp);
					
				}
			}

			

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			if (connection != null) {
					connection.close();
			}
		}
		return list;
	}


	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
