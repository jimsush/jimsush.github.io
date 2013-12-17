package net.sf.modu.criterion;

import net.sf.modu.mindex.Op;


/**
 * condition1=value1
 * 
 * cond1=value1 or cond2=value2 and cond3=value3
 * 
 *
 */
public interface Criterion {
	
	/**
	 * match entry and this Criterion
	 * @param entry
	 * @return
	 */
	public boolean match(Object entry);

	public String getFirstFieldName();
	
	public Op getFirstOp();

	public Object getFirstValue();
	
}
