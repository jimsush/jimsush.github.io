package net.sf.profiler.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * filter class and method by regular expression
 *
 */
public class RuleMatcher {

	private Pattern pattern;
	private String matchName;
	
	public RuleMatcher(String filterName,String ruleExpress){
		this.matchName=filterName;
		this.pattern = Pattern.compile(ruleExpress,Pattern.CASE_INSENSITIVE);
	}
	
	/**
	 * 
	 * @param className
	 * @return true ignore false reserve
	 */
	public boolean match(String className){
		Matcher matcher = pattern.matcher(className);
		return matcher.matches();
	}
	
	public String getMatchName() {
		return matchName;
	}
	
}
