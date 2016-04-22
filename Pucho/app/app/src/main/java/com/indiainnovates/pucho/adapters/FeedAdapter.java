package com.indiainnovates.pucho.adapters;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.indiainnovates.pucho.R;
import com.indiainnovates.pucho.listeners.ShareButtonClickListener;
import com.indiainnovates.pucho.models.FeedModel;
import com.indiainnovates.pucho.widgets.BezelImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Raghunandan on 31-01-2016.
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FeedModel> mList = new ArrayList<>();

    public static final int VIEW_ITEM = 1;
    public static final int VIEW_PROG = 0;
    public static final int VIEW_IMAGE = 2;
    public static final int VIEW_AUDIO = 3;
    public static final int VIEW_VIDEO = 4;


    private ShareButtonClickListener shareButtonClickListener;
    private String userName;
    private String photoUri;


    public FeedAdapter(ShareButtonClickListener shareButtonClickListener)
    {
               this.shareButtonClickListener = shareButtonClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        FeedModel questions = mList.get(position);
        if(holder instanceof ViewHoldera) {

            ViewHoldera viewHolder = (ViewHoldera)holder;
            viewHolder.name.setText(questions.getUser().getFullName());
            viewHolder.date.setText(questions.getAskedOn());
            viewHolder.question.setText(questions.getTitle());
            if(questions.getUser().getFullName().equals(getUserName()))
            {
                Picasso.with(viewHolder.user_image.getContext())
                        .load(Uri.parse(getPhotoUri()))
                        .placeholder(R.drawable.person_image_empty)
                        .error(R.drawable.person_image_empty)
                        .into(viewHolder.user_image);
            }


        }else{
            ((ProgressViewHolder)holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<FeedModel> data) {
        this.mList.addAll(data);
        notifyDataSetChanged();
    }


    public List<FeedModel> getmList() {
        return mList;
    }

    public void add(FeedModel feedModel) {
        mList.add(feedModel);
        notifyDataSetChanged();
    }

    public void remove() {

        mList.remove(mList.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position)!=null? VIEW_ITEM: VIEW_PROG;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;

    }

    public String getPhotoUri() {
        return photoUri;
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {


        ProgressBar progressBar;
        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }

    public class ViewHoldera extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageButton share, like;
        public CardView cv;
        public BezelImageView user_image;
        public TextView name, date, likes, question, answerCount;


        public ViewHoldera(View itemLayoutView) {
            super(itemLayoutView);
           /* share = (ImageButton) itemLayoutView.findViewById(R.id.share_btn);
            like = (ImageButton) itemLayoutView.findViewById(R.id.like_btn);*/
            cv = (CardView) itemLayoutView.findViewById(R.id.card_view);
            name = (TextView) itemLayoutView.findViewById(R.id.name);
            date = (TextView) itemLayoutView.findViewById(R.id.date);
            question = (TextView) itemLayoutView.findViewById(R.id.question);
            user_image =(BezelImageView) itemLayoutView.findViewById(R.id.profile_image);
           /* answerCount = (TextView) itemLayoutView.findViewById(R.id.answer_count);
            likes = (TextView) itemLayoutView.findViewById(R.id.like);*/

            //share.setOnClickListener(this);

            cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int pos = getAdapterPosition();
            switch(view.getId())
            {
                /*case R.id.share_btn :
                    if(shareButtonClickListener!=null)
                    {
                        shareButtonClickListener.share(pos);
                    }else {
                        Toast.makeText(view.getContext().getApplicationContext(),
                                "Share cklicked at " + pos, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;*/
                case R.id.card_view :

                    if(shareButtonClickListener!=null)
                    {
                        shareButtonClickListener.onCardClick(pos);
                    }else {
                        Toast.makeText(view.getContext().getApplicationContext(),
                                "Card Clicked at " + pos, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
            }
        }
    }
}
