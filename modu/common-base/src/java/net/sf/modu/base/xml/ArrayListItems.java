package net.sf.modu.base.xml;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Title: ArrayListItems
 * </p>
 * <p>
 * Description: Ϊ��ʹ��castor�ļ��ϣ���list���а�װ
 * </p>
 * 
 */
public class ArrayListItems<T> implements Serializable{
    
    private static final long serialVersionUID = -5107393535117241810L;
    
    /**
     * ������item
     */
    private List<T> items=null;
    
    public List<T> getItems(){
        return this.items;
    }
    
    public void setItems(List<T> items){
        this.items=items;
    }
    
}
