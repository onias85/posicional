package  com.ccp.sfr.commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ccp.sfr.columns.ColumnDataType;
import com.ccp.sfr.commons.Exceptions.EmptyInsertException;
import com.ccp.sfr.commons.Exceptions.EndOfResultSetException;
import com.ccp.sfr.commons.Exceptions.HasNotTableException;
import com.ccp.sfr.commons.Exceptions.InvalidCharactersQuantityException;
import com.ccp.sfr.commons.Exceptions.LayoutNotFoundForThisLineValueException;
import com.ccp.sfr.commons.Exceptions.SequentialReadingException;
import com.ccp.sfr.decorators.database.DatabaseConnectionDecorator;
import com.ccp.sfr.utils.FileUtils;
import com.ccp.sfr.utils.MultipleErrorsException;
import com.ccp.sfr.utils.SystemException;

/**
 * 
 * @author Onias Vieira Junior
 *
 */
public class CopyData {

	

	/**
	 * Este metodo funciona apenas com todas as seguintes condicoes dendo cumpridas:
	 * 
	 * A) Deve haver um arquivo .ini que descreva o layout sequencial do arquivo que esta sendo lido;
	 * 
	 * B) Deve haver tabelas em banco de dados, uma para cada tipo de linha com os corretos nomes e tipos das colunas
	 * 
	 * 
	 * @param connection
	 * @param fileToRead
	 * @param layoutConfiguration
	 */
	public static int[] _fromFileToDataBase(DatabaseConnectionDecorator cnn, InputStream layoutConfiguration, SqlStatementGenerator ssg, String input, String output) {
		
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutFile", layoutConfiguration);
		AssertionsUtils.validateNotEmptyAndNotNullObject("input", input);
		AssertionsUtils.validateNotEmptyAndNotNullObject("cnn", cnn);
		AssertionsUtils.validateNotEmptyAndNotNullObject("ssg", ssg);	
		Connection connection = cnn.getConnection();
		
//		output = output.replace(".txt",  "_report_errors.csv");
		int lineNumber = 0;
		int errors = 0;
		
		Statement statement  = null;
		FileReader fr = null;
		BufferedReader br = null;
		try{

			statement = connection.createStatement();
			fr = new FileReader(input);
			br = new BufferedReader(fr);
			connection.setAutoCommit(false);

			String lineValue;
			
			while((lineValue = br.readLine()) != null){

				lineNumber++;
			
				boolean empty = lineValue.length() == 0;
				
				if(empty){
					continue;
				}
				
				try{
					String sql = ssg.getSqlStatement(layoutConfiguration, input, lineValue);
					System.out.println(sql);
					statement.addBatch(sql);
				}catch(EmptyInsertException  e){ 
					// se o layout nao for gravavel em banco de dados
					continue;
				}catch(HasNotTableException e){
					// se o layout nao for gravavel em banco de dados
					continue;
				}
				
				catch(InvalidCharactersQuantityException  e) {
					notifyError(input, output, lineNumber, e);
					
				}catch(LayoutNotFoundForThisLineValueException e){
					notifyError(input, output, lineNumber, e);
				}
				catch(MultipleErrorsException e){
					
					List<String> list = e.messages;
					
					for (String error : list) {
						addLineError(output,input, "" + lineNumber, error);
					}
					errors++;
				}
			}
			validateErrors(connection, input, output, errors);
			
			int[] results = validateBatch(connection, input, ssg, output, statement);

			connection.commit();
			return results;
		} catch (SQLException e) {
			String message = String.format("Ocorreu um erro ao inserir em banco de dados o conteúdo do arquivo sequencial '%s'", input);
			int[] reportError = reportError(connection,  errors, message, e, input, output);
			return reportError;
		} catch(IOException e){
			String message = String.format("Ocorreu um erro ao ler o conteúdo do arquivo sequencial '%s'", input);
			int[] reportError = reportError(connection,  errors, message, e, input, output);
			return reportError;
		}finally{
			ReflectionUtils.closeResources(fr, br, statement);
		}
	}

	private static void notifyError(String input, String output, int lineNumber, SequentialReadingException e) {
		String message = e.getMessage();
		addLineError(output, input, "" + lineNumber, message);
		String errorMessage = String.format("Ocorreram erros ao copiar dados do arquivo sequencial '%s' para o banco de dados, para mais detalhes, favor abrir o arquivo '%s' ",input, output);
		throw new SystemException(errorMessage, e);
	}

	private static int[] validateBatch(Connection connection, String path, SqlStatementGenerator ssg, String reportErrors, Statement statement)
			throws SQLException {
		int[] results = statement.executeBatch();
		int k = 0;
		int sqlErrors = 0;
		for (int result : results) {
			
			boolean hasError = result != 1;
			
			if(hasError){
				String lineValueByIndex = FileUtils.getLineValueByIndex(path, k);
				Map<String, String> dataBaseColumnsAndValues = ssg.getDataBaseColumnsAndValues(lineValueByIndex);
				String errorMessage = "Não foi atualizada nenhuma linha. Dados: " + dataBaseColumnsAndValues;
				if(result > 1){
					errorMessage = "Foi atualizado mais que uma linha. Dados: " + dataBaseColumnsAndValues;
				}
				addLineError(reportErrors, path , "" + k, errorMessage);
				sqlErrors++;
			}
			k++;
		}
		
		validateErrors(connection, path, reportErrors, sqlErrors);
		return results;
	}

	private static void validateErrors(Connection connection, String path, String reportErrors, int errors) {
		
		boolean hasErrors = errors > 0;
		
		if(hasErrors){
			rollback(connection);
			reportErrors = reportErrors.replace(".csv", "report_errors.csv");
			String errorMessage = String.format("Ocorreram erros ao copiar dados do arquivo sequencial '%s' para o banco de dados, para mais detalhes, favor abrir o arquivo '%s' ",path, reportErrors);
			throw new SystemException(errorMessage);
		}
	}

	private static void addLineError(String output, String observedFile, String lineNumber, String message) {
		
		output = output.replace(".csv", "report_errors.csv");
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("reportErrors", observedFile);	
//		AssertionsUtils.validateNotEmptyAndNotNullObject("lineNumber", lineNumber);	
		AssertionsUtils.validateNotEmptyAndNotNullObject("message", message);	
		
		String timeAndHour = getTimeAndHour();
		writeReportTitle(output);	
		FileUtils.appendLine(output, timeAndHour + ";" + observedFile + ";" +  lineNumber + ";" + message);
		
	}

	private static String getTimeAndHour() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date now = new Date();
		String timeAndHour = sdf.format(now);
		return timeAndHour;
	}

	private static int[] reportError(Connection connection, int errors, String message, Exception e, String inputFile,  String reportErrors) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("reportErrors", reportErrors);
		AssertionsUtils.validateNotEmptyAndNotNullObject("connection", connection);	
		AssertionsUtils.validateNotEmptyAndNotNullObject("inputFile", inputFile);	
		AssertionsUtils.validateNotEmptyAndNotNullObject("message", message);
		AssertionsUtils.validateNotEmptyAndNotNullObject("errors", errors);
		AssertionsUtils.validateNotEmptyAndNotNullObject("e", e);
		
//		reportErrors = reportErrors.replace(".csv", "report_errors.csv");

		String contentLine = message + ". Causado por: " + e.getMessage();
		addLineError(reportErrors,inputFile, "", contentLine);
		rollback(connection);
		throw new SystemException(message, e);
	}

	private static void writeReportTitle(String reportErrors) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("reportErrors", reportErrors);
		
		File errors = FileUtils.createFile(reportErrors);
		int fileLinesCount = FileUtils.getNumberOfRowsFromFile(errors);
		boolean firstTime = fileLinesCount == 0;
		
		if(firstTime){
			FileUtils.appendLine(reportErrors, "Data e Hora;Arquivo;Número da Linha;Mensagem de Erro");
		}
	}

	private static void rollback(Connection connection) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("connection", connection);	
		
		try {
			connection.rollback();
		} catch (SQLException e1) {
			// silenciator básico....
		}
	}

	
	/**
	 * Converte um pojo em uma linha de arquivo
	 * @param pojoInstance
	 * @param fileToWrite
	 * @param layoutConfig
	 */
	public static void _fromPojoToFile(Object pojoInstance, String fileToWrite, InputStream layoutConfig){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("pojoInstance", pojoInstance);	
		AssertionsUtils.validateNotEmptyAndNotNullObject("fileToWrite", fileToWrite);	
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutConfig", layoutConfig);	
		
		if(pojoInstance == null){
			return;
		}
		
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutFile", layoutConfig);
		AssertionsUtils.validateNotEmptyAndNotNullObject("fileToWrite", fileToWrite);

		LayoutCollection arquivoComLayouts = LayoutCollection._loadLayoutCollection(layoutConfig);		
		LayoutRepresentation layout = arquivoComLayouts._loadLayoutByHisName(pojoInstance.getClass().getSimpleName());
		layout._fromPojoSourceToFileTarget(pojoInstance, fileToWrite);
		
	}
	
	public static void _fromDataBaseToFile(InputStream layoutConfig, String layoutName, ResultSet resultSet, String fileToWrite){
		
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutConfig", layoutConfig);
		AssertionsUtils.validateNotEmptyAndNotNullObject("fileToWrite", fileToWrite);
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutName", layoutName);
		AssertionsUtils.validateNotEmptyAndNotNullObject("resultSet", resultSet);

		LayoutCollection arquivoComLayouts = LayoutCollection._loadLayoutCollection(layoutConfig);		
		LayoutRepresentation layout = arquivoComLayouts._loadLayoutByHisName(layoutName);
		while(true){
			try{
				layout._fromDataBaseSourceToFileTarget(resultSet, fileToWrite);
			}catch(EndOfResultSetException e){
				try {
					resultSet.close();
				} catch (SQLException e1) {
					throw new SystemException(e1);
				}
				break;
			}
		}
	}


	public static void _fromFileToDataBase(Connection connection, InputStream fileToStream, String input, String output) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("input", input);	
		AssertionsUtils.validateNotEmptyAndNotNullObject("input", output);	
		AssertionsUtils.validateNotEmptyAndNotNullObject("connection", connection);	
		AssertionsUtils.validateNotEmptyAndNotNullObject("fileToStream", fileToStream);	
	
		InsertStatementGenerator ssg = new InsertStatementGenerator();
		
		DatabaseConnectionDecorator cnn = new DatabaseConnectionDecorator(connection);
		_fromFileToDataBase(cnn, fileToStream , ssg, input, output);
	}

	public static List<Map<String, String>> _fromFileToMap(InputStream is, String path){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("is", is);	
		AssertionsUtils.validateNotEmptyAndNotNullObject("path", path);	

		LayoutCollection coll = LayoutCollection._loadLayoutCollection(is);
		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			String lineValue;
			
			while((lineValue = br.readLine()) != null){

				LayoutRepresentation layout = coll._findTheMostAppropriateLayoutForThisSequentialValue(lineValue);
				Map<String, String> map = layout.readFile(lineValue);
				result.add(map);
			}
		
		} catch (IOException e) {
			throw new SystemException("Erro ao ler o arquivo " + path, e);
		}finally{
			ReflectionUtils.closeResources(fr, br);
		}
		
		return result;
	}
	
	public static void _fromFileToCsv(InputStream is, String sequentialFilePath, String csvFilePath){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("sequentialFilePath", sequentialFilePath);
		AssertionsUtils.validateNotEmptyAndNotNullObject("csvFilePath", csvFilePath);
		AssertionsUtils.validateNotEmptyAndNotNullObject("is", is);
		
		FileReader fr = null;
		BufferedReader br = null;
		try{
			
			fr = new FileReader(sequentialFilePath); 
			br = new BufferedReader(fr);
			
			LayoutCollection coll = LayoutCollection._loadLayoutCollection(is);
			String lineValue;
			
			while((lineValue = br.readLine()) != null){

				LayoutRepresentation layout = coll._findTheMostAppropriateLayoutForThisSequentialValue(lineValue);
				
				Set<ColumnDataType> columns = layout.getColumns();
				long currentTimeMillis = System.currentTimeMillis();
				TargetCsv tc = new TargetCsv( csvFilePath.replace(".csv",  "_RELATORIO_" + currentTimeMillis + ".csv"), columns);
				
				layout._fromFileSourceToAnotherTarget(lineValue, tc);
			}
		
		} catch (IOException e) {
			throw new SystemException("Erro ao ler o arquivo " + sequentialFilePath, e);
		}finally{
			ReflectionUtils.closeResources(fr, br);
		}
	}
}
