package Controller;

import Services.GlobalVariables;
import com.aventstack.extentreports.ExtentTest;
import org.testng.*;


public class Listener implements ITestListener,IInvokedMethodListener,ISuiteListener  {
    //public static volatile GlobalVariablesManager GVM=new GlobalVariablesManager();
    //public ExtentTest test;
    public static Boolean firstRunFlag=true;


    public void onTestStart(ITestResult iTestResult) {

    }

    public void onTestSuccess(ITestResult iTestResult) {

    }

    public void onTestFailure(ITestResult iTestResult) {

    }

    public void onTestSkipped(ITestResult iTestResult) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    public void onStart(ITestContext iTestContext) {

    }

    public void onFinish(ITestContext iTestContext) {

    }

    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {


    }

    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {

    }

    public void onStart(ISuite iSuite) {

    }

    public void onFinish(ISuite iSuite) {


    }
}
