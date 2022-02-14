package API_AutomationTestCases;

import Controller.BaseController;
import Services.ExcelDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Test_GetSatellitePosition_API extends BaseController {
    private Response response;
    private String url;

    @Test(dataProvider =  "SatPosition-API")
    public void test_SatellitePositionAPI(String testCaseID, String testCaseSummary, String satelliteID,
                                          String timeStamps, String units, String requestType, String contentType,
                                          String expectedKey, String expectedValue, String expectedResponse,
                                          String expectedResponseCode, String expectedResponseType)
    {
        if (units!=null){
            url=globalVariables.baseUrl+satelliteID+"/positions?timestamps="+timeStamps+"&units="+units;
        }else
            url=globalVariables.baseUrl+satelliteID+"/positions?timestamps="+timeStamps;

        switch (requestType.toLowerCase()){
            case "post":
                response = given().header("Content-Type", contentType).
                        when().
                        post(url).
                        then().
                        extract().
                        response();
                break;
            case "get":
                 response = given().header("Content-Type", contentType).
                        when().
                        get(url).
                        then().
                        extract().
                        response();
                 break;
        }
        globalVariables.commonFunc.TestLogInfo(
                 testCaseID, testCaseSummary, contentType, url, requestType,
                 expectedResponse, response.getBody().jsonPath().prettify(),
                 expectedResponseCode, response.statusCode());

        globalVariables.commonFunc.responseValidation(
                response, expectedResponse, Integer.parseInt(expectedResponseCode),
                expectedKey, expectedValue, expectedResponseType );
    }

    @DataProvider(name = "SatPosition-API")
    public Object[][] getData(){
        ExcelDataProvider excelDataProvider= new ExcelDataProvider(globalVariables);
        Object data[][]= excelDataProvider.testData(globalVariables.TestDataFilePath,"SatPosition-API");
        return data;
    }
}
