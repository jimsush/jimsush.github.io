package org.sf.ora2h.model;

public class Payload {
	
	private long dspi;
	private String content;
	
	public Payload(){}
	
	public Payload(long dspi, String content){
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
	
	public String toString(){
		return dspi+" "+content;
	}
	
}
