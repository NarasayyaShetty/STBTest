package MobileAutomationTesting;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class MissellaneousActivities extends BaseClass{
	
	@Test(description="Missallaneous Activities in application ()", enabled=false)
	public void orintationTest() throws MalformedURLException, URISyntaxException, InterruptedException {
		//Activity and launch the app using package and activity
		Activity activity=new Activity("com.zee5.aosp","com.zee5.android.ui.player.presentation.PlayerActivity");
		//driver.startActivity(activity);
		((JavascriptExecutor)driver).executeScript("mobile: startActivity",ImmutableMap.of("intent","com.zee5.aosp/com.zee5.android.ui.player.presentation.PlayerActivity"));
		
		driver.findElement(AppiumBy.accessibilityId("Views")).click();
		driver.findElement(AppiumBy.accessibilityId("Animation")).click();
		
		//Android key events, like Home, Back etc
		
		
		driver.pressKey(new KeyEvent(AndroidKey.HOME));
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		
		//To rotate device to landscape we need to use DeviceRotation class and rotate method
		DeviceRotation dr=new DeviceRotation(0,0,180);
		driver.rotate(dr);
		
		
		
	}
	
	public void clipBoard() {
		
	}
	
	

}
