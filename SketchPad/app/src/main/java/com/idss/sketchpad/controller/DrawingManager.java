package com.idss.sketchpad.controller;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import com.idss.sketchpad.model.DrawingTools;
import com.idss.sketchpad.utils.Constants;

public class DrawingManager {

	private static DrawingManager mInstance=null;
	
	public DrawingTools mDrawingUtilities=null;
	
	public int mThemeIndex;
	
	private DrawingManager()
	{
		mDrawingUtilities=new DrawingTools();
		resetDrawingTools();
	}
	
	public static DrawingManager getInstance()
	{
		if(mInstance==null)
		{
			mInstance=new DrawingManager();			
		}
		
		return mInstance;
	}
	

	public void resetDrawingTools()
	{
		System.out.println(" resetting");
		mThemeIndex=Constants.THEME_INDEX_TRANSPARENT;
		
		mDrawingUtilities.mBitmap = Bitmap.createBitmap(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT ,
				Bitmap.Config.ARGB_8888);
	       
	        //Log.d("BITMAP","Restoring...");
	        //mBitmap=BitmapFactory.decodeByteArray(bytes, 0, bytes.length); 
	       
		mDrawingUtilities.mCanvas = new Canvas(mDrawingUtilities.mBitmap);
	        
		mDrawingUtilities.mPath = new Path();
		mDrawingUtilities.mBitmapPaint = new Paint(Paint.DITHER_FLAG);
	        
	    //Added later..
		mDrawingUtilities.mPaint = new Paint();
		mDrawingUtilities.mPaint.setAntiAlias(true);
		mDrawingUtilities.mPaint.setDither(true);
		mDrawingUtilities.mPaint.setColor(0xFFFF0000);
		mDrawingUtilities.mPaint.setStyle(Paint.Style.STROKE);
		mDrawingUtilities.mPaint.setStrokeJoin(Paint.Join.ROUND);
		mDrawingUtilities.mPaint.setStrokeCap(Paint.Cap.ROUND);
		mDrawingUtilities.mPaint.setStrokeWidth(Constants.PEN_THICKNESS[0]);
	;
		//WILL BE UPDATED IN LATER VERSIONS..
	
		//
	  ;
	             
	}
	
	/**
	 * Clears the drawing board.
	 */
	public static void clearDrawingBoard()
	{
		mInstance=null;
	}
	
	/**
	 * method to check whether previous drawing should be resumed or start new one.
	 * @return true if previous drawing should be resumed else false.
	 */
	public static boolean isThereAnythingToResume()
	{
		return mInstance==null?false:true;
	}

	public void resetBitmapCanvasAndPath() {
		// TODO Auto-generated method stub
		mDrawingUtilities.mBitmap = Bitmap.createBitmap(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT ,
				Bitmap.Config.ARGB_8888);
	       
	        //Log.d("BITMAP","Restoring...");
	        //mBitmap=BitmapFactory.decodeByteArray(bytes, 0, bytes.length); 
	       
		mDrawingUtilities.mCanvas = new Canvas(mDrawingUtilities.mBitmap);
	        
		mDrawingUtilities.mPath = new Path();
	}
}
