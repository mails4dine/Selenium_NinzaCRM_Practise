package testng;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test; // Import TestNG Annotation

import ObjectRepository.LoginPage;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;

public class Login {

    @Test
    public void loginTest() throws IOException, InterruptedException {
        PropertyFileUtility putil = new PropertyFileUtility();
        WebDriverUtility wutil = new WebDriverUtility();

        String BROWSER = putil.togetDataFrompropertiesFile("Browser");
        String URL = putil.togetDataFrompropertiesFile("Url");
        String USERNAME = putil.togetDataFrompropertiesFile("Username");
        String PASSWORD = putil.togetDataFrompropertiesFile("Password");

        WebDriver driver = null;
        
        if (BROWSER.equalsIgnoreCase("Chrome")) {
            ChromeOptions options = new ChromeOptions();            
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_leak_detection", false);	
			options.setExperimentalOption("prefs", prefs);
            driver = new ChromeDriver(options);
        } else if (BROWSER.equalsIgnoreCase("Edge")) {
            driver = new EdgeDriver();
        } else if (BROWSER.equalsIgnoreCase("Firefox")) {
            driver = new FirefoxDriver();
        }

        driver.manage().window().maximize();
        wutil.waitForPagetoLoad(driver);
        driver.get(URL);

        LoginPage lp = new LoginPage(driver);
        lp.getUN().sendKeys(USERNAME);
        lp.getPWD().sendKeys(PASSWORD);

        WebElement signin = lp.getLoginBtn();
        wutil.singleClick(signin);
        
      
        wutil.waitForUrlToContain(driver, "dashboard");
       
        
       
      
        System.out.println("Title: " + driver.getTitle());
        System.out.println("URL: " + driver.getCurrentUrl());
       
        
        // Validation
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Home page URL does not contain 'dashboard'");
        System.out.println("Assertion Passed: Title is correct.");
        Reporter.log(driver.getCurrentUrl(), true);
        
       driver.quit();
    }
}
