package ImplementationOfTestNG;

import java.io.IOException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ObjectRepository.LoginPage;
import genericUtility.BaseClass;
import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import ObjectRepository.CampaignPage;
import ObjectRepository.HomePage;


/**
 * Test script to create a Campaign with status and target size..
 * @author YourName
 */
public class CreateCampaignWithStatusTest extends BaseClass {

    @Test(description = "Verify that a user can create a campaign with a specific status and size")
    public void createCampaignWithStatusTest() throws IOException {
    	
        // --- STEP 1: RETRIEVE TEST DATA ---
        // Accessing utilities inherited from BaseClass and generic utility(Excelfileutility,javautility)-- (e.g., pUtil, eUtil, jUtil, wUtil)
    	String URL = putil.togetDataFrompropertiesFile("Url");
        String UN = putil.togetDataFrompropertiesFile("Username");
        String PWD = putil.togetDataFrompropertiesFile("Password");

        ExcelFileUtility eutil= new ExcelFileUtility();
        JavaUtility jutil=new JavaUtility();
        String campaignBaseName = eutil.togetDataFromExcelFile("Campaign", 1, 2);
        String statusValue = eutil.togetDataFromExcelFile("Campaign", 1, 3);
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
        campaignPage.getClosemsg().click();

        // --- STEP 7: FINAL ASSERTION AND REPORTING ---
        Assert.assertEquals(capturedCampaignName, fullCampaignName, "Error: Campaign name mismatch in toast message.");
        
        Reporter.log("Successfully created Campaign: " + fullCampaignName, true);
        Reporter.log("Campaign Details: [URL: " + driver.getCurrentUrl() + " | Status: " + statusValue + " | Size: " + targetSize + "]", true);
    }
}