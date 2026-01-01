package ImplementationOfTestNG._Commonloginpage_BaseClasss;

import java.io.IOException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;


import ObjectRepository.ProductPage;
import genericUtility.BaseClass1;

import ObjectRepository.CampaignPage;
import ObjectRepository.HomePage;


public class CreateProductTest extends BaseClass1{
	@Test
	public void createProductTest() throws IOException, InterruptedException {
		

     // Accessing utilities inherited from BaseClass and generic utility(Excelfileutility,javautility)-- (e.g., pUtil, eUtil, jUtil, wUtil)
       
        
		String baseProductName=eutil.togetDataFromExcelFile("Product", 1, 1);		
		String stockQuantity=eutil.togetDataFromExcelFile("Product", 1, 3);
		String unitPrice=eutil.togetDataFromExcelFile("Product", 1, 4);
		String uniqueId = String.valueOf(jutil.togetRandomNumber());
		String fullproductname=baseProductName+uniqueId;
		


        // --- STEP 4: NAVIGATION ---
        HomePage homepage=new HomePage(driver);
        //Thread.sleep(2000);
        wutil.waitForVisibilityofElement(driver, homepage.getProduct());
        homepage.getProduct().click();
        
        ProductPage productPage = new ProductPage(driver);
        wutil.waitForVisibilityofElement(driver, productPage.getAddProductBtn());
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
        wutil.waitForVisibilityofElement(driver,campaignPage.getClosemsg());
        campaignPage.getClosemsg().click();

        // --- STEP 7: FINAL ASSERTION AND REPORTING ---
        Assert.assertEquals(capturedProductName, fullproductname, "Validation Failure: Product name in toast does not match input.");
        Reporter.log("Successfully created Product: " + fullproductname, true);     
       // Reporter.log("Product Details: [URL: " + driver.getCurrentUrl() + " | Product Name: " + fullproductname + "]", true);
		
	}
}
