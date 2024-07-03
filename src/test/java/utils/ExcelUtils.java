package utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

    /**
     * Reads data from an Excel file.
     *
     * @param filePath  Path to the Excel file.
     * @param sheetName Name of the sheet in the Excel file to read data from.
     * @return List of strings containing data from the first column of each row.
     */
    public static List<String> readExcelData(String filePath, String sheetName) {
        List<String> data = new ArrayList<>();  // List to hold the read data
        DataFormatter formatter = new DataFormatter();  // Formatter to get formatted cell values

        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {  // Open workbook and create workbook instance

            Sheet sheet = workbook.getSheet(sheetName);  // Get the sheet by name

            for (Row row : sheet) {  // Iterate through each row in the sheet
                data.add(formatter.formatCellValue(row.getCell(0)));  // Read cell value from first column and add to list
            }
        } catch (IOException e) {  // Handle IO exceptions
            e.printStackTrace();  // Print stack trace for debugging
        }
        return data;  // Return the collected data
    }
}
