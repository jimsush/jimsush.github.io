package net.sf.modu.base.scheduler;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * <p>
 * Title: SchedulerJobNamingEngine
 * </p>
 * <p>
 * Description:
 * <br>调度任务名称产生器，用于产生不重复的调度任务名称
 * </p>
 * 
 * @author yangtao
 * created 2010-11-10 下午01:03:26
 * modified [who date description]
 * check [who date description]
 */
final public class SchedulerJobNamingEngine {

    private static final String DEFAULT_SCHEDULER_JOB = "DEFAULT_SCHEDULER_JOB";

    private static AtomicLong count = new AtomicLong(0);

    /**
     * 生成一个唯一的调度任务名称
     * @return
     *      调度任务名称
     */
    public static String createSchedulerJobName() {
        return DEFAULT_SCHEDULER_JOB + count.getAndIncrement();
    }

}
