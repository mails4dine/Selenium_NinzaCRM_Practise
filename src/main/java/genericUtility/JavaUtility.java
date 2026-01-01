package genericUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class JavaUtility {
	Random ran =new Random();
	public int togetRandomNumber()
	{
		
    int randomCount=ran.nextInt(1000);
    return randomCount;
	}
	public String togetRandomString()
	{
		String chars = "AEJNUZ";
	    Random ran = new Random();
	    StringBuilder sb = new StringBuilder(3);

	    for (int i = 0; i < 6; i++) {
	        sb.append(chars.charAt(ran.nextInt(chars.length())));
	    }
	    return sb.toString();
	}
	public String getCurrentDate()
	{
		Date date=new Date();
		SimpleDateFormat sim=new SimpleDateFormat("dd-MM-yyyy");
		String CurrentDate=sim.format(date);
		return CurrentDate;
	}
	public String togetRequiredDate(int days)
	{
		Date date=new Date();
		   SimpleDateFormat sim=new SimpleDateFormat("dd-MM-yyyy");
		   sim.format(date);
		   Calendar cal=sim.getCalendar();
		   cal.add(Calendar.DAY_OF_MONTH, days);
		  String daterequired= sim.format(cal.getTime());
		  return daterequired;
	}
	
	public String getSystemDateInIST() {
	    Date date = new Date();
	    return date.toString().replace(" ", "_").replace(":", "_");
	}
}
