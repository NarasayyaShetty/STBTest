package Utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class AppiumUtils {
	
	public static void clickElement(AndroidDriver driver,WebElement element) {
		try {
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.visibilityOf(element)).click();
		    driver.pressKey(new KeyEvent(AndroidKey.ENTER));
		}catch(NoSuchElementException e) {
			e.printStackTrace();
		}
	}
	public static void safeStaticWait(AndroidDriver driver) throws InterruptedException {
	    long waited = 0;
	    long totalWaitMillis=60000;
	    long heartbeatIntervalMillis=15000;
	    
	    while (waited < totalWaitMillis) {
	        Thread.sleep(heartbeatIntervalMillis);
	        waited += heartbeatIntervalMillis;
	        try {
	            driver.getPageSource();  // dummy command to keep session alive
	        } catch (Exception e) {
	            System.out.println("Warning: Heartbeat failed: " + e.getMessage());
	        }
	    }
	}
	public static void deleteOldLogFiles() {
	    try {
	        String logDirPath = System.getProperty("user.dir") + File.separator + "LogFiles";
	        File logDir = new File(logDirPath);

	        if (logDir.exists() && logDir.isDirectory()) {
	            for (File dateFolder : logDir.listFiles()) {
	                if (dateFolder.isDirectory()) {
	                    for (File file : dateFolder.listFiles()) {
	                        file.delete(); // delete each file
	                    }
	                    dateFolder.delete(); // delete date folder after contents
	                }
	            }
	            System.out.println(" Old log files deleted.");
	        } else {
	            System.out.println(" Log directory does not exist. No files to delete.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public static String capture(AndroidDriver driver, String name){
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("ddMMyyyy");
        Date date=calendar.getTime();
        String currentDate=sdf.format(date);
        String desPath="";
        String filePath=System.getProperty("user.dir")+File.separator+"Results"+File.separator+"Screenshot"+File.separator+currentDate;
        
        try{
            File file=new File(filePath);
            if(!file.exists()){
                file.mkdirs();
            
            TakesScreenshot ts=(TakesScreenshot) driver;
            File srcFile=ts.getScreenshotAs(OutputType.FILE);
            String fileName=String.format(name+"_%s.png", UUID.randomUUID().toString());
            File desFile=new File("Screenshot/"+currentDate+"/"+fileName);
            desPath=desFile.getAbsolutePath();
            FileUtils.copyFile(srcFile,desFile);
            System.out.println(desPath);
           
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return desPath;
	}
	
	public static String getScreenshotPath(AndroidDriver driver,String testCaseName) {
		Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("ddMMyyyy");
        Date date=calendar.getTime();
        String currentDate=sdf.format(date);
		File source=driver.getScreenshotAs(OutputType.FILE);
		String filepath=System.getProperty("user.dir") + File.separator + "Results" + File.separator +"Screenshot"+File.separator+currentDate+File.separator
				+testCaseName+".png";
		File desFile=new File(filepath);
		try {
			FileUtils.copyFile(source, desFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filepath;
	}
}
