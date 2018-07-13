package  com.ccp.sfr.commons;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.ini4j.Profile.Section;
import org.ini4j.Wini;




import com.ccp.sfr.commons.Exceptions.LayoutNotFoundForThisLineValueException;
import com.ccp.sfr.commons.Exceptions.ThereIsNoSuchLayoutInTheFileException;
import com.ccp.sfr.commons.Exceptions.TooMuchLayoutsForThisLineValueException;
import com.ccp.sfr.utils.FileUtils;
import com.ccp.sfr.utils.SystemException;
import com.google.gson.Gson;

/**
 * @author onias
 *
 */

/**
 * 
 * @author Onias Vieira Junior
 *
 */


public class LayoutCollection {

	private static final String DATA_BASE_CONFIGURATION = "tables_relationships";

	private final static HashMap<InputStream, LayoutCollection> layoutFiles = new HashMap<InputStream, LayoutCollection>();
	
	private final HashMap<String, LayoutRepresentation> layouts = new HashMap<String, LayoutRepresentation>();
	
	private final Wini iniFile;
	
	private LayoutCollection(Wini iniFile) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("iniFile", iniFile);
		
		this.iniFile = iniFile;
		
		this.loadLayouts();
	}

	public Collection<LayoutRepresentation> getAllLayouts(){
		
		Collection<LayoutRepresentation> values = Collections.unmodifiableCollection(this.layouts.values());
		
		return values;
	}
	
	public synchronized static LayoutCollection _loadLayoutCollection(InputStream is){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("is", is);
		
		boolean layoutFileJaCarregadoAntes = layoutFiles.containsKey(is);
		
		if(layoutFileJaCarregadoAntes){
			LayoutCollection layoutFile = layoutFiles.get(is);
			return layoutFile;
		}
		
		
		try {
			Wini iniFile = new Wini();
			Reader reader = FileUtils.convertToReader("ISO8859-1", is);
			iniFile.load(reader);
			LayoutCollection layoutFile = new LayoutCollection(iniFile);
			layoutFiles.put(is, layoutFile);
			return layoutFile;
		} catch (IOException e) {
			throw new SystemException("Erro ao carregar arquivo de especificacao de  layouts", e);
		}
	}
	
	public synchronized LayoutRepresentation _loadLayoutByHisName(String layoutName){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutName", layoutName);
		
		boolean layoutJaCarregadoAnteriormente = this.layouts.containsKey(layoutName);
		
		if(layoutJaCarregadoAnteriormente){
			LayoutRepresentation line = this.layouts.get(layoutName);
			return line;
		}
		
		boolean layoutNaoExiste = this.iniFile.containsKey(layoutName) == false;
		
		if(layoutNaoExiste){
			throw new  ThereIsNoSuchLayoutInTheFileException(layoutName);
		}
		
		Map<String, String> layoutConfiguration = this.iniFile.get(layoutName);
		
		HashMap<String, String> dataBaseConfiguration = this.getDataBaseConfigurationForThisLayout(layoutName);
		
		LayoutRepresentation layout = this.loadLayout(layoutName, layoutConfiguration, dataBaseConfiguration);
		
		this.layouts.put(layoutName, layout);
		
		return layout;
	}


	private LayoutRepresentation loadLayout(String layoutName, Map<String, String> layoutConfiguration, HashMap<String, String> dataBaseConfiguration) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutName", layoutName);
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutConfiguration", layoutConfiguration);
		AssertionsUtils.validateNotEmptyAndNotNullObject("dataBaseConfiguration", dataBaseConfiguration);
		
		boolean isRootLayout = dataBaseConfiguration.containsKey("parent") == false;
		
		if(isRootLayout){
			LayoutRepresentation rootLayout = new LayoutRepresentation(layoutName, layoutConfiguration, dataBaseConfiguration);
			return rootLayout;
		}
		
		String layoutParentName = dataBaseConfiguration.get("parent");
		LayoutRepresentation layoutParent = this._loadLayoutByHisName(layoutParentName);
		LayoutRepresentation fileLine = new LayoutRepresentation(layoutName, layoutConfiguration, dataBaseConfiguration, layoutParent);
		return fileLine;
	}


	@SuppressWarnings("unchecked")
	private HashMap<String, String> getDataBaseConfigurationForThisLayout(String layoutName) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("layoutName", layoutName);
		
		boolean hasNotDataBaseConfiguration = this.iniFile.containsKey(DATA_BASE_CONFIGURATION) == false;
		if(hasNotDataBaseConfiguration){
			return new HashMap<String, String>();
		}
		Section allDataBaseConfiguration = this.iniFile.get(DATA_BASE_CONFIGURATION);
		boolean hasNotDataBaseConfigurationToThisLayoutException = allDataBaseConfiguration.containsKey(layoutName) == false;
		
		if(hasNotDataBaseConfigurationToThisLayoutException){
			return new HashMap<String, String>();
		}
		
		String jsonDataBaseConfiguration = allDataBaseConfiguration.get(layoutName);
	 	Gson gson = new Gson();
		HashMap<String, String> dataBaseConfiguration = gson.fromJson(jsonDataBaseConfiguration, HashMap.class);
		return dataBaseConfiguration;

	}

	private void loadLayouts(){

		Set<String> keySet = this.iniFile.keySet();
		
		for (String layoutName : keySet) {
			
			boolean isDataBaseConfiguration = DATA_BASE_CONFIGURATION.equals(layoutName);
			
			if(isDataBaseConfiguration){
				continue;
			}
			this._loadLayoutByHisName(layoutName);
		}
	}
	//findTheMostAppropriateLayoutForThisSequentialValue
	public synchronized LayoutRepresentation _findTheMostAppropriateLayoutForThisSequentialValue(String sequentialValue){
		
		AssertionsUtils.validateNotEmptyAndNotNullObject("sequentialValue", sequentialValue);
		
		ArrayList<LayoutRepresentation> layouts = this.loadAllPossibilities(sequentialValue);
		
		LayoutRepresentation layoutRepresentation = this.validatePossibilitiesFound(sequentialValue, layouts);
		return layoutRepresentation;
	}


	private LayoutRepresentation validatePossibilitiesFound(String sequentialValue, ArrayList<LayoutRepresentation> layouts) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("sequentialValue", sequentialValue);
		AssertionsUtils.validateNotEmptyAndNotNullObject("layouts", layouts);
		
		boolean layoutNotFoundForThisLineValue = layouts.size() == 0;
		
		if(layoutNotFoundForThisLineValue){
			throw new LayoutNotFoundForThisLineValueException(sequentialValue);
		}
		
		boolean tooMuchLayoutsForThisLineValue = layouts.size() > 1;
		
		if(tooMuchLayoutsForThisLineValue){
			throw new TooMuchLayoutsForThisLineValueException(layouts, sequentialValue);
		}
		
		LayoutRepresentation layoutRepresentation = layouts.get(0);
		return layoutRepresentation;
	}


	private ArrayList<LayoutRepresentation> loadAllPossibilities(String sequentialValue) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("sequentialValue", sequentialValue);
	
		Set<String> keySet = this.layouts.keySet();
		
		ArrayList<LayoutRepresentation> layouts = new ArrayList<LayoutRepresentation>();
		
		for (String layoutName : keySet) {
			
			boolean isDataBaseConfiguration = DATA_BASE_CONFIGURATION.equals(layoutName);
			
			if(isDataBaseConfiguration){
				continue;
			}
			
			LayoutRepresentation layout = this.layouts.get(layoutName);
			
			boolean layoutsMatches = layout.isThisLayout(sequentialValue);
			
			if(layoutsMatches){
				layouts.add(layout);
			}
		}
		return layouts;
	}
}