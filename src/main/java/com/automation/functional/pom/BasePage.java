package com.automation.functional.pom;

import com.automation.utilities.DriverManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 
 * @author - Uday Kumar Goshika
 * @version 1.0
 * @since 2021-06-09
 * 
 *        BasePage class is used to implement methods to load each page based on
 *        preconditions
 *
 * @param <T> - T is any class which defines a page in the application
 */

public abstract class BasePage<T> {

	protected WebDriver driver;

	private static final long PAGE_LOAD_TIMEOUT = 40;
	private static final int AJAX_ELEMENT_TIMEOUT = 10;

	/**
	 * this constructor is used to initialize drive instance
	 */

	public BasePage() {
		this.driver = DriverManager.getDriver();
	}

	/**
	 * openPage method is used to load a specific page on a specific condition
	 * 
	 * @param pageClass - any page class that we have implemented as part of page
	 *                  factory model in the application
	 * @return - returns the loaded page to utilize it's elements as part of script
	 */
	public T openPage(Class<T> pageClass) {
		T page = null;
		try {
			driver = DriverManager.getDriver();
			AjaxElementLocatorFactory ajaxElemFactory = new AjaxElementLocatorFactory(driver, AJAX_ELEMENT_TIMEOUT);
			page = PageFactory.initElements(driver, pageClass);
			PageFactory.initElements(ajaxElemFactory, page);
			ExpectedCondition pageLoadCondition = ((BasePage) page).getPageLoadCondition();
			waitUntilPageIsLoaded(pageLoadCondition);
		} catch (NoSuchElementException e) {
			throw new IllegalStateException(String.format("This is not the %s page", pageClass.getSimpleName()));
		}
		return page;
	}

	/**
	 * this method is implemented to provide page load condition
	 * 
	 * @param pageLoadCondition - it returns the ExpectedCondition object to wait
	 *                          until that condition matches
	 */
	private void waitUntilPageIsLoaded(ExpectedCondition pageLoadCondition) {
		WebDriverWait wait = new WebDriverWait(driver, PAGE_LOAD_TIMEOUT);
		wait.until(pageLoadCondition);
	}

	/**
	 * This is an abstract method, each page should implement this class to specify
	 * the page load condition For example if user clicks on any element on
	 * particular page and that action opens/navigates a new page
	 * 
	 * @return - it returns ExpectedCondition object, which is used to wait for that
	 *         page until it loads
	 */
	protected abstract ExpectedCondition getPageLoadCondition();

	/**
	 * This method is used to click on an element in the application
	 * 
	 * @param element     - It is WebElement object of an clickable element in the
	 *                    application
	 * @param elementName - It is general name of an element
	 */
	public void click(WebElement element, String elementName) {

		element.click();
	}

	public void clickOnElement(WebElement element) {

		element.click();
	}

	public void enterText(WebElement element, String value) {

		element.sendKeys(value);
	}

	public void waitUntilElementIsClickable(WebElement element){
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(element));

	}




	/**
	 * This method is used to enter some value in text input type element
	 * 
	 * @param element     - It is WebElement object of an input type element in the
	 *                    application
	 * @param value       - The value that user wants to enter in text input type
	 *                    element
	 * @param elementName - It is general name of an element
	 */
	public void type(WebElement element, String value, String elementName) {

		element.sendKeys(value);
	}

}
