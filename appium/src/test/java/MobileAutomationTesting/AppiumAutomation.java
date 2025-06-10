package MobileAutomationTesting;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppiumAutomation extends BaseClass{
	
	@Test(description="Practice program")
	public void appiumTest01() throws MalformedURLException, URISyntaxException, InterruptedException {
		
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		WebElement preferenceTab=driver.findElement(AppiumBy.accessibilityId("Preference"));
		String text=preferenceTab.getText();
		System.out.println(text);
		preferenceTab.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("3. Preference dependencies"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/widget_frame"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("(//android.widget.RelativeLayout)[2]"))).click();
		
		String alertTitle=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/alertTitle"))).getText();
		Assert.assertEquals(alertTitle, "WiFi settings","Text is not equal");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/edit"))).sendKeys("Narasayya's WiFI");
		driver.findElements(AppiumBy.className("android.widget.Button")).get(1).click();
		
		
		//wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("9. Switch"))).click();
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/checkbox"))).click();
	}
	
	

}
