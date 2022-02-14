package Controller;


import Reports.ExtentManager;
import Services.*;
import com.aspose.email.*;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.IHookCallBack;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import org.testng.internal.TestResult;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
@Listeners(Listener.class)
public class BaseController implements org.testng.IHookable{
    private WebDriver driver;
    protected volatile GlobalVariables globalVariables;

    public BaseController(){
    }

    @BeforeSuite()
    public void Starter(ITestContext context) {     //Starter starts the browser on which testing is to be conducted
        globalVariables=GlobalVariablesManager.objectCreationMethod().getGlobalVariable();
        String log4jConfPath = System.getProperty("user.dir") + "/log4j2.properties";
        PropertyConfigurator.configure(log4jConfPath);
        System.out.println(java.lang.Thread.currentThread());
        System.out.println(java.lang.Thread.activeCount());
        globalVariables.suiteName=context.getSuite().getName();
    }

    @BeforeClass
    public  void beforeClassRun(){
    }

    @BeforeTest()
    public void testStarter(ITestContext testContext){
        globalVariables=GlobalVariablesManager.objectCreationMethod().getGlobalVariable();
        globalVariables.testName=testContext.getName();
    }

    public void run(IHookCallBack iHookCallBack, ITestResult iTestResult) {
        //****BEFORE START OF TEST CASE*************
        globalVariables.softAssertion=new SoftAssert();
        if (globalVariables.testSuite==null || !globalVariables.testName.equals(globalVariables.className)) {
            globalVariables.testSuite = globalVariables.extent.createTest(globalVariables.testName);
            globalVariables.className=globalVariables.testName;
        }
        globalVariables.test=globalVariables.testSuite.createNode(iTestResult.getMethod().getMethodName());
        globalVariables.testTest=globalVariables.test;
        globalVariables.testStep=globalVariables.test;
        globalVariables.testSubStep=globalVariables.test;
        globalVariables.test.assignCategory(globalVariables.testName);
        globalVariables.logger= LogManager.getLogger(iTestResult.getClass());
        globalVariables.skipFlag=false;
        globalVariables.logger.info(globalVariables.suiteName+" | "+"******************************************************");
        globalVariables.logger.info(globalVariables.suiteName+" | "+"************Starting Test Case: " + iTestResult.getMethod().getMethodName() + "************");
        globalVariables.logger.info(globalVariables.suiteName+" | "+"******************************************************");
        iHookCallBack.runTestMethod(iTestResult);   //****TEST CASE EXECUTING************
        //****AFTER TEST CASE*************
        if (iTestResult.getThrowable()!=null){
            if (!iTestResult.getThrowable().getCause().getClass().toString().equals("class java.lang.AssertionError"))
                globalVariables.commonFunc.logError(iTestResult,"");
        }
        if (globalVariables.skipFlag){
            iTestResult.setStatus(TestResult.SKIP);
        }
        globalVariables.extentManager.GetExtent().flush();
        globalVariables.softAssertion.assertAll();
    }


}
