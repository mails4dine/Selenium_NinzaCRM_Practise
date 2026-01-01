package DDT;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadDataFromExcel1 {

	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		FileInputStream fis1= new FileInputStream("./src/test/resources/TestScriptData.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
	Sheet sh=	wb.getSheet("Mobile");
	int rowcount=sh.getLastRowNum();
	for(int i=0;i<=rowcount;i++)
	{
	String productcat =	sh.getRow(i).getCell(0).getStringCellValue();
	String productname=	sh.getRow(i).getCell(1).getStringCellValue();
	System.out.println(productcat+"-"+productname);
	
	}
	
	
	}
}
