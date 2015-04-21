package org.sf.ora2h.util;

import java.util.ArrayList;
import java.util.List;

import org.sf.ora2h.model.Entry;
import org.sf.ora2h.model.Payload;
import org.sf.ora2h.model.VarMetaData;

public class Utils {
	
	public static int NUM_WRITE_THREAD=10;
	public static int BATCH_SIZE=1000;
	public static long MILLSEC_DELAY=60000L; //60sec
	//public static int LEN_DELAY_Q=100;
	
	public static int NUM_META=100000; //10 million meta
	public static int NUM_VAR_META=5000;
	public static int NUM_PAYLOAD=4000;
	

	public static byte[] decodeBase64(String base64Text){
		return base64Text.getBytes();
	}
	
	public static void write2Hbase(Entry entry){
//		try{
//			Thread.sleep(1);
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
	//	if(entry.getPayload()!=null || entry.getVar()!=null)
			System.out.println(entry);
	}
	
	public static VarMetaData combineMultiVarMeta(List<VarMetaData> vars){
		if(vars==null || vars.size()==0)
			return null;
		
		VarMetaData newVar=new VarMetaData();
		if(vars.size()==1){
			VarMetaData orginalVar=vars.iterator().next();
			newVar.setDspi(orginalVar.getDspi());
			newVar.setContent(orginalVar.getContent());
		}else{
			// at least 2 elements
			int i=0;
			StringBuilder sb=new StringBuilder();
			for(VarMetaData var : vars){
				if(i==0){
					newVar.setDspi(var.getDspi());
				}
				sb.append(var.getContent()+"");
				i++;
			}
			newVar.setContent(sb.toString());
		}
		return newVar;
	}
	
	public static Payload combineMultiPayload(List<Payload> pays){
		if(pays==null || pays.size()==0)
			return null;
		
		Payload newPay=new Payload();
		if(pays.size()==1){
			Payload orginalPay=pays.iterator().next();
			newPay.setDspi(orginalPay.getDspi());
			newPay.setContent(orginalPay.getContent());
		}else{
			// at least 2 elements
			int i=0;
			StringBuilder sb=new StringBuilder();
			for(Payload p : pays){
				if(i==0){
					newPay.setDspi(p.getDspi());
				}
				sb.append(p.getContent()+"");
				i++;
			}
			newPay.setContent(sb.toString());
		}
		return newPay;
	}

	
	@SuppressWarnings("unchecked")
	public static List<Entry>[] splitList2Smalls(List<Entry> entries,int splitNumber){
		List<Entry>[] lists=new List[splitNumber];
		for(int i=0;i<splitNumber;i++){
			lists[i]=new ArrayList<Entry>();
		}
		if(entries==null || entries.size()==0){
			// no element in given list
			return lists;
		}
		
		int pos=0;
		for(Entry entry : entries){
				int slotNo=pos % splitNumber;
				lists[slotNo].add(entry);
				pos++;
		}
		return lists;
	}
	
	
	
}
