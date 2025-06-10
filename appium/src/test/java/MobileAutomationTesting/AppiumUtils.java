package MobileAutomationTesting;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;

public class AppiumUtils {
	
	public static void longClickGestureAction(WebElement element, WebDriver driver) {
		
		((JavascriptExecutor)driver).executeScript("mobile: longClickGesture", ImmutableMap.of(
				"elementId", ((RemoteWebElement)(element)).getId(),
				"duration",2000
				));
	}
	
	public static void swapGesture(WebElement element,WebDriver driver,String direction) {
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
			    "elementId",((RemoteWebElement)element).getId(), 
			    "direction", direction,
			    "percent", 0.75
			));
	}
	
	public static void dragGesture(WebElement element, WebDriver driver, int x, int y) {
		//WebElement sourceElement=driver.findElement(AppiumBy.xpath("(//android.view.View)[1]"));
		
		
		((JavascriptExecutor)driver).executeScript("mobile: dragGesture",ImmutableMap.of(
				"elementId",((RemoteWebElement)element).getId(),
				"endX" ,x,
				"endY", y
				
				));
	}

}
