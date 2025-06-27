package Pages;

import static Utilities.AppiumUtils.safeStaticWait;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Tests.BaseTest;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class Zee5 {

	AndroidDriver driver = BaseTest.getDriver();

	public boolean selectMenuOption(String menuName) {
		boolean status = false;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
		try {
			//com.zee5.aosp:id/collection_header_icon
			WebElement menu = wait.until(ExpectedConditions
					.visibilityOfElementLocated(AppiumBy.id("com.zee5.aosp:id/banner_image")));
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
			List<WebElement> menuOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AppiumBy
					.xpath("//android.widget.TextView[@resource-id='com.zee5.aosp:id/collection_header_label']")));
			for (WebElement option : menuOptions) {
				if (option.getText().equalsIgnoreCase(menuName)) {
					System.out.println(option.getText());
					option.click();
					driver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
					break;
			
			}
			}
			status = true;

		} catch (Exception e) {
			WebElement profile = wait
					.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.zee5.aosp:id/watching")));
			driver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
			selectMenuOption(menuName);
		}

		return status;
	}

	public boolean searchContent(String contentName) {
		boolean status = false;
		try {
			WebElement searchField = driver.findElement(AppiumBy.id("com.zee5.aosp:id/searchQuery"));
			searchField.clear();
			searchField.sendKeys(contentName);
			Thread.sleep(2000);
			driver.pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
			driver.pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
			status = true;

		} catch (Exception e) {
			System.out.println("Exception occureed while searching the content on Zee5 app");
			e.printStackTrace();
			status = false;
		}
		return status;
	}

	public boolean selectAndPlayTheSearchedContent(String contentName) throws InterruptedException {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			List<WebElement> contents = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
					AppiumBy.xpath("//android.widget.TextView[@resource-id='com.zee5.aosp:id/displayTitle']")));
			for (WebElement content : contents) {
				if (content.getText().equalsIgnoreCase(contentName)) {
					System.out.println(content.getText());
					Thread.sleep(2000);
					driver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
					break;
				} else {
					driver.pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
				}
			}
			status = true;
		} catch (NoSuchElementException e) {
			System.out.println("Exception occureed while selecting the content");
			e.printStackTrace();
			status = false;
		}
		return status;
	}

	public boolean playTheContent() {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					AppiumBy.xpath("//android.widget.ImageView[@resource-id='com.zee5.aosp:id/img_option']")));
			driver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
			safeStaticWait(driver, 45000, 15000);
			status = true;

		} catch (Exception e) {
			System.out.println("Exception occurred whiel playing the content");
			status = false;
		}
		return status;
	}

	public boolean playLiveContent() {
		boolean status = false;
		try {
			safeStaticWait(driver, 45000, 15000);
			status = true;

		} catch (Exception e) {
			System.out.println("Exception occurred whiel playing the content");
			status = false;
		}
		return status;

	}

	public boolean backNavigationLiveContent() {
		boolean status = false;
		try {
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
			driver.findElement(AppiumBy.accessibilityId("Search")).isDisplayed();
			selectMenuOption("Home");
			selectMenuOption("Search");
			status = true;

		} catch (Exception e) {
			System.out.println("Exception occurred whiel back navigation");
			status = false;
		}
		return status;

	}

	public boolean backNavigattionFromPlayerScreen() {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					AppiumBy.xpath("//android.widget.ImageView[@resource-id='com.zee5.aosp:id/img_option']")));
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
			driver.findElement(AppiumBy.accessibilityId("Search")).isDisplayed();
			selectMenuOption("Home");
			selectMenuOption("Search");
			status = true;

		} catch (Exception e) {
			System.out.println("Exception occurred whiel backnavigation from player screen");
			status = false;
		}
		return status;
	}

}
