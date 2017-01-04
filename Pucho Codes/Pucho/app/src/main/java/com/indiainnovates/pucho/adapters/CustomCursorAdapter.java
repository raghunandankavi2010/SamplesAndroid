package com.indiainnovates.pucho.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.indiainnovates.pucho.AnswersActivity;
import com.indiainnovates.pucho.PuchoApplication;
import com.indiainnovates.pucho.R;
import com.indiainnovates.pucho.listeners.CursorCardClickListener;
import com.indiainnovates.pucho.listeners.ShareButtonClickListener;
import com.indiainnovates.pucho.models.FeedModel;
import com.indiainnovates.pucho.models.QuestionAskedBy;
import com.indiainnovates.pucho.provider.FavoriteContract;
import com.indiainnovates.pucho.utils.CircleCropTransformation;
import com.indiainnovates.pucho.utils.Utility;
import com.indiainnovates.pucho.widgets.BezelImageView;
import com.indiainnovates.pucho.widgets.ForegroundRelativeLayout;

import javax.inject.Inject;

/**
 * Created by Raghunandan on 11-02-2016.
 */
public class CustomCursorAdapter extends CursorRecyclerViewAdapter<CustomCursorAdapter.ViewHoldera> {


    private String userName;
    private String photoUri;
    private CursorCardClickListener cursorCardClickListener;
    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    Glide glide;

    private String url,name;

    public CustomCursorAdapter(Context context, Fragment fragment, Cursor cursor) {
        super(context, cursor);

        PuchoApplication.component().inject(this);
        cursorCardClickListener = (CursorCardClickListener) fragment;
        url = sharedPreferences.getString("photo_uri", "");
        name = sharedPreferences.getString("user_name","");
    }


    @Override
    public CustomCursorAdapter.ViewHoldera onCreateViewHolder(ViewGroup parent, int viewType) {

        CustomCursorAdapter.ViewHoldera viewHolder;

        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_row, parent, false);
        // create ViewHolder
        viewHolder = new ViewHoldera(itemLayoutView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHoldera holder, Cursor cursor) {

        ViewHoldera viewHolder = (ViewHoldera) holder;
        Log.i("....", "" + cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_ASKEDBY_USERNAME)));
        viewHolder.name.setText(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_ASKEDBY_USERNAME)));
        viewHolder.date.setText(Utility.calcualte_timeDifference(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_ASKEDON))));
        viewHolder.question.setText(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_CONTENT)));
        if (cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_ASKEDBY_USERNAME)).equals(name) && !TextUtils.isEmpty(url)) {
            glide.with(viewHolder.user_image.getContext())
                    .load(Uri.parse(url))
                    .placeholder(R.drawable.person_image_empty)
                    .error(R.drawable.person_image_empty)
                    .transform(new CircleCropTransformation(viewHolder.user_image.getContext()))
                    .into(viewHolder.user_image);
        }
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;

    }

    public String getPhotoUri() {
        return photoUri;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public class ViewHoldera extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageButton share, like;
        public ForegroundRelativeLayout cv;
        public BezelImageView user_image;
        public TextView name, date, likes, question, answerCount;


        public ViewHoldera(View itemLayoutView) {
            super(itemLayoutView);
           /* share = (ImageButton) itemLayoutView.findViewById(R.id.share_btn);
            like = (ImageButton) itemLayoutView.findViewById(R.id.like_btn);*/
            cv = (ForegroundRelativeLayout) itemLayoutView.findViewById(R.id.card_view);
            name = (TextView) itemLayoutView.findViewById(R.id.name);
            date = (TextView) itemLayoutView.findViewById(R.id.date);
            question = (TextView) itemLayoutView.findViewById(R.id.question);
            user_image = (BezelImageView) itemLayoutView.findViewById(R.id.profile_image);
           /* answerCount = (TextView) itemLayoutView.findViewById(R.id.answer_count);
            likes = (TextView) itemLayoutView.findViewById(R.id.like);*/

            //share.setOnClickListener(this);

            cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int pos = getAdapterPosition();
            switch (view.getId()) {

                case R.id.card_view:

                    if (cursorCardClickListener != null) {
                        Cursor cursor = getItem(pos);
                        cursorCardClickListener.getFeedDetails(getFeefModelFromCursor(cursor));

                    } else {
                        Toast.makeText(view.getContext().getApplicationContext(),
                                "Card Clicked at " + pos, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
            }
        }

    }

    public static FeedModel getFeefModelFromCursor(Cursor cursor) {

        FeedModel feedModel = new FeedModel();
        feedModel.setTitle(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_CONTENT)));
        feedModel.setId(cursor.getInt(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_ID)));
        if(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_ACTIVE)).equals("true"))
        {
            feedModel.setActive(true);
        }else
        {
            feedModel.setActive(false);
        }
        feedModel.setUpvote(cursor.getInt(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_UPVOTE)));
        feedModel.setUpvote(cursor.getInt(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_DOWNVOTE)));
        feedModel.setAskedOn(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_ASKEDON)));
        QuestionAskedBy user = new QuestionAskedBy();
        user.setFullName(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_ASKEDBY_USERNAME)));
        feedModel.setUser(user);
        feedModel.setAudioFileUrl(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_AUDIO_FILE_URL)));


        return feedModel;
    }


}
