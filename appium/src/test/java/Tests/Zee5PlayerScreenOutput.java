package Tests;

import static AppsTesting.AdbCommendsClass.deviceName;
import static AppsTesting.AdbCommendsClass.printAppNameAndVersion;
import static AppsTesting.PlayerCommends.getAudioOutput;
import static AppsTesting.PlayerCommends.getVideoResolution;
import static AppsTesting.PlayerCommends.getVisionOutput;
import static Pages.LauncherScreen.launchApp;
import static Utilities.ExcelDataWrite.excelWrite;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Pages.Zee5;
import Utilities.DataProviderClass;

public class Zee5PlayerScreenOutput extends BaseTest {
	Zee5 zee;
	String[] s;
	String appName = "Zee5";
	String deviceNameandVersion;
	String appVersionName;
	boolean status;

	@BeforeClass(alwaysRun = true)
	public void setUp() {
		try {

			s = new String[6];
			deviceNameandVersion = deviceName();
			appVersionName = printAppNameAndVersion(appName);
			zee = new Zee5();
			status = launchApp(appName);
			Assert.assertTrue(status,"Unable to launch the Zee5 app");
			zee.selectMenuOption("Search");
			//Assert.assertTrue(status,"Unable to naviagate search section");
			

		} catch (Exception e) {
			System.out.println("Exception occureed on Zee5 setUp");
			e.printStackTrace();
		}
	}
	
	@Test(description="Zee5 player outputs", dataProvider="Zee5ContentName", dataProviderClass=DataProviderClass.class, priority=1,enabled=true)
	public void zee5PlayerResults(String contentName) {
		try {
		
		status=zee.searchContent(contentName);
		Assert.assertTrue(status,"Unable to search the content on zee5 search field");
		status=zee.selectAndPlayTheSearchedContent(contentName);
		Assert.assertTrue(status,"Unable to select the searched content");
		status=zee.playTheContent();
		Assert.assertTrue(status,"Unable to play the content");
		s[0] = deviceNameandVersion;
		s[1] = appVersionName;
		s[2] = contentName;
		s[3] = getAudioOutput();
		s[4] = getVisionOutput();
		s[5] = getVideoResolution();
		excelWrite(s);
		status=zee.backNavigattionFromPlayerScreen();	
		Assert.assertTrue(status,"Back navigation is failed");
		}catch(Exception e) {
			System.out.println("Exception occurred from Zee5 testcases");
			e.printStackTrace();
		}
	}

	@Test(enabled=false)
	public void zee5PlayerResults() {
		try {
		
		status=zee.searchContent("vimanam");
		Assert.assertTrue(status,"Unable to search the content on zee5 search field");
		status=zee.selectAndPlayTheSearchedContent("vimanam");
		Assert.assertTrue(status,"Unable to select the searched content");
		status=zee.playTheContent();
		Assert.assertTrue(status,"Unable to play the content");
//		s[0] = deviceNameandVersion;
//		s[1] = appVersionName;
//		s[2] = contentName;
//		s[3] = getAudioOutput();
//		s[4] = getVisionOutput();
//		s[5] = getVideoResolution();
//		excelWrite(s);
		status=zee.backNavigattionFromPlayerScreen();	
		//Assert.assertTrue(status,"Back navigation is failed");
		}catch(Exception e) {
			System.out.println("Exception occurred from Zee5 testcases");
			e.printStackTrace();
		}
	}
	
	@Test(description ="Live content playback", priority=2, dataProvider="Zee5LiveContentName",dataProviderClass=DataProviderClass.class,enabled=true)
	public void zee5LiveChannelPlayerResults(String contentName) {
		try {
		status=zee.searchContent(contentName);
		Assert.assertTrue(status,"Unable to search the content on zee5 search field");
		status=zee.selectAndPlayTheSearchedContent(contentName);
		Assert.assertTrue(status,"Unable to select the searched content");
		status=zee.playLiveContent();
		Assert.assertTrue(status,"Unable to play the content");
		s[0] = deviceNameandVersion;
		s[1] = appVersionName;
		s[2] = contentName;
		s[3] = getAudioOutput();
		s[4] = getVisionOutput();
		s[5] = getVideoResolution();
		excelWrite(s);
		status=zee.backNavigationLiveContent();
		Assert.assertTrue(status,"Back navigation is failed from live content");
		}
	catch(Exception e) {
			System.out.println("Exception occurred from Zee5 testcases");
			e.printStackTrace();
		}
	}

}
