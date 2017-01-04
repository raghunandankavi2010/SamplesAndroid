package innovates.com.pucho.adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import innovates.com.pucho.AnswersActivity;
import innovates.com.pucho.R;
import innovates.com.pucho.models.Answers;
import innovates.com.pucho.models.Questions;
import innovates.com.pucho.widgets.BezelImageView;


/**
 * Created by sony on 4/18/2015.
 */
public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private ArrayList<Questions> mList, mOrigin;

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results.count>0)
                mList = (ArrayList<Questions>) results.values;
                else
                mList = mOrigin;
                QuestionAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults result = new FilterResults();
                ArrayList<Questions> newValues = new ArrayList<>();
                if(!TextUtils.isEmpty(constraint)) {
                    for (int i = 0; i < mOrigin.size(); i++) {

                        if ((mOrigin.get(i).getContent().toString().toLowerCase()).startsWith(constraint.toString().toLowerCase())) {

                            newValues.add(mOrigin.get(i));

                        }
                    }
                    result.values = newValues;
                    result.count = newValues.size();
                }
                else
                {
                    result.values = mOrigin;
                    result.count = mOrigin.size();
                }


                return result;
            }
        };
    }

    public void setData() {
        //list.clear();
        mList = mOrigin;
    }

    public void setInitData(ArrayList<Questions> data) {
        //list.clear();
        mList = data;
        mOrigin = data;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        RecyclerView.ViewHolder  viewHolder;
        if(viewType==VIEW_ITEM) {
            View itemLayoutView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.question_row, parent, false);
            // create ViewHolder
             viewHolder = new ViewHoldera(itemLayoutView);
        }else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progress_item, parent, false);

            viewHolder = new ProgressViewHolder(v);
        }

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, final int position) {

        Questions questions = mList.get(position);
        if(vh instanceof ViewHoldera) {

            ViewHoldera viewHolder = (ViewHoldera)vh;
            viewHolder.cv.setTag(questions.getAnswers());
            viewHolder.cv.setTag(R.string.tag_question, questions.getContent());
            viewHolder.cv.setTag(R.string.tag_question1, questions.getQuestionID());
            viewHolder.name.setText(questions.getUser().getUserName());
            viewHolder.date.setText(questions.getAskedOn());
            viewHolder.question.setText(questions.getContent());


            viewHolder.share.setTag(position);
            viewHolder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Share button clicked at " + position, Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Answers> listAnswers = (ArrayList<Answers>) v.getTag();
                    String question = (String) v.getTag(R.string.tag_question);
                    int id = (Integer) v.getTag(R.string.tag_question1);
                    //Toast.makeText(v.getContext(), "Card Clicked at "+position, Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().postSticky(listAnswers);
                    EventBus.getDefault().postSticky(question);
                    EventBus.getDefault().postSticky(id);
                    Intent intent = new Intent(v.getContext(), AnswersActivity.class);
                    intent.putExtra("question_id", id);
                    v.getContext().startActivity(intent);

                }
            });
        }else{
            ((ProgressViewHolder)vh).progressBar.setIndeterminate(true);
        }


    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHoldera extends RecyclerView.ViewHolder {

        public ImageButton share, like;
        public CardView cv;
        public BezelImageView user_image;
        public TextView name, date, likes, question, answerCount;


        public ViewHoldera(View itemLayoutView) {
            super(itemLayoutView);
            share = (ImageButton) itemLayoutView.findViewById(R.id.share_btn);
            like = (ImageButton) itemLayoutView.findViewById(R.id.like_btn);
            cv = (CardView) itemLayoutView.findViewById(R.id.card_view);
            name = (TextView) itemLayoutView.findViewById(R.id.name);
            date = (TextView) itemLayoutView.findViewById(R.id.date);
            question = (TextView) itemLayoutView.findViewById(R.id.question);
            answerCount = (TextView) itemLayoutView.findViewById(R.id.answer_count);
            likes = (TextView) itemLayoutView.findViewById(R.id.like);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position)!=null? VIEW_ITEM: VIEW_PROG;
    }
}
