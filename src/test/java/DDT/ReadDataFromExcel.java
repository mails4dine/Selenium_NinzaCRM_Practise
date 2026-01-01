package DDT;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadDataFromExcel {

	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		FileInputStream fis1= new FileInputStream("./src/test/resources/TestScriptData.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
	Sheet sh=	wb.getSheet("Campaign");
	Row r=sh.getRow(1);
String campname=	r.getCell(2).getStringCellValue();
String status=	r.getCell(3).getStringCellValue();
//double targetsize = r.getCell(5).getNumericCellValue();
String target =r.getCell(5).getStringCellValue();
//String target=String.valueOf(targetsize);
System.out.println(campname);
System.out.println(status);
System.out.println(target);
		
	}
}
