package Pages;

import static Utilities.AppiumUtils.safeStaticWait;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class SunNxt {
	AndroidDriver driver;

	public SunNxt(AndroidDriver driver) {
		this.driver = driver;
	}

	public boolean selectMenuOption(String menuOption) {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.suntv.sunnxt:id/bg_banner")));
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
			List<WebElement> options = driver.findElements(
					AppiumBy.xpath("//android.widget.TextView[@resource-id='com.suntv.sunnxt:id/header_label']"));
			for (WebElement option : options) {
				if (option.getText().equalsIgnoreCase(menuOption)) {
					System.out.println(option.getText());
					option.click();
					driver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
					break;
				}
			}

			status = true;

		} catch (Exception e) {
			System.out.println("Exception is occureed while selecting menu option from sunNxt home page");
			status = false;
		}

		return status;
	}

	public boolean searchContent(String contentName) {
		// android.widget.Button
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(
					AppiumBy.xpath("//android.widget.EditText[@resource-id='com.suntv.sunnxt:id/search_text']")));
			searchField.clear();
			searchField.sendKeys(contentName);
			status = true;

		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}

	public boolean selectSearchedContentAndPlay(String contentName) {
		contentName=contentName.toLowerCase();
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
					"//android.widget.TextView[@resource-id='com.suntv.sunnxt:id/textview_genre_title' and @text='Movies']")));

			List<WebElement> movies = driver.findElements(
					AppiumBy.xpath("//android.widget.TextView[@resource-id='com.suntv.sunnxt:id/title']"));
			for (WebElement movie : movies) {
				String name=movie.getText().toLowerCase();
				if (name.equalsIgnoreCase(contentName)) {
					System.out.println(movie.getText());
					movie.click();
					driver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
					break;
				}
			}
			WebElement playButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
					AppiumBy.id("com.suntv.sunnxt:id/playB_btton")));
			playButton.click();
			safeStaticWait(driver, 45000, 15000);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	public boolean backNavigationFromPlayerScreen() {
		boolean status=false;
		try {
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(15));
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AppiumBy.id("com.suntv.sunnxt:id/playB_btton")));
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
			status=true;
			
		}catch(Exception e) {
			e.printStackTrace();
			status=false;
		}
		return status;
		
	}

}
