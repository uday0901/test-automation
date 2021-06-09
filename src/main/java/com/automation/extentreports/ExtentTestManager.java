package com.automation.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import java.io.IOException;

/**
 * 
 * @author - Uday Kumar Goshika
 * @version 1.0
 * @since 2021-06-09
 *
 */
public class ExtentTestManager {

	/**
	 * This class is used to manage logging information in the generated report and
	 * also used to append screenshots on test scenario failure
	 */

	private static ThreadLocal<ExtentTest> testReport = new ThreadLocal<>();
	static ExtentReports extent = ExtentManager.getReporter();

	/**
	 * Constructor with blank details
	 */
	private ExtentTestManager() {

	}

	/**
	 * This method Returns the value in the current thread's copy of this
	 * thread-local variable. If the variable has no value for the current thread,
	 * it is first initialized to the value returned by an invocation of the
	 * initialValue method.
	 * 
	 * @return - the current thread's value of this thread-local
	 */
	public static synchronized ExtentTest getTest() {

		return testReport.get();
	}

	/**
	 * This method logs an event with Status.INFO with details
	 * 
	 * @param message -message that user wants to log as part of report
	 */
	public static void logInfo(String message) {

		testReport.get().info(message);
	}

	/**
	 * This method logs an event with Status and custom Markup such as: Code block
	 * Label Table
	 */

	public static void scenarioPass() {

		String passLogg = "SCENARIO PASSED";
		Markup markup = MarkupHelper.createLabel(passLogg, ExtentColor.GREEN);
		testReport.get().log(Status.PASS, markup);

	}

	/**
	 * This method is used to add screenshot on test scenario failure
	 * 
	 * @return - returns true if the screenshot added to the report
	 */
	public static synchronized boolean addScreenShotsOnFailure() {

		try {

			ExtentManager.captureScreenshot();
			testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
					MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.screenshotName).build());
			String failureLogg = "SCENARIO FAILED";
			Markup markup = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
			testReport.get().log(Status.FAIL, markup);
		} catch (IOException e) {
			
		}
		return true;
	}

	/**
	 * 
	 * @param testName - name of the test scenario
	 * @return - returns ExtentTest object with test details
	 */

	public static synchronized ExtentTest startTest(String testName) {
		return startTest(testName, "");
	}

	/**
	 * 
	 * @param testName - name of the test scenario
	 * @param desc - Description for the senario
	 * @return - returns ExtentTest object
	 */

	public static synchronized ExtentTest startTest(String testName, String desc) {
		ExtentTest test = extent.createTest(testName, desc);
		testReport.set(test);
		return test;
	}

}
