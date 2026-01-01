package genericUtility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

public class BaseClass {
    public WebDriver driver;
    public static WebDriver sdriver;
    
    public PropertyFileUtility putil = new PropertyFileUtility();
    public WebDriverUtility wutil = new WebDriverUtility();

    @BeforeClass
    public void bcConfig() throws IOException {
        String BROWSER = putil.togetDataFrompropertiesFile("Browser");
        if (BROWSER.equalsIgnoreCase("chrome")) {
        	ChromeOptions options = new ChromeOptions();

			Map<String, Object> prefs = new HashMap<>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_leak_detection", false);	
			options.setExperimentalOption("prefs", prefs);
			driver=new ChromeDriver(options);
        } else if (BROWSER.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            driver = new EdgeDriver();
        }
        sdriver = driver;
        driver.manage().window().maximize();
        wutil.waitForPagetoLoad(driver);
    }

    @AfterClass
    public void acConfig() {
        if (driver != null) {
            driver.quit();
        }
    }
}