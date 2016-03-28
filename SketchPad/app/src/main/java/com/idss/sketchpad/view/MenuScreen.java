package com.idss.sketchpad.view;

import com.idss.sketchpad.utils.Constants;
import com.idss.sketchpad.utils.MyLogManager;
import com.idss.sketchpad.controller.DrawingManager;
import com.idss.sketchpad.model.MyLogLevel;
import com.idss.sketchpad.model.MyLogProcessType;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Custom class of type {@link Activity}, designed for menu screen.
 * 
 * @author Chandan
 *
 */
public class MenuScreen extends Activity implements OnTouchListener{
   
	/**
	 * Header, used for logs of this class.
	 */
	private final String TAG=MenuScreen.class.getSimpleName();
	
	
	/**
	 * Button reference for new drawing screen.
	 */
	private Button mButtonNewDrawing;
	
	/**
	 * Button reference for resuming of previous drawing if any.
	 */
	private Button mButtonResumeDrawing;
	
	/**
	 * Button reference for share screen.
	 */
	//private Button mButtonShare;
	
	/**
	 * Button reference for More apps web link.
	 */
	private Button mButtonMoreApps;
	
	
	/**
	 * Button reference for about us screen.
	 */
	private Button mButtonAboutUs;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        MyLogManager.processLog(MyLogProcessType.DISPLAY,true,MyLogLevel.DEBUG,TAG,"In method onCreate()");
        
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.screen_menu);
        
        mButtonNewDrawing=(Button)findViewById(R.id.buttonNewDrawing);
        mButtonResumeDrawing=(Button)findViewById(R.id.buttonResumeDrawing);
       // mButtonShare=(Button)findViewById(R.id.buttonShare);
        mButtonMoreApps=(Button)findViewById(R.id.buttonMoreApps);
        mButtonAboutUs=(Button)findViewById(R.id.buttonAboutUs);
        
        mButtonNewDrawing.setOnTouchListener(this);
        mButtonResumeDrawing.setOnTouchListener(this);
       // mButtonShare.setOnTouchListener(this);
        mButtonMoreApps.setOnTouchListener(this);
        mButtonAboutUs.setOnTouchListener(this);
        
        if(!DrawingManager.isThereAnythingToResume())
        {
        	mButtonResumeDrawing.setVisibility(View.GONE);
        }
       
    }
   
    
    @Override
    public void onStart()
    {
    	super.onStart();
    	
    }
    
    @Override
    public void onPause()
    {
    	super.onPause();
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	
    }
    
    

	@Override
	public boolean onTouch(View view, MotionEvent event) 
	{
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				switch(view.getId())
				{
					case R.id.buttonNewDrawing:
						//mButtonNewDrawing.setBackgroundColor(R.color.button_background_onclick_color);
						break;
						
					case R.id.buttonResumeDrawing:
						//mButtonResumeDrawing.setBackgroundColor(R.color.button_background_onclick_color);
						break;
						
//					case R.id.buttonShare:
//						//mButtonShare.setBackgroundColor(R.color.button_background_onclick_color);
//						break;
						
					case R.id.buttonMoreApps:
						//mButtonMoreApps.setBackgroundColor(R.color.button_background_onclick_color);
						break;
						
					case R.id.buttonAboutUs:
						//mButtonAboutUs.setBackgroundColor(R.color.button_background_onclick_color);
						break;
				}
				break;
				
			case MotionEvent.ACTION_UP:
				switch(view.getId())
				{
					case R.id.buttonNewDrawing:
						//mButtonNewDrawing.setBackgroundColor(R.color.button_background_color);
						DrawingManager.clearDrawingBoard();
						DrawingRoomScreen.color=Color.BLACK;
						DrawingRoomScreen.spray=false;
						//as startDrawingRoomScreen(); is common in both cases.., 
						//No break statement is required.Be careful here..!!
					case R.id.buttonResumeDrawing:
						//mButtonResumeDrawing.setBackgroundColor(R.color.button_background_color);
						startDrawingRoomScreen();
						
						break;
					
//					case R.id.buttonShare:
//						//mButtonShare.setBackgroundColor(R.color.button_background_color);
//						startShareScreen();
//						break;
						
					case R.id.buttonMoreApps:
						//mButtonMoreApps.setBackgroundColor(R.color.button_background_color);
						openWebPageFor(Constants.URL_COMPANY_APP_MARKET);
						break;
						
					case R.id.buttonAboutUs:
						//mButtonAboutUs.setBackgroundColor(R.color.button_background_color);
						openWebPageFor(Constants.URL_COMPANY_SITE);
						break;
				}
				break;
		}
		return true;
	}
    

    
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent)
    {
    	switch(keyCode)
    	{
    		case KeyEvent.KEYCODE_BACK:
    			onClickBackButton();
    			break;  			
    			
    	}
    	
    	return true;
    }
    
    /**
     * Method to handle BACK key press action.
     */
    private void onClickBackButton()
    {
    	finish();
    }
    
    /**
     *  Starts {@link DrawingRoomScreen} and closes {@link MenuScreen}
     */
    private void startDrawingRoomScreen()
    {
    	startActivity(new Intent(MenuScreen.this,DrawingRoomScreen.class));
    	finish();
    }


    /**
     *  Starts {@link ShareScreen} and closes {@link MenuScreen}
     */
    private void startShareScreen()
    {
    	startActivity(new Intent(MenuScreen.this,ShareScreen.class));
    	finish();
    }

  
    private void openWebPageFor(final String link)
    {
    	Intent intent = new Intent(Intent.ACTION_VIEW);
    	intent.setData(Uri.parse(link));
    	startActivity(intent);

    }
   
}