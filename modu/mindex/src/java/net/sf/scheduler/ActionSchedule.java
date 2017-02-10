package net.sf.scheduler;

public class ActionSchedule {
	
	public static final int INIT=1;
	public static final int READY=2;
	public static final int RUNNING=3;
	public static final int SUCCESS=4;
	public static final int FAILED=5;
	
	
	private long actionId;
	private String nominalTime;
	
	public long getActionId() {
		return actionId;
	}
	public void setActionId(long actionId) {
		this.actionId = actionId;
	}
	public String getNominalTime() {
		return nominalTime;
	}
	public void setNominalTime(String nominalTime) {
		this.nominalTime = nominalTime;
	}
	
}
