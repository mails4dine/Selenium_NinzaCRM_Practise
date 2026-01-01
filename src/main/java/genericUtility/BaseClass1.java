package genericUtility;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import ObjectRepository.HomePage;
import ObjectRepository.LoginPage;

public class BaseClass1 {
    public WebDriver driver;    
    public PropertyFileUtility putil = new PropertyFileUtility();
    public WebDriverUtility wutil = new WebDriverUtility();
    public ExcelFileUtility eutil = new ExcelFileUtility();
    public JavaUtility jutil = new JavaUtility();
    
    
    @BeforeSuite
    public void configBeforeSuite() {
        System.out.println("--- Cleaning old screenshots and Connecting to DB ---");
        File folder = new File("./errorshots/");
        if (folder.exists()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File f : files) f.delete();
            }
        }
    }
    @BeforeClass
    public void launchBrowser() throws IOException, InterruptedException {
    	String BROWSER = putil.togetDataFrompropertiesFile("Browser");
        if (BROWSER.equalsIgnoreCase("chrome")) {
        	ChromeOptions options = new ChromeOptions();
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_leak_detection", false);
			options.addArguments("--remote-allow-origins=*");
			System.setProperty("webdriver.chrome.silentOutput", "true");
            java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.OFF);
			options.setExperimentalOption("prefs", prefs);
			
			driver=new ChromeDriver(options);
        } else if (BROWSER.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            driver = new EdgeDriver();
        }        
        driver.manage().window().maximize();
               
    }

    @BeforeMethod
    public void loginToApp() throws IOException, InterruptedException {
    	
    	String URL = putil.togetDataFrompropertiesFile("Url");
        String UN = putil.togetDataFrompropertiesFile("Username");
        String PWD = putil.togetDataFrompropertiesFile("Password");        
        driver.get(URL);
        wutil.waitForPagetoLoad(driver);
        LoginPage loginPage = new LoginPage(driver);  
        Thread.sleep(2000);
        loginPage.loginToApplication(UN, PWD); 
        
    }

    @AfterMethod
    public void logoutFromApp() throws InterruptedException {
    	HomePage homepage=new HomePage(driver); 
    	Thread.sleep(2000);
        homepage.logoutToApplication();        
    }

    @AfterClass
    public void closeBrowser() throws InterruptedException {
        if (driver != null) {
            Thread.sleep(1000);
            driver.quit();
        }
    }

    @AfterSuite
    public void configAfterSuite() {
        System.out.println("--- Registering Email Shutdown Hook ---");

        // Use Shutdown Hook to allow Maven/TestNG time to write the final HTML reports to disk
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                // Extended wait time to ensure file I/O completion
                Thread.sleep(5000); 
                
                java.util.List<String> paths = new java.util.ArrayList<>();

                // 1. Collect Screenshots from the current run
                File screenshotFolder = new File("./errorshots/");
                if (screenshotFolder.exists()) {
                    File[] screenshots = screenshotFolder.listFiles();
                    if (screenshots != null) {
                        for (File file : screenshots) {
                            if (file.isFile()) paths.add(file.getAbsolutePath());
                        }
                    }
                }

                // 2. Collect HTML Reports (Checking both Maven and Local TestNG locations)
                String[] reportLocations = {
                    "./target/surefire-reports/emailable-report.html",
                    
                    "./target/surefire-reports/index.html",
                   
                };

                for (String reportPath : reportLocations) {
                    File reportFile = new File(reportPath);
                    if (reportFile.exists()) {
                        paths.add(reportFile.getAbsolutePath());
                        System.out.println("Found report: " + reportPath);
                    }
                }

                // 3. Send Email
                if (!paths.isEmpty()) {
                    EmailUtility mail = new EmailUtility();
                    mail.sendEmailWithReports(paths.toArray(new String[0]));
                    System.out.println("--- Shutdown Hook: Email Sent with " + paths.size() + " attachments ---");
                } else {
                    System.out.println("--- Shutdown Hook: No reports or screenshots found to email ---");
                }
            } catch (Exception e) {
                System.err.println("Failed to execute Email Shutdown Hook");
                e.printStackTrace();
            }
        }));
    }
}