package net.sf.modu.base.util.cmd;

/**
 * <p>
 * Title: CmdStreamListener
 * </p>
 * <p>
 * Description: ����ִ��������ļ�����(����ִ��cmd������չ)
 * </p>
 * 
 * @author sufeng
 * created 2010-11-15 ����12:02:52
 * modified [who date description]
 * check [who date description]
 */
public interface CmdStreamListener {

    /**
     * ִ�н��һ��һ�е����
     * @param lineMessage
     */
    public void outLine(String type,String lineMessage);
    
    /**
     * ִ�н�������쳣
     * @param ex
     */
    public void outException(Exception ex);
    
    
}
