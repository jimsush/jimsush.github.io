package org.sf.ora2h.model;

public class Entry {

	private long dspi;
	private MetaData meta;
	private VarMetaData var;
	private Payload payload;
	
	public Entry(MetaData meta, VarMetaData var,Payload payload){
		this.dspi=meta.getDspi();
		this.meta=meta;
		this.var=var;
		this.payload=payload;
	}
	
	public long getDspi() {
		return dspi;
	}
	
	public void setDspi(long dspi) {
		this.dspi = dspi;
	}
	
	public MetaData getMeta() {
		return meta;
	}
	
	public void setMeta(MetaData meta) {
		this.meta = meta;
	}
	
	public VarMetaData getVar() {
		return var;
	}
	
	public void setVar(VarMetaData var) {
		this.var = var;
	}
	
	public Payload getPayload() {
		return payload;
	}
	
	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	
	public String toString(){
		StringBuilder sb=new StringBuilder();
		if(this.meta!=null)
			sb.append(this.meta.getDspi()).append(" field:").append(this.meta.getField1());
		if(this.var!=null)
			sb.append(" var:").append(this.var.getContent());
		if(this.payload!=null)
			sb.append(" payload:").append(this.payload.getContent());
		return sb.toString();
	}
	
}
