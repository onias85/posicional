package  com.ccp.sfr.commons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;


import com.ccp.sfr.columns.ColumnDataType;
import com.ccp.sfr.utils.SystemException;
import com.ccp.sfr.utils.Utils;


/**
 * Cria arquivo sequencial com base em uma configuracao de layout
 * @author Onias Vieira Junior
 *
 */
class TargetFileLine implements Target{


	private final String fileToWrite;
	
	
	
	TargetFileLine(String fileToWrite) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("fileToWrite", fileToWrite);

		this.fileToWrite = fileToWrite;
	}

	/**
	 * Escreve as colunas em um arquivo de saida de forma ordenada de acordo com a posicao inicial de cada coluna mapeada no arquivo de layout
	 */
//	@Override
	public void write( Map<ColumnDataType, String> columnsAndTheirValue) {
		
		AssertionsUtils.validateNotEmptyAndNotNullObject("columnsAndTheirValue", columnsAndTheirValue);

		ArrayList<ColumnDataType> sortedColumns = this.getSortedColumns(columnsAndTheirValue);
		
		StringBuilder lineContent = this.getValuesAsString(columnsAndTheirValue, sortedColumns);

		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter out = null;
		try {
//			File file = new File(this.fileToWrite);
		
//			boolean arquivoAindaNaoCriado = file.exists() == false;
			
//			if(arquivoAindaNaoCriado){
//				Files.write(Paths.get(this.fileToWrite), lineContent.toString().getBytes(), StandardOpenOption.CREATE);
//				return;
//			}
//			
//			Files.write(Paths.get(this.fileToWrite), lineContent.toString().getBytes(), StandardOpenOption.APPEND);

		    fw = new FileWriter(this.fileToWrite, true);
		    bw = new BufferedWriter(fw);
		    out = new PrintWriter(bw);
		    out.println(lineContent.toString());
		    out.close();
		} catch (IOException e) {
			throw new SystemException("Erro ao inserir linha no arquivo", e);
		} catch(SystemException e){
			this.deleteFile(this.fileToWrite, e);
		}finally{
			ReflectionUtils.closeResources(fw, bw, out);
		}
	}

	public void deleteFile(String fileToWrite, SystemException e) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("fileToWrite", fileToWrite);
		AssertionsUtils.validateNotEmptyAndNotNullObject("e", e);
		
		new File(fileToWrite).delete();
		throw new SystemException(e);
	}

	private StringBuilder getValuesAsString(Map<ColumnDataType, String> columnsAndTheirValue, ArrayList<ColumnDataType> sortedColumns) {
	
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("columnsAndTheirValue", columnsAndTheirValue);
		AssertionsUtils.validateNotEmptyAndNotNullObject("sortedColumns", sortedColumns);
		
		StringBuilder lineContent = new StringBuilder();

		for (ColumnDataType column : sortedColumns) {
		
			Object columnValue = columnsAndTheirValue.get(column);
			
			lineContent.append(columnValue);
		}
		return lineContent;
	}

	private ArrayList<ColumnDataType> getSortedColumns(Map<ColumnDataType, String> columnsAndTheirValue) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("columnsAndTheirValue", columnsAndTheirValue);
	
		Set<ColumnDataType> keySet = columnsAndTheirValue.keySet();
		
		ArrayList<ColumnDataType> sortedColumns = Utils.getSortedColumns(keySet);
		
		return sortedColumns;
	}

	
}
