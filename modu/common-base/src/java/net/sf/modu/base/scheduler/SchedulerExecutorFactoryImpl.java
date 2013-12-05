package net.sf.modu.base.scheduler;



/**
 * <p>
 * Title: SchedulerExecutorFactoryImpl
 * </p>
 * <p>
 * Description:
 * <br>调度管理器工厂接口<code>SchedulerExecutorFactory<code>实现类
 * </p>
 * 
 * @author yangtao
 * created 2010-11-10 上午09:37:31
 * modified [who date description]
 * check [who date description]
 */
public class SchedulerExecutorFactoryImpl implements SchedulerExecutorFactory {
    
    @Override
    public SchedulerExecutor createSchedulerExecutor() {
        SchedulerExecutor schedulerExecutor= new DefaultSchedulerExecutor();
        schedulerExecutor.start();
        return schedulerExecutor;
    }


}
