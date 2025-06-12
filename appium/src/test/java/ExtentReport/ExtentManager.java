package ExtentReport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {

	static ExtentReports extentReports;

	public static String getFilePath() {
	    // Get current date in ddMMyyyy format
	    String currentDate = new SimpleDateFormat("ddMMyyyy").format(new Date());

	    // Get current time in HHmmssSSS format (hour-minute-second-millisecond)
	    String currentTime = new SimpleDateFormat("HHmmssSSS").format(new Date());

	    // Build the unique and readable file path
	    String filePath = System.getProperty("user.dir") + File.separator + "Results" + File.separator
	            + "TestReport" + File.separator + currentDate + File.separator
	            + "ExtentReportResult_" + currentTime + ".html";

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
