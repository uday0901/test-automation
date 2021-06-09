package com.automation.functional.tests;

import com.automation.utilities.DetectOS;
import com.automation.utilities.DriverFactory;
import com.automation.utilities.DriverManager;
import cucumber.api.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author - Uday Kumar Goshika
 * @version 1.0
 * @since 2021-06-09
 *
 */

public class BaseSteps {

    private static final Logger log = LogManager.getLogger(BaseSteps.class);
    
	/**
	 * This class helps to set browser executables and to launch browser
	 */
	public static WebDriver driver = null;
	private Properties configProperties = new Properties();
	private FileInputStream fis;
	protected Scenario scenario;
	public boolean grid = false;

	/**
	 * This method is used to set browser executables as per detected OS on which
	 * scripts are executing
	 */

	public void setBrowserExecutables() {
		DetectOS.OSType ostype = DetectOS.getOperatingSystemType();
		String os = ostype.toString();

		if (os.equalsIgnoreCase("WINDOWS")) {
			DriverFactory.setChromeDriverPath(
					System.getProperty("user.dir") + "/src/main/resources/executables/chromedriver_win32.exe");
			DriverFactory.setGeckoDriverPath(
					System.getProperty("user.dir") + "/src/main/resources/executables/geckodriver_win64.exe");
			DriverFactory.setIeDriverPath(
					System.getProperty("user.dir") + "/src/main/resources/executables/IEDriverServer_win64.exe");

		} else if (os.equalsIgnoreCase("LINUX")) {
			DriverFactory.setChromeDriverPath(
					System.getProperty("user.dir") + "/src/main/resources/executables/chromedriver_linux64");
			DriverFactory.setGeckoDriverPath(
					System.getProperty("user.dir") + "/src/main/resources/executables/geckodriver_linux64");

		} else if (os.equalsIgnoreCase("MACOS")) {
			DriverFactory.setChromeDriverPath(
					System.getProperty("user.dir") + "/src/main/resources/executables/chromedriver_mac");
			DriverFactory.setGeckoDriverPath(
					System.getProperty("user.dir") + "/src/main/resources/executables/geckodriver_mac");
		}

	}

	/**
	 * to set the grid for remote execution
	 */
	public void setGrid() {
		DriverFactory.setGridPath("http://localhost:4444/wd/hub");
		DriverFactory.setConfigPropertyFilePath(
				System.getProperty("user.dir") + "/src/main/resources/properties/Config.properties");

	}

	/**
	 * To load properties from properties file
	 */
	public void loadProperties() {
		/*
		 * Initialize properties Initialize logs load executables
		 * 
		 */
		try {
			fis = new FileInputStream(DriverFactory.getConfigPropertyFilePath());
			configProperties.load(fis);
			log.info("Config properties file loaded");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method is used to launch the browser for specified browser type and it set the desired capabilities for remote execution
	 * 
	 * @param browser - name of the browser on which user wants to execute
	 *                automation scripts
	 */
	public void launchBrowser(String browser) {
		// setBrowserExecutables();
		setGrid();
		loadProperties();

		if (System.getenv("ExecutionType") != null && System.getenv("ExecutionType").equalsIgnoreCase("Grid")) {

			grid = false;
		}

		DriverFactory.setRemote(grid);

		if (DriverFactory.isRemote()) {
			DesiredCapabilities capabilities = null;

			if (browser.equals("firefox")) {

				capabilities = DesiredCapabilities.firefox();
				capabilities.setBrowserName("firefox");
				capabilities.setPlatform(Platform.ANY);

			} else if (browser.equals("chrome")) {

				capabilities = DesiredCapabilities.chrome();
				capabilities.setBrowserName("chrome");
				capabilities.setPlatform(Platform.ANY);
			} else if (browser.equals("ie")) {

				capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setBrowserName("iexplore");
				capabilities.setPlatform(Platform.WIN10);
			}

			try {
				System.out.println("GRID PATH: "+DriverFactory.getGridPath());
				driver = new RemoteWebDriver(new URL(DriverFactory.getGridPath()), capabilities);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		} else if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverPath());
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", DriverFactory.getGeckoDriverPath());
			driver = new FirefoxDriver();
			}else if(browser.equalsIgnoreCase("ie")) {
				
			}

		DriverManager.setWebDriver(driver);
		log.info("Driver Initialized !!!");
		DriverManager.getDriver().manage().window().maximize();
		DriverManager.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	/**
	 * This method is used to quit the driver - driver instance will be closed
	 */
	public void quit() {
		DriverManager.getDriver().quit();
		log.info("Test Execution Completed !!!");
	}

}
