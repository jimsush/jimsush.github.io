package net.sf.profiler.server;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * 
 * modify every method call byte code, add log function when enter the method
 *
 */
public class LogClazzTransformer implements ClassFileTransformer {

	private RuleMatcher classMatcher;
	private RuleMatcher methodMatcher;
	private String timerCountClassPath;
	
	public LogClazzTransformer(){
		timerCountClassPath=TimerCount.class.getName().replace(".", "/");//avoid hard code
		
		// parse user defined match rules
		String ruleExpress=System.getProperty("class.match");
		if(ruleExpress!=null && ruleExpress.length()>0)
			classMatcher=new RuleMatcher("class", ruleExpress);
		
		ruleExpress=System.getProperty("method.match");
		if(ruleExpress!=null && ruleExpress.length()>0)
			methodMatcher=new RuleMatcher("method", ruleExpress);
	}
	
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		if (loader != ClassLoader.getSystemClassLoader()) {
			return classfileBuffer;
		}
		
		// can't profile yourself, avoid stack over flow
		if(className.startsWith(timerCountClassPath)){ //"net/sf/profiler/server/TimerCount"
            return classfileBuffer;
        }	
		
		if(className.startsWith("net/sf/profiler/")){ //ignore self
            return classfileBuffer;
        }	
		
		// if not match rule,then not modify the byte codes
		if(classMatcher!=null){
			if(!classMatcher.match(className))
				return classfileBuffer;
		}
		
		if(className.indexOf("$")!=-1){ //ignore inner class
			return classfileBuffer;
		}

		// wave byte code
		byte[] result = classfileBuffer;
		try{
			ClassReader reader = new ClassReader(classfileBuffer);
			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS); //must be COMPUTE_MAXS,otherwise VerifyException, stack size too large
			ClassAdapter adapter = new PerfClassAdapter(writer, className, methodMatcher); 
			reader.accept(adapter, 0);
			result = writer.toByteArray();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}

}
