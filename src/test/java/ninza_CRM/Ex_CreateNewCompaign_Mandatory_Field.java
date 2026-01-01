package ninza_CRM;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Ex_CreateNewCompaign_Mandatory_Field {
	
	public static void main(String[] args) throws InterruptedException {
		WebDriver driver=null;
		ChromeOptions options = new ChromeOptions();

		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_leak_detection", false);	
		options.setExperimentalOption("prefs", prefs);
		driver=new ChromeDriver(options);
	
		
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(50));
		driver.manage().window().maximize();
		driver.get("http://49.249.28.218:8098/ ");
		driver.findElement(By.id("username")).sendKeys("rmgyantra");
		driver.findElement(By.id("inputPassword")).sendKeys("rmgy@9999");
		driver.findElement(By.xpath("//button[@class='btn btn-primary btn-lg btn-block']")).click();
		
		Thread.sleep(2000);
        driver.findElement(By.xpath("//span[contains(text(),'Create Campaign')]")).click();
      WebElement name=  driver.findElement(By.name("campaignName"));
      String compaignname="Dhinesh";
      name.sendKeys(compaignname);
        driver.findElement(By.name("campaignStatus")).sendKeys("Active");
       WebElement target= driver.findElement(By.name("targetSize"));
       target.clear();
       target.sendKeys("4");
       driver.findElement(By.name("targetAudience")).sendKeys("Childrens");
      // driver.findElement(By.name("description")).sendKeys("Test automation");
      // WebElement description = wait.until(ExpectedConditions.elementToBeClickable(By.name("description")));
       //description.sendKeys("Test automation");
      // WebElement description = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("description")));
      // description.sendKeys("Test automation");
       WebElement description = wait.until(ExpectedConditions.visibilityOfElementLocated(
    		    By.xpath("//label[contains(text(),'Description')]/..//textarea")));
    		description.sendKeys("Test automation");
        driver.findElement(By.xpath("//button[contains(text(),'Create Campaign')]")).click();
        
       WebElement confirmationpopup= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='Toastify__toast-body']")));
       
       String confirmcreation=confirmationpopup.getText().split(" ")[1].trim();
       System.out.println(confirmcreation);
    //  String Cname= compaignname.split("_")[0].trim();
       
       if(confirmcreation.contains(compaignname))
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
