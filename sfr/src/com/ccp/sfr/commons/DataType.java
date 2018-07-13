package  com.ccp.sfr.commons;

import com.ccp.sfr.commons.Exceptions.InvalidLengthColumnException;
import com.ccp.sfr.utils.NumberUtils;
import com.ccp.sfr.utils.StringUtils;


/**
 * 
 * @author Onias Vieira Junior
 *
 */
public enum DataType {

	NUMBER,
	TEXT,
	RESERVED_TEXT,
	RESERVED_NUMBER,
	NOT_EMPTY_TEXT{
		@Override
		public boolean isValidValue(String value) {
			boolean isValid = false == AssertionsUtils.isEmptyOrNull(value);
			return isValid;
		}
	},
	;

//	public abstract boolean isReserved();
	
	public static boolean isDataType(String name){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("name", name);
		
		DataType[] values = DataType.values();
		
		for (DataType dataType : values) {
			boolean equals = dataType.name().equals(name);
			if(equals){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isNumber(){
		String name = this.name();
		boolean x = name.contains("NUMBER");
		return x;
	}
	
	public boolean isReserved(){

		String name = this.name();
		boolean startsWith = name.startsWith("RESERVED_");
		return startsWith;
	}
	
	public boolean isValidValue(String value) {
		
		
		boolean reserved = this.isReserved();
		
		if(reserved) {
			return true;
		}
		
		boolean isAlpha = false == this.isNumber();
		
		if(isAlpha) {
			return true;
		}
		
		boolean isNumber = NumberUtils.isPositiveIntegerNumber(value);
		
		if(isNumber) {
			return true;
		}
		
		return false;
	}
	
	public String getFormattedValue(String value, int columnWidth){
		
		//Added by Marina
//		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);
		AssertionsUtils.validateNotEmptyAndNotNullObject("columnWidth", columnWidth);
		if(value == null){
			value = "";
		}
		
		
		int length = value.length();
		
		if(length > columnWidth){
			throw new InvalidLengthColumnException(value, columnWidth);
		}
		
		char complement = ' ';

		boolean isReserved = this.isReserved();
		
		if(isReserved){
			value = "";
		}
		
		boolean isNumber = this.isNumber();
		
		if(isNumber){
			complement = '0';
			
		}
		
		
		String formattedValue = StringUtils.completeLeftString(value, complement, columnWidth);
		
		return formattedValue;
	}
	
}


//String getValue(String value){
//if(value == null){
//	return "";
//}
//value = value.toUpperCase();
//String stripAccents = StringUtils.stripAccents(value);
//char newValue = ' ';
//String semCaracteresEspeciais = br.com.santander.reinf.utils.StringUtils.replaceString(stripAccents, newValue, 
//		'!', '@', '#', '$', '%', '&', '*', '(', ')', '-', '+', '\\', '|', ',', '<', '>', '.', ';', ':', '/', '?', '[', ']', '{', '}'
//		, '_');
//return semCaracteresEspeciais;
//}
