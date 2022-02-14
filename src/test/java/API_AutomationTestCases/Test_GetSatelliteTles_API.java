package API_AutomationTestCases;

import Controller.BaseController;
import Services.ExcelDataProvider;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class Test_GetSatelliteTles_API extends BaseController {
    private Response response;
    private String url;

    @Test(dataProvider =  "SatTle-API")
    public void test_SatelliteTlesAPI(String testCaseID, String testCaseSummary, String satelliteID,
                                      String format, String requestType, String contentType,
                                      String expectedKey, String expectedValue, String expectedResponse,
                                      String expectedResponseCode, String expectedResponseType) {
        if (format!=null){
            url=globalVariables.baseUrl+satelliteID+"/tles?format="+format;
        }else
            url=globalVariables.baseUrl+satelliteID+"/tles";

        switch (requestType.toLowerCase()) {
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
                testCaseID, testCaseSummary, contentType, url,
                requestType, expectedResponse, response.getBody().asString(),
                expectedResponseCode, response.statusCode());

        globalVariables.commonFunc.responseValidation(
                response, expectedResponse, Integer.parseInt(expectedResponseCode),
                expectedKey, expectedValue, expectedResponseType );
    }

    @DataProvider(name = "SatTle-API")
    public Object[][] getData(){
        ExcelDataProvider excelDataProvider= new ExcelDataProvider(globalVariables);
        Object data[][]= excelDataProvider.testData(globalVariables.TestDataFilePath,"SatTle-API");
        return data;
    }
}
