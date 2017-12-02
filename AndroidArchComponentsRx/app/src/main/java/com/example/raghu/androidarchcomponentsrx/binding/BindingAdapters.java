package com.example.raghu.androidarchcomponentsrx.binding;

/**
 * Created by raghu on 5/11/17.
 */

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.raghu.androidarchcomponentsrx.models.Example;
import com.example.raghu.androidarchcomponentsrx.vo.Resource;
import com.example.raghu.androidarchcomponentsrx.vo.Status;

public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter(value = "text")
    public static void setText(TextView textView, Resource resource){

        if(textView == null)
            return;

        if(resource==null ||resource.data == null)
            return;
        Example example =(Example) resource.data;
        textView.setText(example.getUser().getName());


    }

    @BindingAdapter(value = "progressVisibility")
    public static void setProgess(ProgressBar progessBar, Resource resource){

        if(progessBar == null)
            return;
        if(resource==null ||resource.data == null)
            return;

        if(resource.status == Status.ERROR)
        progessBar.setVisibility(View.GONE);
        else if(resource.status == Status.LOADING)
            progessBar.setVisibility(View.VISIBLE);
        else{
            progessBar.setVisibility(View.GONE);
        }

    }
}
