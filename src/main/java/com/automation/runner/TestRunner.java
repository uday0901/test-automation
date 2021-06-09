package com.automation.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.Test;

/**
 * 
 * @author - Uday Kumar Goshika
 * @version 1.0
 * @since 2021-06-09
 *
 */

/**
 * 
 * CucumberOptions is used to execute test cases using Cucumber with different
 * options below are the different possible options features = {The paths of the
 * feature files}, default value is {} glue = {The paths of the step definition
 * files}, default value is {} tags = What tags in the features files should be
 * executed monochrome = true - Display the console output in much readable way.
 * Default value is false dryRun = true - Checks if all the steps have step
 * definition. Default value is false
 * 
 *
 */
@CucumberOptions(monochrome = true, plugin = { "pretty",
		"html:target/cucumber" }, features = "src/main/java/com/automation/functional/features/validatecharts.feature", glue = "com.automation.functional.tests", tags = {
				"@SmokeTest" })

public class TestRunner {

	@Test
	public void runCukesTest() {
		
		new TestNGCucumberRunner(getClass()).runCukes();
		
	}
}
