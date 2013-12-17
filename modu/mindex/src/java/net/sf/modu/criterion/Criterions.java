package net.sf.modu.criterion;

import java.util.ArrayList;
import java.util.List;

import net.sf.modu.mindex.Op;

public class Criterions {
	
	private List<Criterion> criterions=new ArrayList<Criterion>();
	
	public Criterions(){
	}
	
	public Criterions(String field,Op op,Object conditionValue){
		SimpleExpression se=new SimpleExpression(field,op,conditionValue);
		add(se);
	}

	public Criterions add(Criterion criterion){
		criterions.add(criterion);
		return this;
	}
	
	public boolean match(Object objInCache){
		for(Criterion criteria : criterions){
			if(!criteria.match(objInCache)){
				return false;
			}
		}
		return true;
	}
	
	public List<Criterion> getCriterions() {
		return criterions;
	}

	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		int i=0;
		for(Criterion criterion : criterions){
			if(i==0)
				sb.append(criterion).append(" ");
			else
				sb.append("AND ").append(criterion).append(" ");
			i++;
		}
		return sb.toString();
	}
}
