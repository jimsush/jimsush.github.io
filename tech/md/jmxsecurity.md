* JVM parameter 
-Dcom.sun.management.jmxremote.port=9999 -Dcom.sun.management.jmxremote.ssl=false -Djava.security.auth.login.config=jaas.conf -Dcom.sun.management.jmxremote.login.config=Sample -Dcom.sun.management.jmxremote.access.file=access.txt

* jaas.conf 
`Sample{
    net.sf.SSOLogin required;
};`

* access.txt 
`test1 readwrite`


* SSOLogin.java 
`import java.security.Principal;
import java.util.Map;
import java.util.Properties;

import javax.management.remote.JMXPrincipal;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class SSOLogin implements LoginModule{

	private CallbackHandler callbackHandler;
	private Subject subject;
	private Principal principal;
	private boolean isAuthenticated = false;

	public SSOLogin(){
		System.out.println("SSOLogin");
	}
	
	@Override
	public boolean abort() throws LoginException {
		System.out.println("abort");
		return true;
	}

	@Override
	public boolean commit() throws LoginException {
		System.out.println("commit");
		if(isAuthenticated){
			subject.getPrincipals().add(principal);
		}else{
			throw new LoginException("Authentication failure");
		}
		return true;
	}

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject=subject;
		this.callbackHandler=callbackHandler;
		System.out.println("initialize");
	}

	@Override
	public boolean login() throws LoginException {
		NameCallback nameCallback = new NameCallback("username");
		PasswordCallback passwordCallback = new PasswordCallback("password",false);
		final Callback[] calls = new Callback[] { nameCallback, passwordCallback };

		try {
			this.callbackHandler.handle(calls);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String password=String.valueOf(passwordCallback.getPassword());
		System.out.println("login: user name:" + nameCallback.getName());
		
		principal = new JMXPrincipal(nameCallback.getName());

		// TODO call SSO API
		isAuthenticated=true;

		return isAuthenticated;
	}

	@Override
	public boolean logout() throws LoginException {
		System.out.println("logout");
		subject.getPrincipals().remove(principal);
		principal = null;
		return true;
	}

}`

