# db2 array with Spring

Working project showing the many ways to invoke a DB2 stored procedure with an Array as an IN parameter. Using Spring boot to ease the
use of JUnits and application configuration.

CallableStatement, Java, Jdbc, SimpleJdbcCall, Spring, Stored Procedure DB2
  
## Overall Steps

### 1 - Create the Type
In order to use Array you will need to create a custom type in DB2.  Work with your company DBA on making this public and not tied to an application specific SCHEMA. For this demo the Array is tied to "IJUDY" schema.  This is for educational purposes only and not intended for production.

### DDL Sample
 'CREATE TYPE "IJUDY"."STRING_ARRAY" AS VARCHAR(128 OCTETS) ARRAY [256];'
 
### 2 - Create the SP using the Type
See the file GET_BY_EMPNUMBERS.sql for a SP that queries the default EMPLOYEE database from IBM's SAMPLE db

## 3 Create Mapper or DAO Classes to Invoke the SP

Please see 

> https://lalitjc.wordpress.com/2013/07/02/different-ways-of-calling-stored-procedure-using-spring/ 

for the inspiration for this article. I went ahead and made a DB2/Array specific version of his article.

The most elegant way is to use the mapper. The only caveat is you will need to create your own 'org.apache.ibatis.type.TypeHandler'. I find this odd as I was able to invoke the SP using JDBC4 and non-vendor specific calls. I think JDBC 3 drivers or earlier may be different.

> See the EmployeeMapper.java interface for exact details

Pay special attention to the Annotation syntax. Especially to the how the IN parameter is defined with the custom TypeHandler
> "#{I_EMP_ID_ARRAY,	jdbcType=ARRAY,		mode=IN,  typeHandler=com.ijudy.mapper.typehandler.Db2ArrayTypeHandler}

### EmployeeDaoImpl
There are 3 implementations for the DAO class. Based on how low level you want to go, you could use native JDBC calls or the more elegant and versatile Spring classes. When using Spring, we ended up turning off AUTO COMMIT and also not caching the Prepared Statement.  These changes may not be required for your design but something to consider

- getEmployeesCallableStatementCreator - Uses the jdbcTemplate and overrides the below interface

>@Override
>public CallableStatement createCallableStatement(Connection connection) throws SQLException

- getEmployeesCallableStatment - Uses the jdbcTemplate only for the connection and has no real Spring dependencies and is the closest to straight direct JDBC calls than the other methods. Make sure to close all of your resources!

- getEmployeesSimpleJdbcCall - This is all Spring and has the most complexity but also the most versatility. The connection and resources should be managed by Spring and you should not have to deal with connection management nor low level details. Again, when we were using this Spring design in our implementation we ended up turning off AUTO COMMIT and also not caching the the Prepared Statement, otherwise the connection seemed to get consumed and locked and not-reusable when invoked again. 

We would see the data come back the first time and then internally roll back and return a 100 return code (NO ROWS FOUND) the second time. There would be no SQLException just the illusion of no records found. The connection would not return data again until after the connection timed out.  This was quite frustrating.  These changes may not be required for your design but something to consider.

