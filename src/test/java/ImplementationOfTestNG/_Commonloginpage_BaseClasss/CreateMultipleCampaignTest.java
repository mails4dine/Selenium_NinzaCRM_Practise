package ImplementationOfTestNG._Commonloginpage_BaseClasss;

import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import ObjectRepository.CampaignPage;
import ObjectRepository.HomePage;
import genericUtility.BaseClass1;

public class CreateMultipleCampaignTest extends BaseClass1 {

    @Test
    public void createMultipleCampaignTest() throws EncryptedDocumentException, IOException {
        wutil.waitForPagetoLoad(driver);
        
        // 1. Get total row count
        int rowCount = eutil.togetRowCount("Campaign");
        Reporter.log("Total data rows found: " + rowCount, true);
        
        // 2. Initialize Page Objects ONCE (Optimization: Move outside the loop)
        HomePage homePage = new HomePage(driver);
        CampaignPage campaignPage = new CampaignPage(driver);

        // 3. Loop through the Excel rows
        for (int i = 1; i <= rowCount; i++) {
            String campaignBaseName = eutil.togetDataFromExcelFile("Campaign", i, 2);        
            String targetSize = eutil.togetDataFromExcelFile("Campaign", i, 5);
            String randomSuffix = jutil.togetRandomString();
            String fullCampaignName = campaignBaseName + randomSuffix;

            // --- STEP 4: NAVIGATION ---
            wutil.waitForVisibilityofElement(driver, homePage.getCreateCampBtn());
            homePage.getCreateCampBtn().click();

            // --- STEP 5: FILL CAMPAIGN DETAILS ---
            campaignPage.getCampaignNameTF().sendKeys(fullCampaignName);
            
            WebElement targetField = campaignPage.getTargetTF();
            targetField.clear();
            targetField.sendKeys(targetSize);

            // --- STEP 6: SUBMIT AND VERIFY ---
            campaignPage.getCreateCampSubmitBtn().click();

            // 1. Capture the Toast Element text immediately
            WebElement toastMessageElement = campaignPage.getToastmsg();
            wutil.waitForVisibilityofElement(driver, toastMessageElement);
            String actualToastMessage = toastMessageElement.getText(); 
            
            // 2. Close the toast message to clear the UI for the next iteration
            wutil.waitForVisibilityofElement(driver, campaignPage.getClosemsg());
            campaignPage.getClosemsg().click();

            // 3. Perform Assertion
            Assert.assertTrue(actualToastMessage.contains(fullCampaignName), 
                "Error: Campaign name mismatch. Expected: [" + fullCampaignName + "] but Toast was: [" + actualToastMessage + "]");
            
            Reporter.log("Successfully created Campaign: " + fullCampaignName, true);
            Reporter.log("===============================================", true);
        }
    }
}