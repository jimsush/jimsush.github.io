package net.sf.modu.base.util.cmd.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.modu.base.util.cmd.CmdStreamListener;

/**
 * <p>
 * Title: StreamGobbler
 * </p>
 * <p>
 * Description: ���������
 * </p>
 * 
 * @author sufeng
 * created 2010-9-17 ����03:43:19
 * modified [who date description]
 * check [who date description]
 */
public class StreamGobbler extends Thread{
    
    private CmdStreamListener listener;
    
    InputStream is;
    String type;  //�����������ERROR��OUTPUT
    
    public StreamGobbler(InputStream is, String type, CmdStreamListener listener){
        this.is = is;
        this.type = type;
        this.listener=listener;
    }
    
    public void run(){
        try{
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null){
                System.out.println(type + ">" + line);
                if(listener!=null){
                    listener.outLine(type,line);
                }
                System.out.flush();
            }
        } catch (IOException ioe){
            ioe.printStackTrace(); 
            if(listener!=null){
                listener.outException(ioe);
            }
        }
    }
    
} 

