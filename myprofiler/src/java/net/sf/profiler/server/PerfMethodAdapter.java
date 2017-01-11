package net.sf.profiler.server;

import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;

/**
 * modify method byte code
 *
 */
public class PerfMethodAdapter extends MethodAdapter {
	private String _className, _methodName;
	private String timerCountClassPath;
	
	public PerfMethodAdapter(MethodVisitor visitor, 
			String className,
			String methodName) { 
		super(visitor);
		_className = className;
		_methodName = methodName;
		timerCountClassPath=TimerCount.class.getName().replace(".", "/");
	}

	public void visitCode() {
		// if enter method, then call TimerCount.start() firstly
		this.visitLdcInsn(_className+"."+_methodName); //param1²ÎÊý
		this.visitMethodInsn(INVOKESTATIC, 
				timerCountClassPath, // "net/sf/profiler/server/TimerCount", 
				"start", 
				"(Ljava/lang/String;)V");
		super.visitCode();
	}

	public void visitInsn(int inst) {
		// execute TimerCount.stop() before return
		switch (inst) {
		case Opcodes.ARETURN:
		case Opcodes.DRETURN:
		case Opcodes.FRETURN:
		case Opcodes.IRETURN:
		case Opcodes.LRETURN:
		case Opcodes.RETURN:
		case Opcodes.ATHROW:
			this.visitLdcInsn(_className+"."+_methodName); //param1
			this.visitMethodInsn(INVOKESTATIC, 
					timerCountClassPath, // "net/sf/profiler/server/TimerCount", 
					"stop", 
					"(Ljava/lang/String;)V");
			break;
		default:
			break;
		}
		super.visitInsn(inst);
	}
	
}

