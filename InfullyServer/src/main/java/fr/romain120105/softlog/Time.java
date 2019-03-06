package fr.romain120105.softlog;

import java.util.Calendar;

/**
 * Created by @Romain120105
 */
public class Time {

	
	public static String getDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.HOUR_OF_DAY) +":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
	}
	
	public static String getFileDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH)
		+ "-" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.HOUR_OF_DAY) + "h-"
		+ calendar.get(Calendar.MINUTE) + "m-" + calendar.get(Calendar.SECOND) + "s";
	}
	
}
