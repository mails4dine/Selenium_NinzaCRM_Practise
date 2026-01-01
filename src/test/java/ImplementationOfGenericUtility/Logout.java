package ImplementationOfGenericUtility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;

public class Logout {
	public static void main(String[] args) throws IOException {
		
		PropertyFileUtility putil=new PropertyFileUtility();
		WebDriverUtility wutil=new WebDriverUtility();
		
		
		String BROWSER=putil.togetDataFrompropertiesFile("Browser");
		String URL=putil.togetDataFrompropertiesFile("Url");
		String USERNAME=putil.togetDataFrompropertiesFile("Username");
		String PASSWORD=putil.togetDataFrompropertiesFile("Password");
		
		
		WebDriver driver=null;
		if(BROWSER.equals("Chrome"))
		{
			ChromeOptions options = new ChromeOptions();

			Map<String, Object> prefs = new HashMap<>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_leak_detection", false);	
			options.setExperimentalOption("prefs", prefs);
			driver=new ChromeDriver(options);
		}
		
		else if(BROWSER.equals("Edge"))
		{
			new EdgeDriver();
		}
		else if(BROWSER.equals("Firefox"))
		{
			new FirefoxDriver();
		}
		
		driver.manage().window().maximize();
		wutil.waitForPagetoLoad(driver);
		driver.get(URL);
		driver.findElement(By.id("username")).sendKeys(USERNAME);
		driver.findElement(By.id("inputPassword")).sendKeys(PASSWORD);
		WebElement signin =	driver.findElement(By.xpath("//button[contains(text(),'Sign In')]"));
		wutil.singleClick(signin);
		
	    WebElement usericon = driver.findElement(By.xpath("//div[@class='user-icon']"));
		
	    wutil.mouseHoverOnWebElement(driver, usericon);
		
	     WebElement logout = driver.findElement(By.xpath("//div[text()='Logout ']"));
		wutil.singleClick(logout);
		System.out.println(driver.getTitle());
		System.out.println(driver.getCurrentUrl());
		driver.quit();
	}

}
