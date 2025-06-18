package Utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import static AppsTesting.AdbCommendsClass.*;

public class ExcelDataWrite {

	public static void excelWrite(String[] values) {
		Workbook workbook;
		Sheet sheet;
		String deviceName = deviceName();
		String sheetName = "FirstSheet";

		// Setup date and path
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		Date date = calendar.getTime();
		String currentDate = sdf.format(date);
		//String sheetName=deviceName+"_"+currentDate; 
		String path = System.getProperty("user.dir") + File.separator + "Results" + File.separator + "ExcelSheetsFolder"
				+ File.separator + currentDate;
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

		String fileName = String.format("ExcelResults.xlsx");
		File excelFile = new File(file, fileName);

		try {
			if (excelFile.exists()) {
				// Open existing workbook
				FileInputStream fis = new FileInputStream(excelFile);
				workbook = new XSSFWorkbook(fis);
				sheet = workbook.getSheet(sheetName);
				fis.close();
			} else {
				// Create new workbook and sheet with headers
				workbook = new XSSFWorkbook();
				sheet = workbook.createSheet(sheetName);
				Row header = sheet.createRow(0);

				header.createCell(0).setCellValue("DeviceName");
				header.createCell(1).setCellValue("AppName");
				header.createCell(2).setCellValue("ContentName");
				header.createCell(3).setCellValue("AudioOutput");
				header.createCell(4).setCellValue("VisionOutput");
				header.createCell(5).setCellValue("VideoResolution");
			}

			// Append new row
			int lastRow = sheet.getLastRowNum();
			Row newRow = sheet.createRow(lastRow + 1);
			for (int j = 0; j < values.length; j++) {
				Cell cell = newRow.createCell(j);
				cell.setCellValue(values[j]);
			}

			// Resize columns
			for (int i = 0; i < values.length; i++) {
				sheet.autoSizeColumn(i);
			}

			// Write back to file
			FileOutputStream fos = new FileOutputStream(excelFile);
			workbook.write(fos);
			workbook.close();
			fos.close();

			System.out.println("Successfully appended data to Excel sheet.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
