package Services;
import java.io.File;
import java.io.FileInputStream;
import com.google.common.base.Stopwatch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelReader{
    private  GlobalVariables globalVariables;
    private  FileInputStream inputStream ;
    private  Workbook testWorkbook;
    private  Sheet testSheet ;

    public ExcelReader(GlobalVariables gv,String filePath, String sheetName) {
        globalVariables = gv;
        File file = new File(filePath);

        //Create an object of FileInputStream class to read excel file
        try {
            inputStream = new FileInputStream(file);
        } catch (Exception e) {
            System.out.println("Exception - Trying to open reading of Excel File, ExcelReader.Java");
        }
        //Find the file extension by splitting file name in substring  and getting only extension name
        String fileExtensionName = filePath.substring(filePath.indexOf("."));
        //Check condition if the file is xlsx file
        if (fileExtensionName.equals(".xlsx")) {
            try {
                if (inputStream != null)
                    testWorkbook = new XSSFWorkbook(inputStream);
            } catch (Exception e) {
                System.out.println("Exception - Trying to open reading of Excel(.xlsx), ExcelReader.Java");
            }
        }
        //Check condition if the file is xls file
        else if (fileExtensionName.equals(".xls")) {
            try {
                if (inputStream != null)
                    testWorkbook = new HSSFWorkbook(inputStream);
            } catch (Exception e) {
                System.out.println("Exception - Trying to open reading of Excel(.xls), ExcelReader.Java");
            }

        }
//        //Read sheet inside the workbook by its name
        if (testWorkbook != null) {
            testSheet = testWorkbook.getSheet(sheetName);
        }
    }

    public int getRowCount() {
        //Find number of rows in excel file
        int rowCount = 0;
        if (testSheet != null) {
            try {
                rowCount = testSheet.getPhysicalNumberOfRows();
                System.out.println("Number of Rows :" + rowCount);
            }catch (Exception e){
                System.out.println(e.getCause());
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            return rowCount;
        }
        else {
            globalVariables.commonFunc.logInfo("Invalid Sheet Name Provided");
            return rowCount;
        }
    }

    public int getColCount() {
        //Find number of Columns in excel file
        int colCount=0;
        if (testSheet != null) {
            try {
                colCount = testSheet.getRow(0).getPhysicalNumberOfCells();
                System.out.println("Number of Columns :" + colCount);
            }catch (Exception e){
                System.out.println(e.getCause());
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            return colCount;
        }
        else {
            globalVariables.commonFunc.logInfo("Invalid Sheet Name Provided");
            return colCount;
        }
    }

    public String cellDataString(int rowNum,int colNum){
        String CellData = null;
        Cell cell = testSheet.getRow(rowNum).getCell(colNum);
        try{
            if(cell.getCellType()== CellType.STRING)
                CellData = cell.getStringCellValue();
            else if(cell.getCellType()==CellType.NUMERIC)
                CellData = NumberToTextConverter.toText(cell.getNumericCellValue());
        }catch (Exception e){
        }
        return CellData;
    }

    public double cellDataNumeric(int rowNum,int colNum){
        double CellData = 0;
        try{
            CellData=testSheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
        }catch (Exception e){
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return CellData;
    }



}