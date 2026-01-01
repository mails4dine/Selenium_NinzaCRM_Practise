package DDT;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Cell;

//import com.google.common.collect.Table.Cell;

public class ReadDataFromExcel_MultipleRows {

	public static void main(String[] args) throws Throwable {
		FileInputStream fis =new FileInputStream("./src/test/resources/TestScriptData.xlsx");
	Workbook wb=WorkbookFactory.create(fis);
	 Sheet sheet = wb.getSheet("Login");

     int lastRow = sheet.getLastRowNum();
	for(int i=0;i<lastRow;i++)
	{
		 Row row = sheet.getRow(i);
		
		 if (row == null) {
             continue;
         }

         for (Cell cell : row) 
         {
        	
        	
             System.out.print(cell.getStringCellValue() + " | ");
            
        	 
        	 
         }
        	System.out.println();
	
		
	}

        System.out.println();
         wb.close();
	}
}
