package com.idss.sketchpad.view;

import com.idss.sketchpad.model.MyLogLevel;
import com.idss.sketchpad.model.MyLogProcessType;
import com.idss.sketchpad.utils.Constants;
import com.idss.sketchpad.utils.MyLogManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


/**
 * Custom class of type {@link Activity}, designed for about us screen.
 * 
 * @author Chandan
 *
 */
public class ShareScreen extends Activity implements OnTouchListener{
   
	/**
	 * Header, used for logs of this class.
	 */
	private final String TAG=ShareScreen.class.getSimpleName();
	
	Button mButtonShareViaEmail;
	
	Button mButtonShareViaTwitter;
	
	Button mButtonShareViaFacebook;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        MyLogManager.processLog(MyLogProcessType.DISPLAY,true,MyLogLevel.DEBUG,TAG,"In method onCreate()");
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.screen_share);
       
        mButtonShareViaEmail=(Button)findViewById(R.id.buttonShareViaEmail);
        mButtonShareViaTwitter=(Button)findViewById(R.id.buttonShareViaTwitter);
        mButtonShareViaFacebook=(Button)findViewById(R.id.buttonShareViaFacebook);
        
        mButtonShareViaEmail.setOnTouchListener(this);
        mButtonShareViaTwitter.setOnTouchListener(this);
        mButtonShareViaFacebook.setOnTouchListener(this);
        
        //For now,
        mButtonShareViaTwitter.setVisibility(View.GONE);
        mButtonShareViaFacebook.setVisibility(View.GONE);
        
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
    

	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				switch(view.getId())
				{
					case R.id.buttonShareViaEmail:
						//mButtonShareViaEmail.setBackgroundColor(R.color.button_background_onclick_color);
						break;
						
					case R.id.buttonShareViaTwitter:
						//mButtonShareViaTwitter.setBackgroundColor(R.color.button_background_onclick_color);
						break;
						
					case R.id.buttonShareViaFacebook:
						//mButtonShareViaFacebook.setBackgroundColor(R.color.button_background_onclick_color);
						break;
					
				}
				break;
				
			case MotionEvent.ACTION_UP:
				switch(view.getId())
				{
					case R.id.buttonShareViaEmail:
						//mButtonShareViaEmail.setBackgroundColor(R.color.button_background_color);
						handleShareViaEmail();
						break;
						
					case R.id.buttonShareViaTwitter:
						//mButtonShareViaTwitter.setBackgroundColor(R.color.button_background_color);
						handleShareViaTwitter();
						break;
						
					case R.id.buttonShareViaFacebook:
						//mButtonShareViaFacebook.setBackgroundColor(R.color.button_background_color);
						handleShareViaFacebook();
						break;
					
				}
				break;
		}
		
		return true;
	}


    
    /**
     *  Starts {@link MenuScreen} and closes {@link ShareScreen}
     */
    private void onClickBackButton()
    {
    	startActivity(new Intent(ShareScreen.this,MenuScreen.class));
    	finish();
    }
    
    private void handleShareViaEmail()
    {
    	MyLogManager.processLog(MyLogProcessType.DISPLAY, true,
    			MyLogLevel.DEBUG, TAG, "In method handleShareViaEmail()");
    	
    	sendEmail();
    	
    }
    
    private void handleShareViaTwitter()
    {
    	
    }
    
    private void handleShareViaFacebook()
    {
    	
    }


    /**
     * Sends the e-mail only iff email has been configured in device.
     */
    private void sendEmail()
    {
    	Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
    	
       String toEmailList[] = { "user@fakehost.com","user2@fakehost.com" };
        String ccEmailList[] = { "user3@fakehost.com","user4@fakehost.com"};
        String bCcEmailList[] = { Constants.EMAIL_COMPANY_ID};
        
       emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "raghunandan2005@gmail.com");
        //emailIntent.putExtra(android.content.Intent.EXTRA_CC, ccEmailList);
        //emailIntent.putExtra(android.content.Intent.EXTRA_BCC, bCcEmailList);
        
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,Constants.EMAIL_SUBJECT_FOR_SHARING);
        emailIntent.setType("image/jpg");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, Constants.EMAIL_BODY_FOR_SHARING);
        
        startActivity(emailIntent);
        //finish();
    }
    
   
}