package PropertiesFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DataFromPropertiesFile {

	Properties p = new Properties();
	String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
			+ "java" + File.separator + "PropertiesFiles" + File.separator + "data.properties";

	public DataFromPropertiesFile() {
		try {
			FileInputStream file = new FileInputStream(path);
			p.load(file);
		} catch (IOException e) {

		}
	}
	
	public String getLocator(String key) {
		return p.getProperty(key);
		
	}

}
