package  com.ccp.sfr.commons;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.ccp.sfr.columns.ColumnDataType;


/**
 * 
 * @author Onias Vieira Junior
 *
 */
public class Exceptions {

	@SuppressWarnings("serial")
	public abstract static class SequentialReadingException extends RuntimeException{

		SequentialReadingException(String message, Throwable cause) {
			super(message, cause);
		}

		SequentialReadingException(String message) {
			super(message);
		}
		
		SequentialReadingException(){
			
		}
		
		@Override
		public String toString() {
			String message = this.getMessage();
			String string = "Erro: "  + message;
			Class<? extends SequentialReadingException> class1 = this.getClass();
			String simpleName = class1.getSimpleName();
			String string2 = simpleName + ": " + string;
			return string2;
		}
		
	}
	

	@SuppressWarnings("serial")
	public static class ColumnWithFixedValueException extends SequentialReadingException{
		
	}
	
	@SuppressWarnings("serial")
	public static class EmptyColumnsException extends SequentialReadingException{
		
	}
	
	@SuppressWarnings("serial")
	public static class LayoutNotFoundForThisLineValueException extends SequentialReadingException{
		
		LayoutNotFoundForThisLineValueException(String lineValue){
			super("Nao foi possivel encontrar layout adequado para a linha com valor: " + lineValue);
		}
	}
	
	@SuppressWarnings("serial")
	public static class ColumnResultSetException extends SequentialReadingException{
		
		public ColumnResultSetException(String columnName, Throwable e){
			super("Houve problemas ao obter a coluna '" + columnName + "'. Erro: " + e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("serial")
	public static class EndOfResultSetException extends SequentialReadingException{
		
	}
	
	
	@SuppressWarnings("serial")
	public static class TooMuchLayoutsForThisLineValueException extends SequentialReadingException{
	
		TooMuchLayoutsForThisLineValueException(Collection<LayoutRepresentation> col, String lineValue){
			super("Nao foi possivel determinar o layout adequado para essa linha, pois foram encontrados mais de uma possibilidade de layout. " + getString(col, lineValue));
		}

		private static String getString(Collection<LayoutRepresentation> col, String lineValue) {
			
			//Added by Marina
			AssertionsUtils.validateNotEmptyAndNotNullObject("col", col);
			AssertionsUtils.validateNotEmptyAndNotNullObject("lineValue", lineValue);
			
			ArrayList<String> layoutNames = new ArrayList<String>();
			
			for (LayoutRepresentation rulesForReadingingRows : col) {
				String layoutName = rulesForReadingingRows.getLayoutName();
				layoutNames.add(layoutName);
			}
			
//			String string = "Valor da linha: " + lineValue + ", layouts aplicaveis: " + layoutNames;
			String string = String.format("Layouts aplicáveis: '%s', Valor da linha: '%s'", layoutNames.toString(), lineValue);
			return string;
		}
	}
	
	
	@SuppressWarnings("serial")
	public static class InsufficientColumnsException  extends SequentialReadingException{

		public InsufficientColumnsException(int index, String lineValue){
			super("Erro ao tentar ler o indice '" + index + "', a linha tem '" + lineValue.length() + "' caracteres. Valor da linha: " + lineValue);
		}
		
	}
	
	@SuppressWarnings("serial")
	public static class InvalidInitialPositionException extends SequentialReadingException{

		public InvalidInitialPositionException(String columnName, String layoutName, String initialPosition){
			super("A coluna '" + columnName + "' do layout '" + layoutName + "' deveria ter seu atributo 'initialPosition' configurado "
					+ "como numerico positivo, entretanto seu valor e '" + initialPosition + "'");
		}
		
		public InvalidInitialPositionException(String columnName, String layoutName, int expectedPosition, int initialPosition){
			super(String.format("A coluna '%s' do layout '%s' deveria ter início na posição '%d', porém a mesma inicia na posição '%d'", columnName, layoutName, expectedPosition, initialPosition));
		}
		
	}
	

	@SuppressWarnings("serial")
	public static class InvalidColumnWidthException extends SequentialReadingException{

		public InvalidColumnWidthException(String columnName, String layoutName, String columnWidth){
			super("A coluna '" + columnName + "' do layout '" + layoutName + "' deveria ter seu atributo 'columnWidth' configurado "
					+ "como numerico positivo, entretanto seu valor e '" + columnWidth + "'");
		}
		
	}

	@SuppressWarnings("serial")
	public static class InvalidFixedSizeException extends SequentialReadingException{

		
		public InvalidFixedSizeException(String columnName, String layoutName, long expectedWidth, String value){
			super(String.format("A coluna de valor fixo com nome '%s', pertencente ao layout '%s', esperava o tamanho '%d', porém recebeu o tamanho '%d'. Valor da coluna: '%s'", columnName, layoutName, expectedWidth, value.length(), value));
		}
		
	}

	
	@SuppressWarnings("serial")
	public static class InvalidFractionalDigitsException extends SequentialReadingException{

		public InvalidFractionalDigitsException(String columnName, String layoutName, String fractionalDigits){
			super("A coluna '" + columnName + "' do layout '" + layoutName + "' deveria ter seu atributo 'fractionalDigits' configurado "
					+ "como numerico positivo, entretanto seu valor e '" + fractionalDigits + "'");
		}
	}

	@SuppressWarnings("serial")
	public static class ThereIsNoSuchLayoutInTheFileException  extends SequentialReadingException{
		ThereIsNoSuchLayoutInTheFileException(String layoutName){
			super(getString(layoutName));
		}

		private static String getString(String layoutName) {
			
			//Added by Marina
			AssertionsUtils.validateNotEmptyAndNotNullObject("layoutName", layoutName);
			
			return "O layout '" + layoutName + "' nao existe no arquivo de configuracao  ";
		}
	}
	
	
	@SuppressWarnings("serial")
	public static class EmptyInsertException extends SequentialReadingException{
		
	}

	@SuppressWarnings("serial")
	public static class HasNotTableException extends SequentialReadingException{
		HasNotTableException(String layoutName) {
			super("O layout '" + layoutName + "' nao possui o atributo tablename configurado para ele no arquivo de configuracao de layouts");
		}
	}
	
	@SuppressWarnings("serial")
	public static class ParentNotYetAddedException extends SequentialReadingException{
		ParentNotYetAddedException(String layoutName) {
			super("Erro ao buscar parent. O parent '" + layoutName + "' ainda nao foi adicionado no 'cache'");
		}
	}

	@SuppressWarnings("serial")
	public static class UndefinedTypeColumnException extends SequentialReadingException{
		public UndefinedTypeColumnException(String layoutName, String columnName, String columnAttributes){
			super(String.format("A coluna '%s' pertencente ao layout '%s',  nao pertence a nenhum dos tipos '%s'. Configuração da coluna: %s", columnName, layoutName, Arrays.toString(ColumnTypes.values()), columnAttributes));
			
		}
		
		private static enum ColumnTypes{
			fixedValue, fractionalDigits, dataType, timePattern
		}
	}
	
	@SuppressWarnings("serial")
	public static class InvalidTimeFormatException extends SequentialReadingException{
		
		public InvalidTimeFormatException(String value, String timePattern){
			
			super(String.format("O valor '%s' não é adequado, pois esta coluna espera um dado temporal no pattern '%s' ", value, timePattern));
		}
	}
	
	@SuppressWarnings("serial")
	public static class InvalidDecimalValueException extends SequentialReadingException{
		
		public InvalidDecimalValueException(String value){
			super(String.format("O valor '%s' não representa um número decimal válido!", value));
		}
	}


	@SuppressWarnings("serial")
	public static class InvalidLengthColumnException extends SequentialReadingException{
		
		public InvalidLengthColumnException(String value, int size){
			super(String.format("O valor '%s' tem mais que '%d' caracteres", value, size));
		}
	}

	@SuppressWarnings("serial")
	public static class InvalidDataTypeException extends SequentialReadingException{
		public InvalidDataTypeException(String columnName, String dataType){
			super(String.format("A coluna '%s' tem o dataType invalido '%s'. DataTypes aceitos: %s", columnName, dataType, Arrays.asList(DataType.values())));
		}
	}	

	@SuppressWarnings("serial")
	public static class ValueWithoutSplitterException extends SequentialReadingException{
	
		public ValueWithoutSplitterException(String splitter){
			super(String.format("Este valor deve conter o separador '%s'", splitter));
		}
	}


	@SuppressWarnings("serial")
	public static class InvalidColumnValueException extends SequentialReadingException{
	
		public InvalidColumnValueException(String dataType){
			super(String.format("O valor informado para esta coluna é incompatível para o tipo de dado '%s'", dataType));
		}
	}

	@SuppressWarnings("serial")
	public static class InvalidIntegerException extends SequentialReadingException{
	
		public InvalidIntegerException(String splitter){
			super(String.format("Ao retirar o separador '%s' desse valor, deve restar somente números", splitter));
		}
	}
	
	@SuppressWarnings("serial")
	public static class InvalidCharactersQuantityException extends SequentialReadingException{
		public InvalidCharactersQuantityException(ColumnDataType cdt, String lineValue){
			super(getMessage(cdt, lineValue));
		}

		private static String getMessage(ColumnDataType cdt, String lineValue) {
			
			//Added by Marina
			AssertionsUtils.validateNotEmptyAndNotNullObject("cdt", cdt);
			AssertionsUtils.validateNotEmptyAndNotNullObject("lineValue", lineValue);

			int lastColumnPosition = cdt.getFinalPosition() ;
			String layoutName = cdt.getLayoutName();
			String columnName = cdt.getColumnName();
			int finalPosition = lineValue.length();
			
			String format = String.format("A coluna '%s' do layout '%s', termina na posição '%d', porém a linha lida possui '%d' caracteres. Valor da linha: '%s'",  columnName,layoutName, lastColumnPosition, finalPosition, lineValue);
			
			return format;
		}
	}	

	@SuppressWarnings("serial")
	public static class ExpectedValuesException extends SequentialReadingException{
		
		public ExpectedValuesException(String value, Iterable<?> list){
			super(String.format("O valor '%s' nao está na lista de valores esperados '%s'", value, list.toString()));
		}
	}
	
	@SuppressWarnings("serial")
	public static class PropertyNotFoundException extends SequentialReadingException{
		public PropertyNotFoundException(String propertyName, String columnName, String layoutName, Collection<String> coll){
			super(getErrorMessage(propertyName, columnName, layoutName, coll));
		}

		private static String getErrorMessage(String propertyName, String columnName, String layoutName, Collection<String> coll) {
			
			//Added by Marina
			AssertionsUtils.validateNotEmptyAndNotNullObject("propertyName", propertyName);
			AssertionsUtils.validateNotEmptyAndNotNullObject("columnName", columnName);
			AssertionsUtils.validateNotEmptyAndNotNullObject("columnName", layoutName);
			AssertionsUtils.validateNotEmptyAndNotNullObject("columnName", coll);
			
			String format = String.format("A propriedade '%s' da coluna '%s' pertencente ao layout '%s' nao foi encontrada na lista de propriedades '%s'",propertyName, columnName, layoutName, coll);
			return format;
		}
	}
	
	@SuppressWarnings("serial")
	public static class DataBaseConnectionException extends SequentialReadingException{
		public DataBaseConnectionException(Throwable e){
			throw new RuntimeException("Erro ao carregar driver ", e);
		}
		public DataBaseConnectionException(ClassNotFoundException e, String driverClass){
			super(getMessage(driverClass), e);
			
		}

		public DataBaseConnectionException(SQLException e){
			super("Erro ao conectar no Banco de Dados", e);
		}
		
		private static String getMessage(String driverClass) {
			return String.format("A classe '%s' nao foi encontrada", driverClass);
		}
	}

	@SuppressWarnings("serial")
	public static class IncorrectSqlSyntaxException extends SequentialReadingException{
		
		public IncorrectSqlSyntaxException(SQLException e, String sql){
			super("Erro de sintax ao executar o comando sql " + sql, e);
		}
		
	}
	
	
}
