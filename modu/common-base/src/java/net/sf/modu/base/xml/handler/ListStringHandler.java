package net.sf.modu.base.xml.handler;

import java.util.ArrayList;
import java.util.List;

import org.exolab.castor.mapping.GeneralizedFieldHandler;

/**
 * 
 * <p>
 * Title: ListStringHandler
 * </p>
 * <p>
 * Description:
 * castorXML文件解析中，ListStringHandler将aaa,bbb,ccc,dddd数据转化为List存放；
 * 相反将List的数据以aaa,bbb,ccc,dddd的形式存放到XML文件
 * </p>
 * 
 */
public class ListStringHandler extends GeneralizedFieldHandler {

	/* (non-Javadoc)
	 * @see org.exolab.castor.mapping.GeneralizedFieldHandler#convertUponGet(java.lang.Object)
	 */
	@Override
	public Object convertUponGet(Object value) {
		return value;
	}

	/* (non-Javadoc)
	 * @see org.exolab.castor.mapping.GeneralizedFieldHandler#convertUponSet(java.lang.Object)
	 */
	@Override
	public Object convertUponSet(Object value) {
		String temp=value.toString();
		String[] array=temp.split(",");
		List<String> list=new ArrayList<String>();
		for(String s:array){
			list.add(s);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see org.exolab.castor.mapping.GeneralizedFieldHandler#getFieldType()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Class getFieldType() {
		return List.class;
	}

}
