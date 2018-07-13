package  com.ccp.sfr.columns;

import java.sql.ResultSet;
import java.util.Map;

import com.ccp.sfr.commons.AssertionsUtils;
import com.ccp.sfr.commons.Exceptions;
import com.ccp.sfr.commons.Exceptions.ColumnWithFixedValueException;
import com.ccp.sfr.commons.Exceptions.InsufficientColumnsException;
import com.ccp.sfr.commons.Exceptions.InvalidFixedSizeException;
import com.ccp.sfr.utils.NumberUtils;



public class ColumnFixedValue extends ColumnDataType {

	private String fixedValue;

	ColumnFixedValue(String columnName, Map<String, Object> configuration, String layoutName) {
		
		super(columnName, configuration, layoutName);
	}
	
	@Override
	void readExtraValues() {
		this.fixedValue = "" + configuration.get("fixedValue");  
	}

	public boolean isTheExpectedValue(String lineValue) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("lineValue", lineValue);
		
		try {
			String value = this.extractValueFromSequencialContent(lineValue);
			
			boolean isTheExpectedValue = value.equals(this.fixedValue);
			
			return isTheExpectedValue;
			
		} catch (InsufficientColumnsException e) {
			return  false;
		}
		
	}

	@Override
	public Object getDataBaseValue(String value) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);

		throw new ColumnWithFixedValueException();
	}
	
	@Override
	public String extractValueFromResultSet(ResultSet rs) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("rs", rs);
		
		return this.fixedValue;
	}
	
	@Override
	public String getFormattedValue(String value) {
		
		return this.fixedValue;
		
	}
	
	public boolean isFixedValue(){
		return true;
	}
	
	@Override
	void validateColumnWidth() {
		String widthColumn = super.getColumnWidth();
		
		boolean isInvalidNumber = NumberUtils.isPositiveIntegerNumber(widthColumn) == false;
		
		if(isInvalidNumber){
			String columnName = this.getColumnName();
			String layoutName = this.getLayoutName();
			throw new Exceptions.InvalidColumnWidthException(columnName, layoutName, widthColumn);
		}
		
		
		long columnWidth = NumberUtils.getValidIntNumber("widthColumn", widthColumn);
		int length = this.fixedValue.length();
		boolean invalidColumnLength = columnWidth != length;
		
		if(invalidColumnLength){
			String columnName = this.getColumnName();
			String layoutName = this.getLayoutName();
			throw new InvalidFixedSizeException(columnName, layoutName, columnWidth, this.fixedValue);
		}
	}

	@Override
	public String getDataBaseType() {
		throw new ColumnWithFixedValueException();
	}
	
	
}
