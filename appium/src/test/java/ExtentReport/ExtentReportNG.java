package ExtentReport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import static AppsTesting.AdbCommendsClass.*;

public class ExtentReportNG {
public static ExtentReports extent;
	
	
	public static ExtentReports getExtentReportObject() {
		
		Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
	    Date date = calendar.getTime();
	    String currentDate = sdf.format(date);
	    String path = System.getProperty("user.dir")+ File.separator+"Results"+File.separator+"ExtentReports" + File.separator +currentDate+File.separator+"index.html";
		ExtentSparkReporter report=new ExtentSparkReporter(path);
		
		report.config().setDocumentTitle("VOD app content results");
		report.config().setReportName("Player results");
		
		extent =new ExtentReports();
		extent.attachReporter(report);
		extent.setSystemInfo("Tester","Narasayya");
		String deviceName=deviceName();
		extent.setSystemInfo("Device details", deviceName);
		return extent;
	}

}
