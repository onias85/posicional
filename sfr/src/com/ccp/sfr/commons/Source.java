package  com.ccp.sfr.commons;

import java.util.Map;
import java.util.Set;

import com.ccp.sfr.columns.ColumnDataType;

/**
 * Representa a origem de onde os dados sao oriundos
 * @author Onias Vieira Junior
 *
 */
public interface Source{
	Map<String, String> read(Set<ColumnDataType> layoutSpecification);
}
