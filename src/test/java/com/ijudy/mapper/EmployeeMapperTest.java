package com.ijudy.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	 final static String EMP_ID_10 = "000010";
	 final static String EMP_ID_20 = "000020";
	 final static String EMP_ID_30 = "000030";
	
	 final static String[] EMP_IDS = new String[]{EMP_ID_10,
												EMP_ID_20,
												EMP_ID_30};
	  @Test
	  final public void testMapper() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("I_EMP_ID_ARRAY", EMP_IDS);
	    List<Employee> emps = this.employeeMapper.getEmployees(params);
		
	    assertThat(emps).isNotNull();
	    assertThat(emps.get(0)).isNotNull();
	    assertThat(emps.get(0).getEmpNo().equalsIgnoreCase("000010"));
	    
	    assertThat(emps.get(1)).isNotNull();
	    assertThat(emps.get(1).getEmpNo().equalsIgnoreCase("000020"));
	  }
}
