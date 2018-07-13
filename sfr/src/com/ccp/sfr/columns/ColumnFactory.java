package  com.ccp.sfr.columns;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.ccp.sfr.commons.AssertionsUtils;
import com.ccp.sfr.commons.Exceptions.UndefinedTypeColumnException;
import com.ccp.sfr.utils.SystemException;
import com.google.gson.Gson;

public class ColumnFactory {

	@SuppressWarnings("unchecked")
	public static ColumnDataType newColumn(String columnName, String columnConfiguration, String layoutName){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("columnName", columnName);
		AssertionsUtils.validateNotEmptyAndNotNullObject("columnConfiguration", columnConfiguration);
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutName", layoutName);
		
		Gson gson = new Gson();
		
		String formatted = columnConfiguration.trim();
		
		HashMap<String, Object> fromJson;
		try {
			fromJson = gson.fromJson(formatted, HashMap.class);
		} catch (com.google.gson.JsonSyntaxException e) {
			String message = String.format("A coluna '%s' do layout '%s' está com um json em formato incorreto: %s", columnName,layoutName, formatted);
			throw new SystemException(message, e);
		}
		
		Map<String, Object> configuration = Collections.unmodifiableMap(fromJson);
		
		boolean isDataType = configuration.containsKey("dataType");

		if(isDataType){

			ColumnDataType column = new ColumnDataType(columnName, configuration, layoutName);
			
			return column;
		}
		
		boolean isFixedValue = configuration.containsKey("fixedValue");
		
		if(isFixedValue){
			ColumnDataType column = new ColumnFixedValue(columnName, configuration, layoutName);
			return column;
		}
		boolean isFractionalDigits = configuration.containsKey("fractionalDigits");
		
		if(isFractionalDigits){
			ColumnDataType column = new ColumnFractionalDigits(columnName, configuration, layoutName);
			return column;
		}

		boolean isTimePattern = configuration.containsKey("timePattern");
		
		if(isTimePattern){
			ColumnDataType column = new ColumnTimePattern(columnName, configuration, layoutName);
			return column;
		}

		boolean isExpectedValues = configuration.containsKey("expectedValues");
		
		if(isExpectedValues){
			ColumnDataType column = new ColumnExpectedValues(columnName, configuration, layoutName);
			return column;
		}

		boolean isSplitter = configuration.containsKey("splitter");
		
		if(isSplitter){
			ColumnDataType column = new ColumnSplitter(columnName, configuration, layoutName);
			return column;
		}

		
		throw new UndefinedTypeColumnException(layoutName, columnName, columnConfiguration);
	}
	
	
}
