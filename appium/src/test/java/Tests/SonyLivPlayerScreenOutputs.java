package Tests;

import static AppsTesting.PlayerCommends.*;
import static AppsTesting.AdbCommendsClass.*;
import static Pages.LauncherScreen.launchApp;
import static Utilities.ExcelDataWrite.excelWrite;

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
	String appName;
	String deviceNameandVersion;
	String appVersionName;

	@BeforeClass
	public void setup() {
		try {
			s = new String[6];
			appName = "SonyLiv";
			sl = new SonyLiv(driver);
			deviceNameandVersion = deviceName();
			appVersionName = printAppNameAndVersion(appName);
			logcatLogs(appName);
			String device = deviceName();
			launchApp(appName);
			sl.selctMenuOption("Search");
		} catch (Exception e) {
			System.out.println("Exception is occureed");
			e.printStackTrace();
		}
	}

	@Test(description = "Playing multiple contents", dataProvider = "sonyLivContentName", dataProviderClass = DataProviderClass.class, priority = 2)
	public void sonyLivePlayerResults(String contentName) {
		try {
			sl.searchAndPlay(contentName);
			s[0] = deviceNameandVersion;
			s[1] = appVersionName;
			s[2] = contentName;
			s[3] = getAudioOutput();
			s[4] = getVisionOutput();
			s[5] = getVideoResolution();
			excelWrite(s);
			sl.backNavigationFromPlayerScreen();
		} catch (Exception e) {
			System.out.println("Exception is occureed");
			e.printStackTrace();
		}
	}

}
