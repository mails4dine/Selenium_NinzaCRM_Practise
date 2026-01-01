package genericUtility;

import java.io.FileInputStream;
import java.io.IOException;


import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtility {

	public String togetDataFromExcelFile(String sheetname,int rowNum,int cellNum) throws EncryptedDocumentException, IOException
	{
		
		FileInputStream fis= new FileInputStream("./src/test/resources/TestScriptData.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		String data =wb.getSheet(sheetname).getRow(rowNum).getCell(cellNum).getStringCellValue();
		wb.close();
		return data;
	}
	public int togetRowCount(String sheetname) throws EncryptedDocumentException, IOException
	{

		FileInputStream fis= new FileInputStream("./src/test/resources/TestScriptData.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		int rowCount =wb.getSheet(sheetname).getLastRowNum();
		return rowCount;
		
	}
}
