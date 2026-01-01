package genericUtility;

import java.io.File;
//import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;
//import java.util.logging.FileHandler;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.io.FileHandler;

public class WebDriverUtility {

	public void waitForPagetoLoad(WebDriver driver)
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
	}
	
	//added new
	public void waitForUrlToChange(WebDriver driver, String oldUrl) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(oldUrl)));
	}
	
	
	//New Added
	public void waitForVisibilityofElement1(WebDriver driver, WebElement element) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    // Add the ignoring clause here
	    wait.ignoring(StaleElementReferenceException.class)
	        .until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForVisibilityofElement(WebDriver driver,WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.ignoring(StaleElementReferenceException.class)
        .until(ExpectedConditions.visibilityOf(element));
	}
	//added new
	public void waitForUrlToContain(WebDriver driver, String urlPartialText) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    wait.until(ExpectedConditions.urlContains(urlPartialText));
	    
	}
	public void switchTOFrame(WebDriver driver,int index)
	{
		driver.switchTo().frame(index);
	}
	public void switchTOFrame(WebDriver driver,String nameOrID)
	{
		driver.switchTo().frame(nameOrID);
	}
	public void switchTOFrame(WebDriver driver,WebElement element)
	{
		driver.switchTo().frame(element);
	}
	public void switchToAlertAndAccept(WebDriver driver)
	{
		driver.switchTo().alert().accept();
	}
	public void switchToAlertAndDismiss(WebDriver driver)
	{
		driver.switchTo().alert().dismiss();
	}
	public String switchToAlertAndText(WebDriver driver)
	{
		String text1=driver.switchTo().alert().getText();
		return text1;
	}
	public void switchToAlertAndSendKeys(WebDriver driver,String text)
	{
		driver.switchTo().alert().sendKeys(text);
	}
	public void select(WebElement element,int index)
	{
		Select sel =new Select(element);
		sel.selectByIndex(index);
	}
	public void select(WebElement element,String value)
	{
		Select sel =new Select(element);
		sel.selectByValue(value);
	}
	public void select(String text,WebElement element)
	{
		Select sel =new Select(element);
		sel.selectByVisibleText(text);
	}
	public void mouseHoverOnWebElement(WebDriver driver,WebElement element)
	{
		Actions act=new Actions(driver);
		act.moveToElement(element).perform();
	}
	public void singleClick(WebElement element) {
	    element.click();
	}

	public void doubleClick(WebDriver driver, WebElement element) {
	    Actions act = new Actions(driver);
	    act.doubleClick(element).perform();
	}

	public void rightClick(WebDriver driver, WebElement element) {
	    Actions act = new Actions(driver);
	    act.contextClick(element).perform();
	}

	public void passInput(WebElement element, String text) {
	    element.clear();
	    element.sendKeys(text);
	}

	public void switchToWindow(WebDriver driver) {
	    Set<String> allWindowIds = driver.getWindowHandles();
	    for (String id : allWindowIds) {
	        driver.switchTo().window(id);
	    }
	}

	/*
	 * public void takeScreenshot(WebDriver driver, String fileName) throws
	 * IOException { TakesScreenshot ts = (TakesScreenshot) driver; File src =
	 * ts.getScreenshotAs(OutputType.FILE); File dest = new File("./errorshots/" +
	 * fileName + ".png"); FileHandler.copy(src, dest); }
	 */
	public String takeScreenshot(WebDriver driver, String fileName) throws IOException {
	    JavaUtility jUtil = new JavaUtility();
	    String timeStamp = jUtil.getSystemDateInIST(); 
	    TakesScreenshot ts = (TakesScreenshot) driver;
	    File src = ts.getScreenshotAs(OutputType.FILE);
	    
	    File folder = new File("./errorshots/");
	    if (!folder.exists()) {
	        folder.mkdirs();
	    }
	    
	    File dest = new File(folder, fileName + "_" + timeStamp + ".png");
	    FileHandler.copy(src, dest);
	    
	    // Return the absolute path so the Listener can find it
	    return dest.getAbsolutePath();
	}

}
