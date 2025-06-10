package Tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static Pages.LauncherScreen.*;
import static Utilities.ExcelDataWrite.excelWrite;
import static AppsTesting.AdbCommendsClass.*;
import static AppsTesting.PlayerCommends.getAudioOutput;
import static AppsTesting.PlayerCommends.getVideoResolution;
import static AppsTesting.PlayerCommends.getVisionOutput;

import Pages.JioHotstar;
import Utilities.DataProviderClass;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class HotstarPlayerScreenOutputs extends BaseTest {
	JioHotstar js;
	String[] s;
	String appName;
	String deviceNameandVersion;
	String appVersionName;

	// , dataProvider="hotstarContentName",dataProviderClass=DataProviderClass.class
	@BeforeClass
	public void setUp() {
		try {
			s = new String[6];
			appName = "JioHotstar";
			logcatLogs(appName);
			deviceNameandVersion = deviceName();
			appVersionName = printAppNameAndVersion(appName);
			js = new JioHotstar(driver);
			launchApp(appName);
			js.clickOnProfile();
			js.selectMenuOption("Search");
		} catch (Exception e) {
			System.out.println("Exception occureed");
			e.printStackTrace();
		}
	}

	@Test(description = "Playing multiple contents", dataProvider = "hotstarContentName", dataProviderClass = DataProviderClass.class)
	public void jioHotstarPlayreResult(String contentName) {
		try {
			js.sendkeysOnSearchField(contentName);
			s[0] = deviceNameandVersion;
			s[1] = appVersionName;
			s[2] = contentName;
			s[3] = getAudioOutput();
			s[4] = getVisionOutput();
			s[5] = getVideoResolution();
			excelWrite(s);
			js.backNavigattionFromPlayerScreen();
		} catch (Exception e) {
			System.out.println("Exception occurred");
			e.printStackTrace();
		}

	}

	@AfterClass
	public void tearDownapp() {
		driver.pressKey(new KeyEvent(AndroidKey.HOME));
	}

}
