package com.indiainnovates.pucho.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.indiainnovates.pucho.R;
import com.indiainnovates.pucho.models.FeedModel;
import com.indiainnovates.pucho.provider.FavoriteContract;
import com.indiainnovates.pucho.widgets.BezelImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by Raghunandan on 11-02-2016.
 */
public class CustomCursorAdapter extends CursorRecyclerViewAdapter<CustomCursorAdapter.ViewHoldera> {


    private String userName;
    private String photoUri;
    private Cursor mCursor;

    public CustomCursorAdapter(Context context, Fragment fragment, Cursor cursor) {
        super(context, cursor);

    }


    @Override
    public CustomCursorAdapter.ViewHoldera onCreateViewHolder(ViewGroup parent, int viewType) {

        CustomCursorAdapter.ViewHoldera  viewHolder;

        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_row, parent, false);
        // create ViewHolder
        viewHolder = new ViewHoldera(itemLayoutView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHoldera holder, Cursor cursor) {

        ViewHoldera viewHolder = (ViewHoldera) holder;
        Log.i("....",""+cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_ASKEDBY_USERNAME)));
        viewHolder.name.setText(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_ASKEDBY_USERNAME)));
        viewHolder.date.setText(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_ASKEDON)));
        viewHolder.question.setText(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_CONTENT)));
        if (cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorite.QUESTION_ASKEDBY_USERNAME)).equals(getUserName())) {
            Picasso.with(viewHolder.user_image.getContext())
                    .load(Uri.parse(getPhotoUri()))
                    .placeholder(R.drawable.person_image_empty)
                    .error(R.drawable.person_image_empty)
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

                   /* if(shareButtonClickListener!=null)
                    {
                        shareButtonClickListener.onCardClick(pos);
                    }else {
                        Toast.makeText(view.getContext().getApplicationContext(),
                                "Card Clicked at " + pos, Toast.LENGTH_SHORT)
                                .show();
                    }*/
                    break;
            }
        }
    }
}
