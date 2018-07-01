package com.ijudy.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
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

/**
 * Tests for {@link EmployeeMapper}.
 */
@RunWith(SpringRunner.class)
@JdbcTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace=Replace.NONE)
@SpringBootTest(classes=AppConfig.class, webEnvironment=WebEnvironment.NONE)
public class EmployeeMapperTest {
	
	 @Autowired
	 private EmployeeMapper employeeMapper;

	  @Test
	  final public void testMapper() {
		  
		List<String> params = new ArrayList<String>(2);
		params.add("000010");
		params.add("000020");
		
	    List<Employee> emps = this.employeeMapper.getEmployees(params);
	    assertThat(emps).isNotNull();
	    assertThat(emps.get(0)).isNotNull();
	    assertThat(emps.get(0).getEmpNo().equalsIgnoreCase("000010"));
	    
	    assertThat(emps.get(1)).isNotNull();
	    assertThat(emps.get(1).getEmpNo().equalsIgnoreCase("000020"));
	  }
}
