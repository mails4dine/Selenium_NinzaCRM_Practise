package ImplementationOfTestNG;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ObjectRepository.HomePage;
import genericUtility.BaseClass1;

public class UserSignOutTest extends BaseClass1 {

	@Test
    public void logoutTest() throws IOException {
		wutil.waitForPagetoLoad(driver);
        HomePage homePage = new HomePage(driver);
	    WebElement usericon = homePage.getUsericon();		
	    wutil.mouseHoverOnWebElement(driver, usericon);		
	    WebElement logout = homePage.getLogoutBtn();
		wutil.singleClick(logout);		
		wutil.waitForPagetoLoad(driver);
	    String currenturl = driver.getCurrentUrl();   
	        
	        Assert.assertTrue(currenturl.endsWith("/"),"Logout Failed: Not redirected to Login page.");
	        Reporter.log("Logout Successfully", true);
	        Reporter.log("URL :"+currenturl,true);
	        Reporter.log("===============================================", true);
    }
}
