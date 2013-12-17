package net.sf.profiler.server;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;


/**
 * modify class byte code
 *
 */
public class PerfClassAdapter extends ClassAdapter {
	
	private String className;
	private RuleMatcher methodMatcher;
	
	public PerfClassAdapter(ClassVisitor visitor, String theClass,RuleMatcher methodMatcher) {
		super(visitor);
		this.className = theClass;
		this.methodMatcher=methodMatcher;
	}
	
	public MethodVisitor visitMethod(int arg,
			String name,
			String descriptor,
			String signature,
			String[] exceptions) {
		MethodVisitor mv = super.visitMethod(arg, 
				name, 
				descriptor, 
				signature, 
				exceptions);
		
		// match method rule, else return old byte code
		if(methodMatcher!=null){
			if(!methodMatcher.match(name))
				return mv;
		}
		
		MethodAdapter ma = new PerfMethodAdapter(mv, className, name);
		return ma;
	}
	
}
