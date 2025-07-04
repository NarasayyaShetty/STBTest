package Utilities;

import com.aventstack.extentreports.*;
import ExtentReport.ExtentReportNG;
import Tests.BaseTest;
import io.appium.java_client.android.AndroidDriver;

import org.apache.commons.io.FileUtils;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
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
    	try {
            // Define Excel report path
            String currentDate = new SimpleDateFormat("ddMMyyyy").format(Calendar.getInstance().getTime());
            String excelPath = System.getProperty("user.dir") + File.separator + "Results" +
                               File.separator + "ExcelSheetsFolder" + File.separator + currentDate +
                               File.separator + "ExcelResults.xlsx";

            File excelFile = new File(excelPath);
            if (excelFile.exists()) {
                ExtentTest summaryTest = extent.createTest("Test Summary Excel Report");

                summaryTest.info("Click below to download the Excel report:");
                summaryTest.info("<a href='" + excelFile.getAbsolutePath() + "' target='_blank'>Download Excel Report</a>");
            } else {
                ExtentTest summaryTest = extent.createTest("Test Summary Excel Report");
                summaryTest.warning("Excel Report not found at expected location: " + excelPath);
            }
            
            String currentDate1 = new SimpleDateFormat("ddMMyyyy").format(Calendar.getInstance().getTime());
            String sourcePath = System.getProperty("user.dir") + File.separator + "Results" +
                               File.separator + "ExtentReports" + File.separator + currentDate1 +
                               File.separator + "index.html";

            String destDirPath = System.getProperty("user.dir") + File.separator + "Results" +
                                File.separator + "Latest";

            String destPath = destDirPath + File.separator + "index.html";

            File sourceFile = new File(sourcePath);
            File destDir = new File(destDirPath);
            File destFile = new File(destDir, "index.html");

            if (!destDir.exists()) {
                destDir.mkdirs();  // create directory if not exists
            }

            if (sourceFile.exists()) {
                Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Report copied to: " + destFile.getAbsolutePath());
            } else {
                System.err.println("Source Extent report not found at: " + sourceFile.getAbsolutePath());
            }
//         // ===== Add report path print and auto-open =====
           String reportPath = System.getProperty("user.dir") + File.separator + "reports" + File.separator + "ExtentReport.html";
           System.out.println("ExtentReport generated at: " + reportPath);
           
           String currentDate2 = new SimpleDateFormat("ddMMyyyy").format(new Date());
           String reportPath1 = System.getProperty("user.dir") + File.separator + "Results" +
                   File.separator + "ExtentReports" + File.separator + currentDate2 +
                   File.separator + "index.html";

            // Optional: Open report in default browser (only works on local machine, not CI)
          File reportFile = new File(reportPath1);
           if (reportFile.exists()) {
                java.awt.Desktop.getDesktop().browse(reportFile.toURI());
            } else {
                System.err.println("ExtentReport.html file not found at: " + reportPath1);
            }
            
           

        } catch (Exception e) {
            ExtentTest summaryTest = extent.createTest("Test Summary Excel Report");
            summaryTest.warning("Exception while attaching Excel Report: " + e.getMessage());
        }

        extent.flush();
    }
}
