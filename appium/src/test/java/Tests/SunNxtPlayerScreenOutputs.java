package Tests;

import static AppsTesting.AdbCommendsClass.deviceName;
import static AppsTesting.AdbCommendsClass.printAppNameAndVersion;
import static AppsTesting.PlayerCommends.getAudioOutput;
import static AppsTesting.PlayerCommends.getVideoResolution;
import static AppsTesting.PlayerCommends.getVisionOutput;
import static Pages.LauncherScreen.launchApp;
import static Utilities.ExcelDataWrite.excelWrite;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Pages.SunNxt;
import Utilities.DataProviderClass;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;


public class SunNxtPlayerScreenOutputs extends BaseTest{
	
	SunNxt sn;
	String[] s;
	String appName="SunNXt";
	String deviceNameandVersion;
	String appVersionName;
	boolean status;
	
	@BeforeClass(alwaysRun=true)
	public void config() {
		s = new String[6];
		deviceNameandVersion = deviceName();
		appVersionName = printAppNameAndVersion(appName);
		sn = new SunNxt(driver);
		status=launchApp(appName);
		Assert.assertTrue(status,"App launch is failed");
		
		boolean status=sn.selectMenuOption("Search");
		Assert.assertTrue(status,"Unable to navigate search section");
		
	}
	
	@Test(description="SunNxt player results",dataProvider="SunNxtContentName",dataProviderClass=DataProviderClass.class,priority=1)
	public void sunNxtPlayerResults(String contentName) {
		try {
		status=sn.searchContent(contentName);
		Assert.assertTrue(status,"Unable to search the content");
		status=sn.selectSearchedContentAndPlay(contentName);
		Assert.assertTrue(status,"Unable to play the searched content from search section");
		s[0] = deviceNameandVersion;
		s[1] = appVersionName;
		s[2] = contentName;
		s[3] = getAudioOutput();
		s[4] = getVisionOutput();
		s[5] = getVideoResolution();
		excelWrite(s);
		status=sn.backNavigationFromPlayerScreen();
		Assert.assertTrue(status,"Back navigation is falied from playerscreen");
		}catch(Exception e) {
			System.out.println("Exception occurred in SunNxt testCase");
			e.printStackTrace();
		}
		
		
	}
	
	@Test(description="intentionally failed",priority=2)
	public void sunNxtFailTestcase() {
		status=sn.searchContent("Petta");
		Assert.assertTrue(status,"Unable to search the content");
		status=sn.selectSearchedContentAndPlay("Petta");
		Assert.assertFalse(true,"Intentionally failed testcase");
	}
	
	@AfterClass(alwaysRun=true)
	public void navigateToHome() {
		driver.pressKey(new KeyEvent(AndroidKey.HOME));
	}

}
