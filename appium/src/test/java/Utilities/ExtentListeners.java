package Utilities;

import com.aventstack.extentreports.*;
import ExtentReport.ExtentReportNG;
import Tests.BaseTest;
import io.appium.java_client.android.AndroidDriver;
import org.testng.*;

import java.lang.reflect.Field;
import java.util.Map;

import static AppsTesting.AdbCommendsClass.collectLogs;

public class ExtentListeners implements ITestListener {

    ExtentReports extent = ExtentReportNG.getExtentReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);

        Object[] parameters = result.getParameters();
        if (parameters != null && parameters.length > 0) {
            extentTest.get().info("The App/Content name is: " + parameters[0].toString());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());

        Object[] parameters = result.getParameters();
        if (parameters != null && parameters.length > 0) {
            extentTest.get().info("Failed App/content: " + parameters[0].toString());
        }

        try {
            // Get appName via reflection from test class
            String appName = "unknown";
            Object testInstance = result.getInstance();
            Field appNameField = testInstance.getClass().getDeclaredField("appName");
            appNameField.setAccessible(true);
            Object value = appNameField.get(testInstance);
            if (value != null) {
                appName = value.toString();
                System.out.println("App Name: " + appName);
            }

            // Use thread-safe driver
            AndroidDriver driver = BaseTest.getDriver();

            // Take and attach screenshot
            String screenshotPath = AppiumUtils.getScreenshotPath(driver, result.getMethod().getMethodName());
            extentTest.get().addScreenCaptureFromPath(screenshotPath, "Screenshot");

            // Collect and attach logs
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

        } catch (Exception e) {
            extentTest.get().warning("Exception during failure processing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().skip("Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
