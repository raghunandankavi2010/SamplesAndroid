package assignment.test.raghu.peppersqaure.adapter;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.RemoteException;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import assignment.test.raghu.peppersqaure.CustomViewGroup;
import assignment.test.raghu.peppersqaure.R;
import assignment.test.raghu.peppersqaure.models.Actors;
import assignment.test.raghu.peppersqaure.provider.ActorContract;

/*
 * Created by Raghunandan on 22-11-2015.*/



public class MyCursorAdapter extends CursorRecyclerViewAdapter<MyCursorAdapter.ViewHolder_item> {

    public interface OnFavClickListener {

        public void onFavClick(Cursor pos);
    }

    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences sharedpreferences;
    private Context mContext;
    OnFavClickListener onFavClickListener;
    private ContentResolver contentResolver;
    private Cursor mCursor;
    public MyCursorAdapter(Context context, Cursor cursor) {

        super(context, cursor);
        mContext = context;
        ////mCursor = getCursor();
        contentResolver = context.getContentResolver();
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        onFavClickListener = (OnFavClickListener) context;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder_item onCreateViewHolder(ViewGroup parent,
                                              int viewType) {

        ViewHolder_item viewHolder;
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test, parent, false);

        viewHolder = new ViewHolder_item(v);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder_item vh, final Cursor cursor) {


        ViewHolder_item viewHolder = (ViewHolder_item) vh;
        viewHolder.tv.setText(cursor.getString(cursor.getColumnIndex(ActorContract.Actors.ACTOR_COUNTRY)));
        viewHolder.tv1.setText(cursor.getString(cursor.getColumnIndex(ActorContract.Actors.ACTOR_NAME)));
        viewHolder.tv2.setText(cursor.getString(cursor.getColumnIndex(ActorContract.Actors.ACTOR_DESCRIPTION)));
        Picasso.with(viewHolder.m_Image.getContext()).
                load(
                        cursor.getString(cursor.getColumnIndex(ActorContract.Actors.ACTOR_IMAGE)))
                .into(viewHolder.m_Image);
        //int fav = cursor.getInt(cursor.getColumnIndex(ActorContract.Actors.ACTOR_FAV));

     /*   if (fav == 1) {
            viewHolder.mFav.setImageDrawable(viewHolder.mFav.getResources().getDrawable(R.drawable.ic_favorite_full));

        } else {
            viewHolder.mFav.setImageDrawable(viewHolder.mFav.getResources().getDrawable(R.drawable.ic_favorite_border));

        }*/

    }

    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder_item extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView m_Image;
        //private ImageButton mShare, mFav;
        public TextView tv, tv1, tv2;

        public ViewHolder_item(View itemLayoutView) {
            super(itemLayoutView);
            tv = (TextView) itemLayoutView.findViewById(R.id.name);
            tv1 = (TextView) itemLayoutView.findViewById(R.id.country);
            tv2 = (TextView) itemLayoutView.findViewById(R.id.description);
            //mShare = (ImageButton) itemLayoutView.findViewById(R.id.imageButton1);
            //mFav = (ImageButton) itemLayoutView.findViewById(R.id.imageButton);
            m_Image = (ImageView) itemLayoutView.findViewById(R.id.profilepic);
            //mFav.setOnClickListener(this);
            //mShare.setOnClickListener(this);

        }
            @Override
            public void onClick(View view) {
               /* int pos = getLayoutPosition();
                Cursor cursor = getItem(pos);
                if(view.getId()==R.id.imageButton) {
                    onFavClickListener.onFavClick(cursor);
                }else if(view.getId()==R.id.imageButton1)
                {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, cursor.getString(cursor.getColumnIndex(ActorContract.Actors.ACTOR_NAME)));
                    sendIntent.setType("text/plain");
                    // Verify the original intent will resolve to at least one activity
                    if (sendIntent.resolveActivity(mContext.getPackageManager()) != null) {
                        mContext.startActivity(sendIntent);
                    }
                }*/
            }

    }


}
