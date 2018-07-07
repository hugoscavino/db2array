package com.ijudy.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ijudy.domain.Employee;

/**
 * 
 * RowMapper for an Employee
 * @see org.springframework.jdbc.core.RowMapper
 * 
 * "EMPNO" CHAR(6 OCTETS) NOT NULL,
 * "FIRSTNME" VARCHAR(12 OCTETS) NOT NULL,
 * "MIDINIT" CHAR(1 OCTETS),
 * "LASTNAME" VARCHAR(15 OCTETS) NOT NULL,
 * "WORKDEPT" CHAR(3 OCTETS),
 * "PHONENO" CHAR(4 OCTETS),
 * "HIREDATE" DATE,
 * "JOB" CHAR(8 OCTETS),
 * "EDLEVEL" SMALLINT NOT NULL,
 * "SEX" CHAR(1 OCTETS),
 * "BIRTHDATE" DATE,
 * "SALARY" DECIMAL(9 , 2),
 * "BONUS" DECIMAL(9 , 2),
 * "COMM" DECIMAL(9 , 2)
 * 
 * @author ijudy
 *
 */
public class EmployeeRowMapper implements RowMapper<Employee> {
   
	@Override
    public Employee mapRow(ResultSet resultset, int rowNum) throws SQLException {
        Employee employee = new Employee();

        employee.setEmpNo(resultset.getString("EMPNO"));
		employee.setFirstName(resultset.getString("FIRSTNME"));
		employee.setMiddle(resultset.getString("MIDINIT"));
		employee.setLastName(resultset.getString("LASTNAME"));
		employee.setWorkDept(resultset.getString("WORKDEPT"));
		employee.setPhone(resultset.getString("PHONENO"));
		employee.setHireDate(resultset.getDate("HIREDATE"));
		employee.setJob(resultset.getString("JOB"));
		employee.setEduLevel(resultset.getInt("EDLEVEL"));		
		employee.setSex(resultset.getString("SEX"));
		employee.setBirth(resultset.getDate("BIRTHDATE"));
		employee.setSalary(resultset.getBigDecimal("SALARY"));
		employee.setBonus(resultset.getBigDecimal("BONUS"));
		employee.setCommission(resultset.getBigDecimal("COMM"));
        return employee;
    }
}
