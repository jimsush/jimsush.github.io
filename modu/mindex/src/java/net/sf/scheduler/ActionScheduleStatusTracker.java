package net.sf.scheduler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ActionScheduleStatusTracker {
	
	private Map<String, ActionScheduleCounter> scheduleCounters=new ConcurrentHashMap<String, ActionScheduleCounter>();
	private Map<Long, Set<String>> actionScheulders=new ConcurrentHashMap<Long, Set<String>>();
	
	private static final int MAX_CACHE_SIZE=500000;
	//private static Logger;
	
	public synchronized void createSchedule(long actionId, String key, long createdTime, long expectedRuntime, int initedStatus){
		if(scheduleCounters.size()>MAX_CACHE_SIZE){
			System.out.println("it is exceeded buffered queue");
			return;
		}
		
		long now=System.currentTimeMillis();
		ActionScheduleCounter counter=new ActionScheduleCounter(key, createdTime);
		counter.setExpectedScheduleTime(expectedRuntime);
		counter.setInitedTime(now);
		if(initedStatus==ActionSchedule.READY){
			counter.setReadyTime(now);
		}
		scheduleCounters.put(key, counter);
		
		Set<String> schedules = actionScheulders.get(actionId);
		if(schedules==null){
			schedules=new HashSet<String>();
			actionScheulders.put(actionId, schedules);
		}
		schedules.add(key);
	}
	
	public synchronized void removeSchedule(long actionId, String key){
		scheduleCounters.remove(key);
		Set<String> schedules = actionScheulders.get(actionId);
		if(schedules!=null){
			schedules.remove(key);
		}
	}
	
	public synchronized void removeAction(long actionId){
		Set<String> schedules = actionScheulders.get(actionId);
		if(schedules!=null){
			for(String scheduleKey : schedules){
				scheduleCounters.remove(scheduleKey);
			}
		}
		
		actionScheulders.remove(actionId);
	}
	
	public synchronized void changeStatus(long actionId, String key, int status){
		ActionScheduleCounter counter = scheduleCounters.get(key);
		if(counter==null){
			System.out.println("lookup schedule "+key+" failed!");
			return;
		}
		
		long now=System.currentTimeMillis();
		switch(status){
		case ActionSchedule.READY:
			counter.setReadyTime(now);
			System.out.println(counter.getReadyOutputInfo());
			break;
		case ActionSchedule.RUNNING:
			counter.setRunningTime(now);
			System.out.println(counter.getRunningOutputInfo());
			break;
		case ActionSchedule.SUCCESS:
		case ActionSchedule.FAILED:
			counter.setCompletedTime(now);
			System.out.println(counter.getCompleteOutputInfo());
			this.removeSchedule(actionId, key);
			break;
		default:
			break;
		}
	}

}
