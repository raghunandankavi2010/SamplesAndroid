package innovates.com.pucho.adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import innovates.com.pucho.AnswersDetailedActivity;
import innovates.com.pucho.R;
import innovates.com.pucho.models.Answers;
import innovates.com.pucho.models.User;


/**
 * Created by Tamil on 4/28/2015.
 */
public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {


    ArrayList<Answers> answerList;
    User user;
    public AnswerAdapter(ArrayList<Answers> answers){
        this.answerList = answers;
    }

    @Override
    public AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        //create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_row, null);

        //create viewHolder
        AnswerViewHolder viewHolder = new AnswerViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AnswerViewHolder viewHolder,final int position){
        //When a particular answer is tapped, a new Activity has to
        // be started with answer_row_1 as the layout
         Answers ans = answerList.get(position);
        user = ans.getUser();
        viewHolder.name.setText(ans.getUserName());
        viewHolder.role.setText(ans.getProfession());
        viewHolder.date.setText(ans.getAnsweredOn());
        viewHolder.cv.setTag(ans);
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Answers answer = (Answers)v.getTag();
                Toast.makeText(v.getContext(), "Card Clicked at " + position, Toast.LENGTH_SHORT).show();
                EventBus.getDefault().postSticky(answer);
                Intent intent = new Intent(v.getContext(), AnswersDetailedActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    public static class AnswerViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView date;
        public CardView cv;
        public TextView role;

        public AnswerViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            //assign the target here.
            this.name = (TextView) itemLayoutView.findViewById(R.id.nameTV);
            this.date = (TextView) itemLayoutView.findViewById(R.id.dateTv1);
            this.role = (TextView) itemLayoutView.findViewById(R.id.roleTV);
            cv = (CardView) itemLayoutView.findViewById(R.id.answer_card_view);
        }
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }
}

