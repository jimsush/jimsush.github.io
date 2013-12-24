package net.sf.modu.oql;

public class OQLTransferFactory {

	/**
	 * create concrete OQL transfer
	 * @param actionExpression 'addhint()' or 'a->b'
	 * @return 
	 */
	public static OQLTransfer createOQLTransfer(String actionExpression){
		if(actionExpression.contains(OQLTransferReplace.REPLACE_SEP)){
			return new OQLTransferReplace(actionExpression);
		}else if(actionExpression.startsWith(OQLTransferHint.HINT_EXP)){
			return new OQLTransferHint(actionExpression);
		}
		throw new UnsupportedOperationException();
	}
}
