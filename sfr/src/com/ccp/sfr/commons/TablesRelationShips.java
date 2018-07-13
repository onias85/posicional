package  com.ccp.sfr.commons;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;

import com.ccp.sfr.commons.Exceptions.ParentNotYetAddedException;
import com.ccp.sfr.utils.ReinfUtils;
import com.ccp.sfr.utils.SystemException;
import com.ccp.sfr.utils.ReinfUtils.HASH_ALGORITHM;


class TablesRelationShips {

	
	private final HashMap<String, BigInteger> relationsShips = new HashMap<String, BigInteger>();
	
	
	final BigInteger fileHash;
	
	TablesRelationShips(String filePath){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("filePath", filePath);
		
		FileInputStream is = null;
		try{
			is = new FileInputStream(filePath);
			String hash = ReinfUtils.getHash(is, HASH_ALGORITHM.MD5);
			this.fileHash = new BigInteger(hash, 16);

		}catch(IOException e){
			throw new SystemException("Erro ao gerar hash do arquivo " + filePath, e);
		}finally{
			ReflectionUtils.closeResources(is);
		}
	}

	BigInteger getIndex(LayoutRepresentation layout) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("layout", layout);

		boolean hasHasNotParent = layout.hasParent() == false;
		
		if(hasHasNotParent){
			return this.fileHash;
		}
		
		String layoutName = layout.getLayoutName();
		boolean layoutJaAdicionadoAnteriormente = this.relationsShips.containsKey(layoutName);
		
		if(layoutJaAdicionadoAnteriormente){
			BigInteger index = this.relationsShips.get(layoutName);
			index = index.add(new BigInteger("1"));
			// atualiza valor com valor + 1
			this.relationsShips.put(layoutName, index);
			return index;
		}
		// inicia novo layout com '1'
		BigInteger index = new BigInteger("1");
		this.relationsShips.put(layoutName, index);
		return index;
	}
	
	BigInteger getParentIndex(LayoutRepresentation parent){
		
		AssertionsUtils.validateNotEmptyAndNotNullObject("parent", parent);

		boolean hasHasNotParent = parent.hasParent() == false;
		
		if(hasHasNotParent){
			return this.fileHash;
		}

		String layoutName = parent.getLayoutName();

		boolean layoutAindaNaoAdicionado = this.relationsShips.containsKey(layoutName) == false;
		
		if(layoutAindaNaoAdicionado){
			throw new ParentNotYetAddedException(layoutName);
		}	
		
		BigInteger index = this.relationsShips.get(layoutName);
		return index;
		
	}

}



