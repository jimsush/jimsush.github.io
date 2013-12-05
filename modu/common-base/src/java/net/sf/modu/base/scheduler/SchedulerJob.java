package net.sf.modu.base.scheduler;

import org.quartz.Trigger;

/**
 * <p>
 * Title: ScheduleJob
 * </p>
 * <p>
 * Description: 
 * <br>��������ӿ�
 * </p>
 * 
 * @author yangtao
 * created 2009-2-18 ����09:40:46
 * modified [who date description]
 * check [who date description]
 */
public interface SchedulerJob {

    /**
     * ��ʼ������������Դ
     */
    void init();

    /**
     * ���ٵ���������Դ
     */
    void destory();
    /**
     * ����ִ�з�����,���ݾ�����Ҫʵ��
     */
    void execute();

    /**
     * ��ȡ������ִ�е�Trigger
     * 
     * @return ����Trigger
     */
    Trigger getTrigger();

    /**
     * ��ȡ������������
     * 
     * @return
     *       ������������
     */
    String getJobName();

    /**
     * ��ȡ�������������������
     * 
     * @return
     *     �����������ڵ��������
     */
    String getJobGroupName();
    
    /**
     * ���������Ƿ������ͨ���÷���ʵ���ж����������ҵ���߼�
     * @return
     *     true ���������������رյ�ǰ��������
     *     false:�����������ִ��
     *      
     */
    boolean needFinished();
    
}
