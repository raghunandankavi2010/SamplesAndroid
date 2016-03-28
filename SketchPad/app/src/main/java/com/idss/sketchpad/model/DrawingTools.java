package com.idss.sketchpad.model;

import com.idss.sketchpad.view.DrawingView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Model class which unites all the utilities used by {@link DrawingView}
 * @author Chandan
 *
 */
public class DrawingTools {
	
	/**
     * 
     */
    public Canvas  mCanvas;
    
    /**
     * 
     */
    public Path    mPath;
    
    /**
     * 
     */
    public Paint   mBitmapPaint;

    /**
     * 
     */
    public Bitmap  mBitmap;
    
    /**
     * 
     */
    public Paint  mPaint;
    
    
    /**
     * 
     */
    public MaskFilter  mEmboss;
    
    
    /**
     * 
     */
    public MaskFilter  mBlur;

}
