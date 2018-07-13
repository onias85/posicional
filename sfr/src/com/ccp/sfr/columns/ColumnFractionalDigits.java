package  com.ccp.sfr.columns;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Map;

import com.ccp.sfr.commons.AssertionsUtils;
import com.ccp.sfr.commons.Exceptions;
import com.ccp.sfr.commons.Exceptions.InvalidFractionalDigitsException;
import com.ccp.sfr.utils.NumberUtils;



public class ColumnFractionalDigits extends ColumnDataType {

	private String fractionalDigits;

	ColumnFractionalDigits(String columnName, Map<String, Object> configuration, String layoutName) {
		super(columnName, configuration, layoutName);
		this.validateFractionalDigits();
	}

	@Override
	void readExtraValues() {
		this.fractionalDigits = "" + super.configuration.get("fractionalDigits");
	}
	private void validateFractionalDigits() {

		boolean isNotIntNumber = NumberUtils.isPositiveIntegerNumber(this.fractionalDigits) == false;
		
		if(isNotIntNumber){
			String columnName = this.getColumnName();
			String layoutName = this.getLayoutName();
			String columnWidth = this.getColumnWidth();
			throw new InvalidFractionalDigitsException(columnName, layoutName, columnWidth);
		}
	}
	
	@Override
	public String getFormattedValue(String value) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);
		
		return value;
	}

	private String format(String value) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);
		
		NumberFormat nf = new DecimalFormat(",##0,00");
		try {
			Number parse = nf.parse(value);
			return parse.toString();
		} catch (ParseException e) {
			throw new Exceptions.InvalidDecimalValueException(value);
		}
	}

	@Override
	public Object getDataBaseValue(String value) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);
		
		String format = this.format(value);
		return format;
	}
	
	@Override
	public String getDataBaseType() {
	
			
		String columnWidth = this.getColumnWidth();
		
		int fd = NumberUtils.getValidIntNumber("fractionalDigits", this.fractionalDigits).intValue();
		int cw = NumberUtils.getValidIntNumber("columnWidth", columnWidth).intValue();
		int intDigits = cw - fd;
		
		String string = "DECIMAL(" + intDigits + "," + this.fractionalDigits + ")";
		return string;

	}

}
