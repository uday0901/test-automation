package com.automation.utilities;

/**
 * 
 * @author - Uday Kumar Goshika
 * @version 1.0
 * @since 2021-06-09
 *
 */

public class DriverFactory {
    
    /**
     * This class is used to set and get driver executable paths and also to set and get remote execution flag for parallel execution
     */

    private static String chromeDriverPath;
    private static String geckoDriverPath;
    private static String ieDriverPath;
    private static String configPropertyFilePath;
    private static String gridPath;
    private static boolean isRemote;
    
    private DriverFactory() {
	
    }
    

    /**
     * @return the isRemote
     */
    public static boolean isRemote() {
        return isRemote;
    }

    /**
     * @param isRemote the isRemote to set
     */
    public static void setRemote(boolean isRemote) {
        DriverFactory.isRemote = isRemote;
    }
    
     /**
     * @return the chromeDriverPath
     */
    public static String getChromeDriverPath() {
	return chromeDriverPath;
    }

    /**
     * @param chromeDriverPath - to set the chromeDriverPath
     */
    public static void setChromeDriverPath(String chromeDriverPath) {
	DriverFactory.chromeDriverPath = chromeDriverPath;
    }

    /**
     * @return the gechoDriverPath
     */
    public static String getGeckoDriverPath() {
	return geckoDriverPath;
    }

    /**
     * @param gechoDriverPath - to set the gechoDriverPath
     */
    public static void setGeckoDriverPath(String geckoDriverPath) {
	DriverFactory.geckoDriverPath = geckoDriverPath;
    }

    /**
     * @return the ieDriverPath
     */
    public static String getIeDriverPath() {
	return ieDriverPath;
    }

    /**
     * @param ieDriverPath - to set the ieDriverPath
     */
    public static void setIeDriverPath(String ieDriverPath) {
	DriverFactory.ieDriverPath = ieDriverPath;
    }

    /**
     * @return the configPropertyFilePath
     */
    public static String getConfigPropertyFilePath() {
	return configPropertyFilePath;
    }

    /**
     * @param configPropertyFilePath the configPropertyFilePath to set
     */
    public static void setConfigPropertyFilePath(String configPropertyFilePath) {
	DriverFactory.configPropertyFilePath = configPropertyFilePath;
    }

    /**
     * @return the gridPath
     */
    public static String getGridPath() {
	return gridPath;
    }

    /**
     * @param gridPath -  to set the gridPath
     */
    public static void setGridPath(String gridPath) {
	DriverFactory.gridPath = gridPath;
    }

}
