package ImplementationOfTestNG._Commonloginpage_BaseClasss;

import java.io.IOException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;


import genericUtility.BaseClass1;
import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import ObjectRepository.CampaignPage;
import ObjectRepository.HomePage;


/**
 * Test script to create a Campaign with status, scheduled date, and target size..
 * @author YourName
 */
public class CreateCampaignWithDateTest extends BaseClass1 {

    @Test(description = "Verify that a user can create a campaign with a specific status, target date, and size")
    public void createCampaignwithDateTest() throws IOException {
    

     // Accessing utilities inherited from BaseClass and generic utility(Excelfileutility,javautility)-- (e.g., pUtil, eUtil, jUtil, wUtil)
        ExcelFileUtility eutil= new ExcelFileUtility();
        JavaUtility jutil=new JavaUtility();
        String campaignBaseName = eutil.togetDataFromExcelFile("Campaign", 1, 2);
        String statusValue = eutil.togetDataFromExcelFile("Campaign", 1, 3);
        String targetSize = eutil.togetDataFromExcelFile("Campaign", 1, 5);
        String randomSuffix = jutil.togetRandomString();
        String fullCampaignName = campaignBaseName + randomSuffix;
        String scheduledCloseDate=jutil.togetRequiredDate(31);

        // --- STEP 4: NAVIGATION ---
        HomePage homePage = new HomePage(driver);
        wutil.waitForVisibilityofElement(driver, homePage.getCreateCampBtn());
        homePage.getCreateCampBtn().click();

        // --- STEP 5: FILL CAMPAIGN DETAILS ---
        CampaignPage campaignPage = new CampaignPage(driver);
        campaignPage.getCampaignNameTF().sendKeys(fullCampaignName);
        campaignPage.getStatusTF().sendKeys(statusValue);
        
        WebElement targetField = campaignPage.getTargetTF();
        targetField.clear();
        targetField.sendKeys(targetSize);

        WebElement closeDateField=campaignPage.getDateTF();
        closeDateField.sendKeys(scheduledCloseDate);

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
       // Reporter.log("Campaign Details: [URL: " + driver.getCurrentUrl() + " | Status: " + statusValue + " | Size: " + targetSize + "  | Close Date: " + scheduledCloseDate + "]", true);
    }
}