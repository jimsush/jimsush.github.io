package net.sf.modu.oql;


public class OQLTransferHint implements OQLTransfer {

	public static final String HINT_EXP="addhint(";
	private String transferExpression;
	private String hintExp=null;
	
	public OQLTransferHint(String transferExpression){
		this.initRule(transferExpression);
	}
	
	@Override
	public String tranfer(String originalOQL) {
		// addhint(abc)

		int pos=transferExpression.indexOf(HINT_EXP);
		if(pos>=0){
			//trim last )
			hintExp=transferExpression.substring(pos+8,transferExpression.length()-1);
		}
		return hintExp;
	}

	@Override
	public String getType() {
		return "hint";
	}

	@Override
	public void initRule(String transferExpression) {
		this.transferExpression=transferExpression;
		int pos=transferExpression.indexOf(HINT_EXP);
		if(pos>=0){
			//trim last )
			hintExp=transferExpression.substring(pos+8,transferExpression.length()-1);
		}else{
			throw new IllegalArgumentException("No "+HINT_EXP+" in "+transferExpression);
		}
	}

}
