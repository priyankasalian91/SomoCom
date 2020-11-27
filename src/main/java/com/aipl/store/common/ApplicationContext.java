package com.aipl.store.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationContext {
	
	public Properties getPropertyfromProp() 
	{
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
    	Properties properties = new Properties();
    	try (InputStream resourceStream = loader.getResourceAsStream("application.properties")) {
    	  properties.load(resourceStream);
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    	
		
	/*	Properties properties = new Properties();
    	try
    	{
	    	
	    	properties.load(new FileInputStream("E:\\CTSRelease\\configsettings.txt"));
		} 
    	catch (IOException e) {
		    e.printStackTrace();
		}*/
    	return properties;
		
	}

}
