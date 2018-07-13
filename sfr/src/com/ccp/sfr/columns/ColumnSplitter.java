package  com.ccp.sfr.columns;

import java.util.Map;

import com.ccp.sfr.commons.AssertionsUtils;
import com.ccp.sfr.commons.Exceptions.InvalidIntegerException;
import com.ccp.sfr.commons.Exceptions.ValueWithoutSplitterException;
import com.ccp.sfr.utils.NumberUtils;



public class ColumnSplitter extends ColumnDataType{

	private String splitter;
	
	@Override
	void readExtraValues() {
		this.splitter = "" + this.configuration.get("splitter");
	}

	
	ColumnSplitter(String columnName, Map<String, Object> configuration, String layoutName) {
		super(columnName, configuration, layoutName);
	}
	@Override
	public String getFormattedValue(String value) {

		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);

		boolean anoMes = value.length() == 6;
		if(anoMes){
			value = value.substring(0, 4) + "." + value.substring(4);
		}
		
		this.validateSplitter(value);
		this.validate(value);
		return value;
	}
	
	@Override
	public Object getDataBaseValue(String value) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);
		
		this.validateSplitter(value);
		 
		String replace = this.getReplacedValue(value);
		
		return replace;
	}


	private void validateSplitter(String value) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);
		
		boolean valueWithoutSplitter = false == value.contains(this.splitter);
		if(valueWithoutSplitter){
			throw new ValueWithoutSplitterException(this.splitter);
		}
	}


	private String getReplacedValue(String value) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);
		
		String replace = this.validate(value);
		return replace;
	}


	private String validate(String value) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);
		
		String replace = value.replace(this.splitter, "");
		
		boolean isInvalid = false == NumberUtils.isPositiveIntegerNumber(replace);
		
		if(isInvalid){
			throw new InvalidIntegerException(this.splitter);
		}
		return replace;
	}
	
	@Override
	public String getDataBaseType() {
		String columnWidth = this.getColumnWidth();
		String string = "NUMBER(" + columnWidth + ",0)";
		return string;
	}

}
