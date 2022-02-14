Pre-requiste- Install the below
 -Eclipse or IntelliJ development environment
 -JDK 
 -Maven 
 -Git
 -Excel
 -Chrome Browser to view the report generated

The code can be downloaded to your local system or the VM you want to execute using the GIT repository
We are using the following for our Automation framework
-Rest Assured library
-TestNG as a test runner
-Extend Report for reporting

Steps for executing the test cases

1. Open Intellij or Eclipse- open Project
2. Build Project -all dependencies will be downloaded
3. Check the TestNG file(testngAPI.xml) for the details on the test cases to be executed -the classes that need to executed are            mentioned under the suite
4. The test cases can be run from 
    -IntelliJ terminal 
    -cmd prompt- navigate to the directory the project is located on the system
   Run the below command, amking changes to the locations and file name for test cases excel(data is stored)and the testngAPI file.
    
  mvn clean install test  -DTestDataFilePath="C:\AutomationFramework\src\Data\TestCases.xlsx" -Dsurefire.suiteXmlFiles=testngAPI.xml

5. After execution the result is generated in the test-output folder as extentreport.html and can be viewed in the browser



The Defect noticed while executing the API 
 - The satellites/[id]/positions API- it clearly mentioned it can return details about a satellite for a comma delimited list of           timestamps upto 10 , where else i could retrieve data for more 
 - The NORAD catalog id is of 9 digits assigned by united States Space Command -wrong reqirement
 - Format of the time stamp has not been defined can it be with decimal


Below can be included -
- The Request sample structure -order if required to be followed 
- If there is any element or attribute that  is allowed to appear multiple times
- Required and optional elements if it is needed
- Proper fromat of each element
- The format of timestamp has not been mentioned or the number of digits
- 




    
