package ImplementationOfTestNG._Commonloginpage_BaseClasss;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;


import ObjectRepository.ProductPage;
import genericUtility.BaseClass1;
import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import ObjectRepository.CampaignPage;
import ObjectRepository.HomePage;


public class CreateCampaignProductTest extends BaseClass1 {

	
	
	
	@Test
    public void createProductTest() throws IOException, InterruptedException {
        String baseProductName = eutil.togetDataFromExcelFile("Product", 1, 1);		
        String stockQuantity = eutil.togetDataFromExcelFile("Product", 1, 3);
        String unitPrice = eutil.togetDataFromExcelFile("Product", 1, 4);
        String uniqueId = String.valueOf(jutil.togetRandomNumber());
        String fullproductname = baseProductName + uniqueId;

        HomePage homepage = new HomePage(driver);
        wutil.waitForVisibilityofElement(driver, homepage.getProduct());
        homepage.getProduct().click();
        
        ProductPage productPage = new ProductPage(driver);
        wutil.waitForVisibilityofElement(driver, productPage.getAddProductBtn());
        productPage.getAddProductBtn().click();

        productPage.getProdName().sendKeys(fullproductname);			  
        wutil.select(productPage.getProdCategory(), 3);			  
        productPage.getQuantity().clear();
        productPage.getQuantity().sendKeys(stockQuantity);
        productPage.getPrice().clear();
        productPage.getPrice().sendKeys(unitPrice);		   
        wutil.select(productPage.getVendor(), 3);
        
        productPage.getAdd().click();
        
        CampaignPage campaignPage = new CampaignPage(driver);
        WebElement toast = campaignPage.getToastmsg();
        wutil.waitForVisibilityofElement(driver, toast);
        
        // Capture text BEFORE closing
        String actualToast = toast.getText();
        campaignPage.getClosemsg().click();

        Assert.assertTrue(actualToast.contains(fullproductname), "Validation Failure: Product name not found in toast.");
        Reporter.log("Successfully created Product: " + fullproductname, true);     
    }
	
	
	    @Test
	    public void createCampaignTest() throws IOException, InterruptedException {
	    	
	        

	    	 String campaignBaseName = eutil.togetDataFromExcelFile("Campaign", 1, 2);        
	         String targetSize = eutil.togetDataFromExcelFile("Campaign", 1, 5);
	         String randomSuffix = jutil.togetRandomString();
	         String fullCampaignName = campaignBaseName + randomSuffix;

	        
	         // --- STEP 4: NAVIGATION ---
	         HomePage homePage = new HomePage(driver);
	         Thread.sleep(400);
	         wutil.waitForVisibilityofElement(driver, homePage.getCreateCampBtn());
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
	         wutil.waitForVisibilityofElement(driver,campaignPage.getClosemsg());
	         campaignPage.getClosemsg().click();

	         // --- STEP 7: FINAL ASSERTION AND REPORTING ---
	         Assert.assertEquals(capturedCampaignName, fullCampaignName, "Error: Campaign name mismatch in toast message.");
	         
	         Reporter.log("Successfully created Campaign: " + fullCampaignName, true);
	        // Reporter.log("Campaign Details: [URL: " + driver.getCurrentUrl() + " | Size: " + targetSize + "]", true);
	    }
	    
	    @Test
	    public void createCampaignWithStatusTest() throws IOException, InterruptedException {
	    	
	        

	        ExcelFileUtility eutil= new ExcelFileUtility();
	        JavaUtility jutil=new JavaUtility();
	        String campaignBaseName = eutil.togetDataFromExcelFile("Campaign", 1, 2);
	        String statusValue = eutil.togetDataFromExcelFile("Campaign", 1, 3);
	        String targetSize = eutil.togetDataFromExcelFile("Campaign", 1, 5);
	        String randomSuffix = jutil.togetRandomString();
	        String fullCampaignName = campaignBaseName + randomSuffix;

	       
	        // --- STEP 4: NAVIGATION ---
	        HomePage homePage = new HomePage(driver);
	        Thread.sleep(400);
	        wutil.waitForVisibilityofElement(driver, homePage.getCreateCampBtn());
	        homePage.getCreateCampBtn().click();
	        // --- STEP 5: FILL CAMPAIGN DETAILS ---
	        CampaignPage campaignPage = new CampaignPage(driver);
	        campaignPage.getCampaignNameTF().sendKeys(fullCampaignName);
	        campaignPage.getStatusTF().sendKeys(statusValue);
	        
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
	        wutil.waitForVisibilityofElement(driver,campaignPage.getClosemsg());
	        campaignPage.getClosemsg().click();

	        // --- STEP 7: FINAL ASSERTION AND REPORTING ---
	        Assert.assertEquals(capturedCampaignName, fullCampaignName, "Error: Campaign name mismatch in toast message.");
	        
	        Reporter.log("Successfully created Campaign: " + fullCampaignName, true);
	       // Reporter.log("Campaign Details: [URL: " + driver.getCurrentUrl() + " | Status: " + statusValue + " | Size: " + targetSize + "]", true);
	    }
	    
	    
	    @Test
	    public void createCampaignwithDateTest() throws IOException, InterruptedException {
	        String campaignBaseName = eutil.togetDataFromExcelFile("Campaign", 1, 2);
	        String statusValue = eutil.togetDataFromExcelFile("Campaign", 1, 3);
	        String targetSize = eutil.togetDataFromExcelFile("Campaign", 1, 5);
	        String randomSuffix = jutil.togetRandomString();
	        String fullCampaignName = campaignBaseName + randomSuffix;
	        String scheduledCloseDate = jutil.togetRequiredDate(31);

	        HomePage homePage = new HomePage(driver);
	        wutil.waitForVisibilityofElement(driver, homePage.getCreateCampBtn());
	        homePage.getCreateCampBtn().click();

	        CampaignPage campaignPage = new CampaignPage(driver);
	        campaignPage.getCampaignNameTF().sendKeys(fullCampaignName);
	        campaignPage.getStatusTF().sendKeys(statusValue);
	        
	        campaignPage.getTargetTF().clear();
	        campaignPage.getTargetTF().sendKeys(targetSize);
	        campaignPage.getDateTF().sendKeys(scheduledCloseDate);

	        campaignPage.getCreateCampSubmitBtn().click();

	        WebElement toast = campaignPage.getToastmsg();
	        wutil.waitForVisibilityofElement(driver, toast);
	        
	        // FIXED: Using actualToast to capture text and fullCampaignName for assertion
	        String actualToast = toast.getText();
	        
	        // Close toast after capturing text
	        wutil.waitForVisibilityofElement(driver, campaignPage.getClosemsg());
	        campaignPage.getClosemsg().click();

	        // FIXED: Variable name updated to fullCampaignName
	        Assert.assertTrue(actualToast.contains(fullCampaignName), "Mismatch in toast message! Found: " + actualToast); 
	        Reporter.log("Successfully created Campaign: " + fullCampaignName, true);
	    }
	
}
