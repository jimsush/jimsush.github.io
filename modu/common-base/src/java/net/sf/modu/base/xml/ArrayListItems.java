package net.sf.modu.base.xml;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Title: ArrayListItems
 * </p>
 * <p>
 * Description: 为了使用castor的集合，对list进行包装
 * </p>
 * 
 */
public class ArrayListItems<T> implements Serializable{
    
    private static final long serialVersionUID = -5107393535117241810L;
    
    /**
     * 包含的item
     */
    private List<T> items=null;
    
    public List<T> getItems(){
        return this.items;
    }
    
    public void setItems(List<T> items){
        this.items=items;
    }
    
}
