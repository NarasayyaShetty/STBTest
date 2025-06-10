package MobileAutomationTesting;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import static MobileAutomationTesting.AppiumUtils.*;

public class ScrallInToElementAction extends BaseClass{
	
	@Test(description="ScrallInToView Gesture Testing")
	public void scrollInToViewAction() throws InterruptedException {
		//method1: provided by Google team using UIAutomator
		driver.findElement(AppiumBy.accessibilityId("Views")).click();
		
		//WebElement webViewElement=driver.findElement(AppiumBy.accessibilityId("WebView"));
		
		
	//	driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"WebView\"));")).click();
		
		WebElement elem=driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))"+".scrollIntoView(text(\"WebView\"));"));
		elem.click();
		//Thread.sleep(2000);
		//driver.findElement(AppiumBy.accessibilityId("WebView")).click();
		
//		boolean canScrollMore;
//		
//		
//	   canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
//			    "left", 100, "top", 100, "width", 200, "height", 200,
//			    "direction", "down",
//			    "percent", 3.0
//			));
//		
	}
	
	

}
