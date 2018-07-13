package  com.ccp.sfr.commons;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.ccp.sfr.columns.ColumnDataType;
import com.ccp.sfr.columns.ColumnFactory;
import com.ccp.sfr.commons.Exceptions.ColumnWithFixedValueException;
import com.ccp.sfr.commons.Exceptions.HasNotTableException;

/**
 * Classe responsavel por delegar escrita de conteudos de acordo com o layout informado ao seu construtor.
 * @author Onias Vieira Junior
 *
 */
public class LayoutRepresentation {
	
	private final Set<ColumnDataType> columns;

	private final String parentIndexFieldName;

	private final LayoutRepresentation parent;

	private final String indexFieldName;
	
	private final String layoutName;

	private final String tableName;
	
	 LayoutRepresentation(String layoutName, Map<String, String> layoutConfiguration, Map<String, String> dataBaseConfiguration) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("dataBaseConfiguration", dataBaseConfiguration);
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutConfiguration", layoutConfiguration);
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutName", layoutName);
		
		this.parent = null;
		this.layoutName = layoutName;
		this.tableName = dataBaseConfiguration.get("tableName");
//		this.indexFieldName = dataBaseConfiguration.get("indexFieldName", "TABLE_INDEX");
		this.indexFieldName = dataBaseConfiguration.get("indexFieldName");
//		this.parentIndexFieldName = dataBaseConfiguration.getOrDefault("parentIndexFieldName", "PARENT_INDEX");
		this.parentIndexFieldName = dataBaseConfiguration.get("parentIndexFieldName");
		
		final Set<ColumnDataType> columns = this.extractColumns(layoutConfiguration);
		
		this.columns = Collections.unmodifiableSet(columns);
	}
	
	LayoutRepresentation(String layoutName, Map<String, String> layoutConfiguration, Map<String, String> dataBaseConfiguration, LayoutRepresentation parent) {

		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutName", layoutName);
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutConfiguration", layoutConfiguration);
		AssertionsUtils.validateNotEmptyAndNotNullObject("dataBaseConfiguration", dataBaseConfiguration);
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutName", parent);
		
//		this.parentIndexFieldName = dataBaseConfiguration.getOrDefault("parentIndexFieldName", "PARENT_INDEX");
//		this.indexFieldName = dataBaseConfiguration.getOrDefault("indexFieldName", "TABLE_INDEX");
		this.parentIndexFieldName = dataBaseConfiguration.get("parentIndexFieldName");
		this.indexFieldName = dataBaseConfiguration.get("indexFieldName");
		this.tableName = dataBaseConfiguration.get("tableName");
		this.layoutName = layoutName;
		this.parent = parent;

		final Set<ColumnDataType> columns = this.extractColumns(layoutConfiguration);
		
		this.columns = Collections.unmodifiableSet(columns);
		
	}
	
	private synchronized void writeThisLine(Map<String, String> columnsAndTheirValues, Target whereToWrite){

		AssertionsUtils.validateNotEmptyAndNotNullObject("whereToWrite", whereToWrite);
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("columnsAndTheirValues", columnsAndTheirValues);
		
		Map<ColumnDataType, String> map = new LinkedHashMap<ColumnDataType, String>();
		
		for (ColumnDataType column : this.columns) {
			
			boolean fixedValue = column.isFixedValue();
		
			if(fixedValue){
				fillFixedValue(map, column);
				continue;
			}
		
			String formattedValue = this.getFormattedValue(columnsAndTheirValues, column);
			map.put(column, formattedValue);
		}
		
		whereToWrite.write(map);
	}

	private void fillFixedValue(Map<ColumnDataType, String> map, ColumnDataType column) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("map", map);
		AssertionsUtils.validateNotEmptyAndNotNullObject("column", column);
				
		String formattedValue = column.getFormattedValue("");

		map.put(column, formattedValue);
	}
	
	private String getFormattedValue(Map<String, String> columnsAndTheirValues, ColumnDataType column){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("columnsAndTheirValues", columnsAndTheirValues);
		AssertionsUtils.validateNotEmptyAndNotNullObject("column", column);

		String columnName = column.getColumnName();
		String value = columnsAndTheirValues.get(columnName);
		String formattedValue = column.getFormattedValue(value);
		return formattedValue;
	}

	private Set<ColumnDataType> extractColumns(	Map<String, String> layoutConfiguration) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutConfiguration", layoutConfiguration);
		
		Set<String> keySet = layoutConfiguration.keySet();
		
		final Set<ColumnDataType> columns = new HashSet<ColumnDataType>();
		
		ArrayList<Throwable> erros = new ArrayList<Throwable>();
		
		int finalPosition = 1;
		
		for (String columnName : keySet) {
			ColumnDataType column = null;
			try {
				String columnConfiguration = layoutConfiguration.get(columnName);
				column = ColumnFactory.newColumn(columnName, columnConfiguration, this.layoutName);
				column.validateCursorPosition(finalPosition);
				finalPosition = column.getFinalPosition() + 1;
				columns.add(column);
				
			} catch (RuntimeException e) {
				erros.add(e);
				if(column == null){
					continue;
				}
				finalPosition = column.getFinalPosition() + 1;
			}
		}
		
		AssertionsUtils.checkForErrors(erros);
		return columns;
	}

	private Map<String, String> readFromSomeWhere(Source fromWhereToRead){
		
		AssertionsUtils.validateNotEmptyAndNotNullObject("fromWhereToRead", fromWhereToRead);
		Map<String, String> read = fromWhereToRead.read(this.columns);
		return read;
	}

	public void _fromAnySourceToAnyTarget(Source source, Target target){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("source", source);
		AssertionsUtils.validateNotEmptyAndNotNullObject("target", target);
		
		Map<String, String> readFromSomeWhere = this.readFromSomeWhere(source);
		this.writeThisLine(readFromSomeWhere, target);
	}

	public void _fromDataBaseSourceToFileTarget(ResultSet resultSet, String fileToWrite){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("resultSet", resultSet);
		AssertionsUtils.validateNotEmptyAndNotNullObject("fileToWrite", fileToWrite);
		
		Source source = new SourceDataBase(resultSet);
		Target target = new TargetFileLine(fileToWrite);
		this._fromAnySourceToAnyTarget(source, target);
	}
	public void _fromFileSourceToAnotherTarget(String sequencialContent, Target target){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("sequencialContent", sequencialContent);
		AssertionsUtils.validateNotEmptyAndNotNullObject("target", target);
		
		Source source = new SourceFile(sequencialContent);
		this._fromAnySourceToAnyTarget(source, target); 
	}
	
	public void _fromAnySourceToFileTarget(Source source, String fileToWrite){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("source", source);
		AssertionsUtils.validateNotEmptyAndNotNullObject("fileToWrite", fileToWrite);
		
		Target target = new TargetFileLine(fileToWrite);
		this._fromAnySourceToAnyTarget(source, target);
	}
	
	public void _fromPojoSourceToFileTarget(Object pojoInstance, String fileToWrite){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("pojoInstance", pojoInstance);
		AssertionsUtils.validateNotEmptyAndNotNullObject("fileToWrite", fileToWrite);
		
		Source source = new SourcePojo(pojoInstance);
		Target target = new TargetFileLine(fileToWrite);
		this._fromAnySourceToAnyTarget(source, target);
		
	}

	public String getLayoutName(){
		AssertionsUtils.validateNotEmptyAndNotNullObject("this.layoutName", this.layoutName);
		return this.layoutName;
	}
	
	public Map<String, String> readFile(String lineValue){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("lineValue", lineValue);
		
		Source instance = new SourceFile(lineValue);
		Map<String, String> map = this.readFromSomeWhere(instance);
		map.put("layoutName", this.layoutName);
		
		return map;
	}
	

	
	public boolean isThisLayout(String lineValue){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("lineValue", lineValue);
		
		boolean isThisLayout = true;
		
		for (ColumnDataType column : this.columns) {
			
			if(isThisLayout == false){
				return false;
			}
			
			boolean hasNotFixedValue = column.isFixedValue() == false;
			
			if(hasNotFixedValue){
				continue;
			}
			
			boolean isTheExpectedValue = column.isTheExpectedValue(lineValue);
			
			isThisLayout &= isTheExpectedValue;
		}
		return true;
		
//		boolean isThisLayout = this.columns.stream().filter(column -> column.isFixedValue())
//				.filter(column -> column.isTheExpectedValue(lineValue) == false).count() == 0;

	}

	public String getTableName() {
		boolean hasNotTable = AssertionsUtils.isEmptyOrNull(this.tableName);
		
		if(hasNotTable){
			throw new HasNotTableException(this.layoutName);
		}
		return this.tableName;
	}

	boolean hasParent() {
		
		boolean hasParent = AssertionsUtils.isEmptyOrNull(this.parent) == false;
		return hasParent;
	}
	
	LayoutRepresentation getParent(){
		return this.parent;
	}

	String getParentIndexFieldName() {
		return this.parentIndexFieldName;
	}

	String getIndexFieldName() {
		return this.indexFieldName;
	}

	@Override
	public String toString() {
		return "LayoutRepresentation [layoutName=" + this.layoutName + "]";
	}
	
	public String getMySqlSyntaxCreateTable(){
		
		boolean noColumns = this.columns.size() == 0;
		
		if(noColumns){
			return "";
		}
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("CREATE TABLE ").append(this.tableName).append("(ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY");
		
		
		for (ColumnDataType column : this.columns) {
			try {
				String dataBaseType = column.getDataBaseType();
				String columnDataBaseName = "" + column.getPropertyValue("columnDataBase");
				sql.append(",").append(columnDataBaseName).append(" ");
				sql.append(dataBaseType);
				
			} catch (ColumnWithFixedValueException e) {
				continue;
			}
		}
		
		sql.append(");");
		
		return sql.toString();
	}

	public Map<String, String> getColumnsAndValues(String lineValue) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("lineValue", lineValue);
	
		Map<String, String> columnsAndValues = new LinkedHashMap<String, String>();
		for (ColumnDataType column : this.columns) {
			String value = column.extractValueFromSequencialContent(lineValue);
			columnsAndValues.put(column.getColumnName(), value);
		}
		
		return columnsAndValues;
	}

	public Set<ColumnDataType> getColumns() {
		return columns;
	}

	
}