package MobileAutomationTesting;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseClass {
	public AppiumDriverLocalService service;
	public AndroidDriver driver;
	
	
	@BeforeClass
	public void configTest() throws URISyntaxException, IOException {
		//code to automate the service
				service=new AppiumServiceBuilder().withAppiumJS(new File ("C:\\Users\\User\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
						.withIPAddress("127.0.0.1").usingPort(4723).build();
				service.start();
				
				//creating the capabilities
				
				UiAutomator2Options options=new UiAutomator2Options();
				//options.setDeviceName("emulator-5556");
				//options.setUdid("emulator-5556");
				//options.setUdid("emulator-5554");
				options.setDeviceName("192.168.1.16:5555");
				options.setUdid("192.168.1.16:5555");
				//options.setApp("C:\\Users\\User\\eclipse-workspace\\appium\\src\\test\\java\\resources\\ApiDemos-debug.apk");
				
				 driver=new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(),options);
				 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				 
	}
	
		
	public void setup() {
		try {
		
		AppiumDriverLocalService service=new AppiumServiceBuilder().withAppiumJS(new File("C:\\Users\\User\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
				.withIPAddress("127.0.0.1").usingPort(4723).build();
		service.start();
		UiAutomator2Options options=new UiAutomator2Options();
		options.setDeviceName("emulator-5554");
		options.setApp("C:\\Users\\User\\eclipse-workspace\\appium\\src\\test\\java\\resources\\ApiDemos-debug.apk");
		options.setUdid("emulator-5554");
		
		
		AndroidDriver driver=new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(),options);
		}catch(URISyntaxException | IOException e) {
			System.out.println("Exception occurred");
			e.printStackTrace();
		}
		finally {
			service.stop();
		}
		
		
	}
	@AfterClass
	
	public void tearDown() {
		driver.quit();
		service.stop();
	}

}
