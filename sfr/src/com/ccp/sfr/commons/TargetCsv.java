package  com.ccp.sfr.commons;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.ccp.sfr.columns.ColumnDataType;
import com.ccp.sfr.utils.FileUtils;
import com.ccp.sfr.utils.Utils;



public class TargetCsv implements Target {

	private final String csvFile;

	public TargetCsv(String csvFile, Set<ColumnDataType> keySet) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("csvFile", csvFile);
		AssertionsUtils.validateNotEmptyAndNotNullObject("keySet", keySet);
		
		
		this.csvFile = csvFile;
		
		StringBuilder sb = new StringBuilder();
		
		ArrayList<ColumnDataType> sortedColumns = Utils.getSortedColumns(keySet);
		
		for (ColumnDataType column : sortedColumns) {

			boolean hasNotProperty = column.hasProperty("csvColumnTitle") == false;

			if (hasNotProperty) {
				continue;
			}

			String value = "" + column.getPropertyValue("csvColumnTitle") + ";";
			sb.append(value);
		}
		
		boolean hasNotContent = sb.toString().length() == 0;
		
		if(hasNotContent){
			return;
		}
		
		FileUtils.createFile(csvFile);
		FileUtils.appendLine(this.csvFile, sb.toString());
	}

//	@Override
	public void write(Map<ColumnDataType, String> columnsAndTheirValues) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("columnsAndTheirValues", columnsAndTheirValues);

		Set<ColumnDataType> keySet = columnsAndTheirValues.keySet();
		StringBuilder sb = new StringBuilder();
		ArrayList<ColumnDataType> sortedColumns = Utils.getSortedColumns(keySet);
		for (ColumnDataType column : sortedColumns) {

			boolean hasNotProperty = column.hasProperty("csvColumnTitle") == false;

			if (hasNotProperty) {
				continue;
			}

			String value = columnsAndTheirValues.get(column) + ";";
			sb.append(value);
			
		}
		
		boolean hasNotContent = sb.toString().length() == 0;
		
		if(hasNotContent){
			return;
		}
		
		FileUtils.appendLine(this.csvFile, sb.toString());


	}


}
