package net.sf.modu.criterion;

import net.sf.modu.mindex.Op;

public class LogicalExpression implements Criterion{

	private Criterion lhs;
	private Criterion rhs;
	private Op op; // or/and
	
	public LogicalExpression(Criterion lhs, Criterion rhs, Op op) {
		this.lhs = lhs;
		this.rhs = rhs;
		this.op = op;
	}
	
	@Override
	public boolean match(Object objInCache) {
		switch(this.op){
		case OR:
			return lhs.match(objInCache) || rhs.match(objInCache);
			
		case AND:
			if(!lhs.match(objInCache))
				return false;
			return rhs.match(objInCache);
			
		default:
			break;
		}
		
		throw new UnsupportedOperationException(this.op+" is not allowed for logical comparsion");
	}

	public String toString(){
		return this.lhs.toString()+" "+this.op+" "+this.rhs.toString();
	}

	@Override
	public String getFirstFieldName() {
		return lhs.getFirstFieldName();
	}

	@Override
	public Op getFirstOp() {
		return lhs.getFirstOp();
	}

	@Override
	public Object getFirstValue() {
		return lhs.getFirstValue();
	}
	
}
