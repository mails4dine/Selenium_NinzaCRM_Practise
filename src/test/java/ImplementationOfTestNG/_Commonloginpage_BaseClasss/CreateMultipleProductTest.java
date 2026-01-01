package ImplementationOfTestNG._Commonloginpage_BaseClasss;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ObjectRepository.CampaignPage;
import ObjectRepository.HomePage;
import ObjectRepository.ProductPage;
import genericUtility.BaseClass1;
public class CreateMultipleProductTest extends BaseClass1 {
    
    @Test
    public void createMultipleProductTest() throws EncryptedDocumentException, IOException, InterruptedException {
        
        wutil.waitForPagetoLoad(driver);
        
        // 1. Get total rows
        int rowCount = eutil.togetRowCount("Product");
        Reporter.log("Total data rows found: " + rowCount, true);
        
        // 2. Initialize Page Objects ONCE (Optimization)
        HomePage homepage = new HomePage(driver);
        ProductPage productPage = new ProductPage(driver);
        CampaignPage campaignPage = new CampaignPage(driver); // For shared toast locators
        
        // 3. Loop through the Excel rows
        for (int i = 1; i <= rowCount; i++) {   
            
            // --- DATA FETCHING ---
            String baseProductName = eutil.togetDataFromExcelFile("Product", i, 1);      
            String stockQuantity = eutil.togetDataFromExcelFile("Product", i, 3);
            String unitPrice = eutil.togetDataFromExcelFile("Product", i, 4);
            String fullproductname = baseProductName + jutil.togetRandomNumber();

            // --- NAVIGATION ---
            wutil.waitForVisibilityofElement(driver, homepage.getProduct());
            homepage.getProduct().click();
            
            wutil.waitForVisibilityofElement(driver, productPage.getAddProductBtn());
            productPage.getAddProductBtn().click();

            // --- DATA ENTRY ---
            productPage.getProdName().sendKeys(fullproductname);             
            wutil.select(productPage.getProdCategory(), 3);             
            
            productPage.getQuantity().clear();
            productPage.getQuantity().sendKeys(stockQuantity);
            
            productPage.getPrice().clear();
            productPage.getPrice().sendKeys(unitPrice);        
            
            wutil.select(productPage.getVendor(), 3);
            
            // --- SUBMIT AND VERIFY ---
            productPage.getAdd().click();
            
            // Wait and Capture Toast
            wutil.waitForVisibilityofElement(driver, campaignPage.getToastmsg());
            String actualToastMessage = campaignPage.getToastmsg().getText(); 
            
            // Close toast to clear the UI for the next iteration
            wutil.waitForVisibilityofElement(driver, campaignPage.getClosemsg());
            campaignPage.getClosemsg().click();

            // --- FINAL ASSERTION ---
            Assert.assertTrue(actualToastMessage.contains(fullproductname), 
                "Validation Failure! Toast message [" + actualToastMessage + "] did not contain: [" + fullproductname + "]");

            Reporter.log("Successfully created Product: " + fullproductname, true);  
        }
    }
}