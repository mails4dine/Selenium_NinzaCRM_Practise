package ImplementationOfReadFromExcelFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateProduct {

	public static void main(String[] args) throws EncryptedDocumentException, IOException, InterruptedException {
		
		//Read Login credential from Excel sheet
		FileInputStream fis= new FileInputStream("./src/test/resources/TestScriptData.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
	    Sheet sh=	wb.getSheet("Login");
	    Row r=sh.getRow(2);
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
		
		driver.manage().window().maximize();
		driver.get(URL);
		driver.findElement(By.id("username")).sendKeys(USERNAME);
		driver.findElement(By.id("inputPassword")).sendKeys(PASSWORD);
		driver.findElement(By.xpath("//button[@class='btn btn-primary btn-lg btn-block']")).click();
		
		//Read a excel file for add product
				FileInputStream fis1= new FileInputStream("./src/test/resources/TestScriptData.xlsx");
				Workbook wb1 = WorkbookFactory.create(fis1);
			Sheet sh1=	wb1.getSheet("Product");
			Row r1=sh1.getRow(1);
			String prdname=	r1.getCell(1).getStringCellValue();
			String pcat=	r1.getCell(2).getStringCellValue();
			String quantity= r1.getCell(3).getStringCellValue();
			String price= r1.getCell(4).getStringCellValue();
			String vendor= r1.getCell(5).getStringCellValue();


			
				//create a product
			Thread.sleep(2000);
			driver.findElement(By.xpath("//a[text()='Products']")).click();
			Thread.sleep(2000);
		    driver.findElement(By.xpath("//span[contains(text(),'Add Product')]")).click();
				
				 WebElement name=  driver.findElement(By.name("productName"));
				  Random ran=new Random();
				  int addsuffix=ran.nextInt(1000);
				 // String psuffix =String.valueOf(addsuffix);
				 String pname=prdname+addsuffix;
				  name.sendKeys(pname);
				        
				 System.out.println(pname);
				   
				   WebElement category =  driver.findElement(By.name("productCategory"));
				   Select select = new Select(category);
				   select.selectByVisibleText(pcat);
				   WebElement qty =driver.findElement(By.name("quantity"));
				   qty.clear();
				   qty.sendKeys(quantity);
				   WebElement target= driver.findElement(By.name("price"));
				   target.clear();
				   target.sendKeys(price);
				   WebElement v_id =  driver.findElement(By.name("vendorId"));
				   Select ret = new Select(v_id);
				   ret.selectByVisibleText(vendor);
				    driver.findElement(By.xpath("//button[contains(text(),'Add')]")).click();
				    WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
				   WebElement confirmationpopup= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='Toastify__toast-body']")));
				   
				   String confirmcreation=confirmationpopup.getText().split(" ")[1].trim();
				  System.out.println(confirmcreation);
				  
				   
				   if(confirmcreation.contains(pname))
				   {
					   System.out.println("Products Added successfully");
				   }
				   else
				   {
					   System.out.println("Products Not added ");   
				   }
				
				  WebElement close = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='close']")));
				  
				 Actions action=new Actions(driver);
				 action.moveToElement(close).click().perform();
				    
				    driver.quit();

	}
}
