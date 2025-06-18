package Utilities;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import ExtentReport.ExtentReportNG;
import io.appium.java_client.android.AndroidDriver;
import static AppsTesting.AdbCommendsClass.*;

import java.lang.reflect.Field;
import java.util.Map;

public class ExtentListeners implements ITestListener {

	ExtentReports extent = ExtentReportNG.getExtentReportObject();
	ExtentTest test;
	public AndroidDriver driver;
	
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	@Override
	public void onStart(ITestContext context) {

	}

	@Override
	public void onTestStart(ITestResult result) {

		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
		
		Object[] parameters = result.getParameters();
		if (parameters != null && parameters.length > 0) {
			extentTest.get().info("The App/Content name is :     " + parameters[0].toString());
		}

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.get().log(Status.PASS, "TestMethod :"+result.getMethod().getMethodName()+" :is Passed");		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		extentTest.get().fail(result.getThrowable());
		Object[] parameters = result.getParameters();
		if (parameters != null && parameters.length > 0) {
			extentTest.get().info("Failed App/content: " + parameters[0].toString());
		}

		try {
	        // Get appName from test class variable
	        String appName = "unknown";
	        Object testInstance = result.getInstance();
	        Field appNameField = testInstance.getClass().getDeclaredField("appName");
	        appNameField.setAccessible(true);
	        Object value = appNameField.get(testInstance);
	        if (value != null) {
	            appName = value.toString();
	            System.out.println(appName);
	        }

	        // Get driver from test class
	        driver = (AndroidDriver) result.getTestClass()
	                .getRealClass()
	                .getField("driver")
	                .get(testInstance);

	        // Attach screenshot
	        String screenshotPath = AppiumUtils.getScreenshotPath(driver, result.getMethod().getMethodName());
	        extentTest.get().addScreenCaptureFromPath(screenshotPath, result.getMethod().getMethodName());

	        // Call log collector (returns log file paths)
	        String finalAppName = appName;
	        Map<String, String> logs = collectLogs(appName, result.getMethod().getMethodName());
	        if (logs.containsKey("full")) {
	            extentTest.get().info("Full Logs: <a href='" + logs.get("full") + "' target='_blank'>Full Log</a>");
	        }
	        if (logs.containsKey("crash")) {
	            extentTest.get().info("Crash Logs: <a href='" + logs.get("crash") + "' target='_blank'>Crash Log</a>");
	        }
	        if (logs.containsKey("anr")) {
	            extentTest.get().info("ANR Logs: <a href='" + logs.get("anr") + "' target='_blank'>ANR Log</a>");
	        }
		
	}catch(Exception e) {
		 e.printStackTrace();
	}
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest.get().info(result.getMethod().getMethodName() +"Test method is skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}

}
