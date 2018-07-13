package  com.ccp.sfr.commons;

import java.math.BigInteger;
import java.util.Map;
import java.util.Set;

import com.ccp.sfr.columns.ColumnDataType;
import com.ccp.sfr.commons.Exceptions.ColumnWithFixedValueException;
import com.ccp.sfr.commons.Exceptions.EmptyInsertException;


/**
 * Gera query apropriada de insert de acordo com layout configurado
 * @author Onias Vieira Junior
 *
 */
public class DefaultInsertTarget implements SqlTarget{

	private final TablesRelationShips tablesRelationShips;

	private final LayoutRepresentation layout;
	
	private String sqlInsert;
	
	public DefaultInsertTarget(LayoutRepresentation layout, String fileToRead) {
		
		AssertionsUtils.validateNotEmptyAndNotNullObject("fileToRead", fileToRead);
		AssertionsUtils.validateNotEmptyAndNotNullObject("layout", layout);
		
		this.tablesRelationShips = new TablesRelationShips(fileToRead);
		this.layout = layout;
	}



//	@Override
	public void write(Map<ColumnDataType, String> columnsAndTheirValue) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("columnsAndTheirValue", columnsAndTheirValue);
		
		String tableName = this.layout.getTableName();
		boolean thereIsNotTable = AssertionsUtils.isEmptyOrNull(tableName);
		
		if(thereIsNotTable){
			return;
		}
		
		Set<ColumnDataType> keySet = columnsAndTheirValue.keySet();
		
		StringBuilder valoresColunas = new StringBuilder();
		StringBuilder nomesColunas = new StringBuilder();
		
		
		for (ColumnDataType column : keySet) {
			
			try{
				String value = columnsAndTheirValue.get(column);
				String dataBaseValue = "" + column.getDataBaseValue(value);
				valoresColunas.append(",").append(dataBaseValue);
				String columnDataBaseName = "" + column.getPropertyValue("columnDataBase");
				nomesColunas.append(",").append(columnDataBaseName);
			}catch(ColumnWithFixedValueException e){
				continue;
				//vai ignorar essa coluna
			}
		}
		boolean nenhumaColunaSeraInserida = valoresColunas.toString().trim().length() == 0;
		
		if(nenhumaColunaSeraInserida){
			return;
		}
		
		this.addForeignKey(valoresColunas, nomesColunas);

		this.addPrimaryKey(valoresColunas, nomesColunas);
		
		StringBuilder insert = new StringBuilder("insert into ").append(tableName).append("(");
		insert.append(nomesColunas).append(") values (").append(valoresColunas).append(")");

//		System.out.println(insert);
		this.sqlInsert = insert.toString().toUpperCase();
	}



	private void addPrimaryKey(StringBuilder valoresColunas, StringBuilder nomesColunas) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("valoresColunas", valoresColunas);
		AssertionsUtils.validateNotEmptyAndNotNullObject("nomesColunas", nomesColunas);

		String indexFieldName = this.layout.getIndexFieldName();
		String indexFieldValue = this.tablesRelationShips.getIndex(this.layout).toString(16);
		valoresColunas.insert(0, "'" + indexFieldValue + "'");
		nomesColunas.insert(0, indexFieldName);// insere no comeco
	}



	private void addForeignKey(StringBuilder valoresColunas, StringBuilder nomesColunas) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("valoresColunas", valoresColunas);
		AssertionsUtils.validateNotEmptyAndNotNullObject("nomesColunas", nomesColunas);

		boolean hasNotparent = this.layout.hasParent() == false;
		
		if(hasNotparent){
			return;
		}
		
		LayoutRepresentation parent = this.layout.getParent();
		BigInteger parentIndex = this.tablesRelationShips.getParentIndex(parent);
		String parentIndexFieldValue = parentIndex.toString(16);
		valoresColunas.insert(0, ",'" + parentIndexFieldValue + "'");

		String parentIndexFieldName = this.layout.getParentIndexFieldName();
		nomesColunas.insert(0,"," + parentIndexFieldName);// insere no comeco
	}



	public String getSql() {
	
		boolean emptyInsert = AssertionsUtils.isEmptyOrNull(this.sqlInsert);
		
		if(emptyInsert){
			throw new EmptyInsertException();
		}
		
		
		return this.sqlInsert;
	}

}