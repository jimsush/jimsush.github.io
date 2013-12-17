package net.sf.modu.mindex;

import java.util.HashMap;
import java.util.Map;


public class GenericModel implements ValueGetter {

	private Map<String,Object> datas=new HashMap<String,Object>();

	@Override
	public Object getValue(String fieldName) {
		return datas.get(fieldName);
	}
	
}
