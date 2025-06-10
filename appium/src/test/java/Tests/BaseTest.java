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

import static ExtentReport.ExtentTestManager.*;
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

	@BeforeSuite(alwaysRun = true)
	public void configTest() throws URISyntaxException, IOException {
		// code to automate the service

		connectStb("192.168.1.23");
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
		options.setDeviceName("192.168.1.23:5555");
		options.setUdid("192.168.1.23:5555");
		// options.setApp("C:\\Users\\User\\eclipse-workspace\\appium\\src\\test\\java\\resources\\ApiDemos-debug.apk");

		driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(Method m) {
		startTests(m.getName());
	}

	@AfterMethod(alwaysRun = true)
	public void afterTestMethod(ITestResult iTestResult) {
		if (iTestResult.getStatus() == ITestResult.SUCCESS) {
			logPass("Step is Passed");
		} else if (iTestResult.getStatus() == ITestResult.FAILURE) {
			String path = capture(driver, "Step Failed");
			logFail(getTest().addScreenCapture(path));
		} else {
			logSkip("Test is skiped");
		}
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown() {
		// driver.quit();
		// service.stop();
		// driver.pressKey(new KeyEvent(AndroidKey.HOME));
		endTest();
	}

}
