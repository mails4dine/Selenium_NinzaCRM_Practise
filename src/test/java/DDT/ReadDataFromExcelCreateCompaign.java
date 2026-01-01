package DDT;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReadDataFromExcelCreateCompaign {

	public static void main(String[] args) throws IOException, InterruptedException {
		FileInputStream fis= new FileInputStream("./src/test/resources/Commondata.properties");
		Properties prop=new Properties();
		prop.load(fis);
	String BROWSER = prop.getProperty("Browser");
	String URL = prop.getProperty("Url");
	String USERNAME = prop.getProperty("Username");
	String PASSWORD = prop.getProperty("Password");
		
		WebDriver driver=null;
		if(BROWSER.equals("Chrome"))
		{
			ChromeOptions options = new ChromeOptions();

			Map<String, Object> prefs = new HashMap<>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_leak_detection", false);	
			options.setExperimentalOption("prefs", prefs);
			driver=new ChromeDriver(options);
		}
		
		else if(BROWSER.equals("Edge"))
		{
			new EdgeDriver();
		}
		else if(BROWSER.equals("Firefox"))
		{
			new FirefoxDriver();
		}
	
		driver.manage().window().maximize();
		driver.get(URL);
		driver.findElement(By.id("username")).sendKeys(USERNAME);
		driver.findElement(By.id("inputPassword")).sendKeys(PASSWORD);
		driver.findElement(By.xpath("//button[@class='btn btn-primary btn-lg btn-block']")).click();
		//without status
		FileInputStream fis1= new FileInputStream("./src/test/resources/TestScriptData.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
	Sheet sh=	wb.getSheet("Campaign");
	Row r=sh.getRow(1);

	String campname=	r.getCell(2).getStringCellValue();
    //String status=	r.getCell(3).getStringCellValue();
//double targetsize = r.getCell(5).getNumericCellValue();
    String target =r.getCell(5).getStringCellValue();
    Thread.sleep(2000);
    driver.findElement(By.xpath("//span[contains(text(),'Create Campaign')]")).click();
  WebElement name=  driver.findElement(By.name("campaignName"));
 // String compaignname=campname;
  name.sendKeys(campname);
    //driver.findElement(By.name("campaignStatus")).sendKeys("Active");
   WebElement targetval= driver.findElement(By.name("targetSize"));
   targetval.clear();
   targetval.sendKeys(target);
   
    driver.findElement(By.xpath("//button[contains(text(),'Create Campaign')]")).click();
    WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
   WebElement confirmationpopup= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='Toastify__toast-body']")));
   
   String confirmcreation=confirmationpopup.getText().split(" ")[1].trim();
   System.out.println(confirmcreation);
  String Cname= campname.split("_")[0].trim();
   
   if(confirmcreation.contains(Cname))
   {
	   System.out.println("Compaign created successfully");
   }
   else
   {
	   System.out.println("Compaign Not created ");   
   }
  
   
   
   
  
   
  WebElement close = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='close']")));
  
 Actions action=new Actions(driver);
 action.moveToElement(close).click().perform();
    
    driver.quit();
    
		
	
	}
	}

