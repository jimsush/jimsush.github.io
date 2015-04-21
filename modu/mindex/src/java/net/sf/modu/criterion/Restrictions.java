
package net.sf.modu.criterion;

import net.sf.modu.mindex.Op;

public class Restrictions {
	
	public static SimpleExpression gt(String propertyName, Object value) {
		return new SimpleExpression(propertyName,Op.GT, value);
	}
	
	public static SimpleExpression ge(String propertyName, Object value) {
		return new SimpleExpression(propertyName,Op.GE, value);
	}
	
	public static SimpleExpression eq(String propertyName, Object value) {
		return new SimpleExpression(propertyName,Op.EQ, value);
	}
	
	public static SimpleExpression ne(String propertyName, Object value) {
		return new SimpleExpression(propertyName,Op.NE, value);
	}
	
	public static SimpleExpression lt(String propertyName, Object value) {
		return new SimpleExpression(propertyName,Op.LT, value);
	}
	
	public static SimpleExpression le(String propertyName, Object value) {
		return new SimpleExpression(propertyName,Op.LE, value);
	}
	
	public static SimpleExpression in(String propertyName, Object ... value) {
		throw new UnsupportedOperationException();
	}
	
	public static SimpleExpression like(String propertyName, Object value) {
		throw new UnsupportedOperationException();
	}
	
	public static LogicalExpression or(Criterion lhs,Criterion rhs) {
		return new LogicalExpression(lhs,rhs,Op.OR);
	}
	
	public static LogicalExpression and(Criterion lhs,Criterion rhs) {
		return new LogicalExpression(lhs,rhs,Op.AND);
	}
	
}
