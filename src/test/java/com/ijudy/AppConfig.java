package com.ijudy;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ijudy.dao.impl.EmployeeDaoImpl;

@Configuration
@ComponentScan
@MapperScan("com.ijudy.mapper")
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
public class AppConfig {
	
    @Autowired
    private Environment env;
 
    @Value("${init-db:false}")
    private String initDatabase;
    
    @Value("${jdbc.datasource.driver-class-name}")
    private String driverClassName;
    
    @Value("${jdbc.url}")
    private String url;
    
    @Value("${jdbc.databasename}")
    private String databasename;

    @Value("${jdbc.serverName}")
    private String serverName;


        
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }    
 
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource)
    {
        return new JdbcTemplate(dataSource);
    }
 
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource)
    {
        return new DataSourceTransactionManager(dataSource);
    }
    
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
      SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
      sessionFactory.setDataSource(dataSource());
      return sessionFactory.getObject();
    }
    
    @Bean
    public EmployeeDaoImpl employeeDaoImpl() throws Exception{
    	EmployeeDaoImpl	dao = new EmployeeDaoImpl();
    	dao.setJdbcTemplate(jdbcTemplate(dataSource()));
    	return dao;
    }
    
      
    @Bean
    public DataSource dataSource()
    {
    	
    	/*
    	 * 	<bean id="dataSource" class="com.ibm.db2.jcc.DB2SimpleDataSource">
    	 *    <property name="serverName" value="maui" />
    	 *    <property name="databaseName" value="TEST" />
    	 *    <property name="portNumber" value="50000"/>
    	 *    <property name="driverType" value="4"/>
    	 *    <property name="user" value="spring"/>
    	 *    <property name="password" value="spring"/>
    	 *  </bean>
    	 */
    	// https://www.ibm.com/support/knowledgecenter/en/SSEPGG_9.7.0/com.ibm.db2.luw.apdv.java.doc/src/tpc/imjcc_rjvdsprp.html
    	/*
    	com.ibm.db2.jcc.DB2SimpleDataSource  dataSource = new com.ibm.db2.jcc.DB2SimpleDataSource();
        dataSource.setServerName(serverName);
        dataSource.setDatabaseName(databasename);
        dataSource.setPortNumber(50000);
        dataSource.setDriverType(4);
        dataSource.setUser(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        */
    	
    	BasicDataSource dataSource = new BasicDataSource();
    	dataSource.setDriverClassName(driverClassName);
    	dataSource.setUrl(url);
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        return dataSource;
    }
}

