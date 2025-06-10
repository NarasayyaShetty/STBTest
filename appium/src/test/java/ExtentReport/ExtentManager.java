package ExtentReport;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {

	static ExtentReports extentReports;

	public static String getFilePath() {
		int time = (int) System.currentTimeMillis();
		String currentTime = String.valueOf(time);
		String filePath = System.getProperty("user.dir") + File.separator + "Results" + File.separator + "TestResults"
				+ File.separator + "ExtentReportResult" + currentTime + ".html";
		return filePath;
	}

	public static synchronized ExtentReports getExtentReport() {
		if (extentReports == null) {
			int time = (int) System.currentTimeMillis();
			String currentTime = String.valueOf(time);
			extentReports = new ExtentReports(getFilePath(), false);

		}
		return extentReports;
	}

}
