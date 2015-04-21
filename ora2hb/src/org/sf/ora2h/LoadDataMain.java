package org.sf.ora2h;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.sf.ora2h.model.MetaData;
import org.sf.ora2h.model.Payload;
import org.sf.ora2h.model.VarMetaData;
import org.sf.ora2h.util.Utils;

public class LoadDataMain {

	public static void main(String[] args){
		
		BlockingQueue<MetaData> metaQ=new LinkedBlockingQueue<MetaData>();
		BlockingQueue<VarMetaData> varmetaQ=new LinkedBlockingQueue<VarMetaData>();
		BlockingQueue<Payload> payloadQ=new LinkedBlockingQueue<Payload>();
		
		DBQueryExecutor queryExecutor=new DBQueryExecutor(metaQ,varmetaQ,payloadQ);
		Dispatcher dispatcher=new Dispatcher(metaQ,varmetaQ,payloadQ);
		dispatcher.setMaxLenOfMeta(Utils.NUM_META);
		dispatcher.setMaxLenOfPayload(Utils.NUM_PAYLOAD);
		dispatcher.setMaxLenOfVarMeta(Utils.NUM_VAR_META);
		dispatcher.start();
		queryExecutor.start();
	}
	
}
