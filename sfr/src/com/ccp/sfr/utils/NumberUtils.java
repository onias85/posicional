package  com.ccp.sfr.utils;

import java.math.BigDecimal;

import com.ccp.sfr.commons.AssertionsUtils;

public class NumberUtils {

	/**
	 * Desloca a virgula de um numero decimal (para a direita  se numeroCasasDecimaisAposVirgula for positivo e esquerda se for negativo)
	 * arredondado esse numero para baixo e inteiro 
	 * @param value
	 * @param numeroCasasDecimaisAposVirgula
	 * @return
	 */
	public static BigDecimal getDouble(Object value, int numeroCasasDecimaisAposVirgula) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);
		AssertionsUtils.validateNotEmptyAndNotNullObject("numeroCasasDecimaisAposVirgula", numeroCasasDecimaisAposVirgula);

		boolean isInvalid = isDoubleNumber(value.toString()) == false;

		if (isInvalid) {
			throw new InvalidDecimalException(value);
		}

		double pow = Math.pow(10, numeroCasasDecimaisAposVirgula);
		BigDecimal casasDecimaisDepoisDaVirgula = new BigDecimal(pow);

		BigDecimal multiply = (new BigDecimal(value.toString())).multiply(casasDecimaisDepoisDaVirgula);
		return multiply;
	}

	/**
	 * Desloca a virgula de um numero decimal (para a direita  se numeroCasasDecimaisAposVirgula for positivo e esquerda se for negativo)
	 * arredondado esse numero para baixo e inteiro e por fim como string
	 * @param value
	 * @param numeroCasasDecimaisAposVirgula
	 * @param columnWidth
	 * @return
	 */
	public static String getDoubleAsString(Object value, int numeroCasasDecimaisAposVirgula, int columnWidth) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("value", value);
		AssertionsUtils.validateNotEmptyAndNotNullObject("numeroCasasDecimaisAposVirgula", numeroCasasDecimaisAposVirgula);
		AssertionsUtils.validateNotEmptyAndNotNullObject("columnWidth", columnWidth);
		
		boolean retornarValorPadrao = AssertionsUtils.isEmptyOrNull(value);

		if (retornarValorPadrao) {

			String valorPadrao = StringUtils.completeLeftString("", '0', columnWidth);

			return valorPadrao;
		}

		BigDecimal multiply = getDouble(value, numeroCasasDecimaisAposVirgula);

		long longValue = multiply.longValue();
		String valueOf = String.valueOf(longValue);

		valueOf = StringUtils.completeLeftString(valueOf, '0', columnWidth);

		return valueOf;
	}

	public static Long getValidIntNumber(String objectName, String objectValue) {

		AssertionsUtils.validateNotEmptyAndNotNullObject(objectName, objectValue);

		try {
			return new Long(objectValue);
		} catch (NumberFormatException e) {
			throw new SystemException(
					"A variavel '" + objectName + "' nao representa um valor inteiro valido. Valor: " + objectValue, e);
		}
	}


	public static boolean isIntegerNumber(String str) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("str", str);
		
		try {
			new Long(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	public static boolean isPositiveIntegerNumber(String str) {
		
		try {
			Long integer = new Long(str);
			boolean isPositiveIntegerNumber = integer >= 0;
			return isPositiveIntegerNumber;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Pergunta se uma string representa um numero decimal
	 * @param str
	 * @return
	 */
	public static boolean isDoubleNumber(String str) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("str", str);

		try {
			new Double(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}

@SuppressWarnings("serial")
class InvalidDecimalException extends RuntimeException{
	InvalidDecimalException(Object value){
		super("O valor '" + value + "' nao representa um numero valido!");
	}
}


