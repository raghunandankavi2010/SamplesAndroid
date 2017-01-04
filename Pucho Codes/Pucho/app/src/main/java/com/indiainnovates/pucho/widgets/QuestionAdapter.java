package com.indiainnovates.pucho.widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Raghunandan on 28-12-2015.
 */
public class QuestionAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    public QuestionAdapter(Context mContext)
    {
      this.mContext = mContext;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
