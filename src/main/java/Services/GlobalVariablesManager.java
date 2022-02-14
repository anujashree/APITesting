package Services;

import Reports.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.Semaphore;

public class GlobalVariablesManager{
        private static volatile HashMap <String,GlobalVariables> GVManager;
        static Boolean reset=true;
        private static ExtentManager extentManager=null;
        private static Boolean logEnvironment=true;
        static Semaphore semaphore;


    private static GlobalVariablesManager obj=null;
    private GlobalVariablesManager(){
        /*Private Constructor will prevent
         * the instantiation of this class directly*/
    }
    static {
        obj = new GlobalVariablesManager();
        GVManager= new HashMap<String, GlobalVariables>();
        extentManager= new ExtentManager();
        semaphore = new Semaphore(1);
        flushReportingDirectory();
    }

    public static GlobalVariablesManager objectCreationMethod(){
        /*This logic will ensure that no more than
         * one object can be created at a time */
        return obj;
    }

    public static Boolean logEnvironment(){
        if (logEnvironment){
            logEnvironment=false;
            return !logEnvironment;
        }
        else
            return logEnvironment;
    }


    public static GlobalVariables getGlobalVariable() {

        try{
            semaphore.acquire();
        }
        catch (Exception e){
            System.out.println("Can not Acquire Mutex for GV");
        }
        if (GVManager.get(java.lang.Thread.currentThread().toString())==null){
            GVManager.put(java.lang.Thread.currentThread().toString(),new GlobalVariables(extentManager));
            System.out.println("New GV Issued for thread: "+java.lang.Thread.currentThread().toString());
        }
        if(logEnvironment())
            extentManager.reportEnvironment(GVManager.get(java.lang.Thread.currentThread().toString()));
        semaphore.release();

        return GVManager.get(java.lang.Thread.currentThread().toString());
    }

    public static Boolean firstSetup(){
        Boolean result=reset;
        reset=false;
        return result;
    }

    static void flushReportingDirectory() {

        System.out.println("Cleaning Directory");
        try {
            FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + "\\test-output\\extentReports"));
            if (new File(System.getProperty("user.dir") + "\\test-output\\extentReports\\extentreport.html").exists())
                FileUtils.forceDelete(new File(System.getProperty("user.dir") + "\\test-output\\extentReports\\extentreport.html"));
        } catch (Exception e) {
            System.out.println("Exception - Trying to clean directory, BaseController.Java|Starter");
        }
    }
}
