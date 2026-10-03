package com.w2a.APITestingFramework.Utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;

public class ExcelDataManager {

	public static Object[][] getExcelData(String filePath, String sheetName, String testName) throws IOException {

		FileInputStream file = new FileInputStream(filePath);

		Workbook workbook = WorkbookFactory.create(file);

		Sheet sheet = workbook.getSheet(sheetName);

		// =====================================================
		// STEP 1: Find the test name
		// =====================================================

		int testNameRow = -1;

		for (int i = 0; i <= sheet.getLastRowNum(); i++) {

			Row row = sheet.getRow(i);

			if (row == null) {
				continue;
			}

			Cell cell = row.getCell(0);

			if (cell != null && cell.toString().trim().equals(testName)) {

				testNameRow = i;
				break;
			}
		}

		if (testNameRow == -1) {

			workbook.close();
			file.close();

			throw new RuntimeException("Test name '" + testName + "' not found in Excel");
		}

		// =====================================================
		// STEP 2: Header row
		// =====================================================

		int headerRowNumber = testNameRow + 1;

		Row headerRow = sheet.getRow(headerRowNumber);

		int columns = headerRow.getPhysicalNumberOfCells();

		// =====================================================
		// STEP 3: Find first data row
		// =====================================================

		int firstDataRow = headerRowNumber + 1;

		// =====================================================
		// STEP 4: Find last data row
		// =====================================================

		int lastDataRow = sheet.getLastRowNum();

		for (int i = firstDataRow; i <= sheet.getLastRowNum(); i++) {

			Row row = sheet.getRow(i);

			// ---------------------------------------------
			// Blank row means this test section has ended
			// ---------------------------------------------

			if (row == null || row.getPhysicalNumberOfCells() == 0) {

				lastDataRow = i - 1;
				break;
			}

		}

		// =====================================================
		// STEP 5: Calculate number of rows
		// =====================================================

		int dataRows = lastDataRow - firstDataRow + 1;

		if (dataRows <= 0) {

			workbook.close();
			file.close();

			throw new RuntimeException("No test data found for: " + testName);
		}

		// =====================================================
		// STEP 6: Create Object[][]
		// =====================================================

		Object[][] data = new Object[dataRows][columns];

		// =====================================================
		// STEP 7: Read data
		// =====================================================

		for (int i = 0; i < dataRows; i++) {

			Row row = sheet.getRow(firstDataRow + i);

			for (int j = 0; j < columns; j++) {

				Cell cell = row.getCell(j);

				if (cell != null) {
					data[i][j] = cell.toString();
				} else {
					data[i][j] = "";
				}
			}
		}

		workbook.close();
		file.close();

		return data;
	}
}