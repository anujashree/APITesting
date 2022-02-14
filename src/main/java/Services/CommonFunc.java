package Services;

import com.aventstack.extentreports.Status;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;
import com.google.gson.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;

public class CommonFunc {
    private GlobalVariables globalVariable;

    public CommonFunc(GlobalVariables globalVariable2){
        globalVariable = globalVariable2;
    }

    public boolean jsonComparison(String actualJson, String expectedJson){
        JsonObject jsonObject1 = JsonParser.parseString(actualJson).getAsJsonObject();
        JsonObject jsonObject2 = JsonParser.parseString(expectedJson).getAsJsonObject();
        try{
        if (jsonObject1.equals(jsonObject2)){
            logPass("Actual Response Matches with Expected Response" );
        }else{
            logFail("Actual Response doesn't Match with Expected Response" );
    }
        }catch (Exception e)
        {
            e.fillInStackTrace();
        }
        return jsonObject1.equals(jsonObject2);
    }

    public void responseCodeValidation(Response response, int statusCode) {
        responseTimeValidation(response);
        try {
            Assert.assertEquals(statusCode, response.getStatusCode());
            logPass("Successfully validated status code, status code is : " + response.getStatusCode());
        } catch (AssertionError e) {
            logFail("Expected status code is : " + statusCode + " , instead of getting : " + response.getStatusCode());
        } catch (Exception e) {
             e.fillInStackTrace();
        }
    }

    public void responseValidation(Response response,String expectedResponse, int expectedStatusCode,
                                   String expectedKey,String expectedValue,String expectedResponseType ){
        switch (expectedResponseType.toLowerCase()){
            case "json":
                if (response.path(expectedKey).equals(expectedValue)){
                    responseCodeValidation(response, expectedStatusCode);
                    logPass("Expected Key Value Pair Found: { '"+expectedKey+"' : '"+ expectedValue+"' }");
                }else{
                    logFail("Expected Key Value Pair Not Found: { '"+expectedKey+"' : '"+ expectedValue+"' }");
                }
                break;
            case "jsonarray":
                JSONArray jsonArray = new JSONArray(response.getBody().asString());
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                if (jsonObject.getString(expectedKey).equals(expectedValue)){
                    responseCodeValidation(response, expectedStatusCode);
                    logPass("Expected Key Value Pair Found: { '"+expectedKey+"' : '"+ expectedValue+"' }");
                }else{
                    logFail("Expected Key Value Pair Not Found: { '"+expectedKey+"' : '"+ expectedValue+"' }");
                }
                break;
            case "text":
                if(response.getBody().asString().contains(expectedValue)){
                    responseCodeValidation(response, expectedStatusCode);
                    logPass("Expected Value Found: "+  expectedValue);
                }else{
                    logFail("Expected Value  Found: "+ expectedValue);
                }
                break;
            default:
                logFail(" PLEASE PROVIDE CORRECT EXPECTED RESPONSE DATA TYPE ");
        }
    }

    public void responseTimeValidation(Response response) {
        try {
            long time= TimeUnit.MILLISECONDS.toSeconds(response.time());
            logInfo( "Api Response Time is : " + time +" Seconds");
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public void TestLogInfo(String testCaseID,String testCaseSummary,String contentType, String url, String requestType,String expectedResponse, String actualResponse,String expectedResponseCode,int responseStatusCode){
        logInfo("TestCaseID : " + testCaseID);
        logInfo("TestCase Summary : " + testCaseSummary);
        logInfo("API End Point : " + url);
        logInfo("ContentType : " + contentType);
        logInfo("Request Type : " + requestType);
        logInfo("Expected Response   : \n" + expectedResponse);
        logInfo("Actual Response   : \n" + actualResponse);
        logInfo("Expected Response Code : " + expectedResponseCode);
        logInfo("Actual Response Code : " + responseStatusCode);
    }

    public void logInfo(String description){
            globalVariable.test.log(Status.INFO,description);
            globalVariable.logger.info(globalVariable.suiteName+" | "+"INFO: " + description.replace("<b>", "").replace("</b>", "").replace("<br />", "\r\n"));
    }

    public void logPass(String description) {
            globalVariable.test.pass(description);
            globalVariable.logger.info(globalVariable.suiteName+" | "+"PASS: " + description.replace("<b>", "").replace("</b>", "").replace("<br />", "\r\n"));
    }

    public void logFail(String description){
            globalVariable.test.log(Status.FAIL, description);
            globalVariable.logger.info(globalVariable.suiteName+" | "+"FAIL: " + description.replace("<b>", "").replace("</b>", "").replace("<br />", "\r\n"));
        }

    public void logWarning(String description){
            globalVariable.test.log(Status.WARNING, description);
            globalVariable.logger.info(globalVariable.suiteName+" | "+"WARNING: " + description.replace("<b>", "").replace("</b>", "").replace("<br />", "\r\n"));
        }

    public void logError(ITestResult iTestResult, String description) {
        if (iTestResult != null)
        {
            StackTraceElement[] stackTrace = iTestResult.getThrowable().getCause().getStackTrace();
        for (int i = 0; i < stackTrace.length; i++)
            globalVariable.test.log(Status.INFO, iTestResult.getThrowable().getCause().getStackTrace()[i].toString());
        }
    }

    public void logSkip(String description){
        globalVariable.test.log(Status.SKIP,description);
        globalVariable.skipFlag=true;
    }

    public void logStep(String Description){
        globalVariable.test=globalVariable.testTest.createNode(Description);
        globalVariable.logger.info(globalVariable.suiteName+" | "+"Step: " + Description);
    }
}
