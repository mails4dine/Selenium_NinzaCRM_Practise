package DDT;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;

public class ReadDataFromExcelCreateCompaign_Allvalues {

	public static void main(String[] args) throws Throwable {
		ExcelFileUtility eutil=new ExcelFileUtility();
		JavaUtility jutil=new JavaUtility();
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
		driver.findElement(By.xpath("//button[contains(text(),'Sign In')]")).click();
		//without status
		
		String campname=eutil.togetDataFromExcelFile("Campaign", 1, 2);
		String size=eutil.togetDataFromExcelFile("Campaign", 1, 5);
        String daterequired=jutil.togetRequiredDate(30);
	
	
	
	
	
//end of line optional to generate name
	Thread.sleep(2000);
	driver.findElement(By.xpath("//span[contains(text(),'Create Campaign')]")).click();
	WebElement name=  driver.findElement(By.name("campaignName"));
	name.sendKeys(campname);
	
	WebElement targetval= driver.findElement(By.name("targetSize"));
	targetval.clear();
	targetval.sendKeys(size);
	
Thread.sleep(2000);
WebElement closeddatefield=driver.findElement(By.name("expectedCloseDate"));
closeddatefield.sendKeys(daterequired);




	driver.findElement(By.xpath("//button[contains(text(),'Create Campaign')]")).click();
	WebElement toastmsg=driver.findElement(By.xpath("//div[@class='Toastify__toast-body']"));
	wutil.waitForVisibilityofElement(driver, toastmsg);
	
		
	//campaign name splitter
	String msg=toastmsg.getText().split(" ")[1].trim();
	System.out.println(msg);
	

	if(msg.contains(campname))
	{
	   System.out.println("Compaign created successfully");
	}
	else
	{
	   System.out.println("Compaign Not created ");   
	}




	WebElement close=driver.findElement(By.xpath("//button[@aria-label='close']"));
wutil.mouseHoverOnWebElement(driver, close);
	
	

	driver.quit();

		
	}
}
