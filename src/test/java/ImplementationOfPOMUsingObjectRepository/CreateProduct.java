package ImplementationOfPOMUsingObjectRepository;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ImplementationOfPropertyFile.Login;
import ObjectRepository.CampaignPage;
import ObjectRepository.HomePage;
import ObjectRepository.LoginPage;
import ObjectRepository.ProductPage;
import genericUtility.ExcelFileUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertyFileUtility;
import genericUtility.WebDriverUtility;

public class CreateProduct {

	public static void main(String[] args) throws EncryptedDocumentException, IOException, InterruptedException {
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
		LoginPage lp=new LoginPage(driver);
		lp.getUN().sendKeys(USERNAME);
		lp.getPWD().sendKeys(PASSWORD);
		
		WebElement signin =lp.getLoginBtn();
		wutil.singleClick(signin);
		
		String ranNum = String.valueOf(jutil.togetRandomNumber());
		String pname=eutil.togetDataFromExcelFile("Product", 1, 1);
		//String pcat=eutil.togetDataFromExcelFile("Product", 1, 2);
		String quantity=eutil.togetDataFromExcelFile("Product", 1, 3);
		String price=eutil.togetDataFromExcelFile("Product", 1, 4);
		//String vendor=eutil.togetDataFromExcelFile("Product", 1, 5);
	   ProductPage p=new ProductPage(driver);
		HomePage hp=new HomePage(driver);
		hp.getProduct().click();
			
		WebElement addproduct=p.getAddProductBtn();
		wutil.singleClick(addproduct);
			
		
			   WebElement name= p.getProdName();			  
			   name.sendKeys(pname+ranNum);			   
			   WebElement category =  p.getProdCategory();
			   wutil.select(category,3);			  
			   WebElement qty =p.getQuantity();
			   qty.clear();
			   qty.sendKeys(quantity);
			   WebElement target= p.getPrice();
			   target.clear();
			   target.sendKeys(price);
			   WebElement v_id =  p.getVendor();
			   wutil.select(v_id, 3);
			   wutil.waitForPagetoLoad(driver);
			   WebElement addnewprod =  p.getAdd();
			   wutil.singleClick(addnewprod);
			  
			    CampaignPage cp=new CampaignPage(driver);
			   WebElement toastmsg= cp.getToastmsg();
			   
			
			
			  Thread.sleep(2000);
			  String msg=toastmsg.getText().split(" ")[1].trim();
			   System.out.println(msg);
			 
			   if(msg.equalsIgnoreCase(pname))
			   {
				   System.out.println("Products Added successfully");
			   }
			   else
			   {
				   System.out.println("Products Not added ");   
			   }
			    
			   wutil.waitForPagetoLoad(driver);
			   
			  WebElement close = cp.getClosemsg();
			 wutil.mouseHoverOnWebElement(driver, close);
				System.out.println("------------------------------");
			   System.out.println("Product Name :" +msg);
			    			    
			    driver.quit();
	}
}
