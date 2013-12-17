package net.sf.modu.mindex;

import java.util.concurrent.locks.ReadWriteLock;


public class EntryHolder {

	private Object primaryKey;
	private Object value;
	private ReadWriteLock rowLock;
	
	public EntryHolder(Object primaryKey,Object value){
		this.primaryKey=primaryKey;
		this.value=value;
	}
	
	public Object getPrimaryKey() {
		return primaryKey;
	}
	
	public Object getValue() {
		return value;
	}
}
