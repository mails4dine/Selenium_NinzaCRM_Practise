package ImplementationOfTestNG._Commonloginpage_BaseClasss;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;


import genericUtility.BaseClass1;

public class UserAuthenticationTest extends BaseClass1 {

	@Test
    public void loginTest() throws IOException {
		
        wutil.waitForUrlToContain(driver, "dashboard");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("dashboard"), "Login Failed: Dashboard not reached.");
        Reporter.log("Login Successfully", true);
        Reporter.log("URL :"+currentUrl,true);
       // Reporter.log("===============================================", true);  

        
    }
}
