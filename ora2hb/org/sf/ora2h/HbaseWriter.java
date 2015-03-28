package org.sf.ora2h;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.sf.ora2h.model.Entry;
import org.sf.ora2h.util.Utils;

public class HbaseWriter extends Thread{

	private BlockingQueue<List<Entry>> queue=new LinkedBlockingQueue<List<Entry>>();
	
	private volatile boolean isStop=false;
	private String name;
	
	public HbaseWriter(String name){
		this.name=name;
	}
	
	public void pushData(List<Entry> data){
		if(data==null || data.size()==0)
			return;
		
		queue.add(data);
	}
	
	public void run(){
		while(!isStop){
			List<Entry> data = null;
			try{
				data=queue.take();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			if(data==null || data.size()==0)
				continue;
			
			for(Entry entry : data){
				Utils.write2Hbase(entry);
			}
		}
		
		System.out.println("write "+name+" stopped");
	}
	
	
	
}
