package  com.ccp.sfr.commons;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ccp.sfr.columns.ColumnDataType;



/**
 * 
 * @author Onias Vieira Junior
 *
 */
@SuppressWarnings("rawtypes")
public class TargetPojo<Pojo> implements Target{

	private final Pojo pojo;
	
	
	TargetPojo(Pojo pojo) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("pojo", pojo);
		
		this.pojo = pojo;
	}

//	@Override
	public void write(Map<ColumnDataType, String> columnsAndTheirValue) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("columnsAndTheirValue", columnsAndTheirValue);
		
		Set<ColumnDataType> keySet = columnsAndTheirValue.keySet();
		Class<? extends Object> class1 = pojo.getClass();
		ArrayList<Throwable> erros = new ArrayList<Throwable>();
		
		for (ColumnDataType column : keySet) {
			try{
				String columnName = column.getColumnName();
				Field field = this.getFieldByAnnotationOrByName(columnName, class1);
				String fieldValue = columnsAndTheirValue.get(column);
				ReflectionUtils.setFieldValue(field, fieldValue, this.pojo);
			}catch(FieldNotFoundException e){
				erros.add(e);
			}catch(IllegalArgumentException e){
				erros.add(e);
				
			}catch(IllegalAccessException e){
				erros.add(e);
			}
		}
		
		AssertionsUtils.checkForErrors(erros);
	}
	
	@java.lang.annotation.Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface SequencialFileColumn{
	 String value();	
	}
	
	private Field getFieldByAnnotationOrByName(String fieldName, Class clazz) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("fieldName", fieldName);
		AssertionsUtils.validateNotEmptyAndNotNullObject("clazz", clazz);
		
		Field[] declaredFields = clazz.getDeclaredFields();
		
		for (Field field : declaredFields) {
			Class<SequencialFileColumn> annotationClass = SequencialFileColumn.class;
			boolean annotationPresent = field.isAnnotationPresent(annotationClass);
			
			if(annotationPresent){
			
				SequencialFileColumn annotation = field.getAnnotation(annotationClass);
				
				String annotationValue = annotation.value();
				boolean fieldFound = annotationValue.equals(fieldName);
			
				if(fieldFound){
					return field;
				}
			}
			
			String name = field.getName();
			boolean fieldFound = name.equals(fieldName);
			if(fieldFound){
				return field;
			}
		}
		throw new FieldNotFoundException(fieldName, clazz);
	}
	@SuppressWarnings("serial")
	static class FieldNotFoundException extends RuntimeException{
		FieldNotFoundException(String fieldName, Class clazz){
			super(TargetPojo.getFileNotFoundMessage(fieldName, clazz));
		}
	}

	private static String getFileNotFoundMessage(String fieldName, Class clazz){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("fieldName", fieldName);
		AssertionsUtils.validateNotEmptyAndNotNullObject("clazz", clazz);
		
		Field[] declaredFields = clazz.getDeclaredFields();
		List<Field> asList = Arrays.asList(declaredFields);
		return "A coluna '" + fieldName + "' nao foi encontrada na classe " + clazz.getName() + ", que contem os fields: " + asList;
	}


}
