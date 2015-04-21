package net.sf.modu.oql;

public class OQLTransferReplace implements OQLTransfer {

	public static final String REPLACE_SEP="->";
	private String transferExpression;
	private String srcExp=null;
	private String dstExp=null;
	
	public OQLTransferReplace(String transferExpression){
		initRule(transferExpression);
	}
	
	@Override
	public String tranfer(String originalOQL) {
		//Reg Express -> Reg Express2
		String newOQL=originalOQL.replaceAll(srcExp, dstExp);
		return newOQL;
	}

	@Override
	public String getType() {
		return "replace";
	}

	@Override
	public void initRule(String transferExpression) {
		this.transferExpression=transferExpression;
		int pos=this.transferExpression.indexOf("->");
		srcExp=this.transferExpression.substring(0,pos);
		dstExp=this.transferExpression.substring(pos+2);
	}

}
