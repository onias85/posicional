package com.ccp.sfr.decorators.database;

import java.io.Closeable;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import com.ccp.sfr.commons.AssertionsUtils;
import com.ccp.sfr.commons.Exceptions;
import com.ccp.sfr.utils.SystemException;

public class PreparedStatementDecorator implements Closeable{

	private final PreparedStatement preparedStatement;

	PreparedStatementDecorator(Connection cnn, String sql) {

		try {
			this.preparedStatement = cnn.prepareStatement(sql);
		} catch (SQLException e) {
			throw new Exceptions.IncorrectSqlSyntaxException(e, sql);
		}
	}

	
	public ResultSet executeQuery(String sql) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("sql", sql);

		try {
			ResultSet executeQuery = this.preparedStatement.executeQuery(sql);
			return executeQuery;
		} catch (SQLException e) {
			throw new Exceptions.IncorrectSqlSyntaxException(e, sql);
		}
	}

	
	public int executeUpdate(String sql) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("sql", sql);

		try {
			return this.preparedStatement.executeUpdate(sql);
		} catch (SQLException e) {
			throw new Exceptions.IncorrectSqlSyntaxException(e, sql);
		}
	}

	
	public void close() {
		try {
			this.preparedStatement.close();
		} catch (SQLException e) {
		}

	}

	
	public int getMaxFieldSize() {
		try {
			return this.preparedStatement.getMaxFieldSize();
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public void setMaxFieldSize(int max) {
		try {
			this.preparedStatement.setMaxFieldSize(max);
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public int getMaxRows() {
		try {
			int maxRows = this.preparedStatement.getMaxRows();
			return maxRows;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public void setMaxRows(int max) {

		try {
			this.preparedStatement.setMaxRows(max);
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public void setEscapeProcessing(boolean enable) {
		try {
			this.preparedStatement.setEscapeProcessing(enable);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public int getQueryTimeout() {

		try {
			int queryTimeout = this.preparedStatement.getQueryTimeout();
			return queryTimeout;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public void setQueryTimeout(int seconds) {
		try {
			this.preparedStatement.setQueryTimeout(seconds);
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public void cancel() {

		try {
			this.preparedStatement.cancel();
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public SQLWarning getWarnings() {
		try {
			SQLWarning warnings = this.preparedStatement.getWarnings();
			return warnings;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public void clearWarnings() {
		try {
			this.preparedStatement.clearWarnings();
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setCursorName(String name) {
		try {
			this.preparedStatement.setCursorName(name);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public boolean execute(String sql) {
		try {
			return this.preparedStatement.execute(sql);
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public ResultSet getResultSet() {
		try {
			ResultSet resultSet = this.preparedStatement.getResultSet();
			return resultSet;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public int getUpdateCount() {
		try {
			int updateCount = this.preparedStatement.getUpdateCount();
			return updateCount;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public boolean getMoreResults() {
		try {
			boolean moreResults = this.preparedStatement.getMoreResults();
			return moreResults;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public void setFetchDirection(int direction) {
		try {
			this.preparedStatement.setFetchDirection(direction);
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public int getFetchDirection() {
		try {
			int fetchDirection = this.preparedStatement.getFetchDirection();
			return fetchDirection;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public void setFetchSize(int rows) {
		try {
			this.preparedStatement.setFetchSize(rows);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public int getFetchSize() {
		try {
			int fetchSize = this.preparedStatement.getFetchSize();
			return fetchSize;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public int getResultSetConcurrency() {
		try {
			int resultSetConcurrency = this.preparedStatement.getResultSetConcurrency();
			return resultSetConcurrency;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public int getResultSetType() {
		try {
			int resultSetType = this.preparedStatement.getResultSetType();
			return resultSetType;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public void addBatch(String sql) {
		try {
			this.preparedStatement.addBatch(sql);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void clearBatch() {
		try {
			this.preparedStatement.clearBatch();
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public int[] executeBatch() {
		try {
			int[] executeBatch = this.preparedStatement.executeBatch();
			return executeBatch;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public Connection getConnection() {
		try {
			Connection connection = this.preparedStatement.getConnection();
			return connection;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public boolean getMoreResults(int current) {
		try {
			boolean moreResults = this.preparedStatement.getMoreResults(current);
			return moreResults;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public ResultSet getGeneratedKeys() {
		try {
			ResultSet generatedKeys = this.preparedStatement.getGeneratedKeys();
			return generatedKeys;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public int executeUpdate(String sql, int autoGeneratedKeys) {
		try {
			int executeUpdate = this.preparedStatement.executeUpdate(sql, autoGeneratedKeys);
			return executeUpdate;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public int executeUpdate(String sql, int[] columnIndexes) {
		try {
			int executeUpdate = this.preparedStatement.executeUpdate(sql, columnIndexes);
			return executeUpdate;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public int executeUpdate(String sql, String[] columnNames) {
		try {
			int executeUpdate = this.preparedStatement.executeUpdate(sql, columnNames);
			return executeUpdate;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public boolean execute(String sql, int autoGeneratedKeys) {
		try {
			boolean execute = this.preparedStatement.execute(sql, autoGeneratedKeys);
			return execute;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public boolean execute(String sql, int[] columnIndexes) {
		try {
			return this.preparedStatement.execute(sql, columnIndexes);
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public boolean execute(String sql, String[] columnNames) {
		try {
			boolean execute = this.preparedStatement.execute(sql, columnNames);
			return execute;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public int getResultSetHoldability() {
		try {
			int resultSetHoldability = this.preparedStatement.getResultSetHoldability();
			return resultSetHoldability;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}


	
	public ResultSet executeQuery() {
		try {
			ResultSet executeQuery = this.preparedStatement.executeQuery();
			return executeQuery;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public int executeUpdate() {
		try {
			int executeUpdate = this.preparedStatement.executeUpdate();
			return executeUpdate;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public void setNull(int parameterIndex, int sqlType) {
		try {
			this.preparedStatement.setNull(parameterIndex, sqlType);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setBoolean(int parameterIndex, boolean x) {
		try {
			this.preparedStatement.setBoolean(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setByte(int parameterIndex, byte x) {
		try {
			this.preparedStatement.setByte(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setShort(int parameterIndex, short x) {
		try {
			this.preparedStatement.setShort(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setInt(int parameterIndex, int x) {
		try {
			this.preparedStatement.setInt(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setLong(int parameterIndex, long x) {
		try {
			this.preparedStatement.setLong(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setFloat(int parameterIndex, float x) {
		try {
			this.preparedStatement.setFloat(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setDouble(int parameterIndex, double x) {
		try {
			this.preparedStatement.setDouble(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setBigDecimal(int parameterIndex, BigDecimal x) {
		try {
			this.preparedStatement.setBigDecimal(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setString(int parameterIndex, String x) {
		try {
			this.preparedStatement.setString(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setBytes(int parameterIndex, byte[] x) {
		try {
			this.preparedStatement.setBytes(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setDate(int parameterIndex, Date x) {
		try {
			this.preparedStatement.setDate(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setTime(int parameterIndex, Time x) {
		try {
			this.preparedStatement.setTime(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setTimestamp(int parameterIndex, Timestamp x) {
		try {
			this.preparedStatement.setTimestamp(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setAsciiStream(int parameterIndex, InputStream x, int length) {
		try {
			this.preparedStatement.setAsciiStream(parameterIndex, x, length);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	@Deprecated
	public void setUnicodeStream(int parameterIndex, InputStream x, int length) {
		try {
			this.preparedStatement.setUnicodeStream(parameterIndex, x, length);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setBinaryStream(int parameterIndex, InputStream x, int length) {
		try {
			this.preparedStatement.setBinaryStream(parameterIndex, x, length);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void clearParameters() {
		try {
			this.preparedStatement.clearParameters();
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setObject(int parameterIndex, Object x, int targetSqlType) {
		try {
			this.preparedStatement.setObject(parameterIndex, x, targetSqlType);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setObject(int parameterIndex, Object x) {
		try {
			this.preparedStatement.setObject(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public boolean execute() {
		try {
			return this.preparedStatement.execute();
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public void addBatch() {
		try {
			this.preparedStatement.addBatch();
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	public void setRef(int parameterIndex, Ref x) {
		try {
			this.preparedStatement.setRef(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setBlob(int parameterIndex, Blob x) {
		try {
			this.preparedStatement.setBlob(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setClob(int parameterIndex, Clob x) {
		try {
			this.preparedStatement.setClob(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setArray(int parameterIndex, Array x) {
		try {
			this.preparedStatement.setArray(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public ResultSetMetaData getMetaData() {
		try {
			ResultSetMetaData metaData = this.preparedStatement.getMetaData();
			return metaData;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public void setDate(int parameterIndex, Date x, Calendar cal) {
		try {
			this.preparedStatement.setDate(parameterIndex, x, cal);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setTime(int parameterIndex, Time x, Calendar cal) {
		try {
			this.preparedStatement.setTime(parameterIndex, x, cal);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) {
		try {
			this.preparedStatement.setTimestamp(parameterIndex, x, cal);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setNull(int parameterIndex, int sqlType, String typeName) {
		try {
			this.preparedStatement.setNull(parameterIndex, sqlType, typeName);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public void setURL(int parameterIndex, URL x) {
		try {
			this.preparedStatement.setURL(parameterIndex, x);
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public ParameterMetaData getParameterMetaData() {
		try {
			ParameterMetaData parameterMetaData = this.preparedStatement.getParameterMetaData();
			return parameterMetaData;
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

	
	public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) {
		try {
			this.preparedStatement.setObject(parameterIndex, x, targetSqlType, scaleOrLength);;
		} catch (SQLException e) {
			throw new SystemException(e);
		}

	}

	
	public ResultSetDecorator getResultSetDecorator(){
		ResultSetDecorator resultSetDecorator = new ResultSetDecorator(this.preparedStatement);
		return resultSetDecorator;
	}

}
