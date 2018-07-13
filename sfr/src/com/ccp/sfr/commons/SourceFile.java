package  com.ccp.sfr.commons;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.ccp.sfr.columns.ColumnDataType;
import com.ccp.sfr.commons.Exceptions.InvalidCharactersQuantityException;
import com.ccp.sfr.commons.Exceptions.SequentialReadingException;
import com.ccp.sfr.utils.MultipleErrorsException;
import com.ccp.sfr.utils.Utils;


/**
 * Le uma linha de arquivo de forma sequencial aderente ao arquivo de layout (arquivo .ini)
 * @author Onias Vieira Junior
 *
 */
class SourceFile implements Source {

	private final String sequencialContent;
	
	SourceFile(String sequencialContent) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("sequencialContent", sequencialContent);
		
		this.sequencialContent = sequencialContent;
	}




//	@Override
	public Map<String, String> read(Set<ColumnDataType> layoutSpecification) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutSpecification", layoutSpecification);

		ArrayList<ColumnDataType> sortedColumns = Utils.getSortedColumns(layoutSpecification);

		this.validateLastPosition(sortedColumns);
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		ArrayList<String> errorMessages = new ArrayList<String>();
		
		for (ColumnDataType column : sortedColumns) {

			try {
				String columnValue = column.extractFormattedValueFromSequencialContent(this.sequencialContent);
				
				String columnName = column.getColumnName();

				map.put(columnName, columnValue);
				
			} catch (SequentialReadingException e) {
			
				String value = column.extractValueFromSequencialContent(this.sequencialContent);
				String columnDetails = column.getColumnDetails();	
				String errorMessage = e.getMessage();
				String message = String.format("%s, valor inserido: '%s', erro: '%s' ",columnDetails, value, errorMessage );
				errorMessages.add(message);
			}
		}
		boolean thereAreErrors = errorMessages.size() != 0;
		
		if(thereAreErrors){
			throw new MultipleErrorsException(errorMessages);
		}
		
		return map;
	}




	private void validateLastPosition(ArrayList<ColumnDataType> sortedColumns) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("sortedColumns", sortedColumns);
				
		int lastIndex = sortedColumns.size() - 1;
		ColumnDataType lastColumn = sortedColumns.get(lastIndex);
		int lastPosition = lastColumn.getFinalPosition();
		
		int length = this.sequencialContent.length();
		boolean invalidCharcatersQuantity = lastPosition != length;
		
		if(invalidCharcatersQuantity){
			throw new InvalidCharactersQuantityException(lastColumn, this.sequencialContent);
		}
	}
}
