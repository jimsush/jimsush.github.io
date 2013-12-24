package net.sf.modu.oql;

public class OQLTransferHint implements OQLTransfer {

	public static final String HINT_EXP="addhint(";
	
	@Override
	public String tranfer(String originalOQL, String transferExpression) {
		// addhint(abc)
		String hintExp=null;
		int pos=transferExpression.indexOf(HINT_EXP);
		if(pos>=0){
			//trim last )
			hintExp=transferExpression.substring(pos+8,transferExpression.length()-1);
		}
		return hintExp;
	}

}
