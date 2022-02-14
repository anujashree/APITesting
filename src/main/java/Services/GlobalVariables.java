package Services;
import Reports.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import java.io.FileInputStream;
import java.util.Properties;


public class GlobalVariables {
    public ExtentTest test;
    public ExtentTest testSuite;
    public ExtentTest testTest;
    public ExtentTest testStep;
    public ExtentTest testSubStep;
    public ExtentReports extent;
    public String className;
    public static volatile ExtentManager extentManager;
    public SoftAssert softAssertion;
    public static Logger logger;
    public CommonFunc commonFunc;
    public String testName;
    public boolean skipFlag=false;
    public String suiteName=null;
    public String TestDataFilePath;
    public String baseUrl;

    public GlobalVariables(ExtentManager extentManager2) {
        commonFunc = new CommonFunc(this);
        Properties properties = new Properties();
        try {
            FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") + "\\application.properties");
            properties.load(objfile);
        }
        catch (Exception e){
            System.out.println("Exception - Trying to read application.properties file, GlobalVariables.Java|GlobalVariables");
        }
        automationVariablesUpdate(properties);
        extentManager=extentManager2;
        extent=extentManager.GetExtent();
    }

    public ExtentTest getTest(){
        return test;
    }

    private void automationVariablesUpdate(Properties obj){
        suiteName=obj.getProperty("suiteName");
        baseUrl=obj.getProperty("baseURL");
            if (System.getProperty("TestDataFilePath")!=null) {
                TestDataFilePath = String.valueOf(System.getProperty("TestDataFilePath"));
            }else
            {
           //     TestDataFilePath = "C:\\Users\\hp\\Desktop\\AutomationFramework\\src\\Data\\TestCases.xlsx";
                System.out.println("Please provide the file path with filename.extension");
            }
        }
}
