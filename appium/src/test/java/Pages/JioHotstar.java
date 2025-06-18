package Pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
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
        boolean status=false;
		try {
			// Navigates to search bar
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
					AppiumBy.xpath("//android.widget.EditText[@resource-id='in.startv.hotstar:id/search_bar']")));
			searchBox.click();
			searchBox.sendKeys(contentName);
			driver.pressKey(new KeyEvent(AndroidKey.BACK));
			status=true;

		} catch (Exception e) {
			e.printStackTrace();
			Thread.sleep(20000);
			status=false;
		}
		return true;
	}

	public boolean selectSearchedContent(String contentName) throws InterruptedException {
		boolean status=false;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			// First it will wait for single content thumbnail if not found it navigates to
			// catch block and finds the content
			// itrates through each content name, if match content founds then click on the
			// content
			WebElement searchResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
					AppiumBy.xpath("//android.widget.ImageView[@resource-id='in.startv.hotstar:id/hero_img']")));
			searchResult.click();
			driver.pressKey(new KeyEvent(AndroidKey.ENTER));
			Thread.sleep(3000);
			driver.pressKey(new KeyEvent(AndroidKey.ENTER));
			safeStaticWait(driver, 45000, 15000);
			//selectAudioOption(driver);
			status=true;
		} catch (NoSuchElementException e) {
			List<WebElement> allContents = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AppiumBy
					.xpath("//android.widget.TextView[@resource-id='in.startv.hotstar:id/search_horizontal_title']")));
			for (WebElement content : allContents) {
				if (content.getText().contains(contentName)) {
					System.out.println(content.getText());
					content.click();
					break;
				}
			}
			driver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
			safeStaticWait(driver, 45000, 15000);
			//selectAudioOption(driver);
			status=true;
			List<WebElement> contents = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AppiumBy
					.xpath("//android.widget.ImageView[@resource-id='in.startv.hotstar:id/search_vertical_img']")));
			for (WebElement content : contents) {
				contents.get(0).click();
				driver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
				break;
			}
			driver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
			safeStaticWait(driver, 45000, 15000);
		//	selectAudioOption(driver);
			status=true;
		}
		
		return status;

	}

	// it performs the back navigation from palyerscreen to search section

	public boolean backNavigattionFromPlayerScreen() throws InterruptedException {
		boolean status=false;
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement watchMoreElement = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
					"//android.widget.TextView[@resource-id='in.startv.hotstar:id/gtv_label' and @text='More Like This']")));
			if (watchMoreElement.isDisplayed()) {
				driver.pressKey(new KeyEvent(AndroidKey.BACK));
			}
			status=true;
		} catch (Exception e) {
			e.printStackTrace();
			status=false;
		}
		return status;
	}

	public void selectQuality(AndroidDriver driver) {
		//// android.widget.TextView[@resource-id="in.startv.hotstar:id/label"]

		driver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		driver.pressKey(new KeyEvent(AndroidKey.DPAD_UP));
		driver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));

	}

	public void selectAudioOption(AndroidDriver driver) {

		driver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		driver.pressKey(new KeyEvent(AndroidKey.DPAD_UP));
		driver.pressKey(new KeyEvent(AndroidKey.DPAD_RIGHT));
		driver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
		try {
			//// android.widget.ImageView[@resource-id="in.startv.hotstar:id/atmosBadge"]
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			///// android.view.ViewGroup[@resource-id="in.startv.hotstar:id/sheet_button"]
			List<WebElement> audios = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
					AppiumBy.xpath("//android.view.ViewGroup[@resource-id='in.startv.hotstar:id/sheet_button']")));
			for (WebElement audio : audios) {
				if(audio.getText().equalsIgnoreCase("original")){
				driver.pressKey(new KeyEvent(AndroidKey.DPAD_CENTER));
				break;
				}else {
					driver.pressKey(new KeyEvent(AndroidKey.DPAD_DOWN));
				}
			}
		} catch (Exception e) {
			System.err.println("Exception occured");
			driver.pressKey(new KeyEvent(AndroidKey.DPAD_LEFT));
		}
	}

}
