 package Tests;
import static AppsTesting.AdbCommendsClass.*;
import static AppsTesting.PlayerCommends.*;
import static Pages.LauncherScreen.*;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import Utilities.DataProviderClass;


public class TestCase1 extends BaseTest {
	
	String appName;
	
	
	
	@Test(description="Launching all the apps one by one",dataProvider="appName", dataProviderClass=DataProviderClass.class)
	public void allAppLaunch(String app) throws InterruptedException {
	currentFocus(getDriver());	
	String device=deviceName();
	appName=app;
	System.out.println(device);
	boolean status=launchApp(appName);
	Assert.assertTrue(status,"Application is not launched, either app is not available in our list");
	Thread.sleep(10000);
	
	}
}
