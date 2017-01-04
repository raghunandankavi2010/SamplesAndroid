package com.indiainnovates.pucho.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.indiainnovates.pucho.R;
import com.indiainnovates.pucho.models.Answers;
import com.indiainnovates.pucho.utils.Utility;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tamil on 4/28/2015.
 */
public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {


    List<Answers> answerList;

    private Context mContext;

    public AnswerAdapter(Context context) {
        mContext = context;
        answerList = new ArrayList<>();
    }

    @Override
    public AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_detail, null);

        //create viewHolder
        AnswerViewHolder viewHolder = new AnswerViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AnswerViewHolder viewHolder, final int position) {
        //When a particular answer is tapped, a new Activity has to
        // be started with answer_row_1 as the layout
        Answers ans = answerList.get(position);

        //Log.i("Answered On","Date"+ans.getAnsweredOn());
        viewHolder.date.setText(Utility.calcualte_timeDifference(ans.getAnsweredOn()));
        viewHolder.name.setText(ans.getAnsweredByUser().getFullName());
        viewHolder.content.setText(ans.getContent());

    }

    public void setData(List<Answers> data) {
        this.answerList.addAll(data);
        notifyDataSetChanged();
    }

    public List<Answers> getAnswerList() {
        return answerList;
    }



    public static class AnswerViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView date;
        public TextView content;


        public AnswerViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            //assign the target here.
            this.name = (TextView) itemLayoutView.findViewById(R.id.name);
            this.date = (TextView) itemLayoutView.findViewById(R.id.answeredOn);
            this.content = (TextView) itemLayoutView.findViewById(R.id.content);

        }

    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }
}

