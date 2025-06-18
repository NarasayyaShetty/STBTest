package Tests;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static Pages.LauncherScreen.*;
import static Utilities.ExcelDataWrite.excelWrite;

import java.time.Duration;
import java.util.List;

import static AppsTesting.AdbCommendsClass.*;
import static AppsTesting.PlayerCommends.getAudioOutput;
import static AppsTesting.PlayerCommends.getVideoResolution;
import static AppsTesting.PlayerCommends.getVisionOutput;

import Pages.JioHotstar;
import Utilities.DataProviderClass;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;



public class HotstarPlayerScreenOutputs extends BaseTest {
	JioHotstar js;
	String[] s;
	String appName="JioHotstar";
	String deviceNameandVersion;
	String appVersionName;
	boolean status;
	// , dataProvider="hotstarContentName",dataProviderClass=DataProviderClass.class
	@BeforeClass(alwaysRun=true)
	public void setUp() {
		try {
			
			//currentFocus(driver);
			s = new String[6];
			deviceNameandVersion = deviceName();
			appVersionName = printAppNameAndVersion(appName);
			js = new JioHotstar(driver);
			status=launchApp(appName);
			Assert.assertTrue(status,"App launch is failed");
			js.clickOnProfile();
			js.selectMenuOption("Search");
		} catch (Exception e) {
			System.out.println("Exception occureed");
			e.printStackTrace();
		}
	}

	@Test(description = "Playing multiple contents", dataProvider = "hotstarContentName", dataProviderClass = DataProviderClass.class,priority=1,enabled=true)
	public void jioHotstarPlayreResult(String contentName) {
		try {
			status=js.sendkeysOnSearchField(contentName);
			Assert.assertTrue(status,"Unable to pass the content name on search field");
			status=js.selectSearchedContent(contentName);
			Assert.assertTrue(status,"Unable to select the searched content from search section");
			s[0] = deviceNameandVersion;
			s[1] = appVersionName;
			s[2] = contentName;
			s[3] = getAudioOutput();
			s[4] = getVisionOutput();
			s[5] = getVideoResolution();
			excelWrite(s);
			status=js.backNavigattionFromPlayerScreen();
			//Assert.assertTrue(status,"Back navigation is failed from player screen");
		} catch (Exception e) {
			System.out.println("Exception occurred");
		
		}

	}
	
	@Test(priority =2)
	public void adScreen() throws InterruptedException {
		boolean status;
		
		 status=js.sendkeysOnSearchField("Snow White");
		 //intentionally failing the testCase
		 Assert.assertFalse(status,"Unable to search the content");
		 status=js.selectSearchedContent("Snow White");
		 Assert.assertTrue(status,"Unable to select the searched content");
		
	}
	

	@AfterClass(alwaysRun=true)
	public void tearDownapp() {
		driver.pressKey(new KeyEvent(AndroidKey.HOME));
	}

}
