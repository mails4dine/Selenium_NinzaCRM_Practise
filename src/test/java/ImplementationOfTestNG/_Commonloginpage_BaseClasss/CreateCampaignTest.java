package ImplementationOfTestNG._Commonloginpage_BaseClasss;

import java.io.IOException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;


import genericUtility.BaseClass1;
import ObjectRepository.CampaignPage;
import ObjectRepository.HomePage;

public class CreateCampaignTest extends BaseClass1 {

  
   @Test
   public void createCampaignTest() throws IOException {
       // 1. Fetch Data
       String campaignBaseName = eutil.togetDataFromExcelFile("Campaign", 1, 2);        
       String targetSize = eutil.togetDataFromExcelFile("Campaign", 1, 5);
       String randomSuffix = jutil.togetRandomString();
       String fullCampaignName = campaignBaseName + randomSuffix;

       // 2. Navigation
       HomePage homePage = new HomePage(driver);
       wutil.waitForVisibilityofElement(driver, homePage.getCreateCampBtn());
       homePage.getCreateCampBtn().click();

       // 3. Fill Details
       CampaignPage campaignPage = new CampaignPage(driver);
       campaignPage.getCampaignNameTF().sendKeys(fullCampaignName);
       
       WebElement targetField = campaignPage.getTargetTF();
       targetField.clear();
       targetField.sendKeys(targetSize);

       // 4. Submit
       campaignPage.getCreateCampSubmitBtn().click();

       // 5. Capture Toast Message
       WebElement toastMessageElement = campaignPage.getToastmsg();
       wutil.waitForVisibilityofElement(driver, toastMessageElement);
       String actualToastText = toastMessageElement.getText(); 
       
       // 6. Close Toast immediately to prevent blocking the UI
       wutil.waitForVisibilityofElement(driver, campaignPage.getClosemsg());
       campaignPage.getClosemsg().click();

       // 7. Robust Assertion
       // We use .contains() because the toast includes "Campaign... Successfully Added"
       Assert.assertTrue(actualToastText.contains(fullCampaignName), 
           "Error: Toast message mismatch! Expected name: [" + fullCampaignName + "] but Toast was: [" + actualToastText + "]");
       
       Reporter.log("Successfully created Campaign: " + fullCampaignName, true);
   }
}