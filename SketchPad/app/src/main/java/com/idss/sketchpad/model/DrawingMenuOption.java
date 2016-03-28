package com.idss.sketchpad.model;

/**
 * Enum mapped to drawing_menu_options in array.xml
 * 
 * @author Chandan
 *
 */
//Ensure that order in enum as well as in array.xml is same.
public enum DrawingMenuOption {

	/**
	 * Represents choose pen thickness option.
	 */
	PEN_THICKNESS,
	
	/**
	 * Represents choose pen color option.
	 */
	PEN_COLOR,
	
	/**
	 * Represents choose drawing theme option.
	 */
	DRAWING_THEME,
	/**
	 * 
	 */
	CHANGE_COLOR_THEME,
	/**
	 * Represents save to gallery option.
	 */
	SAVE_SDCARD,
	
	/**
	 * Represents share about the app option.
	 */
	//SHARE
	ERASE,
	
	/**
	 * Pick Image from Gallery
	 */
	PICK_IMAGE_GALLERY,
	
	/**
	 * Use Blur 
	 */
	BLUR,
	/**
	 * Use Emboss
	 */
	EMBOSS,
	/**
	 * 
	 */
	SRC_ATOP,
	/**
	 * 
	 */
	CLEAR,
	/**
	 * 
	 */
	/*ZOOM_IN,
	*//**
	 * 
	 *//*
	ZOOM_OUT,*/
	/**
	 * 
	 */
	DARKEN,
	LIGHTEN,
	SPRAY,
	FREEHAND,
	
}
