package  com.ccp.sfr.commons;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.ccp.sfr.columns.ColumnDataType;
import com.ccp.sfr.commons.Exceptions.PropertyNotFoundException;


public abstract class SqlStatementGenerator {

	protected LayoutRepresentation layout;

	protected LayoutCollection layouts;

	protected String fileToRead;
	
	protected String lineValue;

	
	protected String getSqlStatement(InputStream layoutConfiguration, String fileToRead, String lineValue) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutConfiguration", layoutConfiguration);
		AssertionsUtils.validateNotEmptyAndNotNullObject("fileToRead", fileToRead);
		AssertionsUtils.validateNotEmptyAndNotNullObject("lineValue", lineValue);

		this.layouts = LayoutCollection._loadLayoutCollection(layoutConfiguration);

		this.layout = this.layouts._findTheMostAppropriateLayoutForThisSequentialValue(lineValue);

		this.fileToRead = fileToRead;
		
		this.lineValue = lineValue;
		
		SqlTarget sqlTarget = this.getSqlTarget();

		this.layout._fromFileSourceToAnotherTarget(lineValue, sqlTarget);

		String sql = sqlTarget.getSql();
		
		return sql;
	}

	protected abstract SqlTarget getSqlTarget();
	
	public Map<String, String> getDataBaseColumnsAndValues(String lineValue){
		
		this.layout = this.layouts._findTheMostAppropriateLayoutForThisSequentialValue(lineValue);
		
		Set<ColumnDataType> columns = this.layout.getColumns();
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		for (ColumnDataType column : columns) {
			try {
			
				String columnValue = column.extractFormattedValueFromSequencialContent(lineValue);
				String columnName = "" + column.getPropertyValue("columnDataBase");
				
				map.put(columnName, columnValue);
				
			} catch (PropertyNotFoundException e) {
				continue;
			}
		}
		
		return map;
		
		
	}
	
}
