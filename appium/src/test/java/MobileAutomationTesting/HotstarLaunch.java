package MobileAutomationTesting;

import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import static AppsTesting.PlayerCommends.*;
import static AppsTesting.AdbCommendsClass.*;
import static Utilities.ExcelDataWrite.*;
import static Pages.LauncherScreen.*;
public class HotstarLaunch extends BaseClass {
	
	@Test
	public void hotstarTest() throws InterruptedException {
//		Activity activity=new Activity("in.startv.hotstar","com.hotstar.MainActivity");
//		((JavascriptExecutor)driver).executeScript("mobile: startActivity", ImmutableMap.of("intent","in.startv.hotstar/com.hotstar.MainActivity"));
		String appName="Hotstar";
		String contentName="RRR";
		connectStb("192.168.1.16");
		String[] s=new String[5];
		root();
		remount();
		launchApp(appName);
		
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(15));
		WebElement profile=wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.widget.ImageView[@resource-id='in.startv.hotstar:id/iv_profile']")));
		profile.click();
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
		
		Thread.sleep(3000);
		driver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
		
		WebElement search=wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@resource-id='in.startv.hotstar:id/tv_title' and @text='Search']")));
		search.click();
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
		
		WebElement searchBox=wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.widget.EditText[@resource-id='in.startv.hotstar:id/search_bar']")));
		searchBox.click();
		searchBox.sendKeys(contentName);
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		
		WebElement searchResult=wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.widget.ImageView[@resource-id='in.startv.hotstar:id/hero_img']")));
		searchResult.click();
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
		Thread.sleep(2000);
		
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
		
		Thread.sleep(5000);
//		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
//		WebElement title=wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@resource-id='in.startv.hotstar:id/tv_title' and @text='RRR']")));
//		String titleName=title.getText();
//		System.out.println(titleName);
		
		
		s[0]=appName;
		s[1]=contentName;
		s[2]=getAudioOutput();
		s[3]=getVisionOutput();
		s[4]=getVideoResolution();
		excelWrite(s);
		
		
	}

}
