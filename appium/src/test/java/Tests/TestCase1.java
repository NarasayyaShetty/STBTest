 package Tests;
import static AppsTesting.AdbCommendsClass.*;
import static AppsTesting.PlayerCommends.*;
import static Pages.LauncherScreen.*;

import java.io.File;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Utilities.DataProviderClass;


public class TestCase1 extends BaseTest {
	
	
	
	
	@Test(description="Launching all the apps one by one",dataProvider="appName", dataProviderClass=DataProviderClass.class)
	public void allAppLaunch(String appName) throws InterruptedException {
		
	currentFocus(driver);	
	String device=deviceName();	
	System.out.println(device);
	logcatLogs(appName);
	launchApp(appName);
	
	Thread.sleep(10000);
	
	}
}
