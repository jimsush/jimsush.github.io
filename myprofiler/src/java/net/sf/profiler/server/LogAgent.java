package net.sf.profiler.server;

import java.lang.instrument.Instrumentation;

/**
 * instrument agent, dynamic modify byte code at runtime
 * java -javaagent:agent1.jar 
 * Premain-Class: net.sf.profiler.sf.LogAgent
 *
 */
public class LogAgent {
	
	public static void premain(String args, Instrumentation inst){
		TimerCount loader=new TimerCount();
		System.out.println("TimerCount have load,loader="+loader);
		inst.addTransformer(new LogClazzTransformer());
	}

}
