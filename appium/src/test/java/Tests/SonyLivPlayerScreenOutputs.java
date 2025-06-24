package Tests;

import static AppsTesting.PlayerCommends.*;
import static AppsTesting.AdbCommendsClass.*;
import static Pages.LauncherScreen.launchApp;
import static Utilities.ExcelDataWrite.excelWrite;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Pages.JioHotstar;
import Pages.SonyLiv;
import Utilities.DataProviderClass;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class SonyLivPlayerScreenOutputs extends BaseTest {

	SonyLiv sl;
	String[] s;
	String appName = "SonyLiv";
	String deviceNameandVersion;
	String appVersionName;
	boolean status;

	@BeforeClass(alwaysRun = true)
	public void setup() {
		try {
			s = new String[6];
			sl = new SonyLiv(driver);
			deviceNameandVersion = deviceName();
			appVersionName = printAppNameAndVersion(appName);
			status = launchApp(appName);
			Assert.assertTrue(status, "App launch is failed");
			status = sl.selctMenuOption("Search");
			Assert.assertTrue(status, "Failed to navigete search section");
		} catch (Exception e) {
			System.out.println("Exception is occureed");
			e.printStackTrace();
		}
	}

	@Test(description = "Playing multiple contents", dataProvider = "sonyLivContentName", dataProviderClass = DataProviderClass.class, priority = 1, enabled = true)
	public void sonyLivePlayerResults(String contentName) {
		try {
			status = sl.searchAndPlay(contentName);
			Assert.assertTrue(status, "Error occures while search and play the content from search section");
			s[0] = deviceNameandVersion;
			s[1] = appVersionName;
			s[2] = contentName;
			s[3] = getAudioOutput();
			s[4] = getVisionOutput();
			s[5] = getVideoResolution();
			excelWrite(s);
			status = sl.backNavigationFromPlayerScreen();
			Assert.assertTrue(status, "Back navigation from playerscreen is failed");
		} catch (Exception e) {
			System.out.println("Exception is occureed");
			e.printStackTrace();
		}
	}

	@Test(description = "intentionally failed", priority = 2)
	public void sonyLivFailTestcase() {
		status = sl.searchAndPlay("Gullak");
		Assert.assertFalse(true, "Intentionally failed testcase");
	}

	@AfterClass(alwaysRun = true)
	public void navigateToHome() {
		driver.pressKey(new KeyEvent(AndroidKey.HOME));
	}

}
