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
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ListenerImplementation implements ITestListener {

    public ExtentReports report;
    public static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>(); 
    // This variable will hold the absolute path
    public static String absolutePath;

    @Override
    public void onStart(ITestContext context) {
        // Use a simpler date format to avoid OS character issues
        String time = new Date().toString().replace(" ", "_").replace(":", "_");
        
        // Define the path clearly
        String fileName = "Report_" + time + ".html";
        File directory = new File("./ExtentReports/");
        if (!directory.exists()) directory.mkdirs();
        
        // Create an absolute path string
        File reportFile = new File(directory, fileName);
        absolutePath = reportFile.getAbsolutePath();
        
        ExtentSparkReporter spark = new ExtentSparkReporter(absolutePath);
        spark.config().setDocumentTitle("Ninza CRM Execution Report");
        spark.config().setReportName("Regression Suite");
        spark.config().setTheme(Theme.DARK);

        report = new ExtentReports();
        report.attachReporter(spark);
    }

    @Override
    public void onFinish(ITestContext context) {
        report.flush(); 
        
        // Use a small delay to ensure the OS has finished writing the file
        try {
            Thread.sleep(2000); 
            File file = new File(absolutePath);
            if (file.exists()) {
                Desktop.getDesktop().browse(file.toURI());
            } else {
                System.out.println("Wait... file still not found at: " + absolutePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ... other methods
}