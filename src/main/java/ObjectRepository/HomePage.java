package ObjectRepository;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericUtility.WebDriverUtility;

public class HomePage {

	WebDriver driver;
	public HomePage(WebDriver driver) {
		
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	@FindBy(linkText="Campaigns")
	private WebElement campaign;
	
	@FindBy(linkText="Products")
	private WebElement product;
	
	@FindBy(xpath="//span[contains(text(),'Create Campaign')]")
	private WebElement createCampBtn;
	
	
	

	@FindBy(xpath="//div[@class='user-icon']")
	private WebElement usericon;
	
	@FindBy(xpath="//div[@class='dropdown-item logout']")
	private WebElement logoutBtn;
	
	public WebElement getCampaign() {
		return campaign;
	}

	
	public WebElement getProduct() {
		return product;
	}
	
	public WebElement getCreateCampBtn() {
		return createCampBtn;
	}


	

	public WebElement getUsericon() {
		return usericon;
	}

	

	public WebElement getLogoutBtn() {
		return logoutBtn;
	}

	public void logoutToApplication() {
	    WebDriverUtility wutil = new WebDriverUtility();
	    int retryCount = 0;
	    
	    // Retry up to 3 times if a Stale Element exception occurs
	    while(retryCount < 3) {
	        try {
	            wutil.waitForVisibilityofElement(driver, campaign);
	            usericon.click();
	            
	            wutil.waitForVisibilityofElement(driver, getLogoutBtn());
	            getLogoutBtn().click();
	            break; // Exit loop if successful
	        } catch (StaleElementReferenceException e) {
	            retryCount++;
	            // Re-initialize elements if necessary or just wait a moment
	            if(retryCount == 3) throw e; 
	        }
	    }
	}
	
	
}
