package  com.ccp.sfr.columns;

import java.util.List;
import java.util.Map;

import com.ccp.sfr.commons.AssertionsUtils;
import com.ccp.sfr.commons.Exceptions;



public class ColumnExpectedValues extends ColumnDataType {

	 List<?> expectedValues;

	ColumnExpectedValues(String columnName, Map<String, Object> configuration, String layoutName) {
		super(columnName, configuration, layoutName);
	}

	@Override
	public String getFormattedValue(String value) {
		
		boolean notContains = this.expectedValues.contains(value) == false;
		
		if(notContains){
			throw new Exceptions.ExpectedValuesException(value, this.expectedValues);
		}
		
		return value;
	}
	
	@Override
	void readExtraValues() {
		this.expectedValues = (List<?>)this.configuration.get("expectedValues");  
	}

	@Override
	public String getDataBaseValue(String value) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);

		
		return "'" + value + "'";
	}
	
	@Override
	public String getDataBaseType() {

		String columnWidth = this.getColumnWidth();
		String string = "VARCHAR(" + columnWidth + ")";
		return string;

	}
	
	public boolean isFixedValue(){
		return false;
	}

}
