package net.sf.modu.base.scheduler;

import org.quartz.Scheduler;

/**
 * <p>
 * Title: AbstratSchedulerJob
 * </p>
 * <p>
 * Description:
 * 调度任务抽象类
 * </p>
 * 
 */
public abstract class AbstractSchedulerJob implements SchedulerJob {
    
    private final static String DEFAULT_GROUP = Scheduler.DEFAULT_GROUP;
    
    /** 任务名称 */
    private String jobName;
    
    /** 任务组名称 */
    private String jobGroupName;

    /**
     * 
     * 采用系统自动生成的任务名称、任务组名称的构造函数
     * 
     */
    public AbstractSchedulerJob() {
        this(DEFAULT_GROUP,SchedulerJobNamingEngine.createSchedulerJobName());
    }
    /**
     * 自定义任务名称、任务组名称的构造函数；
     * 在一个调度任务管理器中，任务组名称必须是唯一的，任务名称在任务组重也必须是唯一的
     * @param jobName
     *        调度任务任务名称
     * @param groupName
     *        调度任务所在的组名称
     */
    public AbstractSchedulerJob(String jobName,String groupName) {
        super();
        if(jobName==null||groupName==null)
            throw new NullPointerException("jobName or jobGroupName is null");
        this.jobGroupName=groupName;
        this.jobName=jobName;
    }
    /**
     * 
     * @see com.yuep.base.scheduler.SchedulerJob#init()
     */
    public void init() {

    }
    /**
     * 
     * @see com.yuep.base.scheduler.SchedulerJob#destory()
     */
    public void destory(){
        
    }
    /**
     * 
     * @see com.yuep.base.scheduler.SchedulerJob#getJobGroupName()
     */
    @Override
    public String getJobGroupName() {
        return jobGroupName;
    }
    /**
     * 
     * @see com.yuep.base.scheduler.SchedulerJob#getJobName()
     */
    @Override
    public String getJobName() {
        return jobName;
    }
    /**
     * 
     * @see com.yuep.base.scheduler.SchedulerJob#needFinished()
     */
    @Override
    public boolean needFinished(){
        return false;
    }
    /**
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString(){
        return jobGroupName+":"+jobName;
    }
    
   

}
