package ImplementationOfTestNG._Commonloginpage_BaseClasss;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ObjectRepository.CampaignPage;
import ObjectRepository.HomePage;
import genericUtility.BaseClass1;

public class DeleteProduct extends BaseClass1{

	 @Test
	    public void deleteProductTest() {
	       // CampaignPage campaignPage = new CampaignPage(driver);
	        
	        // --- STEP 1: UI SYNCHRONIZATION & FILTERING ---
	        wutil.waitForPagetoLoad(driver);
	        
	        HomePage homepage=new HomePage(driver);
	        wutil.waitForVisibilityofElement1(driver, homepage.getProduct());
	        homepage.getProduct().click();
	        // Locator for search filter
	        WebElement dropdown = driver.findElement(By.tagName("select"));
	        wutil.waitForVisibilityofElement1(driver, dropdown);
	        Select select = new Select(dropdown);
	        select.selectByVisibleText("Search by Product Name");

	        WebElement textfield = driver.findElement(By.xpath("//input[@placeholder='Search by product Name']")); 
	        wutil.waitForVisibilityofElement1(driver, textfield);
	        textfield.sendKeys("Zukebar");
	        
	        // --- STEP 2: DEFINE LOCATORS ---
	        By deleteIconXpath = By.xpath("//i[@title='Delete']");
	        By confirmDeleteXpath = By.xpath("//input[@value='Delete']");
	        int count=0;
	        // --- STEP 3: DYNAMIC DELETION LOOP ---
	     // --- STEP 3: OPTIMIZED DYNAMIC LOOP ---
	        while (true) {
	            // 1. Check for buttons with zero timeout if possible, 
	            // or simply accept the last check might take a few seconds.
	            List<WebElement> currentBtns = driver.findElements(deleteIconXpath);
	            
	            if (currentBtns.isEmpty()) {
	                System.out.println("No more buttons. Closing now.");
	                break; 
	            }

	            try {
	                WebElement btnToDelete = currentBtns.get(0);
	                count++;
	                btnToDelete.click();

	                // Use EXPLICIT waits for the confirm button instead of relying on implicit
	                WebElement confirmBtn = driver.findElement(confirmDeleteXpath);
	                confirmBtn.click();
	                
	                CampaignPage campaignPage = new CampaignPage(driver);
	                
	                WebElement toastMessageElement = campaignPage.getToastmsg();
	    	        wutil.waitForVisibilityofElement(driver, toastMessageElement);
	                
	                
	                wutil.waitForVisibilityofElement(driver,campaignPage.getClosemsg());
	    	        campaignPage.getClosemsg().click();

	                // ... rest of your code ...
	                
	            } catch (Exception e) {
	                // If an error occurs, do one quick check. 
	                // If it's empty, get out immediately.
	                break; 
	            }
	            
	           
	        }
	        Reporter.log("Total :"+count + "Product's Deleted Successfully ", true);
	    }
}
