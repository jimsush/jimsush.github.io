package net.sf.modu.oql;

public interface OQLTransfer {

	/**
	 * init transfer rule
	 * @param transferExpression 
	 */
	public void initRule(String transferExpression);
	
	/**
	 * tranfer this OQL to new OQL
	 * @param originalOQL
	 * @return new OQL
	 */
	public String tranfer(String originalOQL);
	
	/**
	 * tranfer type
	 * @return
	 */
	public String getType();

}
