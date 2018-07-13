package  com.ccp.sfr.testes;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import com.ccp.sfr.commons.CopyData;
import com.ccp.sfr.commons.LayoutCollection;
import com.ccp.sfr.commons.LayoutRepresentation;
import com.ccp.sfr.utils.DataBaseUtils;
import com.ccp.sfr.utils.FileUtils;
import com.ccp.sfr.utils.ReinfUtils.HASH_ALGORITHM;


/**
 * Para exemplo de como usar este framework siga os seguintes passos:
 * 1) Tenha uma conexao com um banco de dados e um driver para este banco, neste exemplo usa-se oracle e a string de conexao e: 
 * DriverManager.getConnection("jdbc:oracle:thin:@192.168.42.32:1516:card", "ascargas", "ascargasdev");
 * 2) No banco de dados da string de conexao acima, execute scripts de criacao de tabelas no caso desse exemplo, esta sendo utilizado os scripts do arquivo create_policard_tables;
 * 3) Tenha um arquivo para layout sequencial, nesse exemplo tem-se o policard.ini, se necessario copie-o pro seu projeto e altere-o pra sua necessidade.
 * 4) Tenha um arquivo que tenha os dados sequenciais por linha que estejam aderentes ao layout sequencial tratado no passo anterior, nesse exemplo esta sendo tratado o arquivo EPA_DMA490403112016
 * 5) Rode e observe os codigos desta classe
 * 
 * @author Onias Vieira Junior
 *
 */

public class Teste {

	public static void main(String[] args) throws Exception {
		DataBaseUtils.executeSql("delete from IRINF.TB_INF_CONB", DataBaseUtils.getConnection());
	}
	
	
	static void testePojo(){

		String codBanco = "1";
		String loteServico = "2";
		String tipoRegistro = "3";
		String cnab1 = "4";
		
		String tipoInscricaoEmpresa = "5";
		String numInscricaoEmpresa = "6";
		String codConvenioBanco = "7";
		String agenciaMantenedoraConta = "8";
		
		String digitoVerificadorAgencia = "9";
		String numContaCorrente = "1";
		String digitoVerificadorConta= "2";
		String digitoVerificadorAgenciaConta = "3";
		
		String nomeEmpresa = "4";
		String nomeBanco = "5";
		String cnab2 = "6";
		String codRemessaRetorno = "7";
		String dataGeracaoArquivo = "";
		String horaGeracaoArquivo = "";
		String numSequencialArquivo = "8";
		String numVersaoLayoutArquivo = "9";
		String densidadeGravacaoArquivo = "1";
		String paraUsoReservadoBanco = "2";
		String paraUsoReservadoEmpresa = "3";
		String cnab3 = "4";
		HeaderArquivo pojo = new HeaderArquivo(codBanco, loteServico , tipoRegistro , cnab1 , tipoInscricaoEmpresa , numInscricaoEmpresa , codConvenioBanco , 
				agenciaMantenedoraConta , digitoVerificadorAgencia , numContaCorrente, digitoVerificadorConta, digitoVerificadorAgenciaConta, nomeEmpresa, nomeBanco, cnab2 , 
				codRemessaRetorno, dataGeracaoArquivo , horaGeracaoArquivo, numSequencialArquivo, numVersaoLayoutArquivo, densidadeGravacaoArquivo, paraUsoReservadoBanco, paraUsoReservadoEmpresa, cnab3);
		
	
		InputStream fileToStream = FileUtils.fileToStream("/data/febrabancobrancas.ini");
	
		CopyData._fromPojoToFile(pojo, "pojo", fileToStream);
	
	}
	
	static void geraHash(){
//		FileUtils.getHash(filePath, hashAlgorithm)

		File gmud = new File("/home/onias/dev/GMUD");
		
		File[] listFiles = gmud.listFiles();
		
		for (File file : listFiles) {
			String hash = FileUtils.getHash(file.getAbsolutePath(), HASH_ALGORITHM.MD5);
			String name = file.getName();
			System.out.println("Nome: " + name + "\nHash: " + hash+ "\n--------------------------\n ");
		}
	}
	
	static void testeVariosArquivos() throws SQLException{
		File exemplos = new File("exemplos");
		File[] list = exemplos.listFiles();
		
//		Connection connection = getConnection();
		
//		String layoutsCollectionFile = "policard.ini";
//		InputStream fileToStream = FileUtils.fileToStream(layoutsCollectionFile);
	
		for (File fileSource : list) {
			String absolutePath = fileSource.getAbsolutePath();
			System.out.println(absolutePath);
//			CopyData._fromFileToDataBase(connection, fileToStream, input, output);
			System.out.println();
		}
	}

	static void testes() {
	
		String layoutsCollectionFile = "policard.ini";
		InputStream fileToStream = FileUtils.fileToStream(layoutsCollectionFile);
		String lineValue = "0011120161604480507201639ADM1006450076        00904951000195POLICARD SYSTEMS    00000092039                                                                                                             ";
		LayoutRepresentation layout = LayoutCollection._loadLayoutCollection(fileToStream)._loadLayoutByHisName("headerArquivo");
		Map<String, String> map = layout.readFile(lineValue);
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			String value = map.get(key);
			System.out.println(key + " = " + value);
		}
		
		boolean thisLayout = layout.isThisLayout(lineValue);
		System.out.println(thisLayout);
	}

	 static void testeCompleto() throws Exception, SQLException {
		long x = System.currentTimeMillis();

		for(int k = 0; k < 10; k++){
			testeCopiaArquivoParaBanco();
			testeCopiaBancoParaArquivo();
		}

		System.out.println(System.currentTimeMillis() - x);
	}

	static void testeCopiaArquivoParaBanco() throws Exception{
		
//		long x = System.currentTimeMillis();
		
//		Connection connection = getConnection();
//		
//		String fileSource = "EPA_DMA490403112016";
//		
//		String layoutsCollectionFile = "policard.ini";
	
//		CopyData._fromFileToDataBase(connection, fileSource, FileUtils.fileToStream(layoutsCollectionFile));
		
//		System.out.println(System.currentTimeMillis() - x);
	}
	
	static void testeCopiaBancoParaArquivo() throws SQLException{
		

		long x = System.currentTimeMillis();
		
		String fileSource = "saida" + x + ".TXT";
		
		String layoutsCollectionFile = "policard.ini";
		String layoutName = "transacao";
	
		Connection connection = getConnection();

		PreparedStatement ps = connection.prepareStatement("select * from POLICARD_TRANSACAO");
		
		ResultSet resultSet = ps.executeQuery();
		
		CopyData._fromDataBaseToFile(FileUtils.fileToStream(layoutsCollectionFile), layoutName, resultSet, fileSource);
		
//		System.out.println(System.currentTimeMillis() - x);
		
	}


	private static Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.42.32:1516:card", "ascargas", "ascargasdev");;
		return connection;
	}
}


class HeaderArquivo{
	String codBanco				              ;
	String loteServico                           ; 
	String tipoRegistro                           ;
	String cnab1		                           ;
	String tipoInscricaoEmpresa                    ;
	String numInscricaoEmpresa		                ;
	String codConvenioBanco	                    ;
	String agenciaMantenedoraConta                 ;
	String digitoVerificadorAgencia               ;
	String numContaCorrente	                    ;
	String digitoVerificadorConta                  ;
	String digitoVerificadorAgenciaConta           ;
	String nomeEmpresa                             ;
	String nomeBanco	                            ;
	String cnab2		                            ;
	String codRemessaRetorno	                    ;
	String dataGeracaoArquivo	                    ;
	String horaGeracaoArquivo	                    ;
	String numSequencialArquivo	                ;
	String numVersaoLayoutArquivo	                ;
	String densidadeGravacaoArquivo	            ;
	String paraUsoReservadoBanco		            ;
	String paraUsoReservadoEmpresa		            ;
	String cnab3		                            ;
	public HeaderArquivo(String codBanco, String loteServico, String tipoRegistro, String cnab1,
			String tipoInscricaoEmpresa, String numInscricaoEmpresa, String codConvenioBanco,
			String agenciaMantenedoraConta, String digitoVerificadorAgencia, String numContaCorrente,
			String digitoVerificadorConta, String digitoVerificadorAgenciaConta, String nomeEmpresa, String nomeBanco,
			String cnab2, String codRemessaRetorno, String dataGeracaoArquivo, String horaGeracaoArquivo,
			String numSequencialArquivo, String numVersaoLayoutArquivo, String densidadeGravacaoArquivo,
			String paraUsoReservadoBanco, String paraUsoReservadoEmpresa, String cnab3) {
		this.codBanco = codBanco;
		this.loteServico = loteServico;
		this.tipoRegistro = tipoRegistro;
		this.cnab1 = cnab1;
		this.tipoInscricaoEmpresa = tipoInscricaoEmpresa;
		this.numInscricaoEmpresa = numInscricaoEmpresa;
		this.codConvenioBanco = codConvenioBanco;
		this.agenciaMantenedoraConta = agenciaMantenedoraConta;
		this.digitoVerificadorAgencia = digitoVerificadorAgencia;
		this.numContaCorrente = numContaCorrente;
		this.digitoVerificadorConta = digitoVerificadorConta;
		this.digitoVerificadorAgenciaConta = digitoVerificadorAgenciaConta;
		this.nomeEmpresa = nomeEmpresa;
		this.nomeBanco = nomeBanco;
		this.cnab2 = cnab2;
		this.codRemessaRetorno = codRemessaRetorno;
		this.dataGeracaoArquivo = dataGeracaoArquivo;
		this.horaGeracaoArquivo = horaGeracaoArquivo;
		this.numSequencialArquivo = numSequencialArquivo;
		this.numVersaoLayoutArquivo = numVersaoLayoutArquivo;
		this.densidadeGravacaoArquivo = densidadeGravacaoArquivo;
		this.paraUsoReservadoBanco = paraUsoReservadoBanco;
		this.paraUsoReservadoEmpresa = paraUsoReservadoEmpresa;
		this.cnab3 = cnab3;
	}

	
}
