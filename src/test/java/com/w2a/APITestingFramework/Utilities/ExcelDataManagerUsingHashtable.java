package com.w2a.APITestingFramework.Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.poi.ss.usermodel.*;

public class ExcelDataManagerUsingHashtable {

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
		// Each row will contain ONE Hashtable
		// =====================================================

		Object[][] data = new Object[dataRows][1];

		// =====================================================
		// STEP 7: Read data into Hashtable
		// =====================================================

		for (int i = 0; i < dataRows; i++) {

			Row row = sheet.getRow(firstDataRow + i);

			Hashtable<String, String> table = new Hashtable<>();

			for (int j = 0; j < columns; j++) {

				// Get header
				Cell headerCell = headerRow.getCell(j);

				String key = "";

				if (headerCell != null) {
					key = headerCell.toString().trim();
				}

				// Get value
				Cell cell = row.getCell(j);

				String value = "";

				if (cell != null) {
					value = cell.toString();
				}

				// Store in Hashtable
				table.put(key, value);
			}

			// Store Hashtable as the DataProvider parameter
			data[i][0] = table;
		}

		workbook.close();
		file.close();

		return data;
	}
}