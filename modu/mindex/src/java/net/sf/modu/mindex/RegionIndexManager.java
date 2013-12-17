package net.sf.modu.mindex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.modu.criterion.Criterion;
import net.sf.modu.criterion.Criterions;


public class RegionIndexManager {

	// all index fields for particular region
	private Map<String,RangeIndex> allIndexes=new HashMap<String, RangeIndex>();
	
	public void addIndex(String name,boolean build,boolean backgroundIndex){
		allIndexes.put(name, new RangeIndex());
		if(build){
			build(name,backgroundIndex);
		}
	}
	
	public void build(String name,boolean backgroundIndex){
		// 
		// TODO
		//
	}
	
	public void removeIndex(String name){
		allIndexes.get(name).clear();
		allIndexes.remove(name);
	}
	
	public void clear(){
		for(Map.Entry<String,RangeIndex> indexEntry : allIndexes.entrySet()){
			indexEntry.getValue().clear();
		}
		allIndexes.clear();
	}
	
	public void addOrUpdateEntry(EntryHolder newEntry,Object oldEntry){
		if(oldEntry==null){
			// add index
			for(Map.Entry<String,RangeIndex> indexEntry : allIndexes.entrySet()){
				String indexFieldName=indexEntry.getKey();
				RangeIndex indexer=indexEntry.getValue();
				Object indexValue = IndexUtil.getAttribute(newEntry.getValue(), indexFieldName);
				indexer.addEntry(indexValue, newEntry);
			}
		}else{
			// update
			for(Map.Entry<String,RangeIndex> indexEntry : allIndexes.entrySet()){
				String indexFieldName=indexEntry.getKey();
				Object newIndexValue = IndexUtil.getAttribute(newEntry.getValue(), indexFieldName);
				Object oldIndexValue = IndexUtil.getAttribute(oldEntry, indexFieldName);
				boolean same=IndexUtil.isEquals(oldIndexValue, newIndexValue);
				if(!same){
					RangeIndex indexer=indexEntry.getValue();
					indexer.removeEntry(oldIndexValue,newEntry.getPrimaryKey());
					indexer.addEntry(newIndexValue, newEntry);
				}
			}
		}
	}

	public List queryInIndex(Criterion indexCriterion){
		RangeIndex rangeIndex = allIndexes.get(indexCriterion.getFirstFieldName());
		if(rangeIndex!=null){
			return rangeIndex.query(indexCriterion.getFirstOp(), indexCriterion.getFirstValue());
		}else{
			// TODO traverse
			return null;
		}
	}
	
	public Criterion getQueryOptimizedIndex(Criterions criterions){
		int max=0;
		Criterion optimizedIndex=null;
		List<Criterion> items = criterions.getCriterions();
		for(Criterion item : items){
			RangeIndex rangeIndex = allIndexes.get(item.getFirstFieldName());
			if(rangeIndex!=null){
				Op op = item.getFirstOp();
				if(op==Op.LIKE || op==Op.NE){
					continue;
				}
				int count=rangeIndex.getIdenticalIndexCount();
				if(count>max){
					max=count;
					optimizedIndex=item;
				}
			}
		}
		return optimizedIndex;
	}


}
