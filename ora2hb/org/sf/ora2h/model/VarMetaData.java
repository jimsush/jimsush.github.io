package org.sf.ora2h.model;

public class VarMetaData {
	
	private long dspi;
	private String content;
	
	public VarMetaData(){}
	
	public VarMetaData(long dspi, String content){
		this.dspi=dspi;
		this.content=content;
	}
	
	public long getDspi() {
		return dspi;
	}
	
	public void setDspi(long dspi) {
		this.dspi = dspi;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
}
