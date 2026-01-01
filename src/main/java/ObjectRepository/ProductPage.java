package ObjectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage {

	WebDriver driver;
	public ProductPage(WebDriver driver) {
		
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//span[text()='Add Product']")
	private WebElement addProductBtn;
	
	@FindBy(name="productName")
	private WebElement prodName;
	
	@FindBy(name="productCategory")
	private WebElement prodCategory;
	
	@FindBy(name="quantity")
	private WebElement quantity;
		
	
	@FindBy(name="price")
	private WebElement price;
	
	@FindBy(name="vendorId")
	private WebElement vendor;
	
	@FindBy(xpath="//button[contains(text(),'Add')]")
	private WebElement addProdSubmitBtn;
	
	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getAddProductBtn() {
		return addProductBtn;
	}

	public WebElement getProdName() {
		return prodName;
	}

	public WebElement getProdCategory() {
		return prodCategory;
	}

	public WebElement getQuantity() {
		return quantity;
	}

	public WebElement getPrice() {
		return price;
	}

	public WebElement getVendor() {
		return vendor;
	}

	public WebElement getAdd() {
		return addProdSubmitBtn;
	}
	
	
	
	
}
