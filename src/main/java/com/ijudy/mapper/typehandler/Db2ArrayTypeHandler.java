package com.ijudy.mapper.typehandler;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * https://stackoverflow.com/questions/136034/pass-and-return-custom-array-object-in-ibatis-and-oracle-in-java
 * 
 * Where String[] in this case is the Array internal storage. You could create another one of these classes
 * for an Integer[] or other complicated type
 * 
 * @author ijudy
 *
 */
public class Db2ArrayTypeHandler implements TypeHandler<String[]> {

	public static final String ARRAY_TYPE = "VARCHAR";

	/**
	 * Lots of documentation states this connection needs to be cast to DB2 specific. With the JDBC 4 type I am using
	 * that was not the case. Beware in some ZOS instances we had to turn auto commit off and and also not pool the prepared
	 * statement
	 */
	@Override
	public void setParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType) throws SQLException {
	      if (parameter == null) {
	            ps.setNull(i, Types.ARRAY);
	        } else {
	            Connection conn = ps.getConnection();
	            Array loc = conn.createArrayOf(ARRAY_TYPE, (String[]) parameter);
	            ps.setArray(i, loc);
	        }
		
	}

	@Override
	public String[] getResult(ResultSet rs, String columnName) throws SQLException {
		return (String[])rs.getArray(columnName).getArray();
	}

	@Override
	public String[] getResult(ResultSet rs, int columnIndex) throws SQLException {
		return (String[])rs.getArray(columnIndex).getArray();
	}

	@Override
	public String[] getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return (String[])cs.getArray(columnIndex).getArray();
	}


}