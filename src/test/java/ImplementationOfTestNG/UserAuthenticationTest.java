package ImplementationOfTestNG;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ObjectRepository.LoginPage;
import genericUtility.BaseClass;

public class UserAuthenticationTest extends BaseClass {

	@Test
    public void loginTest() throws IOException {
		wutil.waitForPagetoLoad(driver);
        wutil.waitForUrlToContain(driver, "dashboard");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("dashboard"), "Login Failed: Dashboard not reached.");
        Reporter.log("Login Successfully", true);
        Reporter.log("URL :"+currentUrl,true);
        Reporter.log("===============================================", true);  

        
    }
}
