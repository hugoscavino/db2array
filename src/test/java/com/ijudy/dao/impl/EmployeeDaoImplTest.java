package com.ijudy.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ijudy.AppConfig;
import com.ijudy.domain.Employee;

@RunWith(SpringRunner.class)
@JdbcTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace=Replace.NONE)
@SpringBootTest(classes=AppConfig.class, webEnvironment=WebEnvironment.NONE)
public class EmployeeDaoImplTest {
	
	@Autowired
	private EmployeeDaoImpl employeeDaoImpl;
	
	final static String EMP_ID_10 = "000010";
	final static String EMP_ID_20 = "000020";
	final static String EMP_ID_30 = "000030";
	
	final static String[] EMP_IDS = new String[]{EMP_ID_10,
												EMP_ID_20,
												EMP_ID_30};
	
	@Test
	public void testGetEmployeesCallableStatment() throws Exception {
		
		
		List<Employee> list = employeeDaoImpl.getEmployeesCallableStatment(EMP_IDS);
		assertNotNull("Employee List is Null", list);
		assertTrue("Employee List is > 0 ", list.size() > 0);
		final String returnedEmpNum = list.get(0).getEmpNo();
		assertTrue("Employee EMPNO eq 000010 instead eq : " + returnedEmpNum, returnedEmpNum.equalsIgnoreCase(EMP_ID_10));
	}
	
	@Test
	public void testGetEmployeesCallableCreator() throws Exception {
		
		List<Employee> list = employeeDaoImpl.getEmployeesCallableStatementCreator(EMP_IDS);
		assertNotNull("Employee List is Null", list);
		assertTrue("Employee List is > 0 ", list.size() > 0);
		final String returnedEmpNum = list.get(0).getEmpNo();
		assertTrue("Employee EMPNO eq 000010 instead eq : " + returnedEmpNum, returnedEmpNum.equalsIgnoreCase(EMP_ID_10));
	}

	@Test
	public void testGetEmployeesSimpleJdbc() throws Exception {
		
		
		List<Employee> list = employeeDaoImpl.getEmployeesSimpleJdbcCall(EMP_IDS);
		assertNotNull("Employee List is Null", list);
		assertTrue("Employee List is > 0 ", list.size() > 0);
		final String returnedEmpNum = list.get(0).getEmpNo();
		assertTrue("Employee EMPNO eq 000010 instead eq : " + returnedEmpNum, returnedEmpNum.equalsIgnoreCase(EMP_ID_10));
	}

}
