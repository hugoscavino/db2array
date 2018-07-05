/**
 *    Copyright 2010-2016 the original author or authors.
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
package com.ijudy.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.ijudy.domain.Employee;

/**
 * A org.mybatis.spring sample mapper. This interface will be used by MapperFactoryBean to create a
 * proxy implementation at Spring application startup.
 */


public interface EmployeeMapper {
	  String STOR_PROC_GET_BY_EMPNUMBERS = "call IJUDY.GET_BY_EMPNUMBERS("
		      + "#{I_EMP_ID_ARRAY,	jdbcType=ARRAY,		mode=IN,  typeHandler=com.ijudy.mapper.typehandler.Db2ArrayTypeHandler},"
		      + "#{O_STATUS,		jdbcType=CHAR,		mode=OUT},"
		      + "#{O_ERROR_CD,		jdbcType=INTEGER,	mode=OUT},"
		      + "#{O_ERROR_MSG,		jdbcType=VARCHAR,	mode=OUT})";
	  
	@Results(id = "empResult", value = {
			  @Result(property = "empNo", 		column = "EMPNO", id = true),
			  @Result(property = "firstName", 	column = "FIRSTNME"),
			  @Result(property = "middle", 		column = "middle"),
			  @Result(property = "lastName", 	column = "lastName"),
			  @Result(property = "workDept", 	column = "workDept"),
			  @Result(property = "hireDate", 	column = "hireDate"),
			  @Result(property = "job", 		column = "job"),
			  @Result(property = "eduLevel", 	column = "eduLevel"),
			  @Result(property = "sex", 		column = "sex"),
			  @Result(property = "birth", 		column = "birth"),
			  @Result(property = "salary", 		column = "salary"),
			  @Result(property = "bonus", 		column = "bonus"),
			  @Result(property = "commission", 	column = "commission"),
			})
	@Select(STOR_PROC_GET_BY_EMPNUMBERS)
	@Options(statementType = StatementType.CALLABLE)
	List<Employee> getEmployees(Map<String, Object> params);

}
