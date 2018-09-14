package Util;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GetProperties {
	
	public static String fetchPropertyValue(String key) throws IOException {
		
		FileInputStream file= new FileInputStream("Config.properties");
		Properties property= new Properties();
		property.load(file);
		return property.get(key).toString();
	}
}
