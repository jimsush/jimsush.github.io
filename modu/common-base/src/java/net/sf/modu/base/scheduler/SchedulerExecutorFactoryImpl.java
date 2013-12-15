package net.sf.modu.base.scheduler;



/**
 * <p>
 * Title: SchedulerExecutorFactoryImpl
 * </p>
 * <p>
 * Description:
 * <br>���ȹ����������ӿ�<code>SchedulerExecutorFactory<code>ʵ����
 * </p>
 * 
 * @author yangtao
 * created 2010-11-10 ����09:37:31
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
