package ImplementationOfReadFromExcelFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.poi.EncryptedDocumentException;
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

public class CreateCompaignwithMandatoryField {

	public static void main(String[] args) throws EncryptedDocumentException, IOException, InterruptedException {
		
		
		//Read login credential from ExcelFile
		FileInputStream fis= new FileInputStream("./src/test/resources/TestScriptData.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
	    Sheet sh=	wb.getSheet("Login");
	    Row r=sh.getRow(1);
	     String BROWSER=	r.getCell(0).getStringCellValue();
	     String URL    =	r.getCell(1).getStringCellValue();
	     String USERNAME=	r.getCell(2).getStringCellValue();
	     String PASSWORD=	r.getCell(3).getStringCellValue();
		
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
			driver=new EdgeDriver();
		}
		else if(BROWSER.equals("Firefox"))
		{
			driver=new FirefoxDriver();
		}
		//WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get(URL);
		driver.findElement(By.id("username")).sendKeys(USERNAME);
		driver.findElement(By.id("inputPassword")).sendKeys(PASSWORD);
		driver.findElement(By.xpath("//button[@class='btn btn-primary btn-lg btn-block']")).click();
		
		//create Campaign
		FileInputStream fis1= new FileInputStream("./src/test/resources/TestScriptData.xlsx");
		Workbook wb1 = WorkbookFactory.create(fis1);
	Sheet sh1=	wb1.getSheet("Campaign");
	Row r1=sh1.getRow(1);

	String campname=	r1.getCell(2).getStringCellValue();
   // String status=	r1.getCell(3).getStringCellValue();

    String target =r1.getCell(5).getStringCellValue();
    Thread.sleep(2000);
    driver.findElement(By.xpath("//span[contains(text(),'Create Campaign')]")).click();
  WebElement name=  driver.findElement(By.name("campaignName"));
  String compaignname=campname;
  name.sendKeys(compaignname);
   // driver.findElement(By.name("campaignStatus")).sendKeys(status);
   WebElement targetval= driver.findElement(By.name("targetSize"));
   targetval.clear();
   targetval.sendKeys(target);
   
    driver.findElement(By.xpath("//button[contains(text(),'Create Campaign')]")).click();
    WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
   WebElement toastmsg= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='Toastify__toast-body']")));
   
   String msg=toastmsg.getText().split(" ")[1].trim();
   System.out.println(msg);
  String Cname= compaignname.split("_")[0].trim();
   
   if(msg.contains(Cname))
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
