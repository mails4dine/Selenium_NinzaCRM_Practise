package ImplementationOfPOMUsingObjectRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;

import ObjectRepository.CampaignPage;
import ObjectRepository.HomePage;
import ObjectRepository.LoginPage;
import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;

public class CreateCampaign {
public static void main(String[] args) throws IOException {
	
	ExcelFileUtility eutil=new ExcelFileUtility();
	JavaUtility jutil=new JavaUtility();
	PropertyFileUtility putil=new PropertyFileUtility();
	WebDriverUtility wutil=new WebDriverUtility();
	// 1. DATA SETUP FOR BROWSER

	String BROWSER=putil.togetDataFrompropertiesFile("Browser");
	String URL=putil.togetDataFrompropertiesFile("Url");
	String USERNAME=putil.togetDataFrompropertiesFile("Username");
	String PASSWORD=putil.togetDataFrompropertiesFile("Password");
	
    WebDriver driver=null;
	if(BROWSER.equalsIgnoreCase("Chrome"))
	{
		ChromeOptions options = new ChromeOptions();

		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_leak_detection", false);	
		options.setExperimentalOption("prefs", prefs);
		driver=new ChromeDriver(options);
	}
	
	else if(BROWSER.equalsIgnoreCase("Edge"))
	{
		driver=new EdgeDriver();
	}
	else if(BROWSER.equalsIgnoreCase("Firefox"))
	{
		driver=new FirefoxDriver();
	}
	
	driver.manage().window().maximize();
	wutil.waitForPagetoLoad(driver);
	driver.get(URL);
	
	// 2. LOGIN ACTION
	LoginPage loginPage = new LoginPage(driver);
    loginPage.getUN().sendKeys(USERNAME);
    loginPage.getPWD().sendKeys(PASSWORD);
    loginPage.getLoginBtn().click();
		    
    
    // 3. LOGIN VERIFICATION
    wutil.waitForUrlToContain(driver, "dashboard");
    Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "FAIL: Login failed.");
    Reporter.log("Login Successfully", true);
    
    // 4. NAVIGATION
    HomePage homePage = new HomePage(driver);
    homePage.getCreateCampBtn().click();
    
    //dATASETUP FOR CAMPAIGN CREATION
    String campaignBaseName = eutil.togetDataFromExcelFile("Campaign", 1, 2);
    String targetSize = eutil.togetDataFromExcelFile("Campaign", 1, 5);
    String status=eutil.togetDataFromExcelFile("Campaign", 1, 3);
    String daterequired=jutil.togetRequiredDate(31);
    String randomSuffix = jutil.togetRandomString();	
    String expectedCampaignName = campaignBaseName + randomSuffix;
    
    // 5. CAMPAIGN CREATION
    CampaignPage campaignPage = new CampaignPage(driver);
    campaignPage.getCampaignNameTF().sendKeys(expectedCampaignName);
    
    WebElement targetField = campaignPage.getTargetTF();
    targetField.clear();
    targetField.sendKeys(targetSize);
    campaignPage.getCreateCampSubmitBtn().click();
    
    // 6. TOAST MESSAGE VERIFICATION
    WebElement toastElement = campaignPage.getToastmsg();
    wutil.waitForVisibilityofElement(driver, toastElement);
    
    // Extracting actual name from toast (Assumes toast format: "Campaign CampaignName Created")
    String actualCampaignName = toastElement.getText().split(" ")[1].trim();
    
    // 7. FINAL ASSERTION & LOGGING
    Assert.assertEquals(actualCampaignName, expectedCampaignName, "FAIL: Campaign name mismatch!");
    
    String currentUrl = driver.getCurrentUrl();
    Reporter.log("Campaign Created Successfully",true);
   /* Reporter.log("<b>Campaign Created Successfully</b><br>" +
                 "URL: " + currentUrl + "<br>" +
                 "Campaign Name: " + actualCampaignName + "<br>" +
                 "Target Size: " + targetSize, true);*/
    
    
    driver.quit();
}
	
}
