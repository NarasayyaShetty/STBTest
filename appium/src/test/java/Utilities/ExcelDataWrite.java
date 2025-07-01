package Utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static AppsTesting.AdbCommendsClass.deviceName;

public class ExcelDataWrite {

    public static void excelWrite(String[] values) {
        Workbook workbook = null;
        Sheet sheet;
        String device = deviceName();  // Get device name dynamically

        // Setup current date and output path
        String currentDate = new SimpleDateFormat("ddMMyyyy").format(Calendar.getInstance().getTime());
      //  String sheetName = device + "_" + currentDate;
        String sheetName = currentDate;

        String path = System.getProperty("user.dir") + File.separator + "Results" +
                      File.separator + "ExcelSheetsFolder" + File.separator + currentDate;

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File excelFile = new File(dir, "ExcelResults.xlsx");

        try {
            if (excelFile.exists()) {
                // Load existing workbook
                try (FileInputStream fis = new FileInputStream(excelFile)) {
                    workbook = new XSSFWorkbook(fis);
                }

                // Check if the sheet already exists
                sheet = workbook.getSheet(sheetName);
                if (sheet == null) {
                    sheet = workbook.createSheet(sheetName);
                    createHeaderRow(sheet);
                }

            } else {
                // Create new workbook and sheet
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet(sheetName);
                createHeaderRow(sheet);
            }

            // Append new row
            int lastRow = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRow + 1);

            for (int j = 0; j < values.length; j++) {
                Cell cell = newRow.createCell(j);
                cell.setCellValue(values[j]);
            }

            // Auto-size columns
            for (int i = 0; i < values.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write to file
            try (FileOutputStream fos = new FileOutputStream(excelFile)) {
                workbook.write(fos);
            }

            workbook.close();
            System.out.println("Successfully appended data to Excel sheet.");

        } catch (IOException e) {
            System.err.println("Error writing to Excel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createHeaderRow(Sheet sheet) {
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("DeviceName");
        header.createCell(1).setCellValue("AppName");
        header.createCell(2).setCellValue("ContentName");
        header.createCell(3).setCellValue("Audio-Type");
        header.createCell(4).setCellValue("Vision-Type");
        header.createCell(5).setCellValue("VideoResolution");
    }
}
