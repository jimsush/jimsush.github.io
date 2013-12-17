package net.sf.profiler.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * count the time cost of call
 * pay attention to multiple thread and call stack
 *
 */
public class TimerCount {

	// must use thread local for concurrent
	// must use stack for invocation stack
	private static ThreadLocal<Stack<CallInfo>> stackInfo=new ThreadLocal<Stack<CallInfo>>();
	
	// record call current layer
	private static ThreadLocal<Integer> layer=new ThreadLocal<Integer>();
	
	private static ThreadLocal<Integer> topMethodStackTraceLength=new ThreadLocal<Integer>();
	
	private static Out2File logger;
	
	public volatile static boolean startStatus=false;

	private static Set<String> topMethods=new HashSet<String>();
	
	// if cost>costThreshold,then write to log
	private static long costThreshold=1;
	
	static {
		// open logger file
		logger=new Out2File();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmSS");
		String agentLogDir = System.getProperty("agentlog.dir");
		if(agentLogDir==null || agentLogDir.length()==0){
			agentLogDir="log/";
		}else{
			if(agentLogDir.endsWith("/")){
				agentLogDir=agentLogDir.substring(0, agentLogDir.length()-1);
			}
		}
		String fileName=agentLogDir+"/"+dateFormat.format(new Date())+".log";
		System.out.println("will open file:"+fileName);
		logger.open(fileName);
		logger.printlnWithDate("open file done.");
		
		// get top layer method list
		String topMethod = System.getProperty("top.method");
		if(topMethod==null || topMethod.length()==0){
			topMethods.add("ClassA.hello");
			topMethods.add("ClassA.world");
		}else{
			String[] split = topMethod.split(",");
			if(split==null || split.length==0){
				topMethods.add("ClassA.hello");
				topMethods.add("ClassA.world");
			}
			for(String sp : split){
				topMethods.add(sp);
			}
		}
		if(topMethods.size()==0){
			throw new RuntimeException("top.method size=0,please set -Dtop.method=ClassA.method,ClassB.method");
		}else{
			int i=0;
			for(String topM : topMethods){
				System.out.println("topMethods["+i+"]="+topM);
				i++;
			}
		}
			
		// cost mill second
		String cost = System.getProperty("cost.threshold");
		if(cost!=null && cost.length()>0){
			long costv=-1;
			try{
				costv=Long.valueOf(cost);
			}catch(Exception ex){
				costv=-1;
			}
			if(costv>=0)
				costThreshold=costv;
		}
		System.out.println("costThreshold="+costThreshold);
		
		// start admin thread
		InnerSocketThread thread=new InnerSocketThread();
		thread.start();
	}
	
	/**
	 * close logger file
	 */
	public static void cleanup(){
		if(logger!=null)
			logger.close();
	}

	/**
	 * start time count
	 * @param invocation name of class, method
	 */
	public static void start(String invocation){
		StackTraceElement[] stackTrace = new Throwable().getStackTrace();
		if(invocation.equals("net/sf/somep/ClassA.publish")){
			
		}
		boolean isFirstLayer=false;
		for(String topMethod: topMethods){
			if(invocation.endsWith(topMethod)){
				isFirstLayer=true;
				topMethodStackTraceLength.set(stackTrace.length);
				break;
			}
		}
		
		Integer number = layer.get();
		if(isFirstLayer){
			if(number==null){
				layer.set(Integer.valueOf(1));
			}else{
				// recursive
				layer.set(Integer.valueOf(number.intValue()+1));
			}
		}else{
			if(number==null){
				// ignore
				return;
			}else{
				layer.set(Integer.valueOf(number.intValue()+1));
			}
		}
		
		long t=System.currentTimeMillis();
		
		//need to consider such situation as 'a call b,b call c,c call d'
		Stack<CallInfo> stack = stackInfo.get();
		if(stack==null){
			stack=new Stack<CallInfo>();
			stackInfo.set(stack);
		}

		CallInfo callInfo=new CallInfo(t,layer.get().intValue(),stackTrace.length);
		stack.push(callInfo);
			
	}
	
	/**
	 * stop time count
	 * <p>if execution throw exception, it is not consider now
	 * @param invocation name of class, method
	 */
	public static void stop(String invocation){
		Integer number = layer.get();
		if(number==null)
			return;

		Stack<CallInfo> stack = stackInfo.get();
		CallInfo callInfo=stack.pop();
		if(stack.size()==0){
			stackInfo.remove();
		}
		
		long t=System.currentTimeMillis();
		Long tid = Thread.currentThread().getId();
		
		String info=getInfo(t,tid,callInfo,invocation);
		if(callInfo.stackNumber==1){
			// top layer call end,will output and clean
			Map<Integer,String> map = threadOutputInfo.get(tid);
			if(map==null){
				if(TimerCount.startStatus){
					logger.println(info);
				}
			}else{
				if(TimerCount.startStatus){
					map.put(1,info);
					// sort output
					List<Integer> keys=new ArrayList<Integer>();
					keys.addAll(map.keySet());
					Collections.sort(keys);
					List sortedOutputInfos=new ArrayList();
					for(Integer key:keys){
						String content = map.get(key);
						if(content!=null)
							sortedOutputInfos.add(content);
					}
					// output all stack
					logger.printlns(sortedOutputInfos);
				}
				
				// clean
				threadOutputInfo.remove(tid);
				map.clear();
			}
			// clean
			layer.remove();
			topMethodStackTraceLength.remove();
		}else{
			// sub stack,push into map for storage
			if(info!=null && TimerCount.startStatus){
				Map<Integer,String> map = threadOutputInfo.get(tid);
				if(map==null){
					map=new HashMap<Integer,String>();
					threadOutputInfo.put(tid, map);
				}
				map.put(callInfo.stackNumber,info);
			}
		}
	}
	
	private static Map<Long,Map<Integer,String>> threadOutputInfo=new ConcurrentHashMap<Long,Map<Integer,String>>(); 
	
	public static String getInfo(long t,Long tid,CallInfo callInfo,String invocation){
		if(invocation.endsWith("<init>"))
			return null;
		if(invocation.endsWith("<clinit>"))
			return null;
		
		long cost=t-callInfo.startTime;
		if(callInfo.stackNumber==1 || cost>=costThreshold){
			// top layer always output
			StringBuilder sb=new StringBuilder();
			Integer size = topMethodStackTraceLength.get();
			if(size!=null){
				int tabKeyNum=callInfo.stackTraceLen-size;
				for(int i=0;i<tabKeyNum;i++){
					sb.append("  ");
				}
			}
			sb.append(invocation).append(" [tid=").append(tid).append(",id=").append(callInfo.stackNumber).append(",cost=").append(cost).append("ms]");
			return sb.toString();
		}
		return null;
	}
	
	public static void main(String[] args) {
		Out2File logger2=new Out2File();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmSS");
		String fileName=dateFormat.format(new Date())+".log";
		logger2.open(fileName);
		logger2.close();
	}

}
