package net.sf.modu.mindex;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class IndexUtil {

	public static Object getAttribute(Object entry,String fieldName){
		if(entry instanceof ValueGetter){
			return ((ValueGetter)entry).getValue(fieldName);
		}else{
			// reflect
			Object obj=null;
			try{
				Field field = entry.getClass().getField(fieldName); //declaredField
				obj=field.get(entry);
			}catch(Exception ex){
				ex.printStackTrace();
				return null;
			}
			return obj;
		}
	}
	
	public static boolean isEquals(Object oldObj, Object newObj){
		if(oldObj!=null && newObj!=null){
			return oldObj.equals(newObj);
		}else if(oldObj!=null){
			return false;
		}else if(oldObj==null && newObj!=null){
			return false;
		}else {
			return true; // both null
		}
	}
	
	public static boolean isGreat(Object first, Object second){
		if(first instanceof Integer){
			return (Integer)first>(Integer)second;
		}else if(first instanceof Long){
			return (Long)first>(Long)second;
		}else if(first instanceof Short){
			return (Short)first>(Short)second;
		}else if(first instanceof Byte){
			return (Byte)first>(Byte)second;
		}else if(first instanceof Float){
			return (Float)first>(Float)second;
		}else if(first instanceof Double){
			return (Double)first>(Double)second;
		}else if(first instanceof Byte){
			return (Byte)first>(Byte)second;
		}else if(first instanceof Comparable){
			return ((Comparable)first).compareTo(second)>0;
		}
		throw new UnsupportedOperationException("> for "+first.getClass().getSimpleName());
	}
	
	public static boolean isGreatEquals(Object first, Object second){
		if(first instanceof Integer){
			return (Integer)first>=(Integer)second;
		}else if(first instanceof Long){
			return (Long)first>=(Long)second;
		}else if(first instanceof Short){
			return (Short)first>=(Short)second;
		}else if(first instanceof Byte){
			return (Byte)first>=(Byte)second;
		}else if(first instanceof Float){
			return (Float)first>=(Float)second;
		}else if(first instanceof Double){
			return (Double)first>=(Double)second;
		}else if(first instanceof Byte){
			return (Byte)first>=(Byte)second;
		}else if(first instanceof Comparable){
			return ((Comparable)first).compareTo(second)>=0;
		}
		throw new UnsupportedOperationException(">= for "+first.getClass().getSimpleName());
	}
	
	public static boolean isLess(Object first, Object second){
		if(first instanceof Integer){
			return (Integer)first<(Integer)second;
		}else if(first instanceof Long){
			return (Long)first<(Long)second;
		}else if(first instanceof Short){
			return (Short)first<(Short)second;
		}else if(first instanceof Byte){
			return (Byte)first<(Byte)second;
		}else if(first instanceof Float){
			return (Float)first<(Float)second;
		}else if(first instanceof Double){
			return (Double)first<(Double)second;
		}else if(first instanceof Byte){
			return (Byte)first<(Byte)second;
		}else if(first instanceof Comparable){
			return ((Comparable)first).compareTo(second)<0;
		}
		throw new UnsupportedOperationException("< for "+first.getClass().getSimpleName());
	}
	
	public static boolean isLessEquals(Object first, Object second){
		if(first instanceof Integer){
			return (Integer)first<=(Integer)second;
		}else if(first instanceof Long){
			return (Long)first<=(Long)second;
		}else if(first instanceof Short){
			return (Short)first<=(Short)second;
		}else if(first instanceof Byte){
			return (Byte)first<=(Byte)second;
		}else if(first instanceof Float){
			return (Float)first<=(Float)second;
		}else if(first instanceof Double){
			return (Double)first<=(Double)second;
		}else if(first instanceof Byte){
			return (Byte)first<=(Byte)second;
		}else if(first instanceof Comparable){
			return ((Comparable)first).compareTo(second)<=0;
		}
		throw new UnsupportedOperationException("<= for "+first.getClass().getSimpleName());
	}

	public static Set unionSet(Set part1, Set part2){
		Set resultSet=new HashSet();
		resultSet.addAll(part1);
		resultSet.addAll(part2);
		return resultSet;
	}
	
	public static Set joinSet(Set part1, Set part2){
		Set resultSet=new HashSet();
		Set smallSet=null;
		Set bigSet=null;
		if(part1.size()>part2.size()){
			smallSet=part2;
			bigSet=part1;
		}else{
			smallSet=part1;
			bigSet=part2;
		}
		for(Object obj:smallSet){
			if(bigSet.contains(obj)){
				resultSet.add(obj);
			}
		}
		return resultSet;
	}
	
	
}
