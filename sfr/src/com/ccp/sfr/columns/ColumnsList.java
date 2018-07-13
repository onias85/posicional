package  com.ccp.sfr.columns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.ccp.sfr.commons.AssertionsUtils;
import com.ccp.sfr.commons.Exceptions;
import com.ccp.sfr.commons.Exceptions.PropertyNotFoundException;
import com.ccp.sfr.utils.Utils;




public class ColumnsList {
	public final List<ColumnDataType> columns;

	public ColumnsList(Collection<ColumnDataType> columns) {
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("columns", columns);

		columns = Utils.getSortedColumns(new HashSet<ColumnDataType>(columns));	
		
		ArrayList<ColumnDataType> list = new ArrayList<ColumnDataType>(columns);
		this.columns = Collections.unmodifiableList(list);
		
	}

	public ColumnsList filterByExistentPorperty(String propertyName){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("propertyName", propertyName);		
		
		List<ColumnDataType> columns = new ArrayList<ColumnDataType>();

		for (ColumnDataType column : this.columns) {
			
			boolean hasNot = column.hasProperty(propertyName) == false;
			
			if(hasNot){
				continue;
			}
			columns.add(column);
		}
		
		ColumnsList columnsCollection = new ColumnsList(columns);
		return columnsCollection;
	}

	public ColumnsList filterByExpectedValue(String lineValue){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("lineValue", lineValue);	
		
		List<ColumnDataType> columns = new ArrayList<ColumnDataType>();

		for (ColumnDataType column : this.columns) {
			
			boolean isNotTheExpectedValue = column.isTheExpectedValue(lineValue) == false;
			
			if(isNotTheExpectedValue){
				continue;
			}
			columns.add(column);
		}
		
		ColumnsList columnsCollection = new ColumnsList(columns);
		return columnsCollection;
	}

	
	public ColumnsList filterByExistentPorpertyValue(String propertyName, String propertyValue){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("propertyName", propertyName);
		AssertionsUtils.validateNotEmptyAndNotNullObject("propertyValue", propertyValue);
		
		List<ColumnDataType> columns = new ArrayList<ColumnDataType>();

		for (ColumnDataType column : this.columns) {
			
			boolean hasNot = column.hasProperty(propertyName) == false;
			
			if(hasNot){
				continue;
			}
			
			Object value = column.getPropertyValue(propertyName);
			boolean isNot = propertyValue.equals(value) == false;
			if(isNot){
				continue;
			}
			columns.add(column);
		}
		
		ColumnsList columnsCollection = new ColumnsList(columns);
		return columnsCollection;
	}
	
	public boolean hasElements(){
		boolean b = this.columns.size() == 0 == false;
		return b;
	}
	
	public ColumnDataType getFirst() {
		
		boolean emptyColumns = this.columns.size() == 0;
		if(emptyColumns) {
			throw new Exceptions.EmptyColumnsException();
		}
		
		ColumnDataType columnDataType = this.columns.get(0);
		return columnDataType;
	}
	
	
	public Iterable<String> listPropertyValues(String propertyName){
		
		
		ArrayList<String> result = new ArrayList<String>();
		
		for (ColumnDataType columnDataType : this.columns) {
			try {
				String value = "" + columnDataType.getPropertyValue(propertyName);
				result.add(value);
			} catch (PropertyNotFoundException e) {
				continue;
			}
		}

		
		return result;
	}

}
