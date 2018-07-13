package  com.ccp.sfr.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

import com.ccp.sfr.columns.ColumnDataType;
import com.ccp.sfr.commons.AssertionsUtils;



/**
 * Classe util apenas no contexto DESTE projeto 
 * @author Onias Vieira Junior
 *
 */
public class Utils {
	
	
	public static ArrayList<ColumnDataType> getSortedColumns(Set<ColumnDataType> keySet) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("keySet", keySet);
		
		ArrayList<ColumnDataType> columns = new ArrayList<ColumnDataType>();

		columns.addAll(keySet);
		
		ColumnComparator c = new ColumnComparator();
		java.util.Collections.sort(columns, c);
//		columns.sort(c);
		return columns;
	}
}

/**
 * Colunas ordenadas de forma crescente por posicao inicial
 * @author onias
 *
 */
class ColumnComparator implements Comparator<ColumnDataType>{

//	@Override
	public int compare(ColumnDataType o1, ColumnDataType o2) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("o1", o1);
		AssertionsUtils.validateNotEmptyAndNotNullObject("o2", o2);
		
		String initialPosition = o1.getInitialPosition();
		String initialPosition2 = o2.getInitialPosition();

		Integer minhaPosicaoInicial = NumberUtils.getValidIntNumber("initialPosition1", initialPosition).intValue();
		Integer outraPosicaoInicial = NumberUtils.getValidIntNumber("initialPosition2", initialPosition2).intValue();
		
		int diferencaEntrePosicoes = minhaPosicaoInicial - outraPosicaoInicial;
		
		return diferencaEntrePosicoes;
	}
}

