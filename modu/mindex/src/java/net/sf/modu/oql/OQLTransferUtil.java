package net.sf.modu.oql;

public class OQLTransferUtil {

	private static OQLTransfer hint=new OQLTransferHint();
	private static OQLTransfer replace=new OQLTransferReplace();
	
	public static OQLTransfer getOQLTransfer(String actionExpression){
		if(actionExpression.contains(OQLTransferReplace.REPLACE_SEP)){
			return replace;
		}else if(actionExpression.startsWith(OQLTransferHint.HINT_EXP)){
			return hint;
		}
		throw new UnsupportedOperationException();
	}
}
