package assignment.test.raghu.peppersqaure;


/**
 * Created by Raghunandan on 01-12-2015.
 */


import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomViewGroup extends ViewGroup {
    private final ImageView mProfileImage;
    private final ImageButton mIcon;
    private final TextView mName,mCountry,mDescription;
    private static final String TAG = CustomViewGroup.class.getSimpleName();


    public CustomViewGroup(Context context) {
        this(context, null);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_view, this, true);
        mIcon = (ImageButton) findViewById(R.id.imageButton);
        mProfileImage = (ImageView) findViewById(R.id.profilepic);
        mName = (TextView) findViewById(R.id.name);
        mCountry = (TextView) findViewById(R.id.country);
        mDescription = (TextView) findViewById(R.id.description);


    }

    private void layoutView(View view, int left, int top, int width, int height) {
        MarginLayoutParams margins = (MarginLayoutParams) view.getLayoutParams();
        final int leftWithMargins = left + margins.leftMargin;
        final int topWithMargins = top + margins.topMargin;

        view.layout(leftWithMargins, topWithMargins,
                leftWithMargins + width, topWithMargins + height);
    }

    private int getWidthWithMargins(View child) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        return child.getWidth() + lp.leftMargin + lp.rightMargin;
    }

    private int getHeightWithMargins(View child) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        return child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
    }

    private int getMeasuredWidthWithMargins(View child) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        return child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
    }

    private int getMeasuredWidthImage(View child) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        return child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
    }

    private int getMeasuredHeightWithMargins(View child) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        return child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
    }

    private int getPaddingTop(View child) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        return  lp.topMargin;
    }

    private int getPaddingLeft(View child) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        return  lp.leftMargin;
    }

    private int getPaddingRight(View child) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        return  lp.rightMargin;
    }

    private int getPaddingBottom(View child) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        return  lp.bottomMargin;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int widthUsed = 0;
        int heightUsed = 0;

        measureChildWithMargins(mProfileImage,
                widthMeasureSpec, 0,
                heightMeasureSpec, 0);
        widthUsed += getMeasuredWidthWithMargins(mProfileImage);
        heightUsed = getMeasuredHeightWithMargins(mProfileImage);

        measureChildWithMargins(mName,
                widthMeasureSpec, 0,
                heightMeasureSpec, 0);
        int mNameWidth = getMeasuredWidthWithMargins(mName);
        //int mNameHeight = getMeasuredHeightWithMargins(mName);
        //heightUsed += getMeasuredHeightWithMargins(mName);

        measureChildWithMargins(mCountry,
                widthMeasureSpec, 0,
                heightMeasureSpec,0);
        widthUsed += Math.max(mNameWidth,getMeasuredWidthWithMargins(mCountry));




        measureChildWithMargins(mDescription,
                    widthMeasureSpec, 0,
                    heightMeasureSpec, 0);
        heightUsed += getMeasuredHeightWithMargins(mDescription);


        measureChildWithMargins(mIcon,
                    widthMeasureSpec, 0,
                    heightMeasureSpec, 0);

        heightUsed += getMeasuredHeightWithMargins(mIcon);


        int heightSize = heightUsed + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();

        int currentTop = paddingTop;

        int contentWidth = r - l - getPaddingRight();

        //Log.i(TAG,""+contentWidth);
        layoutView(mProfileImage, r-(mProfileImage.getMeasuredWidth())-getPaddingRight(mProfileImage),t+getPaddingTop(mProfileImage),
                mProfileImage.getMeasuredWidth(), mProfileImage.getMeasuredHeight());
        currentTop += getHeightWithMargins(mProfileImage);

        layoutView(mCountry, l+ getPaddingLeft(mCountry), t+getPaddingTop(mCountry),
                mCountry.getMeasuredWidth(), mCountry.getMeasuredHeight());
        //currentTop += getHeightWithMargins(mMessageText);

        layoutView(mName, l + getPaddingLeft(mName), getHeightWithMargins(mCountry)+ getPaddingTop(mName),
                mName.getMeasuredWidth(), mName.getMeasuredHeight());

       /* layoutView(mDescription, l- paddingLeft, getHeightWithMargins(mProfileImage)+mName.getPaddingTop(),
                mProfileImage.getMeasuredWidth(), mProfileImage.getMeasuredHeight());*/

        layoutView(mDescription, l +getPaddingLeft(mDescription), currentTop+getPaddingTop(mDescription),
                mDescription.getMeasuredWidth()-getPaddingRight(mDescription), mDescription.getMeasuredHeight());
        currentTop +=getHeightWithMargins(mDescription);


        layoutView(mIcon, r - mIcon.getMeasuredWidth()-getPaddingRight(mIcon) , currentTop+getPaddingTop(mIcon),
                mIcon.getMeasuredWidth(), mIcon.getMeasuredHeight()-getPaddingBottom(mIcon));
        currentTop +=getHeightWithMargins(mIcon);



    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }


}
