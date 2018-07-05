package com.ijudy.dao.impl;

import java.math.BigDecimal;
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
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.ijudy.dao.EmployeeDao;
import com.ijudy.dao.EmployeeRowMapper;
import com.ijudy.domain.Employee;

public class EmployeeDaoImpl implements EmployeeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	final String PROC_NAME 	= "GET_BY_EMPNUMBERS";
	final String SCHEMA 	= "IJUDY";
	final String SCHEMA_PROC_NAME = "{call " + SCHEMA + "." + PROC_NAME + "(?,?,?,?)}";
	final String ARRAY_TYPE = "VARCHAR";
	
	@Override
	public List<Employee> getEmployeesCallableStatment(String[] empNumbers) throws SQLException {

		Connection connection = null;
		List<Employee> list = new ArrayList<Employee>();

		try {

			// Get Connection instance from dataSource
			connection = jdbcTemplate.getDataSource().getConnection();
			CallableStatement callableSt = connection.prepareCall(SCHEMA_PROC_NAME);

			Array EMP_ARRAY = connection.createArrayOf("VARCHAR", empNumbers);

			callableSt.setArray(1, EMP_ARRAY);
			callableSt.registerOutParameter(2, Types.CHAR);
			callableSt.registerOutParameter(3, Types.INTEGER);
			callableSt.registerOutParameter(4, Types.VARCHAR);

			// Call Stored Procedure
			ResultSet resultset = callableSt.executeQuery();
			if (resultset != null) {
				while (resultset.next()) {
					EmployeeRowMapper mapper = new EmployeeRowMapper();
					Employee employee = mapper.mapRow(resultset, resultset.getRow());
					list.add(employee);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			if (connection != null) {
				connection.close();
			}
		}
		return list;
	}

	@Override
	public List<Employee> getEmployeesCallableStatementCreator(String[] empNumbers) throws SQLException {

		List<Employee> list = new ArrayList<Employee>();

		SqlParameter empIds = new SqlParameter(Types.ARRAY);
		SqlOutParameter O_STATUS = new SqlOutParameter("O_STATUS", Types.CHAR);
		SqlOutParameter O_ERROR_CD = new SqlOutParameter("O_ERROR_CD", Types.INTEGER);
		SqlOutParameter O_ERROR_MSG = new SqlOutParameter("O_ERROR_MSG", Types.VARCHAR);

		List<SqlParameter> paramList = new ArrayList<SqlParameter>();
		paramList.add(empIds);
		paramList.add(O_STATUS);
		paramList.add(O_ERROR_CD);
		paramList.add(O_ERROR_MSG);

		Map<String, Object> resultMap = jdbcTemplate.call(new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection connection) throws SQLException {
				Array EMP_ARRAY = connection.createArrayOf(ARRAY_TYPE, empNumbers);
				CallableStatement callableStatement = connection.prepareCall(SCHEMA_PROC_NAME);
				callableStatement.setArray(1, EMP_ARRAY);
				callableStatement.registerOutParameter(2, Types.CHAR);
				callableStatement.registerOutParameter(3, Types.INTEGER);
				callableStatement.registerOutParameter(4, Types.VARCHAR);
				return callableStatement;

			}
		}, paramList);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> resultsList = (List<Map<String, Object>>)resultMap.get("#result-set-1");
		for (Map<String, Object> row :  resultsList) {
			Employee employee = new Employee();
	        employee.setEmpNo((String)row.get("EMPNO"));
			employee.setFirstName((String)row.get("FIRSTNME"));
			employee.setMiddle((String)row.get("MIDINIT"));
			employee.setLastName((String)row.get("LASTNAME"));
			employee.setWorkDept((String)row.get("WORKDEPT"));
			employee.setPhone((String)row.get("PHONENO"));
			employee.setHireDate((java.sql.Date)row.get("HIREDATE"));
			employee.setJob((String)row.get("JOB"));
			employee.setEduLevel((Integer)row.get("EDLEVEL"));		
			employee.setSex((String)row.get("SEX"));
			employee.setBirth((java.sql.Date)row.get("BIRTHDATE"));
			employee.setSalary((BigDecimal)row.get("SALARY"));
			employee.setBonus((BigDecimal)row.get("BONUS"));
			employee.setCommission((BigDecimal)row.get("COMM"));
			list.add(employee);
		}
 
		return list;

	}
	
	@Override
	public List<Employee> getEmployeesSimpleJdbcCall(String[] empNumbers) throws SQLException {

		jdbcTemplate.setResultsMapCaseInsensitive(true);
		
		List<Employee> list = new ArrayList<Employee>();
		
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).
										withProcedureName(PROC_NAME).
										withSchemaName(SCHEMA).
										withoutProcedureColumnMetaDataAccess().
										useInParameterNames("I_EMP_ID_ARRAY").
										declareParameters(
												new SqlParameter("I_EMP_ID_ARRAY", Types.ARRAY),
												new SqlOutParameter("O_STATUS", Types.CHAR),
												new SqlOutParameter("O_ERROR_CD", Types.INTEGER),
												new SqlOutParameter("O_ERROR_MSG", Types.VARCHAR)
										).returningResultSet("employees", new EmployeeRowMapper());

		Connection conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
		Array EMP_ARRAY = conn.createArrayOf(ARRAY_TYPE, empNumbers);
		
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource().
													addValue("I_EMP_ID_ARRAY", EMP_ARRAY);
		Map<String, Object> rs = simpleJdbcCall.execute(sqlParameterSource);
		
		@SuppressWarnings("unchecked")
		List<Employee> employees = (List<Employee>)rs.get("employees");
		
		String status  = (String)rs.get("O_STATUS");
		
		if (employees != null) {
			list.addAll(employees);
		}
		return list;
	}

}

