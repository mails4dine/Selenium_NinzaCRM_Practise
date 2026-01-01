package testng;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Dataproviders {

	@Test(dataProvider="loginDetails")
	public void login(String un,String pwd) {
		
		System.out.println(un+"---------"+pwd);
	}
	@DataProvider
	public Object[][] loginDetails() {
		
		Object[][] objarr= new Object[3][2];
			
		objarr[0][0]="Dhinesh";
		objarr[0][1]="kumat";
		objarr[1][0]="Jenny";
		objarr[1][1]="Thomas";
		objarr[2][0]="harsha";
		objarr[2][1]="adhithi";
		return objarr;
		
	}
}
