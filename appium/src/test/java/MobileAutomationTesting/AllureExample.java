package MobileAutomationTesting;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AllureExample {
	
	@Test
	public void testcase01() {
		Assert.assertTrue(true,"Test step is passed");
	}
	
	@Test
	public void testcase02() {
		Assert.assertTrue(false,"Test step is failed");
	}

}
