package me.raghu.facebookcommentscreenlikeanimation;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CustomRecyclerView extends RecyclerView {
    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        int xDown = 0;
        int yDown = 0;
        int diff = 0;
        LinearLayoutManager layoutManager = (LinearLayoutManager) this.getLayoutManager();
        assert layoutManager != null;
        int lastItem = layoutManager.findLastVisibleItemPosition();
        int firstItem = layoutManager.findFirstCompletelyVisibleItemPosition();


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                 xDown = (int) event.getX();
                 yDown = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                diff = (int) (yDown-event.getY());
                if(diff >0 && firstItem == 0 ) {
                    return super.onInterceptTouchEvent(event);
                }
                else {
                    return true;
                }
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN && this.getScrollState() == RecyclerView.SCROLL_STATE_SETTLING) {
            this.stopScroll();
        }
        return super.onInterceptTouchEvent(event);
    }
}
