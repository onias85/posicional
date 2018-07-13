package  com.ccp.sfr.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ccp.sfr.commons.AssertionsUtils;

@SuppressWarnings("serial")
public class MultipleErrorsException extends RuntimeException {
	
	public final List<String> messages;
	
	public MultipleErrorsException(String message){
		super(message);
		this.messages = new ArrayList<String>();
		this.messages.add(message);
	}
	
	public MultipleErrorsException(List<String> messages) {
		super(messages.toString());
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("messages", messages);
		
		this.messages = Collections.unmodifiableList(messages);
	}

	public MultipleErrorsException(Iterable<Throwable> erros) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("erros", erros);
		
		List<String> messages = new ArrayList<String>();
		for (Throwable e : erros) {
			String completeMessage = getCompleteMessage(e);
			messages.add(completeMessage);
		}
		
		this.messages = Collections.unmodifiableList(messages);
	}

	private static String getCompleteMessage(Throwable e){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("e", e);
		
		StringBuilder sb = new StringBuilder();
		String message = e.getMessage();
		sb.append(message);
		
		Throwable cause = e.getCause();
		
		boolean hasCause = cause != null;
	
		if(hasCause){
			String completeMessage = getCompleteMessage(cause);
			sb.append(", causado por: ").append(completeMessage);
		}
		
		return sb.toString();
		
	}
}
