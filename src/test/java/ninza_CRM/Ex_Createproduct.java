package ninza_CRM;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Ex_Createproduct {

	public static void main(String[] args) throws InterruptedException {
		ChromeOptions options = new ChromeOptions();

		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);

		options.setExperimentalOption("prefs", prefs);
		WebDriver driver=new ChromeDriver(options);
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("http://49.249.28.218:8098/ ");
		driver.findElement(By.id("username")).sendKeys("rmgyantra");
		driver.findElement(By.id("inputPassword")).sendKeys("rmgy@9999");
		driver.findElement(By.xpath("//button[@class='btn btn-primary btn-lg btn-block']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[text()='Products']")).click();
		Thread.sleep(2000);
	    driver.findElement(By.xpath("//span[contains(text(),'Add Product')]")).click();
	  WebElement name=  driver.findElement(By.name("productName"));
	  Random ran=new Random();
	  int tan=ran.nextInt(1000);
	  String pname="Dhinesh"+tan;
	  name.sendKeys(pname);
	        
	  System.out.println(pname);
	   
	   WebElement category =  driver.findElement(By.name("productCategory"));
	   Select select = new Select(category);
	   select.selectByVisibleText("Electronics");
	   WebElement qty =driver.findElement(By.name("quantity"));
	   qty.clear();
	   qty.sendKeys("40");
	   WebElement target= driver.findElement(By.name("price"));
	   target.clear();
	   target.sendKeys("10");
	   WebElement v_id =  driver.findElement(By.name("vendorId"));
	   Select ret = new Select(v_id);
	   ret.selectByVisibleText("Vendor_22313 - (Electronics)");
	    driver.findElement(By.xpath("//button[contains(text(),'Add')]")).click();
	    
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
