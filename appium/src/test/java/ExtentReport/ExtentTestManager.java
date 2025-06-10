package ExtentReport;

import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentTestManager {

	static ExtentReports extentReport = ExtentManager.getExtentReport();
	static Map<Object, Object> extentMap = new HashMap<>();

	public static ExtentTest startTests(String testName) {
		ExtentTest extentTest = extentReport.startTest(testName);
		extentMap.put((int) (long) Thread.currentThread().getId(), extentTest);

		return extentTest;
	}

	public static ExtentTest getTest() {
		return (ExtentTest) extentMap.get((int) (long) Thread.currentThread().getId());
	}

	public static void logInfo(String info) {
		getTest().log(LogStatus.INFO, info);
	}

	public static void logPass(String pass) {
		getTest().log(LogStatus.PASS, pass);
	}

	public static void logFail(String fail) {
		getTest().log(LogStatus.FAIL, fail);
	}

	public static void logSkip(String skip) {
		getTest().log(LogStatus.SKIP, skip);
	}

	public static void endTest() {
		extentReport.endTest(getTest());
		extentReport.flush();
	}

}
