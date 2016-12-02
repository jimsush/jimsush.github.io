eclipse debugger can't stop at breakpoints because the compiler does not generate some debugged information:

need to change file .settings/org.eclipse.jdt.core.prefs with below settings:

* org.eclipse.jdt.core.compiler.debug.lineNumber=generate

* org.eclipse.jdt.core.compiler.debug.localVariable=generate

* org.eclipse.jdt.core.compiler.debug.sourceFile=generate
