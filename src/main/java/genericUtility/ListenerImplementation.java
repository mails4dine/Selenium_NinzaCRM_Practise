package genericUtility;

import java.awt.Desktop;
import java.io.File;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ListenerImplementation implements ITestListener {

    public ExtentReports report;
    public static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>(); 
    public static String reportFullName;
    
    // Initialize Utility here so it can be used in onTestFailure
    WebDriverUtility wutil = new WebDriverUtility();

    @Override
    public void onStart(ITestContext context) {
        String time = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        File reportDir = new File("ExtentReports"); 
        if (!reportDir.exists()) reportDir.mkdirs();
        
        reportFullName = reportDir.getAbsolutePath() + File.separator + "Report_" + time + ".html";
        
        ExtentSparkReporter spark = new ExtentSparkReporter(reportFullName);
        spark.config().setDocumentTitle("Ninza CRM Execution Report");
        spark.config().setReportName("Regression Suite");
        spark.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);

        report = new ExtentReports();
        report.attachReporter(spark);
        report.setSystemInfo("Platform", "Windows 11");
        report.setSystemInfo("Tester", "Dhinesh");
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
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        testThread.get().log(Status.FAIL, testName + " ==> FAILED");
        testThread.get().log(Status.FAIL, result.getThrowable());

        try {
            // Captures screenshot and returns absolute path
            // Ensure BaseClass.sdriver is your static WebDriver instance
            String path = wutil.takeScreenshot(BaseClass.sdriver, testName);
            testThread.get().addScreenCaptureFromPath(path);
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testThread.get().log(Status.SKIP, result.getMethod().getMethodName() + " SKIPPED");
        testThread.get().log(Status.SKIP, result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        if (report != null) {
            report.flush();
            try {
                Thread.sleep(2000); 
                File file = new File(reportFullName);
                if (file.exists()) {
                    Desktop.getDesktop().browse(file.toURI());
                }
            } catch (Exception e) {
                System.out.println("Auto-open failed: " + e.getMessage());
            }
        } else {
            System.out.println("Extent Report was not initialized. Skipping flush.");
        }
    }
}