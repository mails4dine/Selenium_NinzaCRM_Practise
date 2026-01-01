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

import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;

public class CreateCampaignwithDate {

	public static void main(String[] args) throws IOException, InterruptedException {
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
		WebElement signin =	driver.findElement(By.xpath("//button[contains(text(),'Sign In')]"));
		wutil.singleClick(signin);
		//without status
		
		String campname=eutil.togetDataFromExcelFile("Campaign", 1, 2);
		String ranString = jutil.togetRandomString();	
		String size=eutil.togetDataFromExcelFile("Campaign", 1, 5);
	    String daterequired=jutil.togetRequiredDate(30);
	    

	    WebElement creatnewcampe=driver.findElement(By.xpath("//span[contains(text(),'Create Campaign')]"));
	    wutil.singleClick(creatnewcampe);
	WebElement name=  driver.findElement(By.name("campaignName"));
	name.sendKeys(campname+ranString);

	WebElement targetval= driver.findElement(By.name("targetSize"));
	targetval.clear();
	targetval.sendKeys(size);

	
	WebElement expectedCloseDate=driver.findElement(By.name("expectedCloseDate"));
	expectedCloseDate.sendKeys(daterequired);
	 WebElement addnewcamp= driver.findElement(By.xpath("//button[contains(text(),'Create Campaign')]"));
	    wutil.singleClick(addnewcamp);
	WebElement toastmsg=driver.findElement(By.xpath("//div[@class='Toastify__toast-body']"));
	wutil.waitForVisibilityofElement(driver, toastmsg);

		
	//campaign name splitter
	String msg=toastmsg.getText().split(" ")[1].trim();
	


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
	System.out.println("------------------------------");
    System.out.println("Campaign Name :" +msg);
    System.out.println("Campaign Expected Close Date :" +daterequired);



	driver.quit();

	}
}
