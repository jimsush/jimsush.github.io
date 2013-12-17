package net.sf.modu.mindex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RangeIndex {
	
	/** Not Null Index */
	private TreeMap<Object,Map> valuesToEntries=new TreeMap<Object,Map>();
	
	/** handle null index */
	private Map nullValuesToEntries=new HashMap();
	
	private ReadWriteLock rwLock=new ReentrantReadWriteLock();
	
	public int getIdenticalIndexCount(){
		return valuesToEntries.size()+1;
	}
	
	public void clear(){
		valuesToEntries.clear();
		nullValuesToEntries.clear();
	}
	
	public void addEntry(Object indexValue,EntryHolder entry){
		if(indexValue==null){
			nullValuesToEntries.put(entry.getPrimaryKey(), entry.getValue());
		}else{
			Map subEntries = valuesToEntries.get(indexValue); // index value
			if(subEntries==null){
				subEntries=new HashMap();
				valuesToEntries.put(indexValue, subEntries);
			}
			subEntries.put(entry.getPrimaryKey(), entry.getValue());
		}
	}
	
	public void removeEntry(Object indexValue,Object primaryKey){
		if(indexValue==null){
			nullValuesToEntries.remove(primaryKey);
		}else{
			Map subEntries = valuesToEntries.get(indexValue); 
			if(subEntries!=null)
				subEntries.remove(primaryKey);
		}
	}
	
	public List query(Op op, Object conditionValue){
		List result=new ArrayList();
		if(conditionValue==null){
			if(op==Op.EQ){
				result.addAll(nullValuesToEntries.values());
			}else if(op==Op.NE){
				for(Map.Entry<Object, Map> indexEntry : valuesToEntries.entrySet()){
					Map<Object,Object> m=(Map<Object,Object>)indexEntry.getValue();
					for(Map.Entry entry : m.entrySet()){
						result.add(entry.getValue());
					}
				}
			}else{
				throw new IllegalArgumentException("Don't support "+op+" for null");
			}
		}else{
			boolean inclusive=true;
			switch(op){
			case EQ:
				Map map = valuesToEntries.get(conditionValue);
				if(map!=null){
					Map<Object,Object> m=(Map<Object,Object>)map;
					for(Map.Entry entry : m.entrySet()){
						result.add(entry.getValue());
					}
				}
				break;
				
			case NE: // TODO the below implementation is not good, it is still a full entry scan
				for(Map.Entry<Object, Map> indexEntry : valuesToEntries.entrySet()){
					Object idxKey=indexEntry.getKey();
					if(!conditionValue.equals(idxKey)){ // exclude
						Map<Object,Object> m=(Map<Object,Object>)indexEntry.getValue();
						for(Map.Entry entry : m.entrySet()){
							result.add(entry.getValue());
						}
					}
				}
				break;
				
			case LT:
				inclusive=false;
			case LE:
				SortedMap<Object, Map> subMap = valuesToEntries.headMap(conditionValue,inclusive);
				if(subMap!=null){
					for(Map.Entry<Object, Map> indexEntry : subMap.entrySet()){
						Map<Object,Object> m=(Map<Object,Object>)indexEntry.getValue();
						for(Map.Entry entry : m.entrySet()){
							result.add(entry.getValue());
						}
					}
				}
				break;
				
			case GT:
				inclusive=false;
			case GE:
				SortedMap<Object, Map> subMap2 = valuesToEntries.tailMap(conditionValue,inclusive);
				if(subMap2!=null){
					for(Map.Entry<Object, Map> indexEntry : subMap2.entrySet()){
						Map<Object,Object> m=(Map<Object,Object>)indexEntry.getValue();
						for(Map.Entry entry : m.entrySet()){
							result.add(entry.getValue());
						}
					}
				}
				break;
				
			default:
				throw new IllegalArgumentException("Don't support "+op);
			}
		
		}
		
		return result;
	}
	
}
