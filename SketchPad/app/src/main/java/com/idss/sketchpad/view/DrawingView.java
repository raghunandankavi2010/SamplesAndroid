package com.idss.sketchpad.view;




import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import com.idss.sketchpad.controller.DrawingManager;
import com.idss.sketchpad.utils.Constants;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;

import android.view.View;

/**
 * Custom View class which is singleton, handles all the process involved in tracing finger.
 */
public class DrawingView  extends View {
public static boolean chk=true;
float sx=1;float sy=1;

private float mScaleFactor = 1.f; Point pnt;
Point spp;
ArrayList<Point> p= new ArrayList<Point>();
ArrayList<Point> tp= new ArrayList<Point>();
static int ptr;
Bitmap bm;
	//private static final float MINP = 0.25f;       
	//private static final float MAXP = 0.75f;

  static Canvas c;
   Context context;
  
    /**
     *  
     */
	private DrawingManager mDrawingManager=null;
    
    /**
     * @param context context of the activity.
     */
    public DrawingView(Context context) {
        super(context);
      
        mDrawingManager=DrawingManager.getInstance();
        this.context=context;
        bm= BitmapFactory.decodeResource(getResources(), R.drawable.icon);
      // mScaleDetector = new ScaleGestureDetector(context, new ScaleListener()); 
       
    }

	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
   
    
    @Override
    protected void onDraw(Canvas canvas) {
    	c=canvas;
    	
        canvas.drawColor(0xfff);//0xFFAAAAA
    	System.out.println("drawing");
    	Display display = ((Activity) context).getWindowManager().getDefaultDisplay();  
    	float w = display.getWidth(); 
    	float h = display.getHeight();
    	Paint bp= new Paint();
    	bp.setColor(Color.RED);
    	bp.setStrokeWidth(5);
    	canvas.drawLine(0, 0, w, 0,bp);
    	canvas.drawLine(0, 0, 0, h,bp);
    	canvas.drawLine(w,h,w,0,bp);
    	canvas.drawLine(w, h, 0,h , bp);
    	canvas.drawColor(R.drawable.bg_view_pad_3);
    	
        canvas.drawBitmap(mDrawingManager.mDrawingUtilities.mBitmap, 0, 0,
        		mDrawingManager.mDrawingUtilities.mBitmapPaint);

    canvas.drawPath(mDrawingManager.mDrawingUtilities.mPath, 
    		mDrawingManager.mDrawingUtilities.mPaint);
    //canvas.scale(mScaleFactor, mScaleFactor); 
   
   	//for (Point point : p) {
//   		while(ptr<p.size())
//   		{
//   			Point point= p.get(ptr);
//            canvas.drawCircle(point.x, point.y,50, mDrawingManager.mDrawingUtilities.mPaint);
//            ptr++;
//       }


    }
  
	/**
     * 
     */
    private float mX;
    
    /**
     * 
     */
    private float mY;
    
    /**
     * 
     */
    private static final float TOUCH_TOLERANCE = 4;

    /**
     * @param x
     * @param y
     */
  

    private void touch_start(float x, float y) {
    	 //mDrawingManager.mDrawingUtilities.mCanvas.drawBitmap(bm, x, y, null); 
//    	if(DrawingRoomScreen.zoom==true)
//    	{
//    	spp= new Point();
//    	spp.x=(int) x;
//    	spp.y=(int) y;
//			if(sx<=5 && sy<=5)
//			{
//				DrawingRoomScreen.content.setScaleX(++sx);
//	    	DrawingRoomScreen.content.setScaleY(++sy);
//	    	DrawingRoomScreen.content.setPivotX(spp.x);
//	    	DrawingRoomScreen.content.setPivotY(spp.y);
//			}
//    	
//
//    	}
//    	if(DrawingRoomScreen.zoomout==true)
//    	{
//    		if(sx >1 && sy>1 )
//   			{
//    	spp= new Point();
//    	spp.x=(int) x;
//    	spp.y=(int) y;
//    	DrawingRoomScreen.content.setScaleX(--sx);
//    	DrawingRoomScreen.content.setScaleY(--sy);
//    	DrawingRoomScreen.content.setPivotX(spp.x);
//    	DrawingRoomScreen.content.setPivotY(spp.y);
//   			}
//    	}
    	if(DrawingRoomScreen.spray==false )
    	{
    		//mDrawingManager.mDrawingUtilities.mCanvas.drawBitmap(bm, x,y, mDrawingManager.mDrawingUtilities.mPaint);
    	mDrawingManager.mDrawingUtilities.mPath.reset();
    	mDrawingManager.mDrawingUtilities.mPath.moveTo(x, y);
        mX = x;
        mY = y;
      
        System.out.println("Hello......................1");
    	}
                if(DrawingRoomScreen.spray==true )
        {	
                	//mDrawingManager.mDrawingUtilities.mCanvas.drawPath(mDrawingManager.mDrawingUtilities.mPath, mDrawingManager.mDrawingUtilities.mPaint); 

                	final Point p1 = new Point();
                	p1.x=(int) x;
                	p1.y=(int) y;
               final int sourceColor= 	mDrawingManager.mDrawingUtilities.mBitmap.getPixel((int)x,(int) y);
               final int targetColor = mDrawingManager.mDrawingUtilities.mPaint.getColor();
               new TheTask(mDrawingManager.mDrawingUtilities.mBitmap, p1, sourceColor, targetColor).execute();
//               ((Activity) context).runOnUiThread(new Runnable() { 
//            	    public void run() { 
//            	    	FloodFill(mDrawingManager.mDrawingUtilities.mBitmap, p1, sourceColor, targetColor);
//            	    } 
//            	}); 
             
                	//FloodFill(mDrawingManager.mDrawingUtilities.mBitmap, p1, sourceColor, targetColor);
//                	Rect r= new Rect((int)x-5, (int)y-5,(int)x+5, (int)y+5);
//                	Rect r = new Rect(4,2,,(int)mDrawingManager.mDrawingUtilities.mPaint.getStrokeWidth()); 
//                	mDrawingManager.mDrawingUtilities.mCanvas.clipRect(r); // see also clipRegion 
//                	mDrawingManager.mDrawingUtilities.mCanvas.drawColor(Color.RED);
//                	invalidate();
//        float radius= mDrawingManager.mDrawingUtilities.mPaint.getStrokeWidth();
//        Paint paint= new Paint();
//        paint.setColor(mDrawingManager.mDrawingUtilities.mPaint.getColor());
//        if(mDrawingManager.mDrawingUtilities.mPaint.getStrokeWidth() == radius){ 
//            int dotsToDrawAtATime = 50;
//         
//            for (int i = 0; i < dotsToDrawAtATime; i++){ 
//            	Random Random = new Random();
//         
//                // Get the location to draw to 
//            	float x1 = (float) (x + Random.nextGaussian()*radius); 
//                float y1 = (float) (y + Random.nextGaussian()*radius); 
//                System.out.println("Values........................"+x1+" "+y1);
//                mDrawingManager.mDrawingUtilities.mCanvas.drawPoint(x1, y1, paint);
//                 
//            } 
//        } 
       }
    }
 
    public void clear(View content)
    {
    	//mDrawingManager.mDrawingUtilities.mPath.rewind();
    	
    	mDrawingManager.mDrawingUtilities.mBitmap = null;
    	mDrawingManager.mDrawingUtilities.mPath = null;
    	mDrawingManager.mDrawingUtilities.mBitmap = Bitmap.createBitmap(480, 480, Bitmap.Config.ARGB_8888);
    	mDrawingManager.mDrawingUtilities.mPath = new Path();
    	mDrawingManager.resetDrawingTools();
    	invalidate();
    		
    }
    public  void erase()
    {
    		
//
//    		mDrawingManager.mDrawingUtilities.mPaint.setAlpha(0xFF);
//			mDrawingManager.mDrawingUtilities.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//			invalidate();
			
    }
    private void touch_move(float x, float y) {
//    	Matrix mxTransform = new Matrix(); 
//        PathMeasure pm = new PathMeasure(mDrawingManager.mDrawingUtilities.mPath, false); 
//        mDrawingManager.mDrawingUtilities.mCanvas.drawBitmap(bm, mxTransform, null); 

    	if(DrawingRoomScreen.spray==false )
    	{
    		
    		//bm.eraseColor(Color.WHITE);
    		
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
    
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
         
        	//mDrawingManager.mDrawingUtilities.mCanvas.drawBitmap(bm, x,y, mDrawingManager.mDrawingUtilities.mPaint);
        	//invalidate();
        	mDrawingManager.mDrawingUtilities.mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
            
        
    	}
    	}
   	  }
    
    
    /**
     * 
     */
    private void touch_up() {
  
//        if(DrawingRoomScreen.spray==true)
//        {
//        float radius= mDrawingManager.mDrawingUtilities.mPaint.getStrokeWidth();
//        Paint paint= new Paint();
//        paint.setColor(mDrawingManager.mDrawingUtilities.mPaint.getColor());
//        if(mDrawingManager.mDrawingUtilities.mPaint.getStrokeWidth() == radius){ 
//            int dotsToDrawAtATime = 20;
//         
//            for (int i = 0; i < dotsToDrawAtATime; i++){ 
//            	Random Random = new Random();
//         
//                // Get the location to draw to 
//              float x1 = (float) (mX + Random.nextGaussian()*radius); 
//                float y1 = (float) (mY + Random.nextGaussian()*radius); 
//                mDrawingManager.mDrawingUtilities.mCanvas.drawPoint(x1, y1, paint);
//                
//            } 
//        } 
//       }
    	if(DrawingRoomScreen.spray==false )
    	{
    		  // bm= BitmapFactory.decodeResource(getResources(), R.drawable.icon);
    		 //  bm.eraseColor(Color.WHITE);
       		//mDrawingManager.mDrawingUtilities.mCanvas.drawBitmap(bm, 0,0, mDrawingManager.mDrawingUtilities.mPaint);
    	mDrawingManager.mDrawingUtilities.mPath.lineTo(mX, mY);
        // commit the path to our offscreen
    	mDrawingManager.mDrawingUtilities.mCanvas.drawPath(
    	mDrawingManager.mDrawingUtilities.mPath, 
    	mDrawingManager.mDrawingUtilities.mPaint);
        // kill this so we don't double draw
    	mDrawingManager.mDrawingUtilities.mPath.reset();
    	mDrawingManager.mDrawingUtilities.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
    	mDrawingManager.mDrawingUtilities.mPaint.setMaskFilter(null);
    	//mDrawingManager.mDrawingUtilities.mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.SCREEN);
    	}
       
    }
//
  @Override
  public boolean onTouchEvent(MotionEvent event) {
     float  x = event.getX();
      float  y = event.getY();
      Point pnt =new Point((int)x,(int)y);
      
    float pressure=event.getPressure();
    if(pressure>1)
    {
    	float[] hsv = new float[3]; 
			int color = 	mDrawingManager.mDrawingUtilities.mPaint.getColor(); 
			Color.colorToHSV(color, hsv); 
			hsv[2] *= 0.8f; // value component 
			color = Color.HSVToColor(hsv); 
			mDrawingManager.mDrawingUtilities.mPaint.setColor(color);
			//mDrawingManager.mDrawingUtilities.mDrawingView.invalidate();
    }
      switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
        	 
              touch_start(x, y); 
              invalidate();
        	  
              break;
             
          case MotionEvent.ACTION_MOVE:
        	 
              touch_move(x, y);
              invalidate();
              
              break;
          case MotionEvent.ACTION_UP:
   	  
              touch_up();
              invalidate();
             
              break;
              
      }
      return true;
  }

//  private void FloodFill(Bitmap bmp, Point pt, int targetColor, int replacementColor){ 
//	  Queue<Point> q = new LinkedList<Point>(); 
//	  q.add(pt); 
//	  while (q.size() > 0) { 
//	      Point n = q.poll(); 
//	      if (bmp.getPixel(n.x, n.y) != targetColor) 
//	          continue; 
//	   
//	      Point w = n, e = new Point(n.x + 1, n.y); 
//	      while ((w.x > 0) && (bmp.getPixel(w.x, w.y) == targetColor)) { 
//	          bmp.setPixel(w.x, w.y, replacementColor); 
//	          if ((w.y > 0) && (bmp.getPixel(w.x, w.y - 1) == targetColor)) 
//	              q.add(new Point(w.x, w.y - 1)); 
//	          if ((w.y < bmp.getHeight() - 1) 
//	                  && (bmp.getPixel(w.x, w.y + 1) == targetColor)) 
//	              q.add(new Point(w.x, w.y + 1)); 
//	          w.x--; 
//	      } 
//	      while ((e.x < bmp.getWidth() - 1) 
//	              && (bmp.getPixel(e.x, e.y) == targetColor)) { 
//	          bmp.setPixel(e.x, e.y, replacementColor); 
//	   
//	          if ((e.y > 0) && (bmp.getPixel(e.x, e.y - 1) == targetColor)) 
//	              q.add(new Point(e.x, e.y - 1)); 
//	          if ((e.y < bmp.getHeight() - 1) 
//	                  && (bmp.getPixel(e.x, e.y + 1) == targetColor)) 
//	              q.add(new Point(e.x, e.y + 1)); 
//	          e.x++; 
//	      } 
//	  }} 
	class TheTask extends AsyncTask<Void, Integer, Void> {
		
		Bitmap bmp;
		Point pt;
		int replacementColor,targetColor;
		ProgressDialog pd;
public TheTask(Bitmap bm,Point p, int sc, int tc)
{
	
	this.bmp=bm;
	this.pt=p;
	this.replacementColor=tc;
	this.targetColor=sc;
	pd= new ProgressDialog(context);
	pd.setMessage("Filling....");
}
		@Override
		protected void onPreExecute() {
pd.show();

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			
		}

		@Override
		protected Void doInBackground(Void... params) {
			FloodFill f= new FloodFill();
			f.floodFill(bmp,pt,targetColor,replacementColor);
////
//			  Queue<Point> q = new LinkedList<Point>(); 
//			  q.add(pt); 
//			  while (q.size() > 0) { 
//			      Point n = q.poll(); 
//			      if (bmp.getPixel(n.x, n.y) != targetColor) 
//			          continue; 
//			   
//			      Point w = n, e = new Point(n.x + 1, n.y); 
//			      while ((w.x > 0) && (bmp.getPixel(w.x, w.y) == targetColor)) { 
//			          bmp.setPixel(w.x, w.y, replacementColor); 
//			          if ((w.y > 0) && (bmp.getPixel(w.x, w.y - 1) == targetColor)) 
//			              q.add(new Point(w.x, w.y - 1)); 
//			          if ((w.y < bmp.getHeight() - 1) 
//			                  && (bmp.getPixel(w.x, w.y + 1) == targetColor)) 
//			              q.add(new Point(w.x, w.y + 1)); 
//			          w.x--; 
//			      } 
//			      while ((e.x < bmp.getWidth() - 1) 
//			              && (bmp.getPixel(e.x, e.y) == targetColor)) { 
//			          bmp.setPixel(e.x, e.y, replacementColor); 
//			   
//			          if ((e.y > 0) && (bmp.getPixel(e.x, e.y - 1) == targetColor)) 
//			              q.add(new Point(e.x, e.y - 1)); 
//			          if ((e.y < bmp.getHeight() - 1) 
//			                  && (bmp.getPixel(e.x, e.y + 1) == targetColor)) 
//			              q.add(new Point(e.x, e.y + 1)); 
//			          e.x++; 
//			      } 
//			  }
//
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {	

	pd.dismiss();
	invalidate();
		}

	}
//
 

}

