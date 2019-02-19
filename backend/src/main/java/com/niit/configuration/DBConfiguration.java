package com.niit.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;



	@Configuration
	@EnableTransactionManagement
	public class DBConfiguration {
	  public DBConfiguration(){
		 System.out.println("DBConfiguration class is instantiated"); 
	  }
	  @Bean
		public SessionFactory sessionFactory() {
		  System.out.println("Inside sessionFactory()");
			LocalSessionFactoryBuilder lsf=
					new LocalSessionFactoryBuilder(getDataSource());
			Properties hibernateProperties=new Properties();
			hibernateProperties.setProperty(
					"hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
			hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
			hibernateProperties.setProperty("hibernate.show_sql", "true");
			lsf.addProperties(hibernateProperties);
			lsf.scanPackages("com.niit.models");
		    return lsf.buildSessionFactory();
		}
		@Bean
		public DataSource getDataSource() {
			System.out.println("Inside getDataSource()");
		    BasicDataSource dataSource = new BasicDataSource();
		    dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		    dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
		    System.out.println("Setting values for username and password");
		    dataSource.setUsername("system");
		    dataSource.setPassword("oracle");
		    System.out.println(dataSource.getUsername());
		    System.out.println(dataSource.getPassword());
		    return dataSource;	    
		}
	 
		@Bean
		public HibernateTransactionManager hibTransManagement(){
			return new HibernateTransactionManager(sessionFactory());
		}
	}