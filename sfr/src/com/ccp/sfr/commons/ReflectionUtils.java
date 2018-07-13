package  com.ccp.sfr.commons;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ccp.sfr.utils.SystemException;

public class ReflectionUtils {

//	private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtils.class);	

	public static void closeResources(Object... obj){
		for (Object object : obj) {
			try {
				closeResources(object);
			} catch (SystemException e) {
//				LOGGER.error("", e);

			}
		}
	}
	
	public static void closeResources(Object obj){
		
		boolean isNull = obj == null;
		
		if(isNull){
			return;
		}
		
		Class<? extends Object> clazz = obj.getClass();
		
		
		Method method;
		try {
			method = clazz.getDeclaredMethod("close");
		} catch (NoSuchMethodException e) {
			String className = clazz.getName();
			String message = String.format("O método close() da classe '%s' não existe", className);
			throw new SystemException(message, e);
		} catch (SecurityException e) {
			String className = clazz.getName();
			String message = String.format("O método close() da classe '%s' tem alguma configuração de OSGI que impede o aceso via reflection", className);
			throw new SystemException(message, e);
		}
		
		try {
			method.invoke(obj);
		} catch (IllegalAccessException e) {
			String className = clazz.getName();
			String message = String.format("O método close() da classe '%s' não é publico", className);
			throw new SystemException(message, e);
		} catch (IllegalArgumentException e) {
			String className = clazz.getName();
			String message = String.format("O método close() da classe '%s' até existe, porém espera parâmetros", className);
			throw new SystemException(message, e);
		} catch (InvocationTargetException e) {
//			LOGGER.warn("", e);
		}
	}
	
	public static Map<String, String> convertEntityToMap(Object entity){
	
		AssertionsUtils.validateNotEmptyAndNotNullObject("entity", entity);
		
		Class<? extends Object> clazz = entity.getClass();
		
		Field[] declaredFields = clazz.getDeclaredFields();
		
		Map<String, String> map = new HashMap<String, String>();
		
		ArrayList<Throwable> erros = new ArrayList<Throwable>();

		for (Field field : declaredFields) {
			
			String fieldName = field.getName();
		
			try {
			
				String fieldValue = getFieldValue(entity, field);
				map.put(fieldName, fieldValue);
				
			} catch (IllegalArgumentException e) {
				erros.add(e);
			} catch(IllegalAccessException e){
				erros.add(e);
			}
		}
		
		boolean thereAreSomeErrors = erros.size() == 0 == false;
		
		if(thereAreSomeErrors){
			throw new SystemException(erros);
		}
		
		return map;
	}
	
	public static String getFieldValue(Object entity, Field field) throws IllegalArgumentException, IllegalAccessException{
		
		AssertionsUtils.validateNotEmptyAndNotNullObject("entity", entity);
		AssertionsUtils.validateNotEmptyAndNotNullObject("field", field);
	
		field.setAccessible(true);
		
		Class<?> type = field.getType();
		boolean isNotPrimitive = type.isPrimitive() == false;
		
		if(isNotPrimitive){
			Object object = field.get(entity);
			if(object == null){
				return "";
			}
			return object.toString();
		}
		
		PRIMITIVES valueOf = getPrimitiveEnum(type);

		String fieldValue = valueOf.getFieldValue(entity, field);
		
		return fieldValue;
	}


	private static PRIMITIVES getPrimitiveEnum(Class<?> type) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("type", type);
	
		String simpleName = "_" + type.getSimpleName();
		
		PRIMITIVES valueOf = PRIMITIVES.valueOf(simpleName);
		return valueOf;
	}
	
	public static void setFieldValue(Field field, String fieldValue, Object target) throws IllegalArgumentException, IllegalAccessException{
		
		AssertionsUtils.validateNotEmptyAndNotNullObject("fieldValue", fieldValue);
		AssertionsUtils.validateNotEmptyAndNotNullObject("target", target);
		AssertionsUtils.validateNotEmptyAndNotNullObject("field", field);

		field.setAccessible(true);
		
		Class<?> type = field.getType();
		boolean isNotPrimitive = type.isPrimitive() == false;

		if(isNotPrimitive){
			field.set(target, fieldValue);
		}
		
		PRIMITIVES primitiveEnum = getPrimitiveEnum(type);
		
		primitiveEnum.setFieldValue(target, field, fieldValue);
	}
	
	
	
	private static enum PRIMITIVES{
		
		_boolean {
			@Override
			String getFieldValue(Object entity, Field field) throws IllegalArgumentException, IllegalAccessException {
				String string = "" + field.getBoolean(entity);
				return string;
			}

			@Override
			void setFieldValue(Object entity, Field field, String fieldValue) throws IllegalArgumentException, IllegalAccessException {

				field.setBoolean(entity, new Boolean(fieldValue));	
			}
		},
		_byte {
			@Override
			String getFieldValue(Object entity, Field field) throws IllegalArgumentException, IllegalAccessException {
				String string = "" + field.getByte(entity);
				return string;
			}

			@Override
			void setFieldValue(Object entity, Field field, String fieldValue) throws IllegalArgumentException, IllegalAccessException {
				field.setByte(entity, new Byte(fieldValue));
			}
		},
		_char {
			@Override
			String getFieldValue(Object entity, Field field) throws IllegalArgumentException, IllegalAccessException {
				String string = "" + field.getChar(entity);
				return string;
			}

			@Override
			void setFieldValue(Object entity, Field field, String fieldValue) throws IllegalArgumentException, IllegalAccessException {
				char[] charArray = fieldValue.toCharArray();
				char value = charArray[0];
				field.setChar(entity, new Character(value));
			}
		},
		_short {
			@Override
			String getFieldValue(Object entity, Field field) throws IllegalArgumentException, IllegalAccessException {
				String string = "" + field.getShort(entity);
				return string;
			}

			@Override
			void setFieldValue(Object entity, Field field, String fieldValue) throws IllegalArgumentException, IllegalAccessException {
				field.setShort(entity, new Short(fieldValue));
				
			}
		},
		_int {
			@Override
			String getFieldValue(Object entity, Field field) throws IllegalArgumentException, IllegalAccessException {
				String string = "" + field.getInt(entity);
				return string;
			}

			@Override
			void setFieldValue(Object entity, Field field, String fieldValue) throws IllegalArgumentException, IllegalAccessException {
				field.setInt(entity, new Integer(fieldValue));
			}
		},
		_long {
			@Override
			String getFieldValue(Object entity, Field field) throws IllegalArgumentException, IllegalAccessException {
				String string = "" + field.getLong(entity);
				return string;
			}

			@Override
			void setFieldValue(Object entity, Field field, String fieldValue) throws IllegalArgumentException, IllegalAccessException {
				field.setLong(entity, new Long(fieldValue));
			}
		},
		_float {
			@Override
			String getFieldValue(Object entity, Field field) throws IllegalArgumentException, IllegalAccessException {
				String string = "" + field.getFloat(entity);
				return string;
			}

			@Override
			void setFieldValue(Object entity, Field field, String fieldValue) throws IllegalArgumentException, IllegalAccessException {
				field.setFloat(entity, new Float(fieldValue));
			}
		},
		_double {
			@Override
			String getFieldValue(Object entity, Field field) throws IllegalArgumentException, IllegalAccessException {
				String string = "" + field.getDouble(entity);
				return string;
			}

			@Override
			void setFieldValue(Object entity, Field field, String fieldValue) throws IllegalArgumentException, IllegalAccessException {
				field.setDouble(entity, new Double(fieldValue));
			}
		}
		;
		
		abstract String getFieldValue(Object entity, Field field) throws IllegalArgumentException, IllegalAccessException;
	
		abstract void setFieldValue(Object entity, Field field, String fieldValue) throws IllegalArgumentException, IllegalAccessException;
	}
}
