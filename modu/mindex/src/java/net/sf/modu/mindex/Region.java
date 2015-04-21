package net.sf.modu.mindex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.modu.criterion.Criterion;
import net.sf.modu.criterion.Criterions;

/**
 * K is the type of primary key, T is the type of entry
 *
 */
public class Region<K,T> {

	public static final int PK_TYPE_STRING=0;
	public static final int PK_TYPE_LONG=1;
	public static final int PK_TYPE_INTEGER=2;
	
	// <soeid, [name,country,age,onboard]>
	private Map<K,T> entries=new HashMap<K, T>();
	
	private RegionIndexManager regionIndexManager=new RegionIndexManager();
	
	public int getPrimaryKeyType(){
		return PK_TYPE_STRING;
	}
	
	public String getRegionName(){
		return "/hello";
	}
	
	public int getRegionId(){
		return 0;
	}
	
	public int getRegionSize(){
		return entries.size();
	}
	
	public void clearRegion(){
		entries.clear();
		regionIndexManager.clear();
	}

	public void registerIndex(String name,boolean build,boolean backgroundIndex){
		regionIndexManager.addIndex(name, build, backgroundIndex);
	}
	
	public void addEntry(K pk,T entry){
		// get row lock
		T oldEntry=entries.get(pk);
		entries.put(pk, entry);
		
		// add index
		regionIndexManager.addOrUpdateEntry(new EntryHolder(pk,entry), oldEntry);
	}
	
	public T getByKey(K pk){
		return entries.get(pk);
	}
	
	public List<T> query(String conditionField, Op op, Object conditionValue){
		return query(new Criterions(conditionField,op,conditionValue));
	}
	
	public List<T> query(Criterions criterions){
		List<Criterion> items = criterions.getCriterions();
		if(items!=null && items.size()>0){
			List result1 =null;
			Criterion bestCriteriaItem = regionIndexManager.getQueryOptimizedIndex(criterions);
			List<Criterion> phase2CriteriaItems;
			if(bestCriteriaItem!=null){
				// use 1 index filed to filter first
				System.out.println("query: select * from "+getRegionName()+" where "+criterions+", used index("+bestCriteriaItem.getFirstFieldName()+")");
				result1 = regionIndexManager.queryInIndex(bestCriteriaItem);
				phase2CriteriaItems=new ArrayList<Criterion>();
				phase2CriteriaItems.addAll(items);
				phase2CriteriaItems.remove(bestCriteriaItem);
			}else{
				// no index used for this query
				result1 =new ArrayList<T>(entries.size());
				result1.addAll(entries.values());
				phase2CriteriaItems=items;
			}
			
			// TODO union other result set
			List finalResult=traverseListWithCondition(result1,phase2CriteriaItems);
			return result1;
		}else{
			// return all result set as without criteria
			List<T> resultSet=new ArrayList<T>();
			resultSet.addAll(entries.values());
			return resultSet;
		}
	}

	private List<T> traverseListWithCondition(List result1,List<Criterion> items){
		if(items==null || items.size()==0)
			return result1;

		Iterator iterator = result1.iterator();
		while(iterator.hasNext()){
			Object entry = iterator.next();
			if(!match(entry,items)){
				iterator.remove();
			}
		}
		
		return result1;
	}
	
	private boolean match(Object entry, List<Criterion> criterions){
		for(Criterion criterion : criterions){
			boolean matched=criterion.match(entry);
			if(matched){
				return true;
			}
		}
		return false;
	}
	
}
