package Services;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ExcelDataProvider {
    private GlobalVariables globalVariable;

    public ExcelDataProvider(GlobalVariables globalVariable2){
        globalVariable = globalVariable2;
    }

    public Object[][] testData(String excelFilePath,String SheetName){
        ExcelReader excelReader= new ExcelReader(globalVariable,excelFilePath,SheetName);

        int rowCount= excelReader.getRowCount();
        int colCount= excelReader.getColCount();

        Object data[][]=new Object[rowCount-1][colCount];

        for(int rowNum=1;rowNum<rowCount;rowNum++)
        {
            for(int colNum=0;colNum<colCount;colNum++)
            {
                data[rowNum-1][colNum]= excelReader.cellDataString(rowNum,colNum);
            }
        }
        return data;
    }
}
