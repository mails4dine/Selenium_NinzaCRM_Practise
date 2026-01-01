package ImplementationOfGenericUtility;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;

public class CreateProduct {

	public static void main(String[] args) throws IOException {
		ExcelFileUtility eutil=new ExcelFileUtility();
		JavaUtility jutil=new JavaUtility();
		PropertyFileUtility putil=new PropertyFileUtility();
		WebDriverUtility wutil=new WebDriverUtility();
		
		
		String BROWSER=putil.togetDataFrompropertiesFile("Browser");
		String URL=putil.togetDataFrompropertiesFile("Url");
		String USERNAME=putil.togetDataFrompropertiesFile("Username");
		String PASSWORD=putil.togetDataFrompropertiesFile("Password");
		
		
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
		wutil.waitForPagetoLoad(driver);
		driver.get(URL);
		driver.findElement(By.id("username")).sendKeys(USERNAME);
		driver.findElement(By.id("inputPassword")).sendKeys(PASSWORD);
		WebElement signin =	driver.findElement(By.xpath("//button[contains(text(),'Sign In')]"));
		wutil.singleClick(signin);
		
		String ranNum = String.valueOf(jutil.togetRandomNumber());
		String pname=eutil.togetDataFromExcelFile("Product", 1, 1);
		//String pcat=eutil.togetDataFromExcelFile("Product", 1, 2);
		String quantity=eutil.togetDataFromExcelFile("Product", 1, 3);
		String price=eutil.togetDataFromExcelFile("Product", 1, 4);
		//String vendor=eutil.togetDataFromExcelFile("Product", 1, 5);
	   
	
		driver.findElement(By.xpath("//a[text()='Products']")).click();
		WebElement addproduct=driver.findElement(By.xpath("//span[contains(text(),'Add Product')]"));
		wutil.singleClick(addproduct);
			
			   WebElement name=  driver.findElement(By.name("productName"));			  
			   name.sendKeys(pname+ranNum);			   
			   WebElement category =  driver.findElement(By.name("productCategory"));
			   wutil.select(category,3);			  
			   WebElement qty =driver.findElement(By.name("quantity"));
			   qty.clear();
			   qty.sendKeys(quantity);
			   WebElement target= driver.findElement(By.name("price"));
			   target.clear();
			   target.sendKeys(price);
			   WebElement v_id =  driver.findElement(By.name("vendorId"));
			   wutil.select(v_id, 3);
			   WebElement addnewprod =    driver.findElement(By.xpath("//button[contains(text(),'Add')]"));
			   wutil.singleClick(addnewprod);
			    WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
			   WebElement toastmsg= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='Toastify__toast-body']")));
			   
			   String msg=toastmsg.getText().split(" ")[1].trim();
			  			   
			   if(msg.contains(pname))
			   {
				   System.out.println("Products Added successfully");
			   }
			   else
			   {
				   System.out.println("Products Not added ");   
			   }
			  
			   
			   
			   
			  
			   
			  WebElement close = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='close']")));
			  wutil.mouseHoverOnWebElement(driver, close);
				System.out.println("------------------------------");
			    System.out.println("Product Name :" +msg);
			    			    
			    driver.quit();

		
	}
}
