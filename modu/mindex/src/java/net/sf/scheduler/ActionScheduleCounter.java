package net.sf.scheduler;

public class ActionScheduleCounter {
	
	private String scheduleKey;
	
	private long createTime;
	private long expectedScheduleTime;
	private long initedTime;
	private long readyTime;
	private long runningTime;
	private long completedTime;
	
	public ActionScheduleCounter(String scheduleKey){
		this.scheduleKey=scheduleKey;
	}
	
	public ActionScheduleCounter(String scheduleKey,long createTime){
		this(scheduleKey);
		this.createTime=createTime;
	}
	
	public String getScheduleKey() {
		return scheduleKey;
	}
	public void setScheduleKey(String scheduleKey) {
		this.scheduleKey = scheduleKey;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getExpectedScheduleTime() {
		return expectedScheduleTime;
	}
	public void setExpectedScheduleTime(long expectedScheduleTime) {
		this.expectedScheduleTime = expectedScheduleTime;
	}
	public long getInitedTime() {
		return initedTime;
	}
	public void setInitedTime(long initedTime) {
		this.initedTime = initedTime;
	}
	
	public long getReadyTime() {
		return readyTime;
	}
	public void setReadyTime(long readyTime) {
		this.readyTime = readyTime;
	}
	public String getReadyOutputInfo(){
		if(this.initedTime<=0){
			return "schedule's status changed: "+scheduleKey+" to READY";
		}else{
			return "schedule's status changed: "+scheduleKey+" to READY,"
				+" spent "+(this.readyTime-this.initedTime)+" ms on waiting pre action schedules";
		}
	}
	
	public long getRunningTime() {
		return runningTime;
	}
	
	public void setRunningTime(long runningTime) {
		this.runningTime = runningTime;
	}

	public String getRunningOutputInfo(){
		if(this.expectedScheduleTime<=0){
			return "schedule's status changed: "+scheduleKey+" to RUNNING";
		}else{
			return "schedule's status changed: "+scheduleKey+" to RUNNING,"
				+" spent "+(this.runningTime-this.expectedScheduleTime)+" ms on waiting scheduling";
		}
	}
	
	public long getCompletedTime() {
		return completedTime;
	}
	public void setCompletedTime(long completedTime) {
		this.completedTime = completedTime;
	}
	
	public String getCompleteOutputInfo(){
		StringBuilder sb=new StringBuilder();
		sb.append("schedule's status changed: "+scheduleKey+" to DONE");
		if(this.initedTime>0 && this.readyTime>0){
			sb.append(", ").append(this.readyTime-this.initedTime).append(" ms on waiting pre action schedules");
		}
		if(this.expectedScheduleTime>0 && this.runningTime>0){
			sb.append(", ").append(this.runningTime-this.expectedScheduleTime).append(" ms on waiting scheduling");
		}
		if(this.runningTime>0 && this.completedTime>0){
			sb.append(", ").append(this.completedTime-this.runningTime).append(" ms on executing");
		}
		return sb.toString();
	}
	
}
