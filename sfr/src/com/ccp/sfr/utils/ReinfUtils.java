package  com.ccp.sfr.utils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ccp.sfr.commons.AssertionsUtils;
public class ReinfUtils {

	public static Map<String, Object> cleanPartOfMap(Map<String, Object> map, String... keysNameToCopy) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("map", map);
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("keysNameToCopy", keysNameToCopy);

		if (keysNameToCopy == null) {
			map.clear();
			return map;
		}

		List<String> asList = Arrays.asList(keysNameToCopy);

		boolean emptyMap = asList.size() == 0;

		if (emptyMap) {
			map.clear();
			return map;
		}

		Set<String> keySet = map.keySet();

		for (String keyName : keySet) {

			boolean keepThisKey = asList.contains(keyName);

			if (keepThisKey) {
				continue;
			}

			map.remove(keyName);
		}

		return map;
	}



	public static String getHash(InputStream is, HASH_ALGORITHM hashAlgorithm){

		AssertionsUtils.validateNotEmptyAndNotNullObject("hashAlgorithm", hashAlgorithm);
		AssertionsUtils.validateNotEmptyAndNotNullObject("is", is);

		MessageDigest digest;
		String output = null;

		try {

			digest = MessageDigest.getInstance(hashAlgorithm.name());
			byte[] buffer = new byte[8192];
			int read = 0;

			while( (read = is.read(buffer)) > 0) {
				digest.update(buffer, 0, read);
			}
			byte[] md5sum = digest.digest();
			BigInteger bigInt = new BigInteger(1, md5sum);
			output = bigInt.toString(16);

		}
		catch(IOException  e) {
			throw new SystemException("Nao foi possivel gerar hash", e);
		}catch(NoSuchAlgorithmException e){

		}
		
		return output;
	}
	
	
	
	public static enum UF {
		AL, AP, AM, BA, CE, DF, ES, GO, MA, MT, MS, MG, PA, PB, PR, PE, PI, RJ, RN, RS, RO, RR, SC, SP, SE, TO
	}
	
	public static enum HASH_ALGORITHM{
		MD5
	}
}


