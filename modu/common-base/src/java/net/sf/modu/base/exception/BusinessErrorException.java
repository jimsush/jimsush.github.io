
package net.sf.modu.base.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Title: BusinessErrorException
 * </p>
 * <p>
 * Description: 业务错误异常(checked exception)
 * </p>
 * 
 * @author sufeng
 * created 2010-10-14 下午05:18:06
 * modified [who date description]
 * check [who date description]
 */
public class BusinessErrorException extends Exception {

    private static final long serialVersionUID = -474826950605994262L;

    /**
     * 业务处理结果栈
     */
    private List<BusinessResult> resultStack=new ArrayList<BusinessResult>();

    /**
     * 业务处理结果栈
     * @return
     */
    public List<BusinessResult> getResultStack() {
        return resultStack;
    }
    
    /**
     * 增加一项业务处理结果
     * @param obj 结果
     */
    public void addResult(BusinessResult obj){
        resultStack.add(obj);
    }

}
