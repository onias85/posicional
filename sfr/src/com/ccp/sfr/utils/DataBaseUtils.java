package  com.ccp.sfr.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.ccp.sfr.commons.AssertionsUtils;
import com.ccp.sfr.commons.ReflectionUtils;

public class DataBaseUtils {

	private static Connection connection;

	public static long getNextOracleSequence(String sequenceName, Connection connection){
		
		AssertionsUtils.validateNotEmptyAndNotNullObject("sequenceName", sequenceName);
		AssertionsUtils.validateNotEmptyAndNotNullObject("connection", connection);
		
		String sql = "SELECT " + sequenceName + ".NEXTVAL FROM DUAL";
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		try{
			
			prepareStatement = connection.prepareStatement(sql);
			resultSet = prepareStatement.executeQuery();
			
			boolean semResultados = resultSet.next() == false;
			
			if(semResultados){
				throw new SystemException("A query [" + sql + "] nao retornou resultado");
			}
			
			long long1 = resultSet.getLong("NEXTVAL");
			return long1;
		}catch(SQLException e){
			throw new SystemException("Erro ao executar a query [" + sql + "]", e);
		}finally{
			ReflectionUtils.closeResources(prepareStatement, resultSet);
		}
	}
	
	public static void executeSql(String sql, Connection connection){

		AssertionsUtils.validateNotEmptyAndNotNullObject("connection", connection);
		AssertionsUtils.validateNotEmptyAndNotNullObject("sql", sql);
		PreparedStatement prepareStatement = null;
		try{
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.execute();
		}catch(SQLException e){
			throw new SystemException("Erro ao executar a query [" + sql + "]", e);
		}finally{
			ReflectionUtils.closeResources(prepareStatement);
		}
	}
	
	public static Connection getConnection() {
		
		boolean conexaoJaEstabelecida = connection != null;
		
		if(conexaoJaEstabelecida) {
			return connection;
		}
		
		Properties properties = new Properties();
		InputStream inStream = DataBaseUtils.class.getResourceAsStream("database.properties");
		try {
			properties.load(inStream);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao tentar ler arquivo de conexão de Banco de Dados.",e);
		} 	
		
		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		String driver = properties.getProperty("driver");
		try {
			Class.forName(driver);
		} catch (Error e) {  
			throw new RuntimeException("Erro ao carregar driver. Dados da conexão: " + properties,e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Erro ao carregar driver. Dados da conexão: " + properties,e);
		}
		try {
			connection = DriverManager.getConnection(url, user, password);		
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao conectar no Banco de Dados. Dados da conexão: " + properties,e);
		}
		
		return connection;
	}
	
	public static int getCount(ResultSet rs){
		try {
			rs.last();
			
			int size = rs.getRow();
			
			rs.beforeFirst();
			
			return size;
		} catch (SQLException e) {
			return 0;
		} 	
		
	}

}
