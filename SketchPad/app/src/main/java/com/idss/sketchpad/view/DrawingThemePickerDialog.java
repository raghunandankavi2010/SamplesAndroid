package com.idss.sketchpad.view;

import com.idss.sketchpad.model.MyLogLevel;
import com.idss.sketchpad.model.MyLogProcessType;
import com.idss.sketchpad.utils.Constants;
import com.idss.sketchpad.utils.MyLogManager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Custom Dialog class which is defined specifically to suit theme picker UI.
 * @author Chandan
 *
 */
public class DrawingThemePickerDialog extends Dialog{
	
	private final String TAG=DrawingThemePickerDialog.class.getSimpleName();
	
	private Context mContext=null;
	
	private LinearLayout mThemeParentLayout=null;
	
	private LinearLayout mThemeRowLayout=null;
	
	private ImageView mThemeAtFirstColumn=null;
	
	private ImageView mThemeAtSecondColumn=null;
	
	private OnThemeChangeListener mOnThemeChangeListener=null;

	protected interface OnThemeChangeListener{
		
		/**Theme change listener method to be implemented by child classes. 
		 * @param themeIndex position of the theme choosed.
		 * @see Constants#DRAWING_PAD_THEME
		 */
		void onThemeChange(int themeIndex);
	}
	
	private class MyOnClickThemeListener implements android.view.View.OnClickListener{

		int mIndex=0;
		public MyOnClickThemeListener(int index)
		{
			mIndex=index;
		}
		
		@Override
		public void onClick(View v) 
		{
			MyLogManager.processLog(MyLogProcessType.DISPLAY, true, 
					MyLogLevel.DEBUG, TAG, "Clicked on :"+mIndex);
			mOnThemeChangeListener.onThemeChange(mIndex);
			DrawingThemePickerDialog.this.dismiss();
			
		}
		
	}
	
	
	/**
	 * @param context
	 * @param themeChangeListener
	 * @param initialTheme
	 */
	public DrawingThemePickerDialog(Context context,OnThemeChangeListener themeChangeListener,int initialTheme)
	{
		super(context);
		
		mContext=context;
		mOnThemeChangeListener=themeChangeListener;
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.dialog_picker);
		
		setTitle(R.string.dialog_choose_theme_text);
		
		mThemeParentLayout=(LinearLayout)findViewById(R.id.holder_layout_for_picker_items);
		
		prepareThemesList();
	}
	
	/**
	 * 
	 */
	private void resetThemeTemplate()
	{
		LayoutInflater layoutInflater=(LayoutInflater) this.getLayoutInflater();
		View view=(LinearLayout)layoutInflater.inflate(R.layout.template_picker_layout, null);
		
		mThemeRowLayout=(LinearLayout)view.findViewById(R.id.teplate_row_layout);
		mThemeAtFirstColumn=(ImageView)mThemeRowLayout.findViewById(R.id.imageview_first_column);
		mThemeAtSecondColumn=(ImageView)mThemeRowLayout.findViewById(R.id.imageview_second_column);
		
	}
	
	/**
	 * 
	 */
	private void prepareThemesList()
	{
		int totalThemes=Constants.DRAWING_PAD_THEME.length;
		
		for(int row=0;row<totalThemes;)//as each row has two themes
		{
			resetThemeTemplate();
			
			//FIRST THUMB IMAGE...
			mThemeAtFirstColumn.setBackgroundDrawable(mContext.getResources().getDrawable(
					Constants.DRAWING_PAD_THEME[row][Constants.INDEX_FOR_BACKGROUND_THUMB_IMAGE]));
			mThemeAtFirstColumn.setOnClickListener(new MyOnClickThemeListener(row));
			
			//SECOND  THUMB IMAGE...
			row++;
			if(row<totalThemes)
			{
				mThemeAtSecondColumn.setBackgroundDrawable(mContext.getResources().getDrawable(
						Constants.DRAWING_PAD_THEME[row][Constants.INDEX_FOR_BACKGROUND_THUMB_IMAGE]));
				mThemeAtSecondColumn.setOnClickListener(new MyOnClickThemeListener(row));
			}
			else
			{
				mThemeAtSecondColumn.setVisibility(View.GONE);
			}
			row++;
			
			mThemeParentLayout.addView(mThemeRowLayout);
			
		}
		
		
	}
	
}
