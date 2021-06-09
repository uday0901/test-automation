package com.automation.utilities;

/**
 * 
 * @author - Uday Kumar Goshika
 * @version 1.0
 * @since 2021-06-09
 *
 */

public class DetectOS {

    /**
     * This class helps determining on which Operating System, JVM is executing this
     */

    /**
     * Types of operating systems
     */
    public enum OSType {
	WINDOWS, MACOS, LINUX
    }

    protected static OSType detectedOS;

    /**
     * 
     * This method detects the operating system from os.name System property and get
     * the result
     * 
     * @returns - the operating system that detected by JVM
     */

    public static OSType getOperatingSystemType() {

	if (detectedOS == null) {
	    String os = System.getProperty("os.name", "generic").toLowerCase();
	    if (os.indexOf("mac") >= 0 || os.indexOf("darwin") >= 0) {
		detectedOS = OSType.MACOS;
	    }
	    else if (os.indexOf("win") >= 0) {
		detectedOS = OSType.WINDOWS;
	    }
	    else if (os.indexOf("nux") >= 0) {
		detectedOS = OSType.LINUX;
	    }

	}

	return detectedOS;
    }

}
