package net.sf.modu.mindex;

public enum Op {
	/** = */
	EQ,
	
	/** != */
	NE,
	
	/** < */
	LT,
	
	/** <= */
	LE,
	
	/** > */
	GT,
	
	/** >= */
	GE,
	
	/** like */
	LIKE,
	
	IN,
	
	BETWEEN,
	
	AND,
	
	OR;
	
	public static String getChar(Op op){
		switch(op){
		case EQ:
			return "=";
		case NE:
			return "!=";
		case LT:
			return "<";
		case LE:
			return "<=";
		case GT:
			return ">";	
		case GE:
			return ">=";
		default:
			return op.toString();
		}
	}
	
}
