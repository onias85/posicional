package  com.ccp.sfr.commons;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ccp.sfr.columns.ColumnDataType;
import com.ccp.sfr.commons.Exceptions.ColumnResultSetException;
import com.ccp.sfr.commons.Exceptions.EndOfResultSetException;
import com.ccp.sfr.commons.Exceptions.PropertyNotFoundException;
import com.ccp.sfr.utils.SystemException;

/**
 * Os dados sao oriundos do banco de dados, entao essa classe e responsavel por  fazer uma leitura numa query, desde que os nomes ou aliases dos campos estejam aderentes ao layout (arquivo .ini)
 *  
 * @author Onias Vieira Junior
 *
 */
class SourceDataBase implements Source {

	private final ResultSet resultSet;
	
	
	
	
	SourceDataBase(ResultSet resultSet) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("resultSet", resultSet);
		
		this.resultSet = resultSet;
	}

	private void validateResultSet() {

		
		try {
			boolean endOfResultSet = this.resultSet.next() == false;
			if(endOfResultSet){
				throw new EndOfResultSetException();
			}
		} catch (SQLException e) {
			throw new SystemException(e);
		}
	}

//	@Override
	public Map<String, String> read(Set<ColumnDataType> layoutSpecification) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutSpecification", layoutSpecification);
		
		this.validateResultSet();
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		ArrayList<Throwable> erros = new ArrayList<Throwable>();
		
		for (ColumnDataType column : layoutSpecification) {
			
			AssertionsUtils.validateNotEmptyAndNotNullObject("column", column);
			
			try{
				String columnTableDatabaseValue = column.extractValueFromResultSet(this.resultSet);
				String columnDataBaseName = "" + column.getPropertyValue("columnDataBase");
				map.put(columnDataBaseName, columnTableDatabaseValue);
			}catch(ColumnResultSetException e){
				erros.add(e);
			}catch(PropertyNotFoundException e){
				continue;
			}
		}
		
		AssertionsUtils.checkForErrors(erros);
		
		return map;
	}
	
}