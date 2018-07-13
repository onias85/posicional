package  com.ccp.sfr.utils;

import java.util.Collection;

/**
 * Classe que representa erros de programador ou inconsistencias
 * @author onias
 *
 */

public class SystemException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SystemException(Throwable e){
		super(e);
	}
	
	public SystemException(Collection<?> col){
		super("Erros:\n" + col);
	}

	public SystemException(String string) {
		super(string);
	}

}
