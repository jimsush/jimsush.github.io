package net.sf.profiler.server;


public class CallInfo {
	
	public long startTime;
	public int  stackNumber;
	public int stackTraceLen;
	
	public CallInfo(){}
	
	public CallInfo(long startTime,int stackNumber,int stackTraceLen){
		this.startTime=startTime;
		this.stackNumber=stackNumber;
		this.stackTraceLen=stackTraceLen;
	}
	
	@Override
	public String toString() {
		return stackNumber+","+startTime+","+stackTraceLen;
	}

}
