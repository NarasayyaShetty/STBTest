package Utilities;

import org.testng.annotations.DataProvider;

public class DataProviderClass {
	
	//adding applications names
	@DataProvider(name="appName")
	public Object[][] appName(){
		return new Object[][]{{"hotstar"},{"Netflix"},{"primevideo"},{"youtube"},{"zee5"},
			{"sonyLiv"},{"sunNxt"},{"docubay"},{"hoichoi"},{"discoveryplus"}};
	}
	//Adding hotstar contents names
	@DataProvider(name="hotstarContentName")
	public Object[][] JioHotstarContents(){
		return new Object[][]{{"Salaar"},{"Bhagavanth Kesari"},{"Captain America Brave new world"},{"Tough me not"},{"Anupama"}};
	}
	
	@DataProvider(name="sonyLivContentName")
	public Object[][] sonyLivContents(){
		return new Object[][]{{"pani"},{"Kankhajura"},{"maharani"},{"marco"},{"piku"}};
	}

}
