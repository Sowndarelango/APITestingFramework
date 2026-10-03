package com.w2a.APITestingFramework.Utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;

public class ExcelUtils {

	public static Object[][] getExcelData(String filePath, String sheetName) throws IOException {

		FileInputStream file = new FileInputStream(filePath);

		Workbook workbook = WorkbookFactory.create(file);

		Sheet sheet = workbook.getSheet(sheetName);

		int rows = sheet.getPhysicalNumberOfRows();
		int columns = sheet.getRow(0).getPhysicalNumberOfCells();

		Object[][] data = new Object[rows - 1][columns];

		for (int i = 1; i < rows; i++) {

			Row row = sheet.getRow(i);

			for (int j = 0; j < columns; j++) {

				Cell cell = row.getCell(j);

				data[i - 1][j] = cell.toString();
			}
		}

		workbook.close();
		file.close();

		return data;
	}
}