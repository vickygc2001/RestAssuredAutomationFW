package com.api.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager2 {

	private static Properties prop = new Properties();
	private static String env;
	private static String path = "config/config.properties";

	static {
		
		env = System.getProperty("env", "qa"); // If you pass env from terminal like "mvn test -Denv=dev, it picks dev... 
		//if you dont pass any env and just use "mvn test" then it picks qa.
		env = env.toLowerCase().trim();
		
		//Using arrow function in switch. Feature of Jave version 14
		switch(env) {
		case "dev" -> path = "config/config.dev.properties";
				
		
		case "qa" -> path = "config/config.qa.properties";
			
		
		case "uat" -> path = "config/config.uat.properties";
			
		default -> path = "config/config.qa.properties";			
		}
		
		
		//Another way to load the config files with less and readable code
		//Note : getContextClassLoader() will be aware of all the files in your project and we are getting the config.properties as input stream
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		
		//Exception handling if the file is not found
		if(input == null) {
			throw new RuntimeException("Cannot find the file at the path" + path);
		}
		
		try {
			prop.load(input);
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
