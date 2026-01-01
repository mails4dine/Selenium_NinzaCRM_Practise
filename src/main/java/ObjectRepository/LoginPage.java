package ObjectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericUtility.WebDriverUtility;

public class LoginPage {

	WebDriver driver;
	public LoginPage(WebDriver driver) {
		
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	@FindBy(id="username")
	private WebElement UN;
	
	@FindBy(id="inputPassword")
	private WebElement PWD;
	
	@FindBy(xpath="//button[contains(text(),'Sign In')]")
	private WebElement LoginBtn;
	
	

	public WebElement getUN() {
		return UN;
	}



	public WebElement getPWD() {
		return PWD;
	}

	
	public WebElement getLoginBtn() {
		return LoginBtn;
	}
	
	public void loginToApplication(String username, String password) throws InterruptedException {
		getUN().sendKeys(username);
        getPWD().sendKeys(password);
        WebDriverUtility wutil=new WebDriverUtility();
      
        wutil.waitForVisibilityofElement(driver,getUN());
        wutil.waitForVisibilityofElement(driver,getPWD());
        wutil.waitForVisibilityofElement(driver,getLoginBtn());
        getLoginBtn().click();
       
	}
	
}
