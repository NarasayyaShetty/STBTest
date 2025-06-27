package Utilities;

import org.testng.annotations.DataProvider;

public class DataProviderClass {
	
	//adding applications names
	@DataProvider(name="appName")
	public Object[][] appName(){
		return new Object[][]{{"hotstar"},{"JioTV+"},{"Netflix"},{"primevideo"},{"youtube"},{"zee5"},
			{"sonyLiv"},{"sunNxt"},{"docubay"},{"hoichoi"},{"discoveryplus"}};
	}
	//Adding hotstar contents names
	@DataProvider(name="hotstarContentName")
	public Object[][] JioHotstarContents(){
		return new Object[][]{{"subham"},{"Salaar"},{"Bhagavanth Kesari"},{"Captain America Brave new world"},{"ironheart"},{"Anupama"}};
	}
	
	@DataProvider(name="sonyLivContentName")
	public Object[][] sonyLivContents(){
		return new Object[][]{{"pani"},{"Kankhajura"},{"maharani"},{"marco"},{"piku"}};
	}
	@DataProvider(name="SunNxtContentName")
	public Object[][] sunNxtContents(){
		return new Object[][] {{"Petta"},{"ET - Telugu"},{"35 chinna vishayam ila"},{"upadhyaksha"},{"ayalaan"}};
	}
	
	@DataProvider(name="Zee5ContentName")
	public Object[][] Zee5Contents(){
		return new Object[][] {{"Max"},{"vimanam"},{"Ummadi Kutumbam"},{"Auto Shankar"}};
	}
	@DataProvider(name="Zee5LiveContentName")
	public Object[][] Zee5LiveContents(){
		return new Object[][] {{"Zee Telugu HD"},{"Zee Kannada HD"}};
	}


}
