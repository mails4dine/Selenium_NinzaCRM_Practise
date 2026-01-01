package testng;

import org.testng.Reporter;
import org.testng.annotations.Test;

public class Priority {

	
	
		@Test(priority =2)
		public void add()
		{
			Reporter.log("add",true);
		}
		
		@Test(enabled = false)
		public void sub()
		{
			Reporter.log("sub",true);
		}
		
		@Test(priority =-2)
		public void mul()
		{
			Reporter.log("mul",true);
		}
		

	

}
