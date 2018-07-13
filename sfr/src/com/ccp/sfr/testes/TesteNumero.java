package  com.ccp.sfr.testes;

import java.io.InputStream;
import java.util.Set;

import com.ccp.sfr.columns.ColumnDataType;
import com.ccp.sfr.commons.Exceptions;
import com.ccp.sfr.commons.LayoutCollection;
import com.ccp.sfr.commons.LayoutRepresentation;
import com.ccp.sfr.utils.FileUtils;


public class TesteNumero {

	public static void main1(String[] args) {

		InputStream is = FileUtils.fileToStream("teste.ini");
		LayoutCollection lc = LayoutCollection._loadLayoutCollection(is);

		String lineValueByIndex = FileUtils.getLineValueByIndex("arquivo.txt", 0);
		LayoutRepresentation layout = lc._findTheMostAppropriateLayoutForThisSequentialValue(lineValueByIndex);
		// Map<String, String> columnsAndValues =
		// layout.getColumnsAndValues(lineValueByIndex);
		// System.out.println(columnsAndValues);
		Set<ColumnDataType> columns = layout.getColumns();
		for (ColumnDataType column : columns) {
			try {
				String value = column.extractValueFromSequencialContent(lineValueByIndex);
				System.out.println(value);
				Object dataBaseValue = column.getDataBaseValue(value);
				System.out.println(dataBaseValue);
			} catch (Exceptions.ColumnWithFixedValueException e) {
				continue;
			}
		}
	}
}
