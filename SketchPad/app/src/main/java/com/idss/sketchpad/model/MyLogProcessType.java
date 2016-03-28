package com.idss.sketchpad.model;


/**
 * Defines how the log should be processed.
 * 
 * @author Chandan
 * 
 * @see #DISPLAY
 * @see #WRITE_TO_FILE
 * @see #WRITE_TO_DATABASE
 *
 */
public enum MyLogProcessType {

	/**
	 * Displays log to console/LogCat
	 */
	DISPLAY,
	
	/**
	 * Write log to a predefined file.
	 */
	WRITE_TO_FILE,
	
	/**
	 * Write log to database.
	 */
	WRITE_TO_DATABASE
	
}
