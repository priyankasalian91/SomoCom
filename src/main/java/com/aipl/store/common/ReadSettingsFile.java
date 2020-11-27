package com.aipl.store.common;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class ReadSettingsFile {

	public Properties configData;
	public String settingsFilePath;
	String  keyData = "";
	
	public String getValueFromParam(String keyword)
	{
		try
		{
			ApplicationContext prop = new ApplicationContext();
			configData = prop.getPropertyfromProp();
			settingsFilePath = configData.getProperty("settingsFilePath");
			Path path = Paths.get(settingsFilePath);
			//byte[] bytes = Files.readAllBytes(path);
			List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
			/*for(String item : allLines)
			{
				if(item.contains(keyword))
				{
					keyData = (item.split("=")[1]).toString();
				}
			}
			*/
			allLines.forEach(item -> 
			{
				if(item.contains(keyword))
				{
					keyData = (item.split("=")[1]).toString();
				}
			});		
		}
		catch(Exception ex)
		{
			System.out.println("ReadSettingsFile / getValueFromParam / Exception : " + ex.getMessage());
			
		}
		
		return keyData;
	}
	
			public String readQueryFromTxtFile(String filePath)
			{
			 String allData = "";
				try
				{
					ApplicationContext prop = new ApplicationContext();
					configData = prop.getPropertyfromProp();
					Path path = Paths.get(filePath);
					//byte[] bytes = Files.readAllBytes(path);
					List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
					for(String item : allLines)
					{
						allData = allData + item;
					}
				}
				catch(Exception ex)
				{
					System.out.println("readQueryFromTxtFile / Exception / Query Not Found : " + ex.getMessage());
					
				}
				return allData;
			}
	
	
}
