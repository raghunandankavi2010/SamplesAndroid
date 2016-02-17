package com.eimmer.recyclerviewheightanimations.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eimmer.recyclerviewheightanimations.R;
import com.eimmer.recyclerviewheightanimations.dataprovider.Question;
import com.eimmer.recyclerviewheightanimations.dataprovider.QuestionProvider;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private Question[] questions;
    private boolean[] expanded;

    public QuestionAdapter() {
        questions = QuestionProvider.getQuestions();
        expanded = new boolean[questions.length];

    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_question, parent, false);
        final QuestionViewHolder questionViewHolder = new QuestionViewHolder(view);
        questionViewHolder.tvQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleViewClick(questionViewHolder);
            }
        });

        return questionViewHolder;
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        holder.dataIndex = position;
        final Question question = questions[position];
        holder.tvAnswer.setText(question.getAnswer());
        holder.tvQuestion.setText(question.getQuestion());
        holder.tvAnswer.setVisibility(expanded[position] ? View.VISIBLE : View.GONE);
    }

    private void handleViewClick(final QuestionViewHolder holder) {
        expanded[holder.dataIndex] = !expanded[holder.dataIndex];
        notifyItemChanged(holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return questions.length;
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestion;
        TextView tvAnswer;
        int dataIndex;

        public TextView getTvAnswer() {
            return tvAnswer;
        }

        public QuestionViewHolder(View itemView) {
            super(itemView);
            tvQuestion = (TextView) itemView.findViewById(R.id.question);
            tvAnswer = (TextView) itemView.findViewById(R.id.answer);
        }

        public boolean isExpanded() {
            return false;
        }
    }
}
