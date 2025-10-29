package com.api.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

	private static Properties prop = new Properties();

	static {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(
					System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" 
			+ File.separator + "resources" + File.separator + "config" + File.separator + "config.properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) throws IOException {
		return prop.getProperty(key);
	}
}
