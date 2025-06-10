package MobileAutomationTesting;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class SonyLivTesting extends BaseClass{
	
	@Test(description="sonyLiv testing")
	public void sonyLivTesting() {
		
		driver.findElements(By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.jiotv.sonyliv:id/verticalGridView']/android.widget.LinearLayout")).get(5).click();
		
	}

}
