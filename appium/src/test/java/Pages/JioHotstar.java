package Pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import static Utilities.AppiumUtils.*;

public class JioHotstar {
	AndroidDriver driver;

	public JioHotstar(AndroidDriver driver) {
		this.driver = driver;
	}

	public void clickOnProfile() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			WebElement profile = wait.until(ExpectedConditions.visibilityOfElementLocated(
					AppiumBy.xpath("//android.widget.ImageView[@resource-id='in.startv.hotstar:id/iv_profile']")));
			profile.click();
			driver.pressKey(new KeyEvent(AndroidKey.ENTER));
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void selectMenuOption(String optionValue) {
		try {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement bannerContent = wait.until(ExpectedConditions.visibilityOfElementLocated(
					AppiumBy.xpath("//android.widget.TextView[@resource-id='in.startv.hotstar:id/title']")));
			if (bannerContent.isDisplayed()) {
				driver.pressKey(new KeyEvent(AndroidKey.BACK));
			}

			List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
					AppiumBy.xpath("//android.widget.TextView[@resource-id='in.startv.hotstar:id/tv_title']")));

			for (WebElement option : options) {
				if (option.getText().equalsIgnoreCase(optionValue)) {
					option.click();
					System.out.println(option.getText());
					driver.pressKey(new KeyEvent(AndroidKey.ENTER));
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean sendkeysOnSearchField(String contentName) throws InterruptedException {

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
					AppiumBy.xpath("//android.widget.EditText[@resource-id='in.startv.hotstar:id/search_bar']")));
			searchBox.click();
			searchBox.sendKeys(contentName);
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
			WebElement searchResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
					AppiumBy.xpath("//android.widget.ImageView[@resource-id='in.startv.hotstar:id/hero_img']")));
			searchResult.click();
			driver.pressKey(new KeyEvent(AndroidKey.ENTER));
			Thread.sleep(3000);
//		try {
//		List<WebElement> languages=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AppiumBy.xpath("//android.widget.TextView[@resource-id='in.startv.hotstar:id/tv_label']")));
//		for(WebElement language:languages) {
//			
//			if(language.getText().contains("Original")) {
//				
//				language.click();
//				driver.pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
//				Thread.sleep(1000);
//				driver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
//				break;
//			}
//		}
//		}catch(Exception e) {
//			
//			driver.pressKey(new KeyEvent(AndroidKey.ENTER));
//			Thread.sleep(100000);
//			return true;
//		}
			driver.pressKey(new KeyEvent(AndroidKey.ENTER));
			safeStaticWait(driver, 90000, 15000);

		} catch (Exception e) {
			e.printStackTrace();
			Thread.sleep(20000);
		}
		return true;
	}

	public void backNavigattionFromPlayerScreen() throws InterruptedException {
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement watchMoreElement = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
					"//android.widget.TextView[@resource-id='in.startv.hotstar:id/gtv_label' and @text='More Like This']")));
			if (watchMoreElement.isDisplayed()) {
				driver.pressKey(new KeyEvent(AndroidKey.BACK));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
