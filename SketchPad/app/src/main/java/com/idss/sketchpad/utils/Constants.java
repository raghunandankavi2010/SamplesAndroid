package com.idss.sketchpad.utils;

import com.idss.sketchpad.view.R;

/**
 * @author Chandan
 *
 */
public final class Constants {

	/************************************************************************************************************
	 * STRING CONSTANTS
	 ***********************************************************************************************************/
	
	/**
	 * Url of the IDSS web site.
	 */
	public final static String URL_COMPANY_SITE="http://www.yugasys.com/";
	
	/**
	 * Url to the IDSS's Android app store. Search key is emebeded in url so that, this link lists all the 
	 * apps from the IDSS.
	 */
	public final static String URL_COMPANY_APP_MARKET="https://market.android.com/search?q=INDIAN+DREAMERS+SOFTWARE+&so=1&c=apps";
	
	/**
	 * email-id of IDSS. To which all the mails sent/shared by end user will be mailed.[in Bcc field]
	 */
	public final static String EMAIL_COMPANY_ID="support@idreamerssoft.com";
	
	
	public final static String EMAIL_SUBJECT_FOR_SHARING="SketchPad-A superb Android App !";
	
	public final static String EMAIL_BODY_FOR_SHARING="Hi\n\n"
			+"		I thought I wouldd tell you about the SKETCH PAD APP.The best android app on android market"
			+"on drawing/sketches.\n	This app is specially designed for people of all ages ."
			+"The app can be downloaded from the Android Market straight to your Android mobile, "
			+"Just search for \"sketch pad\". If you like this app, please tell your friends " 
			+"about it and share the fun you had by sketch pad through your sketches with " 
			+"different colors and background. Many more exciting features to be updated and " 
			+"released in the next version of sketch pad. Have an eye on it.\n\nThanks for your time";
	
	/************************************************************************************************************
	 * INTEGER CONSTANTS
	 ***********************************************************************************************************/
	/*
	 * Splash time delay in mili seconds.
	 */
	public final static int SPLASH_SCREEN_DISPLAY_TIME=2000;
	
	public final static int THEME_INDEX_TRANSPARENT=-1;
	
	public final static int INDEX_FOR_BACKGROUND_IMAGE=0;
	
	public final static int INDEX_FOR_BACKGROUND_THUMB_IMAGE=1;
	
	
	public  static int SCREEN_WIDTH=480;
	
	public static int SCREEN_HEIGHT=320;
	
	public final static String[] PEN_COLOR={
		"#F9270B",//RED
		"#26FB09",//LIGHT GREEN
		"#0511FF",//DARK BLUE
		"#F1FA0A",//YELLOW
		"#0DF7DE",//SKY BLUE
		"#016F01",//DARK GREEN
		"#FFFFFF",//WHITE
		"#000000",//BLACK		
		"#660000",
		"#9900CC",
		"#4A9586",
		"#99FF33",
		"#CC0066",
		"#3923D6"
		
		
	};
	
	/**
	 * Array specifying various thickness available
	 */
	public final static int[] PEN_THICKNESS={
		2,
		4,
		6,
		8,
		12,
		16
	};
	
	
	/************************************************************************************************************
	 * BOOLEAN CONSTANTS
	 ***********************************************************************************************************/
	
	
	/************************************************************************************************************
	 * RESOURCE CONSTANTS
	 ***********************************************************************************************************/
	
	
	public final static int[][]DRAWING_PAD_THEME={
		// {RESOURCE_ID_OF_BACKGROUND_IMAGE, RESOURCE_ID_OF_THUMBNAIL }
		// [ROW][INDEX_FOR_BACKGROUND_IMAGE]  ,  [ROW][INDEX_FOR_BACKGROUND_THUMB_IMAGE]
		{ R.drawable.bg_view_pad_1,R.drawable.bg_view_pad_thumb_1},
		{ R.drawable.bg_view_pad_2,R.drawable.bg_view_pad_thumb_2},
		{ R.drawable.bg_view_pad_3,R.drawable.bg_view_pad_thumb_3},
		{ R.drawable.bg_view_pad_4,R.drawable.bg_view_pad_thumb_4},
		{ R.drawable.bg_view_pad_5,R.drawable.bg_view_pad_thumb_5},
		{ R.drawable.bg_view_pad_6,R.drawable.bg_view_pad_thumb_6},
		{ R.drawable.bg_view_pad_7,R.drawable.bg_view_pad_thumb_7},
		{ R.drawable.bg_view_pad_8,R.drawable.bg_view_pad_thumb_8},
		{ R.drawable.bg_view_pad_9,R.drawable.bg_view_pad_thumb_9},
		{ R.drawable.bg_view_pad_10,R.drawable.bg_view_pad_thumb_10},
		{ R.drawable.bg_view_pad_11,R.drawable.bg_view_pad_thumb_11}
		
	};
	
}
