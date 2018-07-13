package  com.ccp.sfr.commons;

public class InsertStatementGenerator extends SqlStatementGenerator {

	@Override
	protected SqlTarget getSqlTarget() {

		DefaultInsertTarget dit = new DefaultInsertTarget(super.layout, super.fileToRead);
		
		return dit;
	}

}
