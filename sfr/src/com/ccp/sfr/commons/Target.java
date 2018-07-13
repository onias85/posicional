package  com.ccp.sfr.commons;

import java.util.Map;

import com.ccp.sfr.columns.ColumnDataType;


/**
 * Subtipos desta interface poderao ler e escrever os valores de um map em arquivo, tabela de banco de dados e qualquer outro lugar
 * de acordo com sua implementacao 
 * @author Onias Vieira Junior
 *
 */
public interface Target{

	void write(Map<ColumnDataType, String> columnsAndTheirValue);
	
}
