package ImplementationOfTestNG;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ObjectRepository.LoginPage;
import ObjectRepository.ProductPage;
import genericUtility.BaseClass;
import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import ObjectRepository.CampaignPage;
import ObjectRepository.HomePage;


public class CreateLoginCampaignProductTest extends BaseClass {

	
	@Test(priority = 3)
    public void loginTest() throws IOException {
		// --- STEP 1: LOGIN ---
        String URL = putil.togetDataFrompropertiesFile("Url");
        String UN = putil.togetDataFrompropertiesFile("Username");
        String PWD = putil.togetDataFrompropertiesFile("Password");

        driver.get(URL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.getUN().sendKeys(UN);
        loginPage.getPWD().sendKeys(PWD);
        loginPage.getLoginBtn().click();

        // --- STEP 2: VERIFY LOGIN ---
        wutil.waitForUrlToContain(driver, "dashboard");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("dashboard"), "Login Failed: Dashboard not reached.");
        Reporter.log("Login Successfully", true);
        Reporter.log("URL :"+currentUrl,true);
        Reporter.log("===============================================", true);  

        
    }
	
	@Test(priority = 1)
	public void createProductTest() throws IOException {
		// --- STEP 1: INITIALIZE UTILITIES & DATA ---
		ExcelFileUtility eutil = new ExcelFileUtility();
        JavaUtility jutil = new JavaUtility();
        
    	String URL = putil.togetDataFrompropertiesFile("Url");
        String UN = putil.togetDataFrompropertiesFile("Username");
        String PWD = putil.togetDataFrompropertiesFile("Password");

     // Accessing utilities inherited from BaseClass and generic utility(Excelfileutility,javautility)-- (e.g., pUtil, eUtil, jUtil, wUtil)
       
        
		String baseProductName=eutil.togetDataFromExcelFile("Product", 1, 1);		
		String stockQuantity=eutil.togetDataFromExcelFile("Product", 1, 3);
		String unitPrice=eutil.togetDataFromExcelFile("Product", 1, 4);
		String uniqueId = String.valueOf(jutil.togetRandomNumber());
		String fullproductname=baseProductName+uniqueId;
		

        // --- STEP 2: LOGIN ---
        driver.get(URL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.getUN().sendKeys(UN);
        loginPage.getPWD().sendKeys(PWD);
        loginPage.getLoginBtn().click();

        // --- STEP 3: VERIFY LOGIN SUCCESS ---
        wutil.waitForUrlToContain(driver, "dashboard");
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Error: Login failed, dashboard not reached.");
        Reporter.log("User logged in successfully.", true);

        // --- STEP 4: NAVIGATION ---
        HomePage homepage=new HomePage(driver);
        homepage.getProduct().click();
        ProductPage productPage = new ProductPage(driver);
        productPage.getAddProductBtn().click();
       

     // --- STEP 5: PRODUCT DATA ENTRY ---
        
           WebElement productname= productPage.getProdName();			  
           productname.sendKeys(fullproductname);			   
		   WebElement categoryDropdown =  productPage.getProdCategory();
		   wutil.select(categoryDropdown,3);			  
		   WebElement quantityField =productPage.getQuantity();
		   quantityField.clear();
		   quantityField.sendKeys(stockQuantity);
		   WebElement priceField= productPage.getPrice();
		   priceField.clear();
		   priceField.sendKeys(unitPrice);		   
		   WebElement vendorDropdown =  productPage.getVendor();
		   wutil.select(vendorDropdown, 3);
		   wutil.waitForPagetoLoad(driver);
		   
        // --- STEP 6: SUBMIT AND VERIFY ---
		   productPage.getAdd().click();
		   
        // Wait for and capture toast message
		   CampaignPage campaignPage=new CampaignPage(driver);
        WebElement toastMessageElement = campaignPage.getToastmsg();
        wutil.waitForVisibilityofElement(driver, toastMessageElement);
        
        // Extracting the created name from the toast (Assuming format: "Created [Name] successfully")
        String capturedProductName = toastMessageElement.getText().split(" ")[1].trim();
        
        // Close toast message
        campaignPage.getClosemsg().click();

        // --- STEP 7: FINAL ASSERTION AND REPORTING ---
        Assert.assertEquals(capturedProductName, fullproductname, "Validation Failure: Product name in toast does not match input.");
        Reporter.log("Successfully created Product: " + fullproductname, true);     
        Reporter.log("Product Details: [URL: " + driver.getCurrentUrl() + " | Product Name: " + fullproductname + "]", true);
	
        
	}
	
	
	
	    @Test(priority = 2)
	    public void createCampaignTest() throws IOException {
	    	
	        // --- STEP 1: RETRIEVE TEST DATA ---
	        // Accessing utilities inherited from BaseClass (e.g., pUtil,wUtil)
	    	String URL = putil.togetDataFrompropertiesFile("Url");
	        String UN = putil.togetDataFrompropertiesFile("Username");
	        String PWD = putil.togetDataFrompropertiesFile("Password");

	     // Accessing utilities inherited from BaseClass and generic utility(Excelfileutility,javautility)-- (e.g., pUtil, eUtil, jUtil, wUtil)
	        ExcelFileUtility eutil= new ExcelFileUtility();
	        JavaUtility jutil=new JavaUtility();
	        String campaignBaseName = eutil.togetDataFromExcelFile("Campaign", 1, 2);        
	        String targetSize = eutil.togetDataFromExcelFile("Campaign", 1, 5);
	        String randomSuffix = jutil.togetRandomString();
	        String fullCampaignName = campaignBaseName + randomSuffix;

	        // --- STEP 2: LOGIN ---
	        driver.get(URL);
	        LoginPage loginPage = new LoginPage(driver);
	        loginPage.getUN().sendKeys(UN);
	        loginPage.getPWD().sendKeys(PWD);
	        loginPage.getLoginBtn().click();

	        // --- STEP 3: VERIFY LOGIN SUCCESS ---
	        wutil.waitForUrlToContain(driver, "dashboard");
	        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Error: Login failed, dashboard not reached.");
	        Reporter.log("User logged in successfully.", true);

	        // --- STEP 4: NAVIGATION ---
	        HomePage homePage = new HomePage(driver);
	        homePage.getCreateCampBtn().click();

	        // --- STEP 5: FILL CAMPAIGN DETAILS ---
	        CampaignPage campaignPage = new CampaignPage(driver);
	        campaignPage.getCampaignNameTF().sendKeys(fullCampaignName);
	      
	        
	        WebElement targetField = campaignPage.getTargetTF();
	        targetField.clear();
	        targetField.sendKeys(targetSize);

	        // --- STEP 6: SUBMIT AND VERIFY ---
	        campaignPage.getCreateCampSubmitBtn().click();

	        // Wait for and capture toast message
	        WebElement toastMessageElement = campaignPage.getToastmsg();
	        wutil.waitForVisibilityofElement(driver, toastMessageElement);
	        
	        // Extracting the created name from the toast (Assuming format: "Created [Name] successfully")
	        String capturedCampaignName = toastMessageElement.getText().split(" ")[1].trim();
	        
	        // Close toast message
	        campaignPage.getClosemsg().click();

	        // --- STEP 7: FINAL ASSERTION AND REPORTING ---
	        Assert.assertEquals(capturedCampaignName, fullCampaignName, "Error: Campaign name mismatch in toast message.");
	        
	        Reporter.log("Successfully created Campaign: " + fullCampaignName, true);
	        Reporter.log("Campaign Details: [URL: " + driver.getCurrentUrl() + " | Size: " + targetSize + "]", true);
	    
	    }
}
