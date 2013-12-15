package net.sf.modu.base.util.cmd;

/**
 * <p>
 * Title: RunService
 * </p>
 * <p>
 * Description: 启动一个外部服务
 * </p>
 * 
 * @author sufeng
 * created 2010-9-17 下午03:43:10
 * modified [who date description]
 * check [who date description]
 */
public interface RunService {
	/**
	 * 启动service
	 */
	public boolean startService();
	
	/**
	 * 停止service
	 */
	public boolean stopService();
	
	/**
	 * 初始化service的配置
	 */
	public boolean initService();
}
