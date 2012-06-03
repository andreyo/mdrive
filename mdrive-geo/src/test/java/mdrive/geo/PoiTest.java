package mdrive.geo;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class PoiTest {

    public static void main(String[] args) throws Exception {
    }

    @Test
    public void testExcel() throws IOException {
        String filename = "streets.xls";

        InputStream fis = null;
        try {
            fis = PoiTest.class.getClassLoader().getResourceAsStream(filename);

            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                if(i > 50) break;
                for (int j = 0; j < sheet.getRow(i).getLastCellNum(); j++) {
                    System.out.print(sheet.getRow(i).getCell(j) + ", ");
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }
}