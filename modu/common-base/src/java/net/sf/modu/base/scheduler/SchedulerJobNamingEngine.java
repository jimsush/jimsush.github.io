package net.sf.modu.base.scheduler;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * <p>
 * Title: SchedulerJobNamingEngine
 * </p>
 * <p>
 * Description:
 * <br>�����������Ʋ����������ڲ������ظ��ĵ�����������
 * </p>
 * 
 * @author yangtao
 * created 2010-11-10 ����01:03:26
 * modified [who date description]
 * check [who date description]
 */
final public class SchedulerJobNamingEngine {

    private static final String DEFAULT_SCHEDULER_JOB = "DEFAULT_SCHEDULER_JOB";

    private static AtomicLong count = new AtomicLong(0);

    /**
     * ����һ��Ψһ�ĵ�����������
     * @return
     *      ������������
     */
    public static String createSchedulerJobName() {
        return DEFAULT_SCHEDULER_JOB + count.getAndIncrement();
    }

}
