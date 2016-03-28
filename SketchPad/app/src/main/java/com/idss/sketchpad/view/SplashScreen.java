package com.idss.sketchpad.view;

import java.util.Timer;
import java.util.TimerTask;

import com.idss.sketchpad.utils.Constants;
import com.idss.sketchpad.utils.MyLogManager;
import com.idss.sketchpad.model.MyLogLevel;
import com.idss.sketchpad.model.MyLogProcessType;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * Custom class of type {@link Activity}, designed for splash screen.
 * 
 * @author Chandan
 *
 */
public class SplashScreen extends Activity {
   
	/**
	 * Header, used for logs of this class.
	 */
	private final String TAG=SplashScreen.class.getSimpleName();
	
	/**
	 * Instance of type {@link Timer} associated with splash timer process.
	 */
	private Timer mSplashTimer;
	
	/**
	 * Instance of  {@link SplashTimerTask}
	 */
	private SplashTimerTask mSplashTimerTask;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        MyLogManager.processLog(MyLogProcessType.DISPLAY,true,MyLogLevel.DEBUG,TAG,"In method onCreate()");
        
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.screen_splash);
        
        //handled in onResume()..
        //startSplashTimer();
        
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
    	
    	stopSplashTimer();
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    	
    	startSplashTimer();
    }
    
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	
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
    	stopSplashTimer();
    	finish();
    }
    
    
    /**
     *  Starts {@link MenuScreen} and closes {@link SplashScreen}
     */
    private void startMenuScreen()
    {
    	stopSplashTimer();
    	startActivity(new Intent(SplashScreen.this,MenuScreen.class));
    	finish();
    }
    
    /**
     *  Starts the timer which schedules termination of splash screen and hence start of Menu screen.
     *  
     *  @see SplashTimerTask
     */
    private void startSplashTimer()
    {
    	if(mSplashTimer!=null)
    	{
    		stopSplashTimer();
    	}
    	
    	mSplashTimerTask=null;
    	
    	mSplashTimerTask=new SplashTimerTask();    	
    	mSplashTimer=new Timer();
    	
    	mSplashTimer.schedule(mSplashTimerTask, Constants.SPLASH_SCREEN_DISPLAY_TIME);
    	
    }
    
    /**
     * Closes the splash timer and set it to null.
     */
    private void stopSplashTimer()
    {
    	if(mSplashTimer!=null)
    	{
    		mSplashTimer.cancel();
    		mSplashTimer=null;
    	}
    	
    }
    
    /**
     * Custom class of type {@link TimerTask} which handles task associated with Splash timer.
     * 
     * @author Chandan
     *
     */
    private class SplashTimerTask extends TimerTask{

		@Override
		public void run()
		{
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() 
				{
					startMenuScreen();
				}
			});
		}
    	
    }
    
   
}