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
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.ijudy.dao.EmployeeDao;
import com.ijudy.dao.EmployeeRowMapper;
import com.ijudy.domain.Employee;

public class EmployeeDaoImpl implements EmployeeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	final String SCHEMA_PROC_NAME = "{call IJUDY.GET_BY_EMPNUMBERS(?,?,?,?)}";
	final String PROC_NAME = "GET_BY_EMPNUMBERS";
	final String SCHEMA = "IJUDY";
	final String ARRAY_TYPE = "VARCHAR";
	@Override
	public List<Employee> getEmployees(String[] empNumbers) throws SQLException {

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate.getDataSource())
				.withProcedureName(PROC_NAME)
				.withSchemaName(SCHEMA);

		List<Employee> list = new ArrayList<Employee>();

		Connection conn = jdbcTemplate.getDataSource().getConnection();

		Array empArray = conn.createArrayOf(ARRAY_TYPE, empNumbers);

		SqlParameterSource I_EMP_ID_ARRAY = new MapSqlParameterSource().addValue("I_EMP_ID_ARRAY", empArray);
		Map<String, Object> rs = jdbcCall.execute(I_EMP_ID_ARRAY);
		return list;
	}

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
					Employee employee = new Employee();
			        employee.setEmpNo(resultset.getString("EMPNO"));
					employee.setFirstName(resultset.getString("FIRSTNME"));
					employee.setMiddle(resultset.getString("MIDINIT"));
					employee.setLastName(resultset.getString("LASTNAME"));
					employee.setWorkDept(resultset.getString("WORKDEPT"));
					employee.setPhone(resultset.getString("PHONENO"));
					employee.setHireDate(resultset.getDate("HIREDATE"));
					employee.setJob(resultset.getString("JOB"));
					employee.setEduLevel(resultset.getString("EDLEVEL"));		
					employee.setSex(resultset.getString("SEX"));
					employee.setBirth(resultset.getDate("BIRTHDATE"));
					employee.setSalary(resultset.getFloat("SALARY"));
					employee.setBonus(resultset.getFloat("BONUS"));
					employee.setCommission(resultset.getFloat("COMM"));
					list.add(employee);
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
				Array EMP_ARRAY = connection.createArrayOf("VARCHAR", empNumbers);
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
			employee.setEduLevel((String)row.get("EDLEVEL"));		
			employee.setSex((String)row.get("SEX"));
			employee.setBirth((java.sql.Date)row.get("BIRTHDATE"));
			employee.setSalary((Float)row.get("SALARY"));
			employee.setBonus((Float)row.get("BONUS"));
			employee.setCommission((Float)row.get("COMM"));
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
										withSchemaName("IJUDY").
										withoutProcedureColumnMetaDataAccess().
										useInParameterNames("I_EMP_ID_ARRAY").
										declareParameters(
												new SqlParameter("I_EMP_ID_ARRAY", Types.ARRAY),
												new SqlOutParameter("O_STATUS", Types.CHAR),
												new SqlOutParameter("O_ERROR_CD", Types.INTEGER),
												new SqlOutParameter("O_ERROR_MSG", Types.VARCHAR)
										).returningResultSet("employIds", new EmployeeRowMapper());
		
		Array EMP_ARRAY = jdbcTemplate.getDataSource().getConnection().createArrayOf("VARCHAR", empNumbers);
		
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("I_EMP_ID_ARRAY", EMP_ARRAY);
		Map<String, Object> rs = simpleJdbcCall.execute(sqlParameterSource);
		
		@SuppressWarnings("unchecked")
		List<Employee> obj = (List<Employee>)rs.get("employIds");
		
		String status  = (String)rs.get("O_STATUS");
		
		if (obj != null) {
			list.addAll(obj);
		}
		return list;
	}

}

