package genericUtility;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class ListenerImplementation implements ITestListener {

    public ExtentReports report;
    public static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>(); 
    WebDriverUtility wUtil = new WebDriverUtility();

        // 1. Declare a global string to hold the report location
        String reportFullName;

        @Override
        public void onStart(ITestContext context) {
            String time = new Date().toString().replace(" ", "_").replace(":", "_");
            
            // 2. Assign the path here once
            reportFullName = "./ExtentReports/Report_" + time + ".html";
            
            ExtentSparkReporter spark = new ExtentSparkReporter(reportFullName);
            spark.config().setDocumentTitle("Ninza CRM Execution Report");
            // ... rest of your config
            
            report = new ExtentReports();
            report.attachReporter(spark);
        }

        @Override
        public void onFinish(ITestContext context) {
            report.flush();
            
            // 3. Use that same variable to open the file
            File file = new File(reportFullName);
            try {
                Desktop.getDesktop().browse(file.toURI());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
}