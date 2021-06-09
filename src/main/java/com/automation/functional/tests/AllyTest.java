package com.automation.functional.tests;

import com.automation.extentreports.ExtentTestManager;
import com.automation.functional.pom.ChartHomePage;
import com.automation.utilities.FileUtilities;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class AllyTest extends BaseSteps {


    public static ChartHomePage chartHomePage;


    String scenarioName;
    static int scenarioNumber = 0;
    @Before
    public synchronized void before(Scenario scenario) {

        scenarioNumber = scenarioNumber + 1;
        this.scenario = scenario;
        scenarioName = scenario.getName();
        ExtentTestManager.startTest("Scenario No : " + scenarioNumber + " : " + scenario.getName());
        //ExtentTestManager.getTest().log(Status.INFO, "Scenario started..... : - " + scenario.getName());
        if(driver == null) {
            setBrowserExecutables();
        }

    }

    @After
    public void after(Scenario scenario) {
        System.out.println(scenario.getName()+"******"+scenario.isFailed()+"*******"+scenario.getStatus());
        if (scenario.isFailed()) {
            ExtentTestManager.addScreenShotsOnFailure();
        } else {
        }
    }

/**
 * It is the very first step in test script automation i.e. launch browser
 *
 * @param browserName - the type browser that user wants to launch
 * @throws Throwable
 */

	@Given("^launch browser \"([^\"]*)\"$")
	public void openBrowser(String browserName) throws Throwable {
		ExtentTestManager.logInfo("Abuot to launch " + browserName + " browser...");
		launchBrowser(browserName);
	}

/**
 * This step definition implements navigate to URL action
 *
 * @param URL - URL that we want to open in browser
 * @throws Throwable
 */
	@When("^user navigates to the URL \"([^\"]*)\"$")
	public void navigateToApplicationURL(String URL) throws Throwable {
		ExtentTestManager.logInfo("About to open " + URL + " url...");
        chartHomePage = new ChartHomePage().open(URL);

	}

    @Then("^validate the chart data$")
    public void updateChartEntries() {
        //chartHomePage = new ChartHomePage().open(System.getProperty("URL")); //will set the environment variable URL according to the test environment i.e. https://spark.adobe.com/express-apps/chart/
        //chartHomePage.clickOnContentTab();
        //chartHomePage.deleteAllRows();

        FileUtilities utils = new FileUtilities();
        JSONArray testData = utils.readJsonFile(System.getProperty("user.dir") + "/src/main/java/com/automation/testdata", "chartData.json");
        Map<String, String> actualAddedData = new HashMap<String, String>();
        for (int i = 0; i < testData.size(); i++) {
            JSONObject json = (JSONObject) testData.get(i);
            chartHomePage.clickOnContentTab();
            chartHomePage.deleteAllRows();
            chartHomePage.enterChartTitle(json.get("title").toString());

            Set<String> keys = json.keySet();
            int addedDataCount = keys.size();
            int count = 2;
            for (String key : keys) {
                if (!key.equals("title")) {
                    chartHomePage.clickOnAddItemButton();
                    chartHomePage.addItems(key, json.get(key).toString(), count);
                    actualAddedData.put(key, json.get(key).toString());
                    count++;
                }else{
                    actualAddedData.put(key, json.get(key).toString());
                }
            }

            Map<String, String> pieChartData =  chartHomePage.getDataFromPieChart(addedDataCount);
            Map<String, String> donutChartData = chartHomePage.getDataFromDonutChart(addedDataCount);
            /*Map<String, String> barChartData = chartHomePage.getDataFromBarChart(addedDataCount);
            Map<String, String> lineChartData = chartHomePage.getDataFromLineChart(addedDataCount);*/
            System.out.println("====================== actualAddedData ====================== \n"+actualAddedData);
            System.out.println("====================== pieChartData ====================== \n"+pieChartData);
            Assert.assertTrue(validateDataOnCharts(actualAddedData, pieChartData));
            /*Assert.assertTrue(validateDataOnCharts(actualAddedData, donutChartData));
            Assert.assertTrue(validateDataOnCharts(actualAddedData, barChartData));
            Assert.assertTrue(validateDataOnCharts(actualAddedData, lineChartData));*/
            actualAddedData.clear();
        }
    }

    public boolean validateDataOnCharts(Map<String, String> actualData, Map<String, String> expectedDat){
        boolean isValidationCorrect = false;
        TreeMap<String, String> actualSortedData = new TreeMap<>(actualData);
        TreeMap<String, String> expectedSortedData = new TreeMap<>(expectedDat);
        if(actualSortedData.equals(expectedSortedData)){
            isValidationCorrect = true;
        }else{
            isValidationCorrect = false;
        }
        return isValidationCorrect;
    }

}
