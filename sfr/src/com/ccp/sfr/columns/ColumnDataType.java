package  com.ccp.sfr.columns;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.ccp.sfr.commons.AssertionsUtils;
import com.ccp.sfr.commons.DataType;
import com.ccp.sfr.commons.Exceptions;
import com.ccp.sfr.commons.Exceptions.ColumnResultSetException;
import com.ccp.sfr.commons.Exceptions.InsufficientColumnsException;
import com.ccp.sfr.commons.Exceptions.InvalidColumnWidthException;
import com.ccp.sfr.commons.Exceptions.InvalidDataTypeException;
import com.ccp.sfr.commons.Exceptions.InvalidInitialPositionException;
import com.ccp.sfr.commons.Exceptions.PropertyNotFoundException;
import com.ccp.sfr.utils.NumberUtils;



/**
 * 
 * @author Onias Vieira Junior
 *
 */
public class ColumnDataType {

	final Map<String, Object> configuration;
	
	private final String initialPosition;
	private final Set<String> attributes;
	private final String columnName;
	private final String layoutName;
	private String columnWidth;
	private String dataType;
	

	ColumnDataType(String columnName, Map<String, Object> configuration, String layoutName) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("valorAntesDoSinalDeIgual", columnName);
		AssertionsUtils.validateNotEmptyAndNotNullObject("configuration", configuration);
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutName", layoutName);

		this.configuration = configuration;
		
		this.initialPosition = "" + configuration.get("initialPosition");
		this.columnWidth =  "" + configuration.get("columnWidth");
		Set<String> keySet = configuration.keySet();
		this.attributes = Collections.unmodifiableSet(keySet);

		this.columnName = columnName;
		this.layoutName = layoutName;

		this.readExtraValues();
		this.validateColumnWidth();
		this.validateInitialPosition();
	}

	void readExtraValues() {
		this.dataType = "" + this.configuration.get("dataType");

	}

	private void validateInitialPosition() {

		boolean invalidInitialPosition = NumberUtils.isPositiveIntegerNumber(this.initialPosition) == false;

		if (invalidInitialPosition) {
			throw new InvalidInitialPositionException(this.columnName, this.layoutName, this.initialPosition);
		}
	}

	private DataType getDataTypeEnum() {

		boolean isNotDataType = DataType.isDataType(this.dataType) == false;

		if (isNotDataType) {
			throw new InvalidDataTypeException(this.columnName, this.dataType);
		}

		DataType valueOf = DataType.valueOf(this.dataType);
		return valueOf;
	}

	void validateColumnWidth() {

		boolean invalidDigitsQuantity = NumberUtils.isPositiveIntegerNumber(this.columnWidth) == false;

		if (invalidDigitsQuantity) {
			throw new InvalidColumnWidthException(this.columnName, this.layoutName, this.columnWidth);
		}

	}

	public String getFormattedValue(String value) {
		

		DataType valueOf = this.getDataTypeEnum();
		
		boolean isInvalid = false == valueOf.isValidValue(value);
		if(isInvalid) {
			throw new Exceptions.InvalidColumnValueException(this.dataType);
		}

		String columnValue = valueOf.getFormattedValue(value, new Integer(this.columnWidth));
		return columnValue;
	}

	public int getFinalPosition() {

		int intitalPosition = new Integer(this.initialPosition);

		int columnWidth = new Integer(this.columnWidth);

		int sum = (intitalPosition - 1) + columnWidth;

		return sum;
	}

	public Object getDataBaseValue(String value) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);
		
		DataType valueOf = this.getDataTypeEnum();

		boolean isNumber = valueOf.isNumber();
		
		if(isNumber){
			return value;
		}
		
		return "'" + value.trim() + "'";
		
	}

	@Override
	public int hashCode() {
		return this.columnName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("obj", obj);
		
		try {
			return ((ColumnDataType) obj).columnName.equals(this.columnName);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String toString() {
		String columnDetails = this.getColumnDetails();
		return columnDetails;
	}

	public String extractValueFromResultSet(ResultSet rs) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("rs", rs);

		boolean reservedColumn = this.isReservedColumn();

		if (reservedColumn) {
			DataType dataTypeEnum = this.getDataTypeEnum();
			Integer columnWidth2 = new Integer(this.columnWidth);
			String columnValue = dataTypeEnum.getFormattedValue("",	columnWidth2);
			return columnValue;
		}

		try {
			Object object = this.getPropertyValue("columnDataBase");
			String string = rs.getString(object.toString());
			return string;
		}catch(PropertyNotFoundException e){
			return "";
		}catch (Throwable e) {
			throw new ColumnResultSetException(this.columnName, e);
		}
	}

	private boolean isReservedColumn() {

		boolean hasNotDataType = AssertionsUtils.isEmptyOrNull(this.dataType);

		if (hasNotDataType) {
			return false;
		}

		DataType dataTypeEnum = this.getDataTypeEnum();

		boolean reservedValueColumn = dataTypeEnum.isReserved();

		if (reservedValueColumn) {
			return true;
		}
		return false;
	}

	public String extractValueFromSequencialContent(String lineValue) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("lineValue", lineValue);

		int initialPosition = NumberUtils.getValidIntNumber("this.initialPosition", this.initialPosition).intValue() - 1;

		int finalPosition = this.getFinalPosition();

		int length = lineValue.length();
		boolean insufficientColumns = length < finalPosition;

		if (insufficientColumns) {
			throw new InsufficientColumnsException(finalPosition, lineValue);
		}

		String substring = lineValue.substring(initialPosition, finalPosition);

		return substring;
	}
	
	public String extractFormattedValueFromSequencialContent(String lineValue) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("lineValue", lineValue);
	
		String value = this.extractValueFromSequencialContent(lineValue);
	
		String formattedValue = this.getFormattedValue(value);
		
		return formattedValue;
	}	
	
	public boolean isTheExpectedValue(String lineValue) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("lineValue", lineValue);
		
		return true;
	}

	public String getColumnWidth() {
		return columnWidth;
	}

	public String getInitialPosition() {
		return initialPosition;
	}

	public Set<String> getAttributes() {
		return attributes;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getLayoutName() {
		return layoutName;
	}

	public String getDataType() {
		return dataType;
	}

	public boolean isFixedValue() {
		return false;
	}

	public void validateCursorPosition(int finalPosition){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("finalPosition", finalPosition);
		
		String initialPosition = this.getInitialPosition();
		Integer ip = new Integer(initialPosition);
		Integer obj = new Integer(finalPosition);
		boolean invalidInitialPosition = ip.equals(obj) == false;
		if(invalidInitialPosition){
			throw new InvalidInitialPositionException(this.layoutName, this.columnName, finalPosition, ip);
		}
	}
	
	public String getDataBaseType(){
		
		DataType valueOf = this.getDataTypeEnum();

		boolean isNumericColumn = valueOf.isNumber();

		if (isNumericColumn) {
			String string = "DECIMAL(" + this.columnWidth + ",0)";
			return string;
		}
		String string = "VARCHAR(" + this.columnWidth + ")";
		return string;
	}
	
	public Object getPropertyValue(String propertyName){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("propertyName", propertyName);
		
		boolean hasNotKey = this.configuration.containsKey(propertyName) == false;
		
		if(hasNotKey){
			Set<String> keySet = this.configuration.keySet();
			throw new Exceptions.PropertyNotFoundException(propertyName, this.columnName, this.layoutName, keySet);
		}
		Object object = this.configuration.get(propertyName);
		return object;
	}
	
	public boolean hasProperty(String propertyName){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("propertyName", propertyName);
	
		boolean hasProperty = this.configuration.containsKey(propertyName);
		
		return hasProperty;
	}
	
	public String getColumnDetails(){
		int finalPosition = this.getFinalPosition();
		Integer initialPosition = new Integer(this.initialPosition);
		String columnDetails = String.format("Coluna: '%s', Layout: '%s', Posições: [de '%d' até '%d']  ", this.columnName, this.layoutName, initialPosition, finalPosition);
		return columnDetails;
	}
}