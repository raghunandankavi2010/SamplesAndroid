package com.india.innovates.pucho.adapters;

/**
 * Created by Raghunandan on 18-03-2016.
 */

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.india.innovates.pucho.PuchoApplication;
import com.india.innovates.pucho.R;
import com.india.innovates.pucho.listeners.ShareButtonClickListener;

import com.india.innovates.pucho.models.MyQuestionsFetched;
import com.india.innovates.pucho.utils.CircleCropTransformation;
import com.india.innovates.pucho.utils.Utility;
import com.india.innovates.pucho.widgets.ForegroundRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * Created by Raghunandan on 31-01-2016.
 */
public class MyQuestionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MyQuestionsFetched> mList = new ArrayList<>();

    public static final int VIEW_ITEM = 1;
    public static final int VIEW_PROG = 0;
    /* For future use to display image, audio and video */
    public static final int VIEW_IMAGE = 2;
    public static final int VIEW_AUDIO = 3;
    public static final int VIEW_VIDEO = 4;


    private ShareButtonClickListener shareButtonClickListener;
    private String userName;
    private String photoUri;
    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    Glide glide;

    private String url,name;


    public MyQuestionsAdapter(ShareButtonClickListener shareButtonClickListener)
    {
        PuchoApplication.component().inject(this);
        this.shareButtonClickListener = shareButtonClickListener;
        url = sharedPreferences.getString("photo_uri", "");
        name = sharedPreferences.getString("user_name","");
        // Log.d("FeedAdapter","Name of logged in person "+name);
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

        MyQuestionsFetched questions = mList.get(position);
        //Log.d("MyQuestionsAdapter","data size"+mList.size());
        if(holder instanceof ViewHoldera) {

            ViewHoldera viewHolder = (ViewHoldera)holder;
            if (Build.VERSION.SDK_INT >= 21) {
                viewHolder.cv.setForeground(ContextCompat.getDrawable(viewHolder.cv.getContext(), R.drawable.ripple));
            } else {
                // no ripple
            }
            viewHolder.name.setText(questions.getUser().getFullName());
            viewHolder.date.setText(questions.getAskedOn());
            if(language.equals("Hindi") && questions.getLanguageContents().size()>0)
            {
                viewHolder.question.setText(questions.getLanguageContents().get(0).getContent(),TextView.BufferType.SPANNABLE);
            }else {
                viewHolder.question.setText(questions.getTitle(), TextView.BufferType.SPANNABLE);
            }
            Utility.fixTextView(viewHolder.question);
            // Log.d("FeedAdapter","Name is "+questions.getUser().getFullName());
            if(questions.getUser().getFullName().equals(name)  && !TextUtils.isEmpty(url))
            {
                glide.with(viewHolder.user_image.getContext() )
                        .load(Uri.parse(url))
                        .placeholder(R.drawable.person_image_empty)
                        .error(R.drawable.person_image_empty)
                        .transform(new CircleCropTransformation(viewHolder.user_image.getContext()))
                        .into(viewHolder.user_image);
            }else
            {
                Drawable drawable = ContextCompat.getDrawable(viewHolder.user_image.getContext(),R.drawable.person_image_empty);
                viewHolder.user_image.setImageDrawable(drawable);
            }


        }else{
            ((ProgressViewHolder)holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {

        //Log.d("Size","data size"+mList.size());
        return mList.size();

    }
    private String language;

    public void setData(final List<MyQuestionsFetched> data,String language) {

        //Log.d("FeedAdapter","data size"+data.size());

        this.language = language;
        Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                mList.addAll(data);
                notifyDataSetChanged();
                //notifyItemRangeInserted(getItemCount(),mList.size());

            }
        };

        handler.post(r);

    }


    public List<MyQuestionsFetched> getmList() {
        return mList;
    }

    public void add(final MyQuestionsFetched feedModel) {

        Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                mList.add(feedModel);
                notifyItemRangeInserted(getItemCount(),mList.size());
            }
        };

        handler.post(r);

    }

    public void remove() {

        mList.remove(mList.size() - 1);
        Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                notifyDataSetChanged();
            }
        };

        handler.post(r);
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
        public ForegroundRelativeLayout cv;
        public ImageView user_image;
        public TextView name, date, likes, question, answerCount;


        public ViewHoldera(View itemLayoutView) {
            super(itemLayoutView);
           /* share = (ImageButton) itemLayoutView.findViewById(R.id.share_btn);
            like = (ImageButton) itemLayoutView.findViewById(R.id.like_btn);*/
            cv = (ForegroundRelativeLayout) itemLayoutView.findViewById(R.id.card_view);
            name = (TextView) itemLayoutView.findViewById(R.id.name);
            date = (TextView) itemLayoutView.findViewById(R.id.date);
            question = (TextView) itemLayoutView.findViewById(R.id.question);
            user_image =(ImageView) itemLayoutView.findViewById(R.id.profile_image);
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
