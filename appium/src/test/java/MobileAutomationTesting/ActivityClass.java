package MobileAutomationTesting;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.android.Activity;

public class ActivityClass extends BaseClass{
	
	@Test
	public void currentActivity() {
		Activity activity=new Activity("com.jio.stb.screensaver","android.service.dreams.DreamActivity");
		
		((JavascriptExecutor)driver).executeScript("mobile: startActivity", ImmutableMap.of("intent","com.jio.stb.screensaver/android.service.dreams.DreamActivity"));
		
	}

}
