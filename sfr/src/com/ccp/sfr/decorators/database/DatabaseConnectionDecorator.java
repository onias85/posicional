package com.ccp.sfr.decorators.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.ccp.sfr.commons.AssertionsUtils;
import com.ccp.sfr.commons.Exceptions;

public class DatabaseConnectionDecorator {

	private final Connection connection;
	
	
	
	public DatabaseConnectionDecorator(Connection connection) {
		AssertionsUtils.validateNotEmptyAndNotNullObject("connection", connection);
		this.connection = connection;
	}


	public DatabaseConnectionDecorator(String driverClass, String urlDataBase, String username, String password){
	
		AssertionsUtils.validateNotEmptyAndNotNullObject("driverClass", driverClass);
		AssertionsUtils.validateNotEmptyAndNotNullObject("urlDataBase", urlDataBase);
		AssertionsUtils.validateNotEmptyAndNotNullObject("password", password);
		AssertionsUtils.validateNotEmptyAndNotNullObject("username", username);

		try {
			Class.forName(driverClass);
		} catch (Error e) {  
			throw new Exceptions.DataBaseConnectionException(e);
		} catch (ClassNotFoundException e) {
			throw new Exceptions.DataBaseConnectionException(e, driverClass);
		}
		try {
			this.connection = DriverManager.getConnection(urlDataBase, username, password);		
		} catch (SQLException e) {
			throw new Exceptions.DataBaseConnectionException(e);
		}
	}

	
	public PreparedStatementDecorator getPreparedStatementDecorator(String sql){
	
		AssertionsUtils.validateNotEmptyAndNotNullObject("sql", sql);
		
		PreparedStatementDecorator psd = new PreparedStatementDecorator(this.connection, sql);
		
		return psd;
	}


	public Connection getConnection() {
		return connection;
	}
	
}
