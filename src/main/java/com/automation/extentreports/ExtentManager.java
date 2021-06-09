package com.automation.extentreports;

import com.automation.utilities.DateFormats;
import com.automation.utilities.DriverManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author - Uday Kumar Goshika
 * @version 1.0
 * @since 2021-06-09
 *
 */

public class ExtentManager {

	/**
	 * This class is used to manage extent reports by setting required
	 * configurations
	 */
	static String screenshotName;
	static int screenShotNumber = 0;
	static ExtentReports extent;

	private ExtentManager() {

	}

	/**
	 * This method is used to add test report visibility options and as well to add
	 * some basic info to the report like who generated report and what is the build
	 * and organization name
	 * 
	 * @return
	 */
	public static synchronized ExtentReports getReporter() {
		String fileName = "ExtentReport_" + DateFormats.getDateFormatForFileNames() + ".html";
		if (extent == null) {

			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(

					System.getProperty("user.dir") + "/reports/" + fileName);

			htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
			htmlReporter.config().setChartVisibilityOnOpen(true);
			htmlReporter.config().setTheme(Theme.STANDARD);
			htmlReporter.config().setDocumentTitle(fileName);
			htmlReporter.config().setEncoding("utf-8");
			htmlReporter.config().setReportName(fileName);

			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
			extent.setSystemInfo("Automation Tester", "Uday Kumar Goshika");
			extent.setSystemInfo("Organization", "Automation Testing");
			extent.setSystemInfo("Build no", "1.0.0");
		}
		return extent;
	}

	/**
	 * This method is used to capture screenshot on test scenario failure
	 */
	public static void captureScreenshot() {
		screenShotNumber = screenShotNumber + 1;
		File scrFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);

		screenshotName = DateFormats.getDateFormatForFileNames() + screenShotNumber + ".jpg";

		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "/reports/" + screenshotName));
		} catch (IOException e) {
		}

	}

}
