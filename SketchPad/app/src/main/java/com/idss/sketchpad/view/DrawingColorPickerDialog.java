package com.idss.sketchpad.view;

import com.idss.sketchpad.model.MyLogLevel;
import com.idss.sketchpad.model.MyLogProcessType;
import com.idss.sketchpad.utils.Constants;
import com.idss.sketchpad.utils.MyLogManager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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
public class DrawingColorPickerDialog extends Dialog{
	
	private final String TAG=DrawingColorPickerDialog.class.getSimpleName();
	
	private Context mContext=null;
	
	private LinearLayout mColorParentLayout=null;
	
	private LinearLayout mColorRowLayout=null;
	
	private ImageView mColorAtFirstColumn=null;
	
	private ImageView mColorAtSecondColumn=null;
	
	private OnColorChangeListener mOnColorChangeListener=null;

	protected interface OnColorChangeListener{
		
		/**Theme change listener method to be implemented by child classes. 
		 * @param colorIndex position of the color choosed.
		 * @see Constants#PEN_COLOR
		 */
		void onColorChange(int colorIndex);
	}
	
	private class MyOnClickColorListener implements android.view.View.OnClickListener{

		int mIndex=0;
		public MyOnClickColorListener(int index)
		{
			mIndex=index;
		}
		
		@Override
		public void onClick(View v) 
		{
			MyLogManager.processLog(MyLogProcessType.DISPLAY, true, 
					MyLogLevel.DEBUG, TAG, "Clicked on :"+mIndex);
			mOnColorChangeListener.onColorChange(mIndex);
			DrawingColorPickerDialog.this.dismiss();
		}
		
	}
	
	
	/**
	 * @param context
	 * @param colorChangeListener
	 * @param initialTheme
	 */
	public DrawingColorPickerDialog(Context context,OnColorChangeListener colorChangeListener,int initialTheme)
	{
		super(context);
		
		mContext=context;
		mOnColorChangeListener=colorChangeListener;
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.dialog_picker);
		
		setTitle(R.string.dialog_choose_pen_thickness_text);
		
		mColorParentLayout=(LinearLayout)findViewById(R.id.holder_layout_for_picker_items);
		
		prepareColorsList();
	}
	
	/**
	 * 
	 */
	private void resetColorTemplate()
	{
		LayoutInflater layoutInflater=(LayoutInflater) this.getLayoutInflater();
		View view=(LinearLayout)layoutInflater.inflate(R.layout.template_picker_layout, null);
		
		mColorRowLayout=(LinearLayout)view.findViewById(R.id.teplate_row_layout);
		mColorAtFirstColumn=(ImageView)mColorRowLayout.findViewById(R.id.imageview_first_column);
		mColorAtSecondColumn=(ImageView)mColorRowLayout.findViewById(R.id.imageview_second_column);
		
	}
	
	/**
	 * 
	 */
	private void prepareColorsList()
	{
		int totalColors=Constants.PEN_COLOR.length;
		
		for(int row=0;row<totalColors;)//as each row has two colors
		{
			resetColorTemplate();
			
			//FIRST COLOR IMAGE...
			mColorAtFirstColumn.setBackgroundColor(Color.parseColor(Constants.PEN_COLOR[row]));
			mColorAtFirstColumn.setOnClickListener(new MyOnClickColorListener(row));
			
			//SECOND  COLOR IMAGE...
			row++;
			if(row<totalColors)
			{
				mColorAtSecondColumn.setBackgroundColor(Color.parseColor(Constants.PEN_COLOR[row]));
				mColorAtSecondColumn.setOnClickListener(new MyOnClickColorListener(row));
			}
			else
			{
				mColorAtSecondColumn.setVisibility(View.GONE);
			}
			row++;
			
			mColorParentLayout.addView(mColorRowLayout);
			
		}
		
		
	}
	
}
