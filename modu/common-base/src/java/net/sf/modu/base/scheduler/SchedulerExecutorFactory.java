package net.sf.modu.base.scheduler;

/**
 * <p>
 * Title: SchedulerExecutorFactory
 * </p>
 * <p>
 * Description:
 * <br>���ȹ����������ӿ�
 * </p>
 * 
 * @author yangtao
 * created 2010-11-10 ����09:33:52
 * modified [who date description]
 * check [who date description]
 */
public interface SchedulerExecutorFactory {
    
    /**
     * ���������������
     * @return
     *        �������������
     */
    public SchedulerExecutor createSchedulerExecutor();


}
