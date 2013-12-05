package net.sf.modu.base.scheduler;

/**
 * <p>
 * Title: SchedulerExecutorFactory
 * </p>
 * <p>
 * Description:
 * <br>调度管理器工厂接口
 * </p>
 * 
 * @author yangtao
 * created 2010-11-10 上午09:33:52
 * modified [who date description]
 * check [who date description]
 */
public interface SchedulerExecutorFactory {
    
    /**
     * 创建调度任务管理
     * @return
     *        调度任务管理器
     */
    public SchedulerExecutor createSchedulerExecutor();


}
