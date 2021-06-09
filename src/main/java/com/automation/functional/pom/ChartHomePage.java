package com.automation.functional.pom;

import com.automation.utilities.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author - Uday Kumar Goshika
 * @version 1.0
 * @since 2021-06-09
 *
 * ChartHomePage class has page elements and action utilities
 *
 */

public class ChartHomePage extends BasePage {


    public ChartHomePage open(String url) {
        DriverManager.getDriver().navigate().to(url);
        return (ChartHomePage) openPage(ChartHomePage.class);
    }

    @FindBy(css="#content-tabs button:nth-child(1)")
    public WebElement contentTab;

    @FindBy(css="#content-tabs button:nth-child(2)")
    public WebElement designTab;

    @FindBy(css="#chart-title")
    public WebElement chartTitle;

    @FindBy(css=".add-item-button")
    public WebElement addItemButton;

    @FindBy(css=".item-label-input")
    public WebElement itemInputField;

    @FindBy(css=".item-value-input")
    public WebElement valueInputField;

    @FindBy(xpath="//button[contains(@class, 'layout pie-chart')]")
    public WebElement pieChartButton;

    @FindBy(xpath="//button[contains(@class, 'layout donut-chart')]")
    public WebElement donutChartButton;

    @FindBy(xpath="//button[contains(@class, 'layout bar-chart')]")
    public WebElement barChartButton;

    @FindBy(xpath="//button[contains(@class, 'layout line-chart')]")
    public WebElement lineChartButton;

    @FindBy(css="g:nth-child(1)")
    public WebElement chartTitleOnGraph;

    public void clickOnContentTab(){
        clickOnElement(contentTab);
    }

    public void clickOnDesignTab(){
        clickOnElement(designTab);
    }

    public void clickOnPieChartButton(){
        clickOnElement(pieChartButton);
    }

    public void clickOnDonutChartButton(){
        clickOnElement(donutChartButton);
    }

    public void clickOnBarChartButton(){
        clickOnElement(barChartButton);
    }

    public void clickOnLineChartButton(){
        clickOnElement(lineChartButton);
    }

    public WebElement getItemNameField(int rowNumber){

        return driver.findElement(By.xpath("//table[@class='edit-items-table']//tr["+rowNumber+"]//td[2]//input"));
    }
    public WebElement getItemValueField(int rowNumber){

        return driver.findElement(By.xpath("//table[@class='edit-items-table']//tr["+rowNumber+"]//td[4]//input"));
    }
    public void clickOnAddItemButton() {
        clickOnContentTab();
        waitUntilElementIsClickable(addItemButton);
        clickOnElement(addItemButton);
    }



    public Map<String, String> getDataFromPieChart(int addedDataCount){
    Map<String, String> actualDataOnChart = new HashMap<>();
        clickOnDesignTab();
        clickOnPieChartButton();
        actualDataOnChart.put("title",getTitleOnDesignChart());
    for(int i=2; i<= addedDataCount; i++){
        String categoryValue = driver.findElement(By.cssSelector("g > g > g:nth-child("+i+") > g:nth-child(1)")).getText().trim();
        String categoryName = driver.findElement(By.cssSelector("g > g > g:nth-child("+i+") > g:nth-child(2)")).getText().trim();
        actualDataOnChart.put(categoryName, categoryValue);
    }
    return actualDataOnChart;
}

    public Map<String, String> getDataFromDonutChart(int addedDataCount){
        Map<String, String> actualDataOnChart = new HashMap<>();
        clickOnDesignTab();
        clickOnDonutChartButton();
        actualDataOnChart.put("title",getTitleOnDesignChart());
        for(int i=1; i < addedDataCount; i++){
            String categoryValue = driver.findElement(By.cssSelector("g:nth-child(2) > g:nth-child("+(i+3)+") > g:nth-child(1)")).getText().trim();
            String categoryName = driver.findElement(By.cssSelector("g:nth-child(2) > g:nth-child("+(i+3)+") > g:nth-child(2)")).getText().trim();
            actualDataOnChart.put(categoryName, categoryValue);
        }
        return actualDataOnChart;
    }

    /*
    * Note: Need to spend time on it to get the values from bar chart based on the coordinates
    * */
    public Map<String, String> getDataFromBarChart(int addedDataCount){
        Map<String, String> actualDataOnChart = new HashMap<>();
        clickOnDesignTab();
        clickOnBarChartButton();
        actualDataOnChart.put("title",getTitleOnDesignChart());
        for(int i=1; i<= addedDataCount; i++){
            String categoryValue = driver.findElement(By.cssSelector("g > g > g:nth-child("+i+") > g:nth-child(1)")).getText().trim();
            String categoryName = driver.findElement(By.cssSelector("g > g > g:nth-child("+i+") > g:nth-child(2)")).getText().trim();
            actualDataOnChart.put(categoryName, categoryValue);
        }
        return actualDataOnChart;
    }

    /*
     * Note: Need to spend time on it to get the values from line chart based on the coordinates
     * */
    public Map<String, String> getDataFromLineChart(int addedDataCount){
        Map<String, String> actualDataOnChart = new HashMap<>();
        clickOnDesignTab();
        clickOnLineChartButton();
        actualDataOnChart.put("title",getTitleOnDesignChart());
        for(int i=1; i<= addedDataCount; i++){
            String categoryValue = driver.findElement(By.cssSelector("g > g > g:nth-child("+i+") > g:nth-child(1)")).getText().trim();
            String categoryName = driver.findElement(By.cssSelector("g > g > g:nth-child("+i+") > g:nth-child(2)")).getText().trim();
            actualDataOnChart.put(categoryName, categoryValue);
        }
        return actualDataOnChart;
    }


   //public WebElement chartTable = driver.findElement(By.css(".edit-items-table"));

    public void enterChartTitle(String title){
        chartTitle.click();
        chartTitle.clear();
        chartTitle.sendKeys(title);
    }

    public void addItems(String itemName, String itemValue, int rowNumber){
        WebElement inputFieldElement = getItemNameField(rowNumber);
        WebElement itemValueFieldElement = getItemValueField(rowNumber);
        clickOnElement(inputFieldElement);
        inputFieldElement.sendKeys(itemName);
        clickOnElement(itemValueFieldElement);
        itemValueFieldElement.clear();
        itemValueFieldElement.sendKeys(itemValue);
    }

    public String getTitleOnDesignChart(){
        return chartTitleOnGraph.getText().trim();
    }

   public void deleteAllRows(){
       while(driver.findElements(By.cssSelector(".edit-items-table tr")).size() > 1) {
           driver.findElement(By.cssSelector(".delete-button")).click();
       }
    }


    @Override
    protected ExpectedCondition getPageLoadCondition() {
        return ExpectedConditions.visibilityOf(contentTab);
    }
}
