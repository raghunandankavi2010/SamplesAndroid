package com.idss.sketchpad.view;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.test.ColorPickerDialog;

import com.idss.sketchpad.controller.DrawingManager;
import com.idss.sketchpad.model.DrawingMenuOption;
import com.idss.sketchpad.utils.Constants;
import com.idss.sketchpad.view.DrawingColorPickerDialog.OnColorChangeListener;
import com.idss.sketchpad.view.DrawingThemePickerDialog.OnThemeChangeListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.*;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Custom class which handles finger tracing process.
 */

public class DrawingRoomScreen extends Activity implements OnThemeChangeListener,
		
       OnColorChangeListener {    
	
	private final String TAG=DrawingRoomScreen.class.getSimpleName();
	 static View content;
	private DrawingManager mDrawingManager=null;
	static Drawable d ;
	private DrawingView mDrawingView=null;
	float sx=1;float sy=1;
	ColorPickerDialog d1;
	static int color;
	static boolean chk =true;
	static String fp;
	private AlertDialog.Builder mDialogMenuOptions=null;
	static boolean spray=false;
//	static boolean zoom=false;
//	static boolean zoomout=false;
	/**
	 * 
	 */
	private AlertDialog.Builder mDialogPenThicknessOptions=null;
	
	private LinearLayout mDrawingPad=null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
             
        Display display=this.getWindowManager().getDefaultDisplay();
        Constants.SCREEN_HEIGHT=display.getHeight();
        Constants.SCREEN_WIDTH=display.getWidth();
        setDrawingBoard();

    }
    /**
     * Prepares drawing board for drawing.
     */
   
    private void setDrawingBoard(){
   	 mDrawingManager=DrawingManager.getInstance();
        
        mDrawingView=new DrawingView(this);
        
        setContentView(R.layout.screen_drawing_room);    
        
        mDrawingPad=(LinearLayout)findViewById(R.id.view_drawing_pad);
        
        mDrawingPad.addView(mDrawingView);
        
        mDrawingPad.setDrawingCacheEnabled(true);
        content = findViewById(R.id.rlid);
        content.setDrawingCacheEnabled(true);
        if(chk==false)
        {
        setDrawingPadTheme();
       
        }
        if(chk==true)
        {
        	   content.setBackgroundColor(color);
        }
        
   }
    
    @Override
    public void onThemeChange(final int themeIndex)
    {
    	//Toast.makeText(DrawingRoomScreen.this, "Theme opted :"+themeIndex,Toast.LENGTH_LONG).show();
    	mDrawingManager.mThemeIndex=mDrawingManager.mThemeIndex==themeIndex?
    			Constants.THEME_INDEX_TRANSPARENT:themeIndex;
    	
    	setDrawingPadTheme();   	
    }
    

    
    @Override
    public void onConfigurationChanged(Configuration configuration)
    {
    	super.onConfigurationChanged(configuration);
    }
    
    
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event)
    {    	
    	switch(keyCode)
    	{
    		case KeyEvent.KEYCODE_BACK:
    			
        		onClickBackButton();
    			break;
    			
    		case KeyEvent.KEYCODE_MENU:
    			onClickMenuButton();
    			break;
    	}
    	
    	return true;
    }
    
    private void setDrawingPadTheme()
    {
    	
    	if(mDrawingManager.mThemeIndex!=Constants.THEME_INDEX_TRANSPARENT)
    	{
    		content.setBackgroundDrawable(getResources().getDrawable(
        			Constants.DRAWING_PAD_THEME[mDrawingManager.mThemeIndex][Constants.INDEX_FOR_BACKGROUND_IMAGE]));
    		 d = Drawable.createFromPath(fp);
    		mDrawingPad.setBackgroundDrawable(d);
    	}
    	else
    	{
    		mDrawingPad.setBackgroundDrawable(null); 	    		
    	}
    }
public void  setDrawingThemefrmGallery()
{
	// To open up a gallery browser
	Intent intent = new Intent();
	intent.setType("image/*");
	intent.setAction(Intent.ACTION_GET_CONTENT);
	startActivityForResult(Intent.createChooser(intent, "Select Picture"),1);
	// To handle when an image is selected from the browser, add the following to your Activity
}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (resultCode == RESULT_OK) {
	if (requestCode == 1) {
	// currImageURI is the global variable I am using to hold the content:// URI of the image
	Uri currImageURI = data.getData();
	System.out.println("Hello======="+getRealPathFromURI(currImageURI));
	String s= getRealPathFromURI(currImageURI);
	File file = new File(s);

    if (file.exists()) {
fp=file.getAbsolutePath();
    d = Drawable.createFromPath(file.getAbsolutePath());
        mDrawingPad.setBackgroundDrawable(d);
    }
    else
    {
    	System.out.println("File Not Found");
    }


	
	}
	}
	}
	// And to convert the image URI to the direct file system path of the image file
	public String getRealPathFromURI(Uri contentUri) {
	// can post image
	String [] proj={MediaStore.Images.Media.DATA};
	Cursor cursor = managedQuery( contentUri,
	proj, // Which columns to return
	null, // WHERE clause; which rows to return (all rows)
	null, // WHERE clause selection arguments (none)
	null); // Order-by clause (ascending by name)
	int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	cursor.moveToFirst();
	return cursor.getString(column_index); 
	
}
    /**
     *  Starts {@link MenuScreen} and closes {@link DrawingRoomScreen}
     */
    private void onClickBackButton()
    {
    	AlertDialog.Builder quitAlert=new AlertDialog.Builder(this);
    	quitAlert.setTitle(R.string.dialog_quit_title_text);
    	quitAlert.setMessage(R.string.dialog_quit_message_text);
    	quitAlert.setPositiveButton(R.string.dialog_button_yes_text,
    		new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				//Need to save state as user likes to resume..
				//Do not reset DrawingManager.java
				startMenuScreen();
			}
		});
    	
    	quitAlert.setNegativeButton(R.string.dialog_button_no_text,
        	new DialogInterface.OnClickListener() {
    		
    		@Override
    		public void onClick(DialogInterface dialog, int which)
    		{
    			//Need not save state as user dont likes to resume..
    			//Reset DrawingManager.java i.e instance set to null
    			
    			DrawingManager.clearDrawingBoard();
    			startMenuScreen();
    		}
    	});
    	
    	quitAlert.setOnKeyListener(new DialogInterface.OnKeyListener() 
    	{			
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
			{
				if(event.getAction()==KeyEvent.ACTION_DOWN)
					dialog.dismiss();
				
				return true;
			}
		});
    	
    	quitAlert.show();
    }
    
    
    private void startMenuScreen()
    {
    	startActivity(new Intent(DrawingRoomScreen.this,MenuScreen.class));
    	finish();
    }

   /**
    * Prepares and displays dialog for menu options.
	*/
    private void onClickMenuButton()
    {
	   mDialogMenuOptions= new AlertDialog.Builder(this);
	   mDialogMenuOptions.setTitle(R.string.dialog_drawing_menu_option_title);
	   mDialogMenuOptions.setItems(R.array.drawing_menu_options, new MyDrawingMenuOptionEventsListener());
	   
	   
	   
	   mDialogMenuOptions.show();
	   
    }
   
   @Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	
}

/**
    * Customized event handler for drawing menu option dialog. This handles click on
    * items & back key
    */
   public class MyDrawingMenuOptionEventsListener implements DialogInterface.OnClickListener,
   				DialogInterface.OnKeyListener{
	   
	   

	@Override
		public void onClick(DialogInterface dialog, int whichItem) 
	   	{
		   	DrawingMenuOption optionId=DrawingMenuOption.values()[whichItem];
		   	switch(optionId)
		   	{
		   		case PEN_THICKNESS:
		   			
		   			onClickPenThicknessPickerOption();
		   			break;
		   			
		   		case PEN_COLOR:
		   			
//		   			new DrawingColorPickerDialog(DrawingRoomScreen.this,
//		   					DrawingRoomScreen.this, 0).show();
		   			final ColorPickerDialog d=	new org.test.ColorPickerDialog(DrawingRoomScreen.this,0xffffffff);
		   			d.setAlphaSliderVisible(true);

					d.setButton("Ok", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							mDrawingManager.mDrawingUtilities.mPaint.setColor(d.getColor());
							
						}
					});

					d.setButton2("Cancel", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});

					d.show();

		   			break;
		   		case DRAWING_THEME:
		   		
	   			new DrawingThemePickerDialog(DrawingRoomScreen.this,
	   					DrawingRoomScreen.this, 0).show();
	   			chk=false;
		   			break;
		   		
		   		case CHANGE_COLOR_THEME:
		   			
		   			 d1=	new org.test.ColorPickerDialog(DrawingRoomScreen.this,0xffffffff);
		   			d1.setAlphaSliderVisible(true);

					d1.setButton("Ok", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							content.setBackgroundDrawable(null);  
							//mDrawingManager.mDrawingUtilities.mPaint.setColor(d.getColor());
						//	content.getBackground().setColorFilter(d1.getColor(), PorterDuff.Mode.LIGHTEN);
							content.setBackgroundColor(d1.getColor());
						 color=	d1.getColor();
						 chk=true;
							 System.out.println("color change");
							content.invalidate();
						}
					});

					d1.setButton2("Cancel", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});

					d1.show();
					
		   			break;
		   		case SAVE_SDCARD:
		   		
		   			AlertDialog.Builder editalert = new AlertDialog.Builder(DrawingRoomScreen.this);
		   			editalert.setTitle("Please Enter the name with which you want to Save");
		   			final EditText input = new EditText(DrawingRoomScreen.this);
		   			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
		   			        LinearLayout.LayoutParams.FILL_PARENT,
		   			        LinearLayout.LayoutParams.FILL_PARENT);
		   			input.setLayoutParams(lp);
		   			editalert.setView(input);
		   			editalert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		   			    public void onClick(DialogInterface dialog, int whichButton) {
				   			content.setDrawingCacheEnabled(true);
				   			String name= input.getText().toString();
				   		    Bitmap bitmap = content.getDrawingCache();
				   	//File f=	 Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();   	
				 //String path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
				   		 String path = Environment.getExternalStorageDirectory().getAbsolutePath(); 
				   		    File file = new File("/sdcard/"+name+".png");	   		
				   		    try 
				   		    {
				   		    	if(!file.exists())
				   		    {
				   		        file.createNewFile();
				   		    }
				   		        FileOutputStream ostream = new FileOutputStream(file);
				   		        bitmap.compress(CompressFormat.PNG, 10, ostream);
				   		        System.out.println("saving......................................................"+path);
				   		        ostream.close();
				   		        content.invalidate();			   		        
				   		    } 
				   		    catch (Exception e) 
				   		    {
				   		        e.printStackTrace();
				   		    }finally
				   		    {
				   		    	
				   		    	content.setDrawingCacheEnabled(false);			   		    	
				   		    }
		   			    }
		   			});

		   			editalert.show();		
		   			break;
		   			
		   		case ERASE:
		   			DrawingRoomScreen.spray=false;
		   		DrawingView v= new DrawingView(getApplicationContext());
		   		v.erase();
		   		System.out.println("hello");
		   			break;
		   			
		   		case PICK_IMAGE_GALLERY:
		   			
		   			setDrawingThemefrmGallery();
		   			break;
		   		
		   		case BLUR:
		   			DrawingRoomScreen.spray=false;
//		   			zoomout=false;
//		   			zoom=false;
		   			Float f= mDrawingManager.mDrawingUtilities.mPaint.getStrokeWidth();
		   			mDrawingManager.mDrawingUtilities.mBlur = new BlurMaskFilter
		   			( f, BlurMaskFilter.Blur.NORMAL);
		   		 if (mDrawingManager.mDrawingUtilities.mPaint.getMaskFilter() != mDrawingManager.mDrawingUtilities.mBlur) {
	                	mDrawingManager.mDrawingUtilities.mPaint.setMaskFilter(mDrawingManager.mDrawingUtilities.mBlur);
	                } else {
	                	mDrawingManager.mDrawingUtilities.mPaint.setMaskFilter(null);
	                }
		   			break;
		   			
		   		case EMBOSS:
		   			DrawingRoomScreen.spray=false;
//		   			zoomout=false;
//		   			zoom=false;
		   			mDrawingManager.mDrawingUtilities.mEmboss = new EmbossMaskFilter(new float[] { 1, 1, 1 },
                            0.4f, 6, 3.5f);

	                if (mDrawingManager.mDrawingUtilities.mPaint.getMaskFilter() != mDrawingManager.mDrawingUtilities.mEmboss) {
	                	mDrawingManager.mDrawingUtilities.mPaint.setMaskFilter(mDrawingManager.mDrawingUtilities.mEmboss);
	                } else {
	                	mDrawingManager.mDrawingUtilities.mPaint.setMaskFilter(null);
	                }
		   			break;
		   		case SRC_ATOP:
		   			DrawingRoomScreen.spray=false;
//		   			zoomout=false;
//		   			zoom=false;
		   			mDrawingManager.mDrawingUtilities.mPaint.setXfermode(new PorterDuffXfermode(
	                                                    PorterDuff.Mode.SRC_ATOP));
		   			mDrawingManager.mDrawingUtilities.mPaint.setAlpha(0x80);
		   			break;
		   		case CLEAR:

		   			mDrawingManager.resetBitmapCanvasAndPath();
	   	    	mDrawingView.invalidate();   	
		   		
				break;
				
//		   		case ZOOM_IN:
////		   		zoom=true;
////		   		zoomout=false;
////	   			DrawingRoomScreen.spray=false;
//		   			if(sx<=5 && sy<=5)
//		   			{
//		   			content.setScaleX(++sx);
//		   			content.setScaleY(++sy);
//		   			content.invalidate();
//		   			mDrawingView.invalidate();
//		   			}
//		   			else
//		   			{
//		   				Toast.makeText(DrawingRoomScreen.this,"Reached Maximum Zoom Limit", 100);
//		   			}
////		   			DrawingView v1= new DrawingView(getApplicationContext());
////			   		v1.zoom(mDrawingView);
//		   			break;
//		   		
//		   		case ZOOM_OUT:
//		   			DrawingRoomScreen.spray=false;
////		   			zoom=false;
////		   			//DrawingView v2= new DrawingView(getApplicationContext());
////			   		//v2.Zoomout();
////		   			zoomout=true;
//
//		   			if(sx >1 && sy>1 )
//		   			{
//		   			
//		   			content.setScaleX(--sx);
//		   			content.setScaleY(--sy);
//		   			content.invalidate();
//		   			mDrawingView.invalidate();
//		   			}
//		   			else
//		   			{
//		   				content.setScaleX(1);
//			   			content.setScaleY(1);
//		   			}
//		   			break;
		   		case DARKEN:
		   			//mDrawingManager.mDrawingUtilities.mBitmap.
		   		//	mDrawingManager.mDrawingUtilities.mPaint.setXfermode(new PorterDuffXfermode(Mode.DARKEN)); 
		   			float[] hsv = new float[3]; 
		   			int color = 	mDrawingManager.mDrawingUtilities.mPaint.getColor(); 
		   			Color.colorToHSV(color, hsv); 
		   			hsv[2] *= 0.8f; // value component 
		   			color = Color.HSVToColor(hsv); 
		   			mDrawingManager.mDrawingUtilities.mPaint.setColor(color);
		   			
		   			mDrawingView.invalidate();
		   			break;
		   		case LIGHTEN:
		   		//	mDrawingManager.mDrawingUtilities.mPaint.setXfermode(new PorterDuffXfermode(Mode.LIGHTEN)); 
		   			float[] hsv2 = new float[3]; 
		   			int color2 = 	mDrawingManager.mDrawingUtilities.mPaint.getColor(); 
		   			Color.colorToHSV(color2, hsv2); 
		   			
		   			hsv2[2] /= 0.8f; // value component 
		   			color = Color.HSVToColor(hsv2); 
		   			mDrawingManager.mDrawingUtilities.mPaint.setColor(color);
		   			mDrawingView.invalidate();
		   			break;
		   		case SPRAY:
		   			DrawingRoomScreen.spray=true;
		   			break;
		   		case FREEHAND:	
		   			DrawingRoomScreen.spray=false;
		   			break;
		   		
		   			default: DrawingRoomScreen.spray=false;
		   		
		   	}
		}

	   @Override
	   public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
	   {
		   if(keyCode==KeyEvent.KEYCODE_BACK &&event.getAction()==KeyEvent.ACTION_DOWN)
		   {
			   dialog.dismiss();
			   content.invalidate();
		   }
		   
		   
		   return true;
	   }
	   
	   
   }
   
   /**
    * Customized event handler for drawing pen thickness option dialog. This handles click on
    * items & back key
   
    *
    */
   private class MyPenThicknessOptionEventsListener implements DialogInterface.OnClickListener,
   				DialogInterface.OnKeyListener{
	   
	   @Override
		public void onClick(DialogInterface dialog, int whichItem) 
	   	{
		  
		   mDrawingManager.mDrawingUtilities.mPaint
		   		.setStrokeWidth(Constants.PEN_THICKNESS[whichItem]);
		   	dialog.dismiss();
		}

	   @Override
	   public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
	   {
		   if(keyCode==KeyEvent.KEYCODE_BACK &&event.getAction()==KeyEvent.ACTION_DOWN)
		   {
			   dialog.dismiss();
		   }
		   
		   return true;
	   }
	   
	   
   }
   
   /**
    * Starts a dialog to choose pen thickness..
    */
   private void onClickPenThicknessPickerOption()
   {
	   mDialogPenThicknessOptions=new AlertDialog.Builder(this);
	   mDialogPenThicknessOptions.setTitle(R.string.dialog_choose_pen_thickness_text);
	   mDialogPenThicknessOptions.setItems(R.array.pen_thickness_options, new MyPenThicknessOptionEventsListener());
	   
	   mDialogPenThicknessOptions.show();
   }
   
   public int getCount() {
	// TODO Auto-generated method stub
	return 0;
}

/**
    * @param name Plain name without any extensions.
    */
   private void saveMyDrawing(String name)
 	{
		ContentValues values = new ContentValues(3);
		values.put(Media.DISPLAY_NAME, name);
		values.put(Media.DESCRIPTION, "Sketches designed by IDSS's SkechPad app");
		values.put(Media.MIME_TYPE, "image/jpeg");

		// Add a new record without the bitmap, but with the values just set.
		// insert() returns the URI of the new record.
		Uri uri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);

		// Now get a handle to the file for that record, and save the data into it.
		// Here, sourceBitmap is a Bitmap object representing the file to save to the database.
		try
		{
			Bitmap sourceBitmap=mDrawingPad.getDrawingCache();
		    OutputStream outStream = getContentResolver().openOutputStream(uri);
		    sourceBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outStream);
		    outStream.close();
		} 
		catch (Exception e)
		{
		    Log.e(TAG, "exception while writing image", e);
		}
  }

   @Override
   public void onColorChange(int colorIndex)
   {
   	mDrawingManager.mDrawingUtilities.mPaint.setColor(Color.parseColor(Constants.PEN_COLOR[colorIndex]));
   }  
   
}
