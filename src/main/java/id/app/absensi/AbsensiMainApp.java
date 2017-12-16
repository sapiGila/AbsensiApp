package id.app.absensi;

import com.poiji.bind.Poiji;
import id.app.absensi.model.Absensi;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class AbsensiMainApp {

    static String[] headers = new String[]{"User ID", "Nama", "Status", "Jam", "Tanggal"};

    public static void main(String[] args) {
        List<Absensi> absensiList = convertFromExcelToList();
        convertFromListToExcel(absensiList);
    }

    private static List<Absensi> convertFromExcelToList() {
        return Poiji.fromExcel(new File("contoh2.xls"), Absensi.class);
    }

    private static void convertFromListToExcel(List<Absensi> absensiList) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet1");// creating a blank sheet

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        int rownum = 1;
        for (Absensi absensi : absensiList) {
            Row row = sheet.createRow(rownum++);
            createList(absensi, row);

        }
        FileOutputStream out = null; // file name with path
        try {
            out = new FileOutputStream(new File("NewFile.xlsx"));
            workbook.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createList(Absensi absensi, Row row) // creating cells for each row
    {
        Cell cell = row.createCell(0);
        cell.setCellValue(absensi.getUid());

        cell = row.createCell(1);
        cell.setCellValue(absensi.getName());

        cell = row.createCell(2);
        cell.setCellValue(absensi.getStatus());

        cell = row.createCell(3);
        cell.setCellValue(absensi.getHour());

        cell = row.createCell(4);
        cell.setCellValue(absensi.getDate());
    }
}
