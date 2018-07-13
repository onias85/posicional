package  com.ccp.sfr.testes;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ccp.sfr.commons.AssertionsUtils;
import com.ccp.sfr.commons.CopyData;
import com.ccp.sfr.commons.LayoutCollection;
import com.ccp.sfr.commons.LayoutRepresentation;
import com.ccp.sfr.utils.FileUtils;
import com.ccp.sfr.utils.MultipleErrorsException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Cliente {

	public static void main(String[] args) {

		
		InputStream is = Teste.class.getResourceAsStream("layouts.ini");
		
		File parent = new File("..\\sequenciais");
		File[] files = parent.listFiles();
		for (File file : files) {
			String arquivo = file.getAbsolutePath();
			String feedback = getFeedback(is, arquivo);
			System.out.println(feedback);
			
		}
		
	}

	static void printAllValues(String arquivo ) {

		InputStream is = FileUtils.class.getResourceAsStream("layouts.ini");
		
		LayoutCollection lc = LayoutCollection._loadLayoutCollection(is);
		
		String lineValueByIndex = FileUtils.getLineValueByIndex(arquivo, 0);
		
		LayoutRepresentation lr = lc._findTheMostAppropriateLayoutForThisSequentialValue(lineValueByIndex);
		
		Map<String, String> readFile = lr.getColumnsAndValues(lineValueByIndex);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		System.out.println(gson.toJson(readFile));
		
		
	}
	
	static String getFeedback(InputStream is, String arquivo) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("is", is);
		AssertionsUtils.validateNotEmptyAndNotNullObject("arquivo", arquivo);
		
		List<Map<String, String>> list;
		try {
		
			list = CopyData._fromFileToMap(is, arquivo);
		
		} catch (MultipleErrorsException e) {
			StringBuilder sb = new StringBuilder();
			List<String>lis = e.messages;
			for (String erro : lis) {
				sb.append(erro).append("\n");
			}
			return sb.toString();
		}
		
		StringBuilder sb = new StringBuilder();
		for (Map<String, String> map : list) {
			Set<String> keySet = map.keySet();
			for (String key : keySet) {
				try {
					String value = map.get(key);
//					int length = value.length();
					sb.append(key).append( ": ").append(value).append("\n");
					
				} catch (RuntimeException e) {
					String message = e.getMessage();
					sb.append(message).append("\n");
				}
			}
		}

		String string = sb.toString();
		return string;
	}
}
