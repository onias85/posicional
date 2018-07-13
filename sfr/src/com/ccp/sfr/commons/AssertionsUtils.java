package  com.ccp.sfr.commons;

import java.util.Collection;
import java.util.InputMismatchException;

import com.ccp.sfr.utils.MultipleErrorsException;

public class AssertionsUtils {

	public static void checkForErrors(Collection<Throwable> erros) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("erros", erros);

		boolean houveErros = erros.size() == 0 == false;

		if (houveErros) {
			throw new MultipleErrorsException(erros);
		}

	}

	/**
	 * Pergunta se determinado objeto e nulo ou vazio
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmptyOrNull(Object value) {
		if (value == null) {
			return true;
		}

		if (value.toString().trim().length() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * Pergunta se um dos itens do array (array) e nulo
	 * 
	 * @param array
	 * @return
	 */
	public static boolean oneOfTheseValuesIsNull(Object... array) {

		validateNotEmptyAndNotNullObject("array", array);

		for (Object object : array) {

			if (object == null) {
				return true;
			}
		}
		return false;
	}

	public static void validateNotEmptyAndNotNullObject(String objectName, Object objectValue) {

		boolean objectIsNull = objectValue == null;

		if (objectIsNull) {
			throw new NullObjectException(objectName);

		}
		// por favor, jamais façam um toString que retorna vazio ou nulo, pois
		// ha de cair aqui
		String string = objectValue.toString();

		boolean objectIsEmpty = string == null || string.length() == 0;

		if (objectIsEmpty) {
			throw new EmptyObjectException(objectName);
		}
	}

	public static void validateArray(String arrayName, Object[] array) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("arrayName", array);

		boolean arrayIsEmpty = array.length == 0;

		if (arrayIsEmpty) {
			throw new EmptyArrayException(arrayName);
		}
		int k = 0;

		for (Object item : array) {

			AssertionsUtils.validateNotEmptyAndNotNullObject(arrayName + "[" + k++ + "]", item);
			k++;
		}
	}

	public static boolean isValidArray(Object[] array) {
		try {
			validateArray("array", array);
			return true;
		} catch (RuntimeException e) {
			return false;
		}
	}
	

	public static boolean isCnpj(String cnpj) {
		
		validateNotEmptyAndNotNullObject("cnpj", cnpj);

		// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
		if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") || cnpj.equals("22222222222222")
				|| cnpj.equals("33333333333333") || cnpj.equals("44444444444444") || cnpj.equals("55555555555555")
				|| cnpj.equals("66666666666666") || cnpj.equals("77777777777777") || cnpj.equals("88888888888888")
				|| cnpj.equals("99999999999999") || (cnpj.length() != 14))
			return (false);

		char dig13, dig14;
		int sm, i, r, num, peso;

		// "try" - protege o código para eventuais erros de conversao de tipo
		// (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 11; i >= 0; i--) {
				// converte o i-ésimo caractere do CNPJ em um número:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posição de '0' na tabela ASCII)
				num = (int) (cnpj.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig13 = '0';
			else
				dig13 = (char) ((11 - r) + 48);

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = (int) (cnpj.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig14 = '0';
			else
				dig14 = (char) ((11 - r) + 48);

			// Verifica se os dígitos calculados conferem com os dígitos
			// informados.
			if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	public static String imprimeCNPJ(String cnpj) {
		// máscara do CNPJ: 99.999.999.9999-99
		String cnpjFormatado = cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "."
				+ cnpj.substring(8, 12) + "-" + cnpj.substring(12, 14);
		return cnpjFormatado;
	}
	
	public static void validateCnpj(String cnpj){
		
		boolean isValidCnpj = isCnpj(cnpj);
		
		if(isValidCnpj){
			return;
		}
		
		throw new CnpjInvalidoException(cnpj);
	}

}

@SuppressWarnings("serial")
class NullObjectException extends RuntimeException {
	NullObjectException(Object objectName) {
		super("A variavel '" + objectName + "' está com valor nulo");
	}
}

@SuppressWarnings("serial")
class EmptyObjectException extends RuntimeException {
	EmptyObjectException(Object objectName) {
		super("a evocação do método 'toString()'  da variavel '" + objectName + "' retornou valor nulo ou vazio");
	}
}

@SuppressWarnings("serial")
class EmptyArrayException extends RuntimeException {
	EmptyArrayException(Object arrayName) {
		super("O array com '" + arrayName + "' está vazio, sem itens");
	}
}

@SuppressWarnings("serial")
class CnpjInvalidoException extends RuntimeException{
	CnpjInvalidoException(String cnpj){
		super("O valor '" + cnpj + "' nao se refere a um CNPJ valido!");
	}
}
