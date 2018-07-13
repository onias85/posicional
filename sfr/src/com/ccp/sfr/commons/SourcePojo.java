package  com.ccp.sfr.commons;

import java.util.Map;
import java.util.Set;

import com.ccp.sfr.columns.ColumnDataType;



/**
 * 
 * @author Onias Vieira Junior
 *
 */
public class SourcePojo implements Source {


	private final Object entity;
	
	public SourcePojo(Object entity) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("entity", entity);
		
		this.entity = entity;
	}

//	@Override
	public Map<String, String> read(Set<ColumnDataType> layoutSpecification) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutSpecification", layoutSpecification);
		
		Map<String, String> convertEntityToMap = ReflectionUtils.convertEntityToMap(this.entity);
		return convertEntityToMap;
	}
}
