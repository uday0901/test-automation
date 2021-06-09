package com.automation.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateFormats {
	
	public static String getDateFormatForFileNames(){
		String dateForFilename = "";
		try {
		Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyyHHmmssSSS");
	    dateForFilename = dateFormat.format(calendar.getTime());
		}catch(Exception e) {
			
		}
		
		return dateForFilename;
	}
}
