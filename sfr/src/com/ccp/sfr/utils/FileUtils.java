package  com.ccp.sfr.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import com.ccp.sfr.commons.AssertionsUtils;
import com.ccp.sfr.commons.ReflectionUtils;
import com.ccp.sfr.utils.ReinfUtils.HASH_ALGORITHM;


public class FileUtils {

	public static int getNumberOfRowsFromFile(File file) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("file", file);
		
		FileReader in = null;
		LineNumberReader linhaLeitura = null;
		try{
			in = new FileReader(file);
			linhaLeitura = new LineNumberReader(in);
			long fileLength = file.length();
			linhaLeitura.skip(fileLength);
			int lineNumber = linhaLeitura.getLineNumber();
			return lineNumber;
		} catch (IOException e) {
			throw new SystemException(
					"Erro ao contar quantidade de linhas do arquivo "
							+ file.getAbsolutePath(), e);
		}finally{
			ReflectionUtils.closeResources(in,linhaLeitura);
		}
	}

	public static Reader convertToReader(String filePath, String charsetName) {
		
		InputStream fileToStream = fileToStream(filePath);

		Reader convertToReader = convertToReader(charsetName, fileToStream);
		return convertToReader;
	}

	public static Reader convertToReader(String charsetName, InputStream fileToStream) {
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(fileToStream, charsetName);
			return inputStreamReader;
		} catch (UnsupportedEncodingException e) {
			String format = String.format("O charset'%s' não é suportado", charsetName);
			throw new SystemException(format, e);
		}
	}

	public static File loadAndValidateDirectory(String path) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("path", path);

		File diretorio = loadExistentFile(path);

		boolean esteArquivoNaoEhPasta = diretorio.isDirectory() == false;

		if (esteArquivoNaoEhPasta) {
			throw new EsteArquivoNaoEhPasta(path);
		}

		return diretorio;
	}

	public static File loadFolder(String folderPath) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("folderPath",
				folderPath);

		File folder = new File(folderPath);

		boolean precisaCriarEstaPasta = folder.exists() == false;

		if (precisaCriarEstaPasta) {
			folder.mkdir();
			return folder;
		}

		folder = loadAndValidateDirectory(folderPath);

		return folder;

	}

	public static File loadAndValidateFile(String path) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("path", path);

		File arquivo = loadExistentFile(path);

		boolean esteArquivoPasta = arquivo.isDirectory();

		if (esteArquivoPasta) {
			throw new EsteArquivoEhPasta(path);
		}

		return arquivo;
	}

	public static File moveToFolder(String pathTargetFolder, File movedFile) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("pathTargetFolder", pathTargetFolder);
		AssertionsUtils.validateNotEmptyAndNotNullObject("movedFile", movedFile);

		File targetFolder = loadFolder(pathTargetFolder);

		String nameOfMovedFile = movedFile.getName();
		File renamedFile = new File(targetFolder, nameOfMovedFile);
		movedFile.renameTo(renamedFile);
		return renamedFile;
	}

	private static File loadExistentFile(String path) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("path", path);

		File diretorio = new File(path);

		boolean arquivoInexistente = diretorio.exists() == false;
		if (arquivoInexistente) {
			throw new EsteArquivoNaoExiste(path);
		}
		return diretorio;
	}

	public static InputStream fileToStream(String filePath) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("filePath", filePath);

		try {
			FileInputStream fis = new FileInputStream(filePath);
			
			return fis;
		} catch (FileNotFoundException e) {
			throw new SystemException("O arquivo " + filePath + " nao existe", 	e);
		}
	}

	public static String getHash(String filePath, HASH_ALGORITHM hashAlgorithm) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("filePath", filePath);
		AssertionsUtils.validateNotEmptyAndNotNullObject("hashAlgorithm", hashAlgorithm);

		File file = loadExistentFile(filePath);
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);
			String hash = ReinfUtils.getHash(fis, hashAlgorithm);
			return hash;
		} catch (IOException e) {
			throw new SystemException("Erro ao gerar hash do arquivo '"
					+ filePath + "'", e);
		}finally{
			ReflectionUtils.closeResources(fis);
		}
	}

	
	public static String getLineValueByIndex(String fileName, int index){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("fileName", fileName);
		
		File file = new File(fileName);

		int k = 0;
		FileReader in = null;
		BufferedReader br = null;
		try {
			in = new FileReader(file);
			br = new BufferedReader(in);
			String lineValue;
			while((lineValue = br.readLine()) != null){
				
				boolean indexFound = k++ == index;
				
				if(indexFound){
					return lineValue;
				}
			}
		} catch (IOException e) {
			throw new SystemException("Erro ao ler o arquivo " + fileName, e);
		}finally{
			ReflectionUtils.closeResources(in,br);
		}
		
		throw new LineIndexNotFound(fileName, index, k);
	}

	public static File createFile(String filePath){
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("filePath", filePath);
		
		File file = new File(filePath);
		
		boolean doesNotExist = file.exists() == false;
		
		if(doesNotExist){
			try {
				file.createNewFile();
			} catch (IOException e) {
				String format = String.format("Erro ao criar o arquivo '%s' ", filePath);
				throw new SystemException(format, e);
			}
		}
		return file;
	}
	
	public static void appendLine(String filePath, String contentLine) {
		
		//Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("filePath", filePath);
		AssertionsUtils.validateNotEmptyAndNotNullObject("contentLine", contentLine);
		FileWriter fw = null;
		PrintWriter out = null;
		BufferedWriter bw = null;
		try {
			 fw = new FileWriter(filePath, true);
			 bw = new BufferedWriter(fw);
			 out = new PrintWriter(bw);
			out.println(new String(contentLine.toString().getBytes()));
	
		} catch (IOException e) {
			String format = String.format("Erro ao gravar valor '%s' no arquivo '%s'", contentLine, filePath);
			throw new SystemException(format, e);
		}finally{
			ReflectionUtils.closeResources(fw,bw,out);
		}
	}
	
	public static String readFileAsString(String file){
		    FileReader     in            = null;
			BufferedReader reader        = null;
		    String         line          = null;
		    StringBuilder  stringBuilder = new StringBuilder();
		    String         ls = "";
		    
		    try {
		    	in = new FileReader (file);
		    	reader = new BufferedReader(in);
		    	while((line = reader.readLine()) != null) {
		            stringBuilder.append(line);
		            stringBuilder.append(ls);
		        }

		        return stringBuilder.toString();
		    }catch(IOException e){
		    	throw new SystemException("Erro ao ler conteudo do arquivo " + file, e);
		    } finally {
		       ReflectionUtils.closeResources(in, reader);
		    }
		    
	}

}

@SuppressWarnings("serial")
class EsteArquivoNaoExiste extends RuntimeException {
	EsteArquivoNaoExiste(String path) {
		super("O path '" + path + "' nao existe");
	}
}

@SuppressWarnings("serial")
class EsteArquivoNaoEhPasta extends RuntimeException {
	EsteArquivoNaoEhPasta(String path) {
		super("O path '" + path + "' nao se refere a uma pasta");
	}
}

@SuppressWarnings("serial")
class EsteArquivoEhPasta extends RuntimeException {
	EsteArquivoEhPasta(String path) {
		super("O path '" + path + "' se refere a uma pasta");
	}
}

@SuppressWarnings("serial")
class LineIndexNotFound extends RuntimeException {
	LineIndexNotFound(String path, int indexWanted, int lastFileIndex) {
		super(String.format("O arquivo '%s' contem '%d' linhas, no entanto foi procurado pela linha '%d'", path, indexWanted, lastFileIndex));
	}
}
