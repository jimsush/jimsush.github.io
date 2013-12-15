/**
 * 
 */
package net.sf.modu.mindex;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author tong
 *
 */
public class RangeIndex {
	private ReadWriteLock rwlock=new ReentrantReadWriteLock();
	private TreeMap<Object, Set<Object>> valuesOfIndex=new TreeMap<Object, Set<Object>>();
	
	public int getValuesNumber(){
		return valuesOfIndex.size();
	}
	
	public void addEntry(Object indexValue,Object entry){
		Set<Object> values=valuesOfIndex.get(indexValue);
		if(values==null){
			values=new HashSet<Object>();
			valuesOfIndex.put(indexValue, values);
		}
		values.add(entry); // hashCode(???), 减少hashCode的计算，到底放entry还是primary Key了?
		
		// why create hashset if just one entry in set?  (to optimize later)
	}
	
	public void remove(Object indexValue,Object key){
		Set<Object> values=valuesOfIndex.get(indexValue);
		
	}
	
	public void query(int operator,Object indexValue){
		
	}
	
}
