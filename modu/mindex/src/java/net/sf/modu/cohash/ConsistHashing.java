package net.sf.modu.cohash;

public class ConsistHashing {
	
	private HashMethod hashMethod;
	
	
	public ConsistHashing(HashMethod hashMethod){
		this.hashMethod=hashMethod;
	}
	
	public void addNode(String nodeId){
		long nodeHash=hashMethod.computeHash(nodeId);
		
		// map to circle
		throw new UnsupportedOperationException();
	}
	
	public void insertData(Object data){
		throw new UnsupportedOperationException();
	}
	
	public String getNode(Object data){
		throw new UnsupportedOperationException();
	}
	
}
