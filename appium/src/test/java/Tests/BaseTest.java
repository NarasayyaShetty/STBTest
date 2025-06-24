package Tests;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import PropertiesFiles.DataFromPropertiesFile;

import static Utilities.AppiumUtils.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import static AppsTesting.AdbCommendsClass.*;

public class BaseTest {
	public AppiumDriverLocalService service;
	public AndroidDriver driver;
	String ip;
	
	@BeforeClass(alwaysRun=true)
	public void configTest() throws URISyntaxException, IOException, InterruptedException {
		DataFromPropertiesFile dp=new DataFromPropertiesFile();
		ip=System.getProperty("ipAddress")!=null?System.getProperty("ipAddress"):dp.getLocator("ip");
		//ip=dp.getLocator("ip");
		System.out.println(ip);
		connectStb(ip);
		// code to automate the service
		service = new AppiumServiceBuilder()
				.withAppiumJS(
						new File("C:\\Users\\User\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
				.withIPAddress("127.0.0.1").usingPort(4723).build();
		service.start();

		// creating the capabilities

		UiAutomator2Options options = new UiAutomator2Options();
		// options.setDeviceName("emulator-5556");
		// options.setUdid("emulator-5556");
		// options.setUdid("emulator-5554");
		options.setDeviceName(ip + ":5555");
		options.setUdid(ip + ":5555");
		options.setAutomationName("UIAutomator2");
		// Disable MJPEG streaming to avoid port 8200 issues
		options.setMjpegServerPort(0);

		// options.setApp("C:\\Users\\User\\eclipse-workspace\\appium\\src\\test\\java\\resources\\ApiDemos-debug.apk");

		int retry = 0;
		while (retry < 3) {
			try {

				driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
				break;
			} catch (Exception e) {
				retry++;
				Thread.sleep(2000);
			}
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.pressKey(new KeyEvent(AndroidKey.BACK));

	}



	@AfterClass(alwaysRun = true)
	public void tearDown() {
		try {
			if (driver != null) {
				//driver.pressKey(new KeyEvent(AndroidKey.HOME));
				driver.quit();
			}
		} catch (Exception e) {
			System.out.println("Driver may already be quit or crashed.");
		}

		try {
			if (service != null) {
				service.stop();
			}
		} catch (Exception e) {
			System.out.println("Appium service may already be stopped.");
		}
	}

}
