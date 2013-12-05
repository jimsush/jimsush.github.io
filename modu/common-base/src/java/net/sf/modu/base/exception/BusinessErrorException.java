
package net.sf.modu.base.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Title: BusinessErrorException
 * </p>
 * <p>
 * Description: ҵ������쳣(checked exception)
 * </p>
 * 
 * @author sufeng
 * created 2010-10-14 ����05:18:06
 * modified [who date description]
 * check [who date description]
 */
public class BusinessErrorException extends Exception {

    private static final long serialVersionUID = -474826950605994262L;

    /**
     * ҵ������ջ
     */
    private List<BusinessResult> resultStack=new ArrayList<BusinessResult>();

    /**
     * ҵ������ջ
     * @return
     */
    public List<BusinessResult> getResultStack() {
        return resultStack;
    }
    
    /**
     * ����һ��ҵ������
     * @param obj ���
     */
    public void addResult(BusinessResult obj){
        resultStack.add(obj);
    }

}
