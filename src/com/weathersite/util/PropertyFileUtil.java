package com.weathersite.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.weathersite.exception.WeatherException;

public final class PropertyFileUtil {
	//return value for key supplied, properties stored in key=value format in config.properties
	public String getPropertyValue(String key) throws WeatherException, IOException {
		InputStream inputStream = null;
		String propVal = "";
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
	
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
	
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new WeatherException("Property file '" + propFileName + "' not found in the classpath");
			}
	
			// get the property value
			propVal = prop.getProperty(key);
	
		} catch (Exception e) {
			throw new WeatherException("Exception when fetching property value for - " + key + ": " + e.getMessage());
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}		
		return propVal;
	}
}
