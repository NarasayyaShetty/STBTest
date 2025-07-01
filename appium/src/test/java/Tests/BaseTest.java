package Tests;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import PropertiesFiles.DataFromPropertiesFile;
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
    private static ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();
    String ip;

    public static AndroidDriver getDriver() {
        return driver.get();
    }

    @BeforeClass(alwaysRun = true)
    public void configTest() throws URISyntaxException, IOException, InterruptedException {
        // Read IP from properties file or system property
        DataFromPropertiesFile dp = new DataFromPropertiesFile();
        ip = System.getProperty("ipAddress") != null ? System.getProperty("ipAddress") : dp.getLocator("ip");
        System.out.println("Device IP: " + ip);

        // Connect ADB to the device
        connectStb(ip);

        // Step 1: Get Appium path from environment variable
        String appiumHome = System.getenv("APPIUM_HOME");
        System.out.println("APPIUM_HOME = " + appiumHome); // for debugging in Jenkins logs

        if (appiumHome == null || appiumHome.isEmpty()) {
            throw new RuntimeException("APPIUM_HOME environment variable is not set!");
        }

        // Step 2: Build path to main.js
        File appiumJS = new File(appiumHome + File.separator + "build" + File.separator + "lib" + File.separator + "main.js");
        if (!appiumJS.exists()) {
            throw new RuntimeException("Appium JS not found at: " + appiumJS.getAbsolutePath());
        }

        // Step 3: Start Appium server programmatically
        service = new AppiumServiceBuilder()
                .withAppiumJS(appiumJS)
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .build();
        service.start();

        //  Step 4: Set desired capabilities
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(ip + ":5555");
        options.setUdid(ip + ":5555");
        options.setAutomationName("UIAutomator2");
        options.setMjpegServerPort(0); // Prevent port 8200 conflicts

        // Step 5: Start driver with retries
        int retry = 0;
        while (retry < 3) {
            try {
                AndroidDriver localDriver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
                driver.set(localDriver);
                break;
            } catch (Exception e) {
                retry++;
                Thread.sleep(2000);
            }
        }

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        getDriver().pressKey(new KeyEvent(AndroidKey.BACK));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        try {
            if (getDriver() != null) {
                getDriver().pressKey(new KeyEvent(AndroidKey.HOME));
                getDriver().quit();
            }
        } catch (Exception e) {
            System.out.println("Driver may already be quit or crashed.");
        }

        try {
            if (service != null) {
                service.stop();
            }
        } catch (Exception e) {
            System.out.println("Appium service may already be stopped.");
        }
    }
}
