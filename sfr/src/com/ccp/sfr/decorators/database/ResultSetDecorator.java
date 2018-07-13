package com.ccp.sfr.decorators.database;

import java.io.Closeable;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import com.ccp.sfr.utils.SystemException;

public class ResultSetDecorator implements Closeable{

	private final ResultSet resultSet;

	ResultSetDecorator(PreparedStatement ps) {
		try {
			this.resultSet = ps.executeQuery();
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}


	public boolean next() {
		try {
			return this.resultSet.next();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void close() {
		try {
			this.resultSet.close();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public boolean wasNull() {
		try {
			return this.resultSet.wasNull();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public String getString(int columnIndex) {
		try {
			return this.resultSet.getString(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public boolean getBoolean(int columnIndex) {
		try {
			return this.resultSet.getBoolean(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public byte getByte(int columnIndex) {
		try {
			return this.resultSet.getByte(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public short getShort(int columnIndex) {
		try {
			return this.resultSet.getShort(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public int getInt(int columnIndex) {
		try {
			return this.resultSet.getInt(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public long getLong(int columnIndex) {
		try {
			return this.resultSet.getLong(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public float getFloat(int columnIndex) {
		try {
			return this.resultSet.getFloat(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public double getDouble(int columnIndex) {
		try {
			return this.resultSet.getDouble(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	@Deprecated
	public BigDecimal getBigDecimal(int columnIndex, int scale) {
		try {
			return this.resultSet.getBigDecimal(columnIndex, scale);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public byte[] getBytes(int columnIndex) {
		try {
			return this.resultSet.getBytes(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Date getDate(int columnIndex) {
		try {
			return this.resultSet.getDate(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Time getTime(int columnIndex) {
		try {
			return this.resultSet.getTime(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Timestamp getTimestamp(int columnIndex) {
		try {
			return this.resultSet.getTimestamp(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public InputStream getAsciiStream(int columnIndex) {
		try {
			return this.resultSet.getAsciiStream(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	@Deprecated
	public InputStream getUnicodeStream(int columnIndex) {
		try {
			return this.resultSet.getUnicodeStream(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public InputStream getBinaryStream(int columnIndex) {
		try {
			return this.resultSet.getBinaryStream(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public String getString(String columnLabel) {
		try {
			return this.resultSet.getString(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public boolean getBoolean(String columnLabel) {
		try {
			return this.resultSet.getBoolean(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public byte getByte(String columnLabel) {
		try {
			return this.resultSet.getByte(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public short getShort(String columnLabel) {
		try {
			return this.resultSet.getShort(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public int getInt(String columnLabel) {
		try {
			return this.resultSet.getInt(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public long getLong(String columnLabel) {
		try {
			return this.resultSet.getLong(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public float getFloat(String columnLabel) {
		try {
			return this.resultSet.getFloat(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public double getDouble(String columnLabel) {
		try {
			return this.resultSet.getDouble(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	@Deprecated
	public BigDecimal getBigDecimal(String columnLabel, int scale) {
		try {
			return this.resultSet.getBigDecimal(columnLabel, scale);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public byte[] getBytes(String columnLabel) {
		try {
			return this.resultSet.getBytes(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Date getDate(String columnLabel) {
		try {
			return this.resultSet.getDate(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Time getTime(String columnLabel) {
		try {
			return this.resultSet.getTime(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Timestamp getTimestamp(String columnLabel) {
		try {
			return this.resultSet.getTimestamp(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public InputStream getAsciiStream(String columnLabel) {
		try {
			return this.resultSet.getAsciiStream(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	@Deprecated
	public InputStream getUnicodeStream(String columnLabel) {
		try {
			return this.resultSet.getUnicodeStream(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public InputStream getBinaryStream(String columnLabel) {
		try {
			return this.resultSet.getBinaryStream(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public SQLWarning getWarnings() {
		try {
			return this.resultSet.getWarnings();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void clearWarnings() {
		try {
			this.resultSet.clearWarnings();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public String getCursorName() {
		try {
			return this.resultSet.getCursorName();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public ResultSetMetaData getMetaData() {
		try {
			return this.resultSet.getMetaData();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Object getObject(int columnIndex) {
		try {
			return this.resultSet.getObject(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Object getObject(String columnLabel) {
		try {
			return this.resultSet.getObject(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public int findColumn(String columnLabel) {
		try {
			return this.resultSet.findColumn(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Reader getCharacterStream(int columnIndex) {
		try {
			return this.resultSet.getCharacterStream(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Reader getCharacterStream(String columnLabel) {
		try {
			return this.resultSet.getCharacterStream(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public BigDecimal getBigDecimal(int columnIndex) {
		try {
			return this.resultSet.getBigDecimal(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public BigDecimal getBigDecimal(String columnLabel) {
		try {
			return this.resultSet.getBigDecimal(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public boolean isBeforeFirst() {
		try {
			return this.resultSet.isBeforeFirst();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public boolean isAfterLast() {
		try {
			return this.resultSet.isAfterLast();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public boolean isFirst() {
		try {
			return this.resultSet.isFirst();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public boolean isLast() {
		try {
			return this.resultSet.isLast();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void beforeFirst() {
		try {
			this.resultSet.beforeFirst();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void afterLast() {
		try {
			this.resultSet.afterLast();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public boolean first() {
		try {
			return this.resultSet.first();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public boolean last() {
		try {
			return this.resultSet.last();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public int getRow() {
		try {
			return this.resultSet.getRow();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public boolean absolute(int row) {
		try {
			return this.resultSet.absolute(row);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public boolean relative(int rows) {
		try {
			return this.resultSet.relative(rows);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public boolean previous() {
		try {
			return this.resultSet.previous();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void setFetchDirection(int direction) {
		try {
			this.resultSet.setFetchDirection(direction);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public int getFetchDirection() {
		try {
			return this.resultSet.getFetchDirection();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void setFetchSize(int rows) {
		try {
			this.resultSet.setFetchSize(rows);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public int getFetchSize() {
		try {
			return this.resultSet.getFetchSize();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public int getType() {
		try {
			return this.resultSet.getType();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public int getConcurrency() {
		try {
			return this.resultSet.getConcurrency();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public boolean rowUpdated() {
		try {
			return this.resultSet.rowUpdated();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public boolean rowInserted() {
		try {
			return this.resultSet.rowInserted();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public boolean rowDeleted() {
		try {
			return this.resultSet.rowDeleted();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateNull(int columnIndex) {
		try {
			this.resultSet.updateNull(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateBoolean(int columnIndex, boolean x) {
		try {
			this.resultSet.updateBoolean(columnIndex, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateByte(int columnIndex, byte x) {
		try {
			this.resultSet.updateByte(columnIndex, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateShort(int columnIndex, short x) {
		try {
			this.resultSet.updateShort(columnIndex, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateInt(int columnIndex, int x) {
		try {
			this.resultSet.updateInt(columnIndex, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateLong(int columnIndex, long x) {
		try {
			this.resultSet.updateLong(columnIndex, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateFloat(int columnIndex, float x) {
		try {
			this.resultSet.updateFloat(columnIndex, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateDouble(int columnIndex, double x) {
		try {
			this.resultSet.updateDouble(columnIndex, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateBigDecimal(int columnIndex, BigDecimal x) {
		try {
			this.resultSet.updateBigDecimal(columnIndex, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateString(int columnIndex, String x) {
		try {
			this.resultSet.updateString(columnIndex, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateBytes(int columnIndex, byte[] x) {
		try {
			this.resultSet.updateBytes(columnIndex, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateDate(int columnIndex, Date x) {
		try {
			this.resultSet.updateDate(columnIndex, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateTime(int columnIndex, Time x) {
		try {
			this.resultSet.updateTime(columnIndex, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateTimestamp(int columnIndex, Timestamp x) {
		try {
			this.resultSet.updateTimestamp(columnIndex, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateAsciiStream(int columnIndex, InputStream x, int length) {
		try {
			this.resultSet.updateAsciiStream(columnIndex, x, length);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateBinaryStream(int columnIndex, InputStream x, int length) {
		try {
			this.resultSet.updateBinaryStream(columnIndex, x, length);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateCharacterStream(int columnIndex, Reader x, int length) {
		try {
			this.resultSet.updateCharacterStream(columnIndex, x, length);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateObject(int columnIndex, Object x, int scaleOrLength) {
		try {
			this.resultSet.updateObject(columnIndex, x, scaleOrLength);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateObject(int columnIndex, Object x) {
		try {
			this.resultSet.updateObject(columnIndex, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateNull(String columnLabel) {
		try {
			this.resultSet.updateNull(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateBoolean(String columnLabel, boolean x) {
		try {
			this.resultSet.updateBoolean(columnLabel, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateByte(String columnLabel, byte x) {
		try {
			this.resultSet.updateByte(columnLabel, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateShort(String columnLabel, short x) {
		try {
			this.resultSet.updateShort(columnLabel, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateInt(String columnLabel, int x) {
		try {
			this.resultSet.updateInt(columnLabel, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateLong(String columnLabel, long x) {
		try {
			this.resultSet.updateLong(columnLabel, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateFloat(String columnLabel, float x) {
		try {
			this.resultSet.updateFloat(columnLabel, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateDouble(String columnLabel, double x) {
		try {
			this.resultSet.updateDouble(columnLabel, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateBigDecimal(String columnLabel, BigDecimal x) {
		try {
			this.resultSet.updateBigDecimal(columnLabel, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateString(String columnLabel, String x) {
		try {
			this.resultSet.updateString(columnLabel, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateBytes(String columnLabel, byte[] x) {
		try {
			this.resultSet.updateBytes(columnLabel, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateDate(String columnLabel, Date x) {
		try {
			this.resultSet.updateDate(columnLabel, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateTime(String columnLabel, Time x) {
		try {
			this.resultSet.updateTime(columnLabel, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateTimestamp(String columnLabel, Timestamp x) {
		try {
			this.resultSet.updateTimestamp(columnLabel, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateAsciiStream(String columnLabel, InputStream x, int length) {
		try {
			this.resultSet.updateAsciiStream(columnLabel, x, length);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateBinaryStream(String columnLabel, InputStream x, int length) {
		try {
			this.resultSet.updateBinaryStream(columnLabel, x, length);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateCharacterStream(String columnLabel, Reader reader,
			int length) {
		try {
			this.resultSet.updateCharacterStream(columnLabel, reader, length);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateObject(String columnLabel, Object x, int scaleOrLength) {
		try {
			this.resultSet.updateObject(columnLabel, x, scaleOrLength);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateObject(String columnLabel, Object x) {
		try {
			this.resultSet.updateObject(columnLabel, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void insertRow() {
		try {
			this.resultSet.insertRow();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateRow() {
		try {
			this.resultSet.updateRow();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void deleteRow() {
		try {
			this.resultSet.deleteRow();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void refreshRow() {
		try {
			this.resultSet.refreshRow();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void cancelRowUpdates() {
		try {
			this.resultSet.cancelRowUpdates();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void moveToInsertRow() {
		try {
			this.resultSet.moveToInsertRow();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void moveToCurrentRow() {
		try {
			this.resultSet.moveToCurrentRow();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Statement getStatement() {
		try {
			return this.resultSet.getStatement();
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Object getObject(int columnIndex, Map<String, Class<?>> map) {
		try {
			return this.resultSet.getObject(columnIndex, map);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Ref getRef(int columnIndex) {
		try {
			return this.resultSet.getRef(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Blob getBlob(int columnIndex) {
		try {
			return this.resultSet.getBlob(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Clob getClob(int columnIndex) {
		try {
			return this.resultSet.getClob(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Array getArray(int columnIndex) {
		try {
			return this.resultSet.getArray(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Object getObject(String columnLabel, Map<String, Class<?>> map) {
		try {
			return this.resultSet.getObject(columnLabel, map);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Ref getRef(String columnLabel) {
		try {
			return this.resultSet.getRef(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Blob getBlob(String columnLabel) {
		try {
			return this.resultSet.getBlob(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Clob getClob(String columnLabel) {
		try {
			return this.resultSet.getClob(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Array getArray(String columnLabel) {
		try {
			return this.resultSet.getArray(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Date getDate(int columnIndex, Calendar cal) {
		try {
			return this.resultSet.getDate(columnIndex, cal);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Date getDate(String columnLabel, Calendar cal) {
		try {
			return this.resultSet.getDate(columnLabel, cal);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Time getTime(int columnIndex, Calendar cal) {
		try {
			return this.resultSet.getTime(columnIndex, cal);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Time getTime(String columnLabel, Calendar cal) {
		try {
			return this.resultSet.getTime(columnLabel, cal);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Timestamp getTimestamp(int columnIndex, Calendar cal) {
		try {
			return this.resultSet.getTimestamp(columnIndex, cal);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public Timestamp getTimestamp(String columnLabel, Calendar cal) {
		try {
			return this.resultSet.getTimestamp(columnLabel, cal);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public URL getURL(int columnIndex) {
		try {
			return this.resultSet.getURL(columnIndex);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public URL getURL(String columnLabel) {
		try {
			return this.resultSet.getURL(columnLabel);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateRef(int columnIndex, Ref x) {
		try {
			this.resultSet.updateRef(columnIndex, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateRef(String columnLabel, Ref x) {
		try {
			this.resultSet.updateRef(columnLabel, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateBlob(int columnIndex, Blob x) {
		try {
			this.resultSet.updateBlob(columnIndex, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateBlob(String columnLabel, Blob x) {
		try {
			this.resultSet.updateBlob(columnLabel, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateClob(int columnIndex, Clob x) {
		try {
			this.resultSet.updateClob(columnIndex, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateClob(String columnLabel, Clob x) {
		try {
			this.resultSet.updateClob(columnLabel, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateArray(int columnIndex, Array x) {
		try {
			this.resultSet.updateArray(columnIndex, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

	public void updateArray(String columnLabel, Array x) {
		try {
			this.resultSet.updateArray(columnLabel, x);
		} catch (SQLException e) {

			throw new SystemException(e);
		}
	}

}
