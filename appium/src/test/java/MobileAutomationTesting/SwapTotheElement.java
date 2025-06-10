package MobileAutomationTesting;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Map;

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

public class SwapTotheElement extends BaseClass{
	
	@Test(description="Swap to the element")
	public void swapGesture() throws InterruptedException {
		driver.findElement(By.xpath("//android.widget.TextView[@content-desc='Views']")).click();
		driver.findElement(By.xpath("//android.widget.TextView[@content-desc='Gallery']")).click();
		driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='1. Photos']")).click();
		
		WebElement firstImage=driver.findElement(By.xpath("(//android.widget.ImageView)[1]"));
		
		Assert.assertEquals(driver.findElement(By.xpath("(//android.widget.ImageView)[1]")).getAttribute("focusable"),"true","First image is focused");
		
		//swipe
		
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
			    "elementId",((RemoteWebElement)firstImage).getId(), 
			    "direction", "left",
			    "percent", 0.50
			));
		
	//	((JavascriptExecutor)driver).executeScript("gesture: swipe", Map.of("elementId", ((RemoteWebElement)firstImage).getId(), "percentage", 75, "direction", "left"));

		Assert.assertEquals(driver.findElement(By.xpath("(//android.widget.ImageView)[1]")).getAttribute("focusable"),"false","First image is focused");

		
		
		
		
		


	}
	
	

}
