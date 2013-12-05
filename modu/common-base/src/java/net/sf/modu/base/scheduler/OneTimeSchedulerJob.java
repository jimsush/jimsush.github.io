package net.sf.modu.base.scheduler;

import java.util.Date;

import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

/**
 * <p>
 * Title: OneTimeSchedulerJob
 * </p>
 * <p>
 * Description: 执行一次的调度任务
 * </p>
 * 
 * @author yangtao
 * created 2009-3-3 下午12:49:41
 * modified [who date description]
 * check [who date description]
 */
public abstract class OneTimeSchedulerJob extends AbstractSchedulerJob {

    private Date startTime;

    /**
     * @param startTime
     *            任务开始时间，如果startTime为null，从当前时间开始执行
     */
    public OneTimeSchedulerJob(Date startTime) {
        super();
        this.startTime = new Date(startTime.getTime());
    }
    /**
     * 
     * @param jobName
     *         任务名称
     * @param groupName
     *         任务所在任务组
     * @param startTime
     *         任务开始时间，如果startTime为null，从当前时间开始执行
     */
    public OneTimeSchedulerJob(String jobName,String groupName,Date startTime) {
        super(jobName,groupName);
        this.startTime = new Date(startTime.getTime());
    }

    /**
     * 
     * @see com.yuep.base.scheduler.SchedulerJob#getTrigger()
     */
    @Override
    public Trigger getTrigger() {
        Trigger trigger = new SimpleTrigger();
        if (startTime == null)
            trigger.setStartTime(new Date());
        else
            trigger.setStartTime(startTime);
        trigger.setName(this.getJobName());
        trigger.setJobName(this.getJobName());
        trigger.setJobGroup(this.getJobGroupName());
        return trigger;
    }

}
