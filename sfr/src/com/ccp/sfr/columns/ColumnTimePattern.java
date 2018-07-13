package  com.ccp.sfr.columns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.ccp.sfr.commons.AssertionsUtils;
import com.ccp.sfr.commons.Exceptions.InvalidTimeFormatException;
import com.ccp.sfr.utils.DateUtils;
import com.ccp.sfr.utils.SystemException;



public class ColumnTimePattern extends ColumnDataType{

	private String timePattern;
	
	ColumnTimePattern(String columnName, Map<String, Object> configuration, String layoutName) {
		super(columnName, configuration, layoutName);
	}
	@Override
	void readExtraValues() {
		this.timePattern = "" + this.configuration.get("timePattern");
	}
	@Override
	public String getFormattedValue(String value) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);
		
		boolean vindoDoBancoDeDados = value.length() == 21;
		if(vindoDoBancoDeDados){
			
			String pattern = "yyyy-MM-dd HH:mm:ss.S";
			
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			
			try {
				Date parse = sdf.parse(value);
				sdf = new SimpleDateFormat(this.timePattern);
				value = sdf.format(parse);
			} catch (ParseException e) {
				throw new SystemException("Erro ao converter '" + value + "' para data no formato '" + pattern + "'", e);
			}
		}
		
		
		boolean isInvalid = DateUtils.isValidDateTime(value, this.timePattern) == false;

		if (isInvalid) {
			throw new InvalidTimeFormatException(value, this.timePattern);
		}
		return value;
	}
	
	@Override
	public Object getDataBaseValue(String value) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);
		
		String format = String.format("TO_DATE('%s','%s')", value, this.timePattern);
//		String format = String.format("DATE_FORMAT('%s','%s')", value, "%Y-%m-%d");
		
		return format;
	}
	
	@Override
	public String getDataBaseType() {
		String columnWidth = this.getColumnWidth();
		String string = "VARCHAR(" + columnWidth + ")";
		return string;
	}
	
}
