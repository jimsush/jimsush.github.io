package org.sf.ora2h.model;

public class MetaData {
	
	private long dspi;
	private String field1;
	
	public MetaData(){}
	
	public MetaData(long dspi, String field1){
		this.dspi=dspi;
		this.field1=field1;
	}
	
	public long getDspi() {
		return dspi;
	}
	
	public void setDspi(long dspi) {
		this.dspi = dspi;
	}
	
	public String getField1() {
		return field1;
	}
	
	public void setField1(String field1) {
		this.field1 = field1;
	}
	
}
