package  com.ccp.sfr.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ccp.sfr.commons.AssertionsUtils;

public class StringUtils {

	/**
	 * Concatena uma string pela esquerda com o caractere especificado ate determinado length
	 * @param str
	 * @param compl
	 * @param length
	 * @return
	 */
	public static String completeLeftString(String str, char compl, int length) {
		
		//Added by Marina
//		AssertionsUtils.validateNotEmptyAndNotNullObject("str", str);
		AssertionsUtils.validateNotEmptyAndNotNullObject("compl", compl);
		AssertionsUtils.validateNotEmptyAndNotNullObject("length", length);
		
		if (str == null) {
			str = "";
		}

		StringBuilder sb = new StringBuilder();

		int diferenca = length - str.length();

		for (int k = 0; k < diferenca; k++) {
			sb.append(compl);
		}

		sb.append(str);

		return sb.toString();
	}

	/**
	 * Concatena uma string pela direita com o caractere especificado ate determinado length
	 * @param str
	 * @param compl
	 * @param length
	 * @return
	 */
	public static String completeRightString(String str, char compl, int length) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("str", str);
		AssertionsUtils.validateNotEmptyAndNotNullObject("compl", compl);
		AssertionsUtils.validateNotEmptyAndNotNullObject("length", length);

		if (str == null) {
			str = "";
		}

		StringBuilder sb = new StringBuilder(str);

		int diferenca = length - str.length();

		for (int k = 0; k < diferenca; k++) {
			sb.append(compl);
		}

		return sb.toString();
	}

	/**
	 * Converte uma string em map de acordo com seus delimitadores ex: nome=onias&idade=32&estado=sp para  map.put("idade", "32"); map.put("nome", "onias"); map.put("estado", "sp");
	 * 
	 * @param str
	 * @param compl
	 * @param length
	 * @return
	 */
	public static Map<String, String> convertLineValueToMap(String value, char lineDelimiter, char columnDelimiter) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);
		AssertionsUtils.validateNotEmptyAndNotNullObject("lineDelimiter", lineDelimiter);
		AssertionsUtils.validateNotEmptyAndNotNullObject("columnDelimiter", columnDelimiter);

		if (value == null) {
			return new HashMap<String, String>();
		}

		Map<String, String> map = new HashMap<String, String>();

		String[] lines = value.split("" + lineDelimiter);

		for (String line : lines) {

			boolean emptyLine = line == null || line.trim().length() == 0;

			if (emptyLine) {
				continue;
			}

			String[] columns = line.split("" + columnDelimiter);

			boolean incorrectColumnLayout = columns.length != 2;

			if (incorrectColumnLayout) {
				continue;
			}

			String key = columns[0];

			if (key == null) {
				continue;
			}

			String columnValue = columns[1];

			if (value == null) {
				continue;
			}

			map.put(key, columnValue);
		}

		return map;
	}

	/**
	 * Apresentada uma collection de string, ele a percorre e retorna o maior de seus lengths
	 * @param coll
	 * @return
	 */
	public static int getLargestLength(Collection<String> coll) {


		AssertionsUtils.validateNotEmptyAndNotNullObject("coll", coll);
		
		int largest = 0;

		for (String string : coll) {

			if (string == null) {
				continue;
			}

			if (largest < string.length()) {
				largest = string.length();
			}
		}

		return largest;
	}

	/**
	 * Troca determinados valores (charsToReplace) de uma string (stringToReplace) por um novo valor (newValue)
	 * @param stringToReplace
	 * @param newValue
	 * @param charsToReplace
	 * @return
	 */
	public static String replaceString(String stringToReplace, char newValue, char...charsToReplace){
	
		AssertionsUtils.validateNotEmptyAndNotNullObject("stringToReplace", stringToReplace);
		AssertionsUtils.validateNotEmptyAndNotNullObject("charsToReplace", charsToReplace);
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("newValue", newValue);

		for (char oldValue : charsToReplace) {
		
			stringToReplace = stringToReplace.replace(oldValue, newValue);
		}
		
		return stringToReplace;
	}

	public static boolean isOutOfRange(String str, int min, int max){
		
		if(str == null){
			str = "";
		}
		
		int length = str.length();
		
		if(length < min){
			return true;
		}
		
		if(length > max){
			return true;
		}
		
		
		return false;
		
	}
}
