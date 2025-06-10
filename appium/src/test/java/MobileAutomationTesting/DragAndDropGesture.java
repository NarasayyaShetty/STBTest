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

public class DragAndDropGesture extends BaseClass{
	
	@Test(description="Drag and Drop Gesture")
	public void swapGesture() throws InterruptedException {
		
		driver.findElement(AppiumBy.accessibilityId("Views")).click();
		driver.findElement(AppiumBy.accessibilityId("Drag and Drop")).click();
		WebElement sourceElement=driver.findElement(AppiumBy.xpath("(//android.view.View)[1]"));
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		
		((JavascriptExecutor)driver).executeScript("mobile: dragGesture",ImmutableMap.of(
				"elementId",((RemoteWebElement)sourceElement).getId(),
				"endX" ,709,
				"endY", 709
				
				));
		
		WebElement textElement=wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.id("io.appium.android.apis:id/drag_result_text")));
		
		Assert.assertEquals(textElement.getText(), "Dropped!","Failed to drop");

	}
	
	

}
