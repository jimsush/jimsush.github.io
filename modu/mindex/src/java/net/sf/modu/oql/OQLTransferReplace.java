package net.sf.modu.oql;

public class OQLTransferReplace implements OQLTransfer {

	public static final String REPLACE_SEP="->";
	
	@Override
	public String tranfer(String originalOQL, String transferExpression) {
		//Reg Express -> Reg Express2
		int pos=transferExpression.indexOf("->");
		String srcExp=transferExpression.substring(0,pos);
		String dstExp=transferExpression.substring(pos+2);
		String newOQL=originalOQL.replaceAll(srcExp, dstExp);
		return newOQL;
	}

}
