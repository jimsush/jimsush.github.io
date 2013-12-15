package net.sf.modu.base.util.cmd;

/**
 * <p>
 * Title: CmdStreamListener
 * </p>
 * <p>
 * Description: 命令执行输出流的监听器(供在执行cmd进行扩展)
 * </p>
 * 
 * @author sufeng
 * created 2010-11-15 下午12:02:52
 * modified [who date description]
 * check [who date description]
 */
public interface CmdStreamListener {

    /**
     * 执行结果一行一行地输出
     * @param lineMessage
     */
    public void outLine(String type,String lineMessage);
    
    /**
     * 执行结果中有异常
     * @param ex
     */
    public void outException(Exception ex);
    
    
}
