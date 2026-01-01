package DDT;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class test {

    public static void main(String[] args) throws Throwable {

        FileInputStream fis = new FileInputStream("./src/test/resources/TestData.xlsx");
        Workbook wb = WorkbookFactory.create(fis);
        Sheet sheet = wb.getSheet("Campaign");

        int lastRow = sheet.getLastRowNum();

        for (int i = 0; i <= lastRow; i++) {
            Row row = sheet.getRow(i);

            if (row == null) {
                continue;
            }

            for (Cell cell : row) {
                switch (cell.getCellType()) {

                    case STRING:
                        System.out.print(cell.getStringCellValue() + " | ");
                        break;

                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            System.out.print(cell.getDateCellValue() + " | ");
                        } else {
                            System.out.print(cell.getNumericCellValue() + " | ");
                        }
                        break;

                    case BOOLEAN:
                        System.out.print(cell.getBooleanCellValue() + " | ");
                        break;

                    case BLANK:
                        System.out.print("BLANK | ");
                        break;

                    default:
                        System.out.print("UNKNOWN | ");
                }
            }
            System.out.println(); // new line for next row
        }

        wb.close();
    }
}
