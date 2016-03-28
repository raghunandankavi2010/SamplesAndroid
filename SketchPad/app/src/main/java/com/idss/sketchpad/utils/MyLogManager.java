package com.idss.sketchpad.utils;

import com.idss.sketchpad.model.MyLogLevel;
import com.idss.sketchpad.model.MyLogProcessType;

import android.util.Log;

/**
 * Class which manages the way of logging. 
 * <br> <br>
 * To enable logging, You must set the corresponding flag in this class.
 * <br>
 * 
 * @author Chandan
 *
 */
public final class MyLogManager {

	/**
	 * Flag if set, only then displays flag to LogCat.
	 */
	private final static boolean DISPLAY_PERMITTED=true;
	
	
	/**
	 * Style used to categorize logging.
	 */
	private final static String CATEGORIZE_STYLE="---------------------";
	
	
	
	
	/**
	 * Method used to display log to log-screen.[LogCat].
	 * 
	 * @param level Any of {@link MyLogLevel}
	 * @param tag   Header for logging.
	 * @param message Log content to be displayed.
	 * @param categorize Flag if set, displays a separation line first and then message. This to enhance readability.
	 */
	private static void display(MyLogLevel level,String tag, String message,boolean categorize)
	{
		if(DISPLAY_PERMITTED)
		{
			if(categorize)
			{
				Log.v(CATEGORIZE_STYLE,CATEGORIZE_STYLE+CATEGORIZE_STYLE+CATEGORIZE_STYLE);
			}
			
			switch(level)
			{
				case ERROR:
					Log.e(tag, message);
					break;
					
				case WARNING:
					Log.w(tag, message);
					break;
					
				case INFORMATION:
					Log.i(tag, message);
					break;
					
				case DEBUG:
					Log.d(tag, message);
					break;
					
				default:
					Log.v(tag+"[Default log level]", message);
					break;	
			}
		}
	}
	
	

	/**
	 * Method which processes the logging event.
	 * @param processType Any of {@link MyLogProcessType}
	 * @param categorize Flag if set, displays a separation line first and then message. This to enhance readability.
	 * @param level Any of {@link MyLogLevel}
	 * @param tag Header for log message.
	 * @param message Content of log.
	 */
	public static void processLog(MyLogProcessType processType,boolean categorize,MyLogLevel level,String tag,String message)
	{
		switch (processType)
		{
			case DISPLAY:
				display(level, tag, message, categorize);
				break;
				
				//More prosses type goes here..
			
			default:
				display(level, tag, message, categorize);
				break;
		}
	}
	
	
	
}
