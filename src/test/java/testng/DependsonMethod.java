package testng;

import org.testng.Reporter;
import org.testng.annotations.Test;

public class DependsonMethod {
	@Test
	public void createacc() {
		Reporter.log("created successfully",true);
	}
@Test(dependsOnMethods = "createacc")
	public void editacc() {
		Reporter.log("edited successfully",true);
	}
	@Test (dependsOnMethods = {"editacc","createacc"})
	public void deleteacc() {
		Reporter.log("deleted successfully",true);
	}
}
