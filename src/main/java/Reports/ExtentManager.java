package Reports;


import Services.GlobalVariables;
import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
//import com.aventstack.extentreports.reporter.ExtentLoggerReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ExtentHtmlReporterConfiguration;
import com.aventstack.extentreports.reporter.configuration.ExtentSparkReporterConfiguration;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.exec.OS;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;

public class ExtentManager {
    private GlobalVariables globalVariables;
    private String fileName;
    private ExtentReports extent;
    private ExtentTest test;
    private ExtentHtmlReporter htmlReporter;
    private ExtentSparkReporter sparkReporter;
    private static final String filePath = "./test-output/extentReports/extentreport.html";
    private static final String newFilePath = "./test-output/extentReports/newReport.html";

    public int count=0;
    private int flushCount=0;

    public ExtentManager() {
    }

//    public void setGV(GlobalVariables globalVariables2){
//        globalVariables=globalVariables2;
//    }

    public ExtentReports GetExtent(){
        if (extent != null)
            return extent; //avoid creating new instance of html file
        extent = new ExtentReports();
        extent.attachReporter(getHtmlReporter());
        extent.attachReporter(getSparkReporter());
        return extent;
    }

    private ExtentHtmlReporter getHtmlReporter() {
        htmlReporter = new ExtentHtmlReporter(filePath);
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("Regression cycle");
        htmlReporter.config().setProtocol(Protocol.HTTP);
        return htmlReporter;
    }


    private ExtentSparkReporter getSparkReporter() {
        sparkReporter = new ExtentSparkReporter(newFilePath);
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Regression cycle");
        sparkReporter.config().enableTimeline(true);
        sparkReporter.config().setProtocol(Protocol.HTTP);
        sparkReporter.config().setTheme(Theme.DARK);
        return sparkReporter;
    }

    public ExtentTest createTest(String name, String description){
        test = extent.createTest(name, description);
        return test;
    }

    public void reportEnvironment(GlobalVariables globalVariables2){
       // if (globalVariables2.driver!=null)
        if (count==0) {
            count++;
           // Capabilities caps = ((RemoteWebDriver) globalVariables2.driver).getCapabilities();

            globalVariables2.extent.setSystemInfo("Login User:",System.getProperty("user.name"));
            try {
                java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
                globalVariables2.extent.setSystemInfo("Machine Name: ",localMachine.getHostName());

            }
            catch (Exception e){
                System.out.println("Exception - Trying to get IP Address of Machine, ExtentManager.Java|reportEnvironment");
            }
        }


    }
    void flushReportingDirectory() {
        if (flushCount == 0) {
            System.out.println("Cleaning Directory");
            try {
                FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + "\\test-output\\extentReports"));
                if (new File(System.getProperty("user.dir") + "\\test-output\\extentReports\\extentreport.html").exists())
                    FileUtils.forceDelete(new File(System.getProperty("user.dir") + "\\test-output\\extentReports\\extentreport.html"));
            } catch (Exception e) {
                globalVariables.commonFunc.logInfo( "Exception - Trying to clean directory, BaseController.Java|Starter");
            }
            flushCount++;
        }
    }
}