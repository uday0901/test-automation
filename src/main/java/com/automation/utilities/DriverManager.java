package com.automation.utilities;

import org.openqa.selenium.WebDriver;

/**
 * 
 * @author - Uday Kumar Goshika
 * @version 1.0
 * @since 2021-06-09
 *
 */

public class DriverManager {
    /**
     * This class is used to set and get WebDriver object and creates individual threads for driver instances in case of parallel execution
     */

   public static ThreadLocal<WebDriver> driverManager = new ThreadLocal<WebDriver>();
    
    private DriverManager() {
	
    }

    /**
     * 
     * @return -  to get WebDriver object
     */
    public static WebDriver getDriver() {
	
	return driverManager.get();

    }

    /**
     * 
     * @param driver - to set WebDriver Object
     */
    public static void setWebDriver(WebDriver driver) {

	driverManager.set(driver);
    }

}