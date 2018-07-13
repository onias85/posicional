package  com.ccp.sfr.testes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.ccp.sfr.columns.ColumnDataType;
import com.ccp.sfr.columns.ColumnsList;
import com.ccp.sfr.commons.AssertionsUtils;
import com.ccp.sfr.commons.Exceptions;
import com.ccp.sfr.commons.LayoutCollection;
import com.ccp.sfr.commons.LayoutRepresentation;
import com.ccp.sfr.commons.ReflectionUtils;
import com.ccp.sfr.utils.FileUtils;
import com.ccp.sfr.utils.SystemException;



public class TesteConexao {

	public static void main1(String[] args) throws Exception {
		
	}

	static void testarRelatorio2060(Connection conn) {

		AssertionsUtils.validateNotEmptyAndNotNullObject("conn", conn);
		
		String filePath = "relatorio2060.txt";
		
		String iniFile = "layouts.ini";

		InputStream fileToStream = FileUtils.fileToStream(iniFile);

		gerarRelatorio2060(filePath, fileToStream, conn);

	}

	private static void gerarRelatorio2060(String filePath, InputStream fileToStream, Connection conn) {
		createFile(filePath);  
		
		LayoutCollection lc = LayoutCollection._loadLayoutCollection(fileToStream);
		
		LayoutRepresentation layout2060A = lc._loadLayoutByHisName("R2060A");
		LayoutRepresentation layout2060B = lc._loadLayoutByHisName("R2060B");
		LayoutRepresentation layout2060C = lc._loadLayoutByHisName("R2060C");
		
		Set<ColumnDataType> columns2060A = layout2060A.getColumns();
		Set<ColumnDataType> columns2060B = layout2060B.getColumns();
		Set<ColumnDataType> columns2060C = layout2060C.getColumns();
		
		Set<ColumnDataType> allColumns = new HashSet<ColumnDataType>();
		
		allColumns.addAll(columns2060A);
		allColumns.addAll(columns2060B);
		allColumns.addAll(columns2060C);
		
		ColumnsList cl = new ColumnsList(allColumns);
		
		int k = 0;
		

		
		StringBuilder sql = new StringBuilder("select distinct "+  
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.TP_OPER,  "+
//		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_INFO_RECE, "+ 
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.SG_SIST_ORIG,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_PROD,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_RETE,  "+
        "to_char(IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.DT_MOVI_ENVI, 'dd-MM-yyyy') as DT_MOVI_ENVI, "+
//		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.DS_EVEN, "+ 
//		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.TP_ARQU, "+ 
//		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_RECI, "+ 
//		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.AM_REFE, "+ 
//		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.TP_AMBI, "+ 
//		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.TP_PROC_EMIS_EVEN, "+ 
//		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.DS_VERS_PROC_EMIS_EVEN, "+ 
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.TP_INSC_CONB,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_INSC_CONB,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.TP_INSC_RECE_BRUT,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_INSC_RECE_BRUT,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.VL_RECE_BRUT,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.VL_TOTL_CTRB_PRVD,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.VL_CTRB_PRVD_EXIG_SUSP,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.VL_CTRB_PRVD_SUSP,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.TP_PROC_CTRB_PRVD,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_PROC_CTRB_PRVD,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_SUSP_RECE_BRUT,  "+
//		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_USUA_INCL_REGT,  "+
//      "IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.DH_INCL_REGT,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_AEC.CD_ATVD_COME,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_AEC.VL_TOTL_RECE,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_AEC.VL_TOTL_EXCL_RECE,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_AEC.VL_TOTL_ADIC_RECE,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_AEC.VL_BASE_CALC_RECE,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_AEC.VL_CTRB_PRVD,  "+
//		"IRINF.TB_INF_CTRB_PRVD_RECE_AEC.CD_USUA_INCL_REGT,  "+
//		"IRINF.TB_INF_CTRB_PRVD_RECE_AEC.DH_INCL_REGT,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.TP_AJUS,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.CD_AJUS,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.VL_AJUS,  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.DS_RESU_AJUS,  "+
        "(substr(TO_CHAR(IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.AM_REFE_AJUS ), 0, 4) || '-' || substr(TO_CHAR(IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.AM_REFE_AJUS ), 5)) as AM_REFE_AJUS  "+
        "from IRINF.TB_INF_CTRB_PRVD_RECE_BRUT inner join IRINF.TB_INF_CTRB_PRVD_RECE_AEC on  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.TP_OPER=IRINF.TB_INF_CTRB_PRVD_RECE_AEC.TP_OPER and  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.SG_SIST_ORIG=IRINF.TB_INF_CTRB_PRVD_RECE_AEC.SG_SIST_ORIG and  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_PROD=IRINF.TB_INF_CTRB_PRVD_RECE_AEC.CD_PROD and  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_RETE=IRINF.TB_INF_CTRB_PRVD_RECE_AEC.CD_RETE and  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.DT_MOVI_ENVI=IRINF.TB_INF_CTRB_PRVD_RECE_AEC.DT_MOVI_ENVI and  "+
//		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.DS_EVEN=IRINF.TB_INF_CTRB_PRVD_RECE_AEC.DS_EVEN and "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.TP_INSC_CONB=IRINF.TB_INF_CTRB_PRVD_RECE_AEC.TP_INSC_CONB_AEC and "+ 
		"IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_INSC_CONB=IRINF.TB_INF_CTRB_PRVD_RECE_AEC.CD_INSC_CONB  "+
		"inner join IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU on 	  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_AEC.TP_OPER=IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.TP_OPER and  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_AEC.SG_SIST_ORIG=IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.SG_SIST_ORIG and  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_AEC.CD_PROD=IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.CD_PROD and  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_AEC.CD_RETE=IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.CD_RETE and  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_AEC.DT_MOVI_ENVI=IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.DT_MOVI_ENVI and   "+
//		"IRINF.TB_INF_CTRB_PRVD_RECE_AEC.DS_EVEN=IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.DS_EVEN and "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_AEC.TP_INSC_CONB_AEC=IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.TP_INSC_CONB_BRUT_AJUS and  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_AEC.CD_INSC_CONB=IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.CD_INSC_CONB and  "+
		"IRINF.TB_INF_CTRB_PRVD_RECE_AEC.CD_ATVD_COME=IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.CD_ATVD_COME "+
        "WHERE add_months(trunc(IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.DT_MOVI_ENVI,'mm'),0) = add_months(trunc(sysdate,'mm'),-1)");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql.toString()); 
			rs = ps.executeQuery();
			while (rs.next()) {
			
				StringBuilder lineValue = new StringBuilder();
				StringBuilder columnLabelValue = new StringBuilder();
				
				java.sql.ResultSetMetaData rmd = rs.getMetaData();

				int columnCount = rmd.getColumnCount();
				
				
				for(int m = 1; m <= columnCount; m++) {
					try {
						String columnLabel = rmd.getColumnLabel(m);
						ColumnsList filterByExistentPorpertyValue = cl.filterByExistentPorpertyValue("columnDataBase", columnLabel);
						ColumnDataType first = filterByExistentPorpertyValue.getFirst();
						Object csvTitle = first.getPropertyValue("csvColumn");
						String columnValue = rs.getString(m);
						
						if(columnValue==null) {
							columnValue="";
						}
						
						lineValue.append(";").append(columnValue);
						
						columnLabelValue.append(";").append(csvTitle);
					} catch (Exceptions.EmptyColumnsException e) {
						continue;
					}catch(Exceptions.PropertyNotFoundException e){
						continue;
					}
					
				}
				if(k==0) {
						appendLine(filePath, columnLabelValue.substring(1)+"");
				
					
				}
				appendLine(filePath, lineValue.substring(1)+"");
				k++;
			}

		} catch (SQLException e) {
			throw new SystemException("Erro de banco de sql ao gerar relatório 2060", e);
		}finally{
			ReflectionUtils.closeResources(ps,rs);
		}
	}
	
	

	static void testarOracle(Connection conn) {

		// Added by Marina
		AssertionsUtils.validateNotEmptyAndNotNullObject("conn", conn);

		StringBuilder sql = new StringBuilder(
				"select distinct table_name,column_name from all_tab_columns where owner = 'IRINF' order by table_name,column_name");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			int k = 1;
			while (rs.next()) {
				System.out.println(rs.getString(1) + ";" + rs.getString(2));
				k++;
			}

			System.out.println(k);
		} catch (SQLException e) {
			// e.printStackTrace();
		}finally{
			ReflectionUtils.closeResources(ps,rs);
		}

		System.out.println(conn);
	}
	
	private static File createFile(String filePath) {

		File file = new File(filePath);

		boolean doesNotExist = file.exists() == false;

		if (doesNotExist) {
			File createFile = FileUtils.createFile(filePath);
			return  createFile;
		} 
		
		File resetFile = resetFile(filePath);
		return resetFile;
		
	}
	
	private static void appendLine(String filePath, String contentLine) {

		FileWriter fw = null;
		PrintWriter out = null;
		BufferedWriter bw = null;
		
		try {
			
			fw = new FileWriter(filePath, true);
			bw = new BufferedWriter(fw);
			out = new PrintWriter(bw);
			// Escreve no arquivo e pula linha!!!!!
			out.println(new String(contentLine.toString().getBytes()));

		} catch (IOException e) {
			String format = String.format("Erro ao gravar valor '%s' no arquivo '%s'", contentLine, filePath);
			 throw new SystemException(format, e);
		}finally{
			ReflectionUtils.closeResources(fw, bw, out);
		}
	}
	
	private static File resetFile(String filePath) {
		File f = new File(filePath) ;
		f.delete();
		try {
			f.createNewFile();
		} catch (IOException e) {
			String format = String.format("Erro ao criar o arquivo '%s' ", filePath);
			throw new SystemException(format, e);
		}
		return f;
		
		
	}


	
	static void teste(Connection conn) {
		
		String sql = "select IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.TP_OPER, "
				+ "IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.SG_SIST_ORIG, "
				+ "IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_PROD, "
				+ "IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_RETE, "
				+ "IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.DT_MOVI_ENVI, "
				+ "IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.TP_INSC_CONB, "
				+ "IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_INSC_CONB, "
				+ "IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.TP_INSC_RECE_BRUT,"
				+ " IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_INSC_RECE_BRUT, "
				+ "IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.VL_RECE_BRUT, "
				+ "IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.VL_TOTL_CTRB_PRVD, "
				+ "IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.VL_CTRB_PRVD_EXIG_SUSP,"
				+ " IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.VL_CTRB_PRVD_SUSP, "
				+ "IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.TP_PROC_CTRB_PRVD, "
				+ "IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_PROC_CTRB_PRVD, "
				+ "IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_SUSP_RECE_BRUT, IRINF.TB_INF_CTRB_PRVD_RECE_AEC.TP_INSC_CONB_AEC, IRINF.TB_INF_CTRB_PRVD_RECE_AEC.CD_INSC_CONB, IRINF.TB_INF_CTRB_PRVD_RECE_AEC.CD_ATVD_COME, IRINF.TB_INF_CTRB_PRVD_RECE_AEC.VL_TOTL_RECE, IRINF.TB_INF_CTRB_PRVD_RECE_AEC.VL_TOTL_EXCL_RECE, IRINF.TB_INF_CTRB_PRVD_RECE_AEC.VL_TOTL_ADIC_RECE, IRINF.TB_INF_CTRB_PRVD_RECE_AEC.VL_BASE_CALC_RECE, IRINF.TB_INF_CTRB_PRVD_RECE_AEC.VL_CTRB_PRVD, IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.TP_INSC_CONB_BRUT_AJUS, IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.TP_AJUS, IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.CD_AJUS, IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.VL_AJUS, IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.DS_RESU_AJUS, IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.AM_REFE_AJUS  from IRINF.TB_INF_CTRB_PRVD_RECE_BRUT join IRINF.TB_INF_CTRB_PRVD_RECE_AEC on IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.SG_SIST_ORIG=IRINF.TB_INF_CTRB_PRVD_RECE_AEC.SG_SIST_ORIG and IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_PROD=IRINF.TB_INF_CTRB_PRVD_RECE_AEC.CD_PROD and IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_RETE=IRINF.TB_INF_CTRB_PRVD_RECE_AEC.CD_RETE and IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.DT_MOVI_ENVI=IRINF.TB_INF_CTRB_PRVD_RECE_AEC.DT_MOVI_ENVI and IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.TP_INSC_CONB=IRINF.TB_INF_CTRB_PRVD_RECE_AEC.TP_INSC_CONB_AEC and IRINF.TB_INF_CTRB_PRVD_RECE_BRUT.CD_INSC_CONB=IRINF.TB_INF_CTRB_PRVD_RECE_AEC.CD_INSC_CONB  join IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU on IRINF.TB_INF_CTRB_PRVD_RECE_AEC.SG_SIST_ORIG=IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.SG_SIST_ORIG and IRINF.TB_INF_CTRB_PRVD_RECE_AEC.CD_PROD=IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.CD_PROD and IRINF.TB_INF_CTRB_PRVD_RECE_AEC.CD_RETE=IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.CD_RETE and IRINF.TB_INF_CTRB_PRVD_RECE_AEC.DT_MOVI_ENVI=IRINF.TB_INF_CTRB_PRVD_RECE_BRUT_AJU.DT_MOVI_ENVI";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			System.out.println(rs.next());
		
		}catch(SQLException e) {
			e.printStackTrace();
		}finally{
			ReflectionUtils.closeResources(ps,rs);
		}	
	}

}
