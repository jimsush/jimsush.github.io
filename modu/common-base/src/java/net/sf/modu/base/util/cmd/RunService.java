package net.sf.modu.base.util.cmd;

/**
 * <p>
 * Title: RunService
 * </p>
 * <p>
 * Description: ����һ���ⲿ����
 * </p>
 * 
 * @author sufeng
 * created 2010-9-17 ����03:43:10
 * modified [who date description]
 * check [who date description]
 */
public interface RunService {
	/**
	 * ����service
	 */
	public boolean startService();
	
	/**
	 * ֹͣservice
	 */
	public boolean stopService();
	
	/**
	 * ��ʼ��service������
	 */
	public boolean initService();
}
