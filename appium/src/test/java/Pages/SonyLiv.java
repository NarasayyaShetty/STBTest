package Pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Tests.BaseTest;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import static Utilities.AppiumUtils.*;

public class SonyLiv {

	AndroidDriver driver=BaseTest.getDriver();

	

	public boolean selctMenuOption(String optionName) throws InterruptedException {
		boolean status = false;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			Thread.sleep(3000);
			WebElement continueWatchingCarousel = driver.findElement(By.xpath(
					"//android.widget.TextView[@resource-id='com.jiotv.sonyliv:id/row_header' and @text='Continue Watching']"));
			if (continueWatchingCarousel.isDisplayed()) {
				driver.pressKey(new KeyEvent(AndroidKey.BACK));
			}
			List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
					AppiumBy.xpath("//android.widget.TextView[@resource-id='com.jiotv.sonyliv:id/menu_text']")));
			for (WebElement option : options) {
				if (option.getText().equalsIgnoreCase(optionName)) {
					System.out.println(option.getText());
					option.click();
					status = true;
					break;
				}
			}

		} catch (Exception e) {
			WebElement profile = wait.until(ExpectedConditions.visibilityOfElementLocated(
					AppiumBy.xpath("//android.view.ViewGroup[@resource-id='com.jiotv.sonyliv:id/profile_parent_cl']")));
			profile.click();
			driver.pressKey(new KeyEvent(AndroidKey.ENTER));
			driver.pressKey(new KeyEvent(AndroidKey.BUTTON_1));
			driver.pressKey(new KeyEvent(AndroidKey.BUTTON_1));
			driver.pressKey(new KeyEvent(AndroidKey.BUTTON_1));
			driver.pressKey(new KeyEvent(AndroidKey.BUTTON_1));
			driver.pressKey(new KeyEvent(AndroidKey.ENTER));
			selctMenuOption(optionName);
		}
		return status;
	}

	public boolean searchAndPlay(String contentName) {
		boolean status = false;
		contentName = contentName.toLowerCase();
		
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement searchField = wait
					.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.jiotv.sonyliv:id/editSearch")));
			searchField.clear();
			searchField.sendKeys(contentName);
//			List<WebElement> keys = driver.findElements(AppiumBy.xpath("//android.widget.TextView"));
//			for (int i = 0; i < contentName.length(); i++) {
//				String temp = "" + contentName.charAt(i);
//
//				for (WebElement key : keys) {
//					if (key.getText().equalsIgnoreCase(temp)) {
//						System.out.println(key.getText());
//						Thread.sleep(1000);
//						key.click();
//						driver.pressKey(new KeyEvent(AndroidKey.ENTER));
//						break;
//					}
//				}
//			}
			WebElement searchResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
					AppiumBy.xpath("//android.view.ViewGroup[@resource-id='com.jiotv.sonyliv:id/main_layout']")));
			searchResult.click();

			Thread.sleep(2000);
			driver.pressKey(new KeyEvent(AndroidKey.ENTER));
			safeStaticWait(driver, 45000, 15000);
			status = true;

		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}

	public boolean backNavigationFromPlayerScreen() {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
			WebElement myListButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
					"//android.widget.TextView[@resource-id='com.jiotv.sonyliv:id/btnText' and @text='My List']")));
			if (myListButton.isDisplayed()) {
				driver.pressKey(new KeyEvent(AndroidKey.BACK));
			}
			WebElement searchElement = wait.until(ExpectedConditions
					.presenceOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@text='Search']")));
			if (searchElement.isDisplayed()) {
				driver.pressKey(new KeyEvent(AndroidKey.BACK));
			}
			WebElement searchOption = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
					"//android.widget.TextView[@resource-id='com.jiotv.sonyliv:id/menu_text' and @text='Search']")));
			if (searchOption.isDisplayed()) {
				driver.pressKey(new KeyEvent(AndroidKey.ENTER));
			}
			status = true;

		} catch (Exception e) {
			e.printStackTrace();
			status = false;

		}
		return status;

	}

}
