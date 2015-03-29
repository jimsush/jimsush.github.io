package org.sf.ora2h;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.sf.ora2h.model.Entry;
import org.sf.ora2h.model.MetaData;
import org.sf.ora2h.model.Payload;
import org.sf.ora2h.model.VarMetaData;
import org.sf.ora2h.util.Utils;

public class Dispatcher extends Thread{

	private BlockingQueue<MetaData> metaQ=null;
	private BlockingQueue<VarMetaData> varmetaQ=null;
	private BlockingQueue<Payload> payloadQ=null;
	
	private HbaseWriter[] writers;
	
	private VarMetaData lastVarMeta;
	private Payload     lastPayload;
	
	private List<Entry> entryQueue=new ArrayList<Entry>();
	private long lastSubmitTime=0;
	
	private int lenOfVarMeta=0;
	private volatile int maxLenOfVarMeta=0;
	
	private int lenOfPayload=0;
	private volatile int maxLenOfPayload=0;
	
	private int lenOfMeta=0;
	private volatile int maxLenOfMeta=0;
	
	public Dispatcher(BlockingQueue<MetaData> metaQ,BlockingQueue<VarMetaData> varmetaQ,BlockingQueue<Payload> payloadQ){
		this.metaQ=metaQ;
		this.varmetaQ=varmetaQ;
		this.payloadQ=payloadQ;
	}
	
	public void setMaxLenOfMeta(int maxLenOfMeta){
		this.maxLenOfMeta=maxLenOfMeta;
	}
	
	public void setMaxLenOfVarMeta(int maxLenOfVarMeta){
		this.maxLenOfVarMeta=maxLenOfVarMeta;
	}
	
	public void setMaxLenOfPayload(int maxLenOfPayload){
		this.maxLenOfPayload=maxLenOfPayload;
	}
	
	public void run(){
		writers=new HbaseWriter[Utils.NUM_WRITE_THREAD];
		for(int i=0; i<Utils.NUM_WRITE_THREAD; i++){
			writers[i]=new HbaseWriter(i+"");
			writers[i].start();
		}
		
		for (;;) {
			if(metaQIsEnd()){
				if(entryQueue.size()>0){
					pushEntry2WriterQueue();
				}
				break;
			}
			try{
				Entry entry=getNextEntry();
				entryQueue.add(entry);
				
				boolean submitable=canSubmit();
				// start to submit to 
				if(submitable){
					pushEntry2WriterQueue();
					
					entryQueue.clear();
					lastSubmitTime=System.currentTimeMillis();
				}
			}catch(Exception ex){
				ex.printStackTrace();
				break;
			}
		}
	}
	
	private Entry getNextEntry() throws Exception{
		MetaData meta = metaQ.take();
		
		List<VarMetaData> varmetaList=new ArrayList<VarMetaData>(); //var meta data with same dspi
		getNextVarMeta(meta.getDspi(), varmetaList);
		
		List<Payload> payloadList=new ArrayList<Payload>(); //payload with same dspi
		getNextPayload(meta.getDspi(), payloadList);
		
		Entry entry=new Entry(meta, Utils.combineMultiVarMeta(varmetaList), Utils.combineMultiPayload(payloadList));
		if(entry.getDspi()<20)
			System.out.println("pick:"+entry);
		return entry;
	}
	
	private void getNextVarMeta(long dspi, List<VarMetaData> varmetaList) throws Exception{
		if(lastVarMeta!=null && dspi < lastVarMeta.getDspi()) // 1,2,3,4,5,6  var: 1,2, 5,6
			return;
		
		if(!varmetaQIsEnd()){
			while(true){
				if(!varmetaQIsEnd()){
					VarMetaData var = varmetaQ.take();
					lenOfVarMeta++;
					
					// first of all, handle the last one in last round
					addLastVarMeta2List(dspi, varmetaList);
					
					if(dspi==var.getDspi()){
						varmetaList.add(var);
					}else if(dspi < var.getDspi()){
						lastVarMeta=var;
						break;
					}
				}else{
					// submit last one
					addLastVarMeta2List(dspi, varmetaList);
					break;
				}
			}
		}else{
			// var_meta_data queue is finished, so need to handle the last element that retrieved in last round loop
			addLastVarMeta2List(dspi, varmetaList);
		}
	}
	
	private void getNextPayload(long dspi, List<Payload> payloadList) throws Exception{
		if(lastPayload!=null && dspi < lastPayload.getDspi())
			return;
		
		if(!payloadQIsEnd()){
			while(true){
				if(!payloadQIsEnd()){
					Payload payload = payloadQ.take();
					lenOfPayload++;
					
					// first of all, handle the last one in last round
					addLastPayload2List(dspi, payloadList);
					
					if(dspi==payload.getDspi()){
						payloadList.add(payload);
					}else if(dspi < payload.getDspi()){
						lastPayload=payload;
						break;
					}
				}else{
					// submit last one
					addLastPayload2List(dspi, payloadList);
					break;
				}
			}
		}else{
			// payload queue is finished, so need to handle the last element that retrieved in last round loop
			addLastPayload2List(dspi, payloadList);
		}
	}
	
	private void addLastVarMeta2List(long dspi, List<VarMetaData> varmetaList){
		if(lastVarMeta!=null && lastVarMeta.getDspi()==dspi){
			varmetaList.add(lastVarMeta);
			lastVarMeta=null;
		}
	}
	
	private void addLastPayload2List(long dspi, List<Payload> payloadList){
		if(lastPayload!=null && lastPayload.getDspi()==dspi){
			payloadList.add(lastPayload);
			lastPayload=null;
		}
	}
	
	private boolean canSubmit(){
		boolean submitable=false;
		long now1=System.currentTimeMillis();
		if(entryQueue.size()>=Utils.BATCH_SIZE){
			submitable=true;
		}else{
			long timeGap=now1-lastSubmitTime;
			if(timeGap>Utils.MILLSEC_DELAY){
				submitable=true;
			}
		}
		return submitable;
	}
	
	private void pushEntry2WriterQueue(){
		List<Entry>[] entriesForWriter = Utils.splitList2Smalls(entryQueue, Utils.NUM_WRITE_THREAD);
		
		for(int i=0; i<Utils.NUM_WRITE_THREAD; i++){
			writers[i].pushData(entriesForWriter[i]);
		}
	}
	
	private boolean varmetaQIsEnd(){
		return lenOfVarMeta>=maxLenOfVarMeta;
	}
	
	private boolean payloadQIsEnd(){
		return lenOfPayload>=maxLenOfPayload;
	}
	
	private boolean metaQIsEnd(){
		return lenOfMeta>=maxLenOfMeta;
	}
	
	
}
