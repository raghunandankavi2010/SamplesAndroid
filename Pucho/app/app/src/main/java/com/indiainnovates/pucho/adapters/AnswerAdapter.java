package com.indiainnovates.pucho.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.indiainnovates.pucho.AnswersActivity;
import com.indiainnovates.pucho.R;
import com.indiainnovates.pucho.models.Answers;
import com.indiainnovates.pucho.models.Success;
import com.indiainnovates.pucho.screen_contracts.AnswerScreen;


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
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_row, null);

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

        //viewHolder.date.setText(ans.getAnsweredOn());
        //viewHolder.cv.setTag(ans);
        //viewHolder.name.setText("");
        viewHolder.content.setText(ans.getContent());
        viewHolder.star.setVisibility(View.GONE);
        /*viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               *//**//* Answers answer = (Answers)v.getTag();
                Toast.makeText(v.getContext(), "Card Clicked at " + position, Toast.LENGTH_SHORT).show();
                //EventBus.getDefault().postSticky(answer);
                Intent intent = new Intent(v.getContext(), AnswersDetailedActivity.class);
                v.getContext().startActivity(intent);*//**//*
            }
        });*/
    }

    public void setData(List<Answers> data) {
        this.answerList.addAll(data);
        notifyDataSetChanged();
    }

    public List<Answers> getAnswerList() {
        return answerList;
    }



    public static class AnswerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView name;
        public TextView date;
        public CardView cv;
        public TextView role,content;
        public ImageButton star;

        public AnswerViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            //assign the target here.
            //this.name = (TextView) itemLayoutView.findViewById(R.id.name);
            //this.date = (TextView) itemLayoutView.findViewById(R.id.date);
            /*this.role = (TextView) itemLayoutView.findViewById(R.id.roleTV);*/
            this.content = (TextView) itemLayoutView.findViewById(R.id.content);
            cv = (CardView) itemLayoutView.findViewById(R.id.card_view);
            star = (ImageButton) itemLayoutView.findViewById(R.id.star_btn);
            //cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }
}

