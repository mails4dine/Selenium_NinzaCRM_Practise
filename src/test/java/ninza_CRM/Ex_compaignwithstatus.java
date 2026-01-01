package ninza_CRM;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Ex_compaignwithstatus {
public static void main(String[] args) throws InterruptedException {
	WebDriver driver=new ChromeDriver();
	WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
	driver.manage().window().maximize();
	driver.get("http://49.249.28.218:8098/ ");
	driver.findElement(By.id("username")).sendKeys("rmgyantra");
	driver.findElement(By.id("inputPassword")).sendKeys("rmgy@9999");
	driver.findElement(By.xpath("//button[@class='btn btn-primary btn-lg btn-block']")).click();
	
	Thread.sleep(2000);
    driver.findElement(By.xpath("//span[contains(text(),'Create Campaign')]")).click();
  WebElement name=  driver.findElement(By.name("campaignName"));
  String compaignname="Jen_02";
  name.sendKeys(compaignname);
    driver.findElement(By.name("campaignStatus")).sendKeys("Active");
   WebElement target= driver.findElement(By.name("targetSize"));
   target.clear();
   target.sendKeys("4");
   
    driver.findElement(By.xpath("//button[contains(text(),'Create Campaign')]")).click();
    
   WebElement confirmationpopup= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='Toastify__toast-body']")));
   
   String confirmcreation=confirmationpopup.getText().split(" ")[1].trim();
   System.out.println(confirmcreation);
  String Cname= compaignname.split("_")[0].trim();
   
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
