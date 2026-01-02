package genericUtility;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException; // Added this import
import java.util.Date;
import java.text.SimpleDateFormat;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ListenerImplementation implements ITestListener {

    public ExtentReports report;
    public static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>(); 
    // Using one consistent variable name
    public static String reportFullName;

    @Override
    public void onStart(ITestContext context) {
        // Use hyphens and underscores instead of colons (Windows compatibility)
        String time = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        
        File reportDir = new File("./ExtentReports/");
        if (!reportDir.exists()) reportDir.mkdirs();
        
        // Save the absolute path so the Browser can find it exactly
        reportFullName = reportDir.getAbsolutePath() + "/Report_" + time + ".html";
        
        ExtentSparkReporter spark = new ExtentSparkReporter(reportFullName);
        spark.config().setDocumentTitle("Ninza CRM Execution Report");
        spark.config().setReportName("Regression Suite");
        spark.config().setTheme(Theme.DARK);

        report = new ExtentReports();
        report.attachReporter(spark);
        report.setSystemInfo("OS", "Windows 11");
        report.setSystemInfo("Browser", "Chrome 143");
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = report.createTest(result.getMethod().getMethodName());
        testThread.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testThread.get().log(Status.PASS, result.getMethod().getMethodName() + " PASSED");
    }

 
    @Override
    public void onFinish(ITestContext context) {
        report.flush();
        
        // Give the system a moment to release the file lock
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        File file = new File(reportFullName);
        if (file.exists()) {
            try {
                // FIX: Using toURI() is good, but Desktop.getDesktop().open() 
                // is often more reliable on Windows for local HTML files
                Desktop.getDesktop().browse(file.toURI());
            } catch (IOException e) {
                System.out.println("Browser auto-open failed, but report is at: " + reportFullName);
            }
        } else {
            System.out.println("Report file not found at: " + file.getAbsolutePath());
        }
    }
}