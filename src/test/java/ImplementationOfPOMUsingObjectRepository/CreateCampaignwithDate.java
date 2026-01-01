package ImplementationOfPOMUsingObjectRepository;

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

import ObjectRepository.CampaignPage;
import ObjectRepository.HomePage;
import ObjectRepository.LoginPage;
import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;

public class CreateCampaignwithDate {
	public static void main(String[] args) throws IOException {
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
		LoginPage lp=new LoginPage(driver);
		lp.getUN().sendKeys(USERNAME);
		lp.getPWD().sendKeys(PASSWORD);
		WebElement signin =lp.getLoginBtn();	
		wutil.singleClick(signin);
		
		
		String campname=eutil.togetDataFromExcelFile("Campaign", 1, 2);
		String ranString = jutil.togetRandomString();	
		String size=eutil.togetDataFromExcelFile("Campaign", 1, 5);
		String status=eutil.togetDataFromExcelFile("Campaign", 1, 3);
		String daterequired=jutil.togetRequiredDate(31);
	   
	HomePage hp=new HomePage(driver);
		CampaignPage cp=new CampaignPage(driver);
		  hp.getCreateCampBtn().click();
		//System.out.println(campname+ranString);
		    
	WebElement name=  cp.getCampaignNameTF();
	name.sendKeys(campname+ranString);

	WebElement cstatus=cp.getStatusTF();
	cstatus.sendKeys(status);
	
	WebElement targetval= cp.getTargetTF();
	targetval.clear();
	targetval.sendKeys(size);

	WebElement expectedCloseDate=cp.getDateTF();
	expectedCloseDate.sendKeys(daterequired);

	cp.getCreateCampSubmitBtn().click();

	WebElement toastmsg=cp.getToastmsg();
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




	WebElement close=cp.getClosemsg();
	wutil.mouseHoverOnWebElement(driver, close);

	System.out.println("------------------------------");
	System.out.println("Campaign Name :" +msg);
	System.out.println("Campaign Expected Close Date  :" +expectedCloseDate );

	driver.quit();
	}

}
