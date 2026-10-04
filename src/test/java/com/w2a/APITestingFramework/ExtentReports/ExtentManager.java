
package com.w2a.APITestingFramework.ExtentReports;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extent;

	private static String reportPath;

	public static synchronized ExtentReports getExtentReports() {

		if (extent == null) {

			// Create timestamp for unique report name
			String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

			// Create report directory
			String reportDirectoryPath = System.getProperty("user.dir") + File.separator + "test-output"
					+ File.separator + "ExtentReports";

			File reportDirectory = new File(reportDirectoryPath);

			if (!reportDirectory.exists()) {
				reportDirectory.mkdirs();
			}

			// Create complete report path
			reportPath = reportDirectoryPath + File.separator + "ExtentReport_" + timestamp + ".html";

			// Create Extent Spark Reporter
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

			// Report title
			sparkReporter.config().setDocumentTitle("API Automation Test Report");

			// Report name
			sparkReporter.config().setReportName("Rest Assured API Automation Results");

			// Dark theme
			sparkReporter.config().setTheme(Theme.DARK);

			// Date format
			sparkReporter.config().setTimeStampFormat("dd-MM-yyyy HH:mm:ss");

			// Create ExtentReports
			extent = new ExtentReports();

			// Attach Spark Reporter
			extent.attachReporter(sparkReporter);

			// System information
			extent.setSystemInfo("Application", "Stripe API");
			extent.setSystemInfo("Automation", "Rest Assured");
			extent.setSystemInfo("Framework", "TestNG");
			extent.setSystemInfo("Java Version", System.getProperty("java.version"));
			extent.setSystemInfo("OS", System.getProperty("os.name"));
			extent.setSystemInfo("User", System.getProperty("user.name"));
		}

		return extent;
	}

	/**
	 * Flush the Extent Report.
	 */
	public static synchronized void flush() {

		if (extent != null) {
			extent.flush();
		}
	}

	/**
	 * Returns the exact report path generated for the current execution.
	 */
	public static String getReportPath() {

		return reportPath;
	}
}
