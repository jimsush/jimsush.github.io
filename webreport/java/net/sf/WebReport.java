package net.sf;

import java.io.FileOutputStream;

import org.openqa.selenium.OutputType;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.Timezone;

public class WebReport {
	
	public static void main(String[] args) throws Exception{
		
		
		JBrowserDriver driver = new JBrowserDriver(Settings.builder().
			      timezone(Timezone.ASIA_SHANGHAI).build());

			    // This will block for the page load and any
			    // associated AJAX requests
			    driver.get("http://example.com");

			    // You can get status code unlike other Selenium drivers.
			    // It blocks for AJAX requests and page loads after clicks 
			    // and keyboard events.
			    System.out.println(driver.getStatusCode());

			    // Returns the page source in its current state, including
			    // any DOM updates that occurred after page load
			    System.out.println(driver.getPageSource());

			    try{
			    	Thread.sleep(1000L);
			    }catch(Exception ex){
			    	ex.printStackTrace();
			    }
			    
			    byte[] bytes = driver.getScreenshotAs(OutputType.BYTES);
			    
			    String fileName= (args!=null && args.length>0) ? args[0] : "d:\\tmp\\web.png";
			    FileOutputStream fo=new FileOutputStream(fileName);
			    fo.write(bytes);
			    fo.close();
			    
			    System.out.println("====done====");
			    
			    // Close the browser. Allows this thread to terminate.
			    driver.quit();
	}

}
