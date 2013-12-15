package net.sf.modu.base.scheduler;

import net.sf.modu.base.exception.ModuException;


/**
 * <p>
 * Title: SchException
 * </p>
 * <p>
 * Description: 
 * <br>�������ģ���쳣����
 * </p>
 * 
 * @author yangtao
 * created 2009-2-18 ����11:01:15
 * modified [who date description]
 * check [who date description]
 */
final public class SchException extends ModuException {
    
    private static final long serialVersionUID = 2498844824074679236L;

    /** ��ʼ��������������� */
    public static final int INIT_SCHEDULER_JOB = 7000;
    /** ������������ */
    public static final int START_SCHEDULER_JOB = 7001;
    /** �رյ������� */
    public static final int SHUTDOWN_SCHEDULER_JOB = 7002;
    /** ��ͣ�������� */
    public static final int PAUSE_SCHEDULER_JOB = 7003;
    /** �ָ��������� */
    public static final int RESUME_SCHEDULER_JOB = 7004;
    /** ����������� */
    public static final int RESET_SCHEDULER_JOB = 7005;
    /** general���� */
    public static final int COMMON_SCHEDULER_JOB=7006;

    public SchException(int errorCode, String... source) {
        super(errorCode, source);
    }

    public SchException(int errorCode, Throwable th, String... source) {
        super(errorCode, th, source);
    }
    
    public SchException(Throwable th) {
        super(th);
    }

}
