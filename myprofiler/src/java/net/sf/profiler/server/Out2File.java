package net.sf.profiler.server;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

/**
 * write log into file
 *
 */
public class Out2File {

    private PrintWriter writer=null;
    
    public void open(String filePath){
        try{
            File file=new File(filePath);
            if(!file.exists()){
                //file.setWritable(true);
                file.createNewFile();
            }
            writer=new PrintWriter(file);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    
    public void close(){
        if(writer!=null){
            writer.close();
            writer=null;
        }
    }
    
    private Object monitor=new Object();
    
    public void printlns(List<Object> contents){
    	if(contents==null || contents.size()==0){
    		return;
    	}
    	synchronized (monitor) {
	    	for(Object obj:contents){
	    		printlnWithoutFlush(obj);
	    	}
	    	flush();
    	}
    }
    
    private void printlnWithoutFlush(Object content){
    	writer.println(content);
    }
    private void flush(){
    	writer.flush();
    }
    
	@SuppressWarnings("deprecation")
	public String printlnWithDate(Object content){
        Date date = new Date(System.currentTimeMillis());
        String info=date.toLocaleString()+","+content;
        synchronized (monitor){ 
        	printlnWithoutFlush(info);
        	flush();
        }
        return info;
    }
    
    public void println(Object content){
        synchronized (monitor){ 
        	printlnWithoutFlush(content);
        	flush();
        }
    }
    
    
    /*
    public String printlnWithDateAndStdOut(Object content){
        Date date = new Date(System.currentTimeMillis());
        String info=date.toLocaleString()+","+content;
        println(info);
        System.out.println(info);
        return info;
    }
    */
    
    
}
