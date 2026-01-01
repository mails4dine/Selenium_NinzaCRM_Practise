package genericUtility;

import java.io.IOException;
import java.util.Date;
import org.openqa.selenium.WebDriver;
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
    WebDriverUtility wUtil = new WebDriverUtility();

    @Override
    public void onStart(ITestContext context) {
        // Initialize the report
        String time = new Date().toString().replace(" ", "_").replace(":", "_");
        ExtentSparkReporter spark = new ExtentSparkReporter("./ExtentReports/Report_" + time + ".html");
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
        // Create test entry and save to ThreadLocal
        ExtentTest test = report.createTest(result.getMethod().getMethodName());
        testThread.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testThread.get().log(Status.PASS, result.getMethod().getMethodName() + " PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        WebDriver driver = null;
        
        // Log the failure in report
        testThread.get().log(Status.FAIL, methodName + " FAILED");
        testThread.get().fail(result.getThrowable());

        try {
            // Get driver instance from the failed test class
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (Exception e) {
            testThread.get().log(Status.WARNING, "Could not access driver for screenshot.");
        }

        if (driver != null) {
            try {
                // Take screenshot and get the file path
                String path = wUtil.takeScreenshot(driver, methodName);
                // Attach screenshot to the report
                testThread.get().addScreenCaptureFromPath(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        report.flush();
    }
}