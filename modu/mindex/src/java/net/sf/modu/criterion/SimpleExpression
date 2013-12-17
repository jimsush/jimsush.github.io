package net.sf.modu.criterion;

import net.sf.modu.mindex.IndexUtil;
import net.sf.modu.mindex.Op;

public class SimpleExpression implements Criterion{

	private String field;
	private Op op;
	private Object conditionValue;
	
	public SimpleExpression(String field,Op op,Object conditionValue){
		this.field=field;
		this.op=op;
		this.conditionValue=conditionValue;
	}
	
	public boolean match(Object objInCache){
		Object valueInCache=IndexUtil.getAttribute(objInCache,field);
		switch(op){
		case EQ:
			return IndexUtil.isEquals(valueInCache, conditionValue);
			
		case NE:
			return !IndexUtil.isEquals(valueInCache, conditionValue);
			
		case GT:
			return IndexUtil.isGreat(valueInCache, conditionValue);
			
		case GE:
			return IndexUtil.isGreatEquals(valueInCache, conditionValue);
			
		case LT:
			return IndexUtil.isLess(valueInCache, conditionValue);
			
		case LE:
			return IndexUtil.isLessEquals(valueInCache, conditionValue);
			
		default:
			break;
		}

		throw new UnsupportedOperationException(this.op+"");
	}

	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append(this.field).append(" ").append(Op.getChar(this.op)).append(" ");
		if(this.conditionValue instanceof String){
			sb.append("'").append(this.conditionValue).append("'");
		}else{
			sb.append(this.conditionValue);
		}
		return sb.toString(); 
	}

	@Override
	public String getFirstFieldName() {
		return this.field;
	}

	@Override
	public Op getFirstOp() {
		return op;
	}

	@Override
	public Object getFirstValue() {
		return this.conditionValue;
	}

}
