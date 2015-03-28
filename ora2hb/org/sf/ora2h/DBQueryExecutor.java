package org.sf.ora2h;

import java.util.concurrent.BlockingQueue;

import org.sf.ora2h.mockdata.MockData;
import org.sf.ora2h.model.MetaData;
import org.sf.ora2h.model.Payload;
import org.sf.ora2h.model.VarMetaData;
import org.sf.ora2h.util.Utils;

public class DBQueryExecutor {

	private String region;
	
	private String metaTable;
	private String varmetaTable;
	private String payloadTable;
	
	private String metaSql;
	private String varmetaSql;
	private String payloadSql;
	
	private BlockingQueue<MetaData> metaQ=null;
	private BlockingQueue<VarMetaData> varmetaQ=null;
	private BlockingQueue<Payload> payloadQ=null;
	
	public DBQueryExecutor(BlockingQueue<MetaData> metaQ,BlockingQueue<VarMetaData> varmetaQ,BlockingQueue<Payload> payloadQ){
		this.metaQ=metaQ;
		this.varmetaQ=varmetaQ;
		this.payloadQ=payloadQ;
	}
	
	public void start(){
		// mock meta/varmeta/payload in oracle, and put the data in 3 separate queues
		Thread metaThread=new Thread(){
			public void run(){
				for(int i=0; i<Utils.NUM_META; i++){
					metaQ.add(MockData.genMetaData(i));
				}
			}	
		};
		
		Thread varmetaThread=new Thread(){
			public void run(){
				int entryCount=0;
				
				for(int i=0; i<Utils.NUM_META; i++){
					if(entryCount >=Utils.NUM_VAR_META){
						break;
					}
					
					if(i%9==1){
						VarMetaData[] vars = MockData.genVarMetaData(i);
						if(vars!=null && vars.length>0){
							for(VarMetaData var : vars){
								varmetaQ.add(var);
								
								entryCount++;
								if(entryCount >=Utils.NUM_VAR_META){
									break;
								}
							}
						}
					}
				}
				System.out.println("varmetaQ len:"+varmetaQ.size());
			}	
		};
		
		Thread payloadThread=new Thread(){
			public void run(){
				int entryCount=0;
				
				for(int i=0; i<Utils.NUM_META; i++){
					if(entryCount >=Utils.NUM_PAYLOAD){
						break;
					}
					
					if(i%7==1){
						Payload[] payloads = MockData.genPayload(i);
						if(payloads!=null && payloads.length>0){
							for(Payload payload : payloads){
								payloadQ.add(payload);
								
								entryCount++;
								if(entryCount >=Utils.NUM_PAYLOAD){
									break;
								}
							}
						}
					}
				}
				System.out.println("payloadQ len:"+payloadQ.size());
			}	
		};
		
		metaThread.start();
		varmetaThread.start();
		payloadThread.start();
	}
	
}
