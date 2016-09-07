package com.india.innovates.pucho.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.india.innovates.pucho.AnswersActivity;
import com.india.innovates.pucho.PuchoApplication;
import com.india.innovates.pucho.R;
//import com.india.innovates.pucho.adapters.CustomCursorAdapter;
import com.india.innovates.pucho.adapters.CursorRecyclerViewAdapter;
//import com.india.innovates.pucho.adapters.CustomCursorAdapter;
import com.india.innovates.pucho.adapters.CustomCursorAdapter;
import com.india.innovates.pucho.listeners.CursorCardClickListener;
import com.india.innovates.pucho.models.FeedModel;
import com.india.innovates.pucho.provider.FavoriteContract;

import javax.inject.Inject;

/**
 * Created by Raghunandan on 11-02-2016.
 */
public class FavoriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, CursorCardClickListener{

    private static final String[] PROJECTION = new String[]
            {

                    BaseColumns._ID, // very importatnt to have this
                    FavoriteContract.Favorite.QUESTION_ID,
                    FavoriteContract.Favorite.QUESTION_ASKEDBY_USERNAME,
                    FavoriteContract.Favorite.QUESTION_ACTIVE,
                    FavoriteContract.Favorite.QUESTION_TITLE,
                    FavoriteContract.Favorite.QUESTION_CONTENT,
                    FavoriteContract.Favorite.QUESTION_UPVOTE,
                    FavoriteContract.Favorite.QUESTION_DOWNVOTE,
                    FavoriteContract.Favorite.QUESTION_AUDIO_FILE_URL,
                    FavoriteContract.Favorite.QUESTION_ASKEDON,
                    FavoriteContract.Favorite.QUESTION_SAVED,
            };


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecylerView;
    private ProgressBar pb;
    private TextView tv;

    private CursorRecyclerViewAdapter mAdapter;
    private Cursor mCursor;
    private static final String TAG = FavoriteFragment.class.getSimpleName();

    @Inject
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.favorite_row, container, false);
        ((PuchoApplication) getActivity().getApplication()).component().inject(this);

        mRecylerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        mAdapter = new CustomCursorAdapter(getActivity(),this,mCursor);
        mRecylerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());// getActivity().getResources().getInteger(R.integer.grid_columns));
        mRecylerView.setLayoutManager(linearLayoutManager);
        mRecylerView.setHasFixedSize(true);
        //mRecylerView.addItemDecoration(new GridItemDecoration());
        /*pb = (ProgressBar) view.findViewById(R.id.progressBar);
        tv = (TextView) view.findViewById(R.id.errorTextView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setVisibility(View.GONE);*/




        // 1 column in portrait mode and 2 columns in landscape mode


        //pb.setVisibility(View.VISIBLE);




        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



       /* mAdapter.changeCursor(mCursor);
        mRecylerView.setAdapter(mAdapter);*/
        getActivity().getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] selectionArgs = {String.valueOf(1)};
        return new CursorLoader(getActivity(),  // Context
                FavoriteContract.Favorite.CONTENT_URI, // URI
                PROJECTION,                // Projection
                null,                           // Selection
                null,                           // Selection args
                null); // Sort
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (mAdapter != null) {
            //pb.setVisibility(View.GONE);
            mAdapter.swapCursor(data);
            Log.d(TAG, "Cursor size"+data.getCount());
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mAdapter.changeCursor(null);
    }




    @Override
    public void getFeedDetails(FeedModel feedModel) {

        Intent intent = new Intent(getActivity(), AnswersActivity.class);

        intent.putExtra("question",feedModel.getTitle());
        Log.d(TAG, "Question Id" + feedModel.getId());
        intent.putExtra("questionid", feedModel.getId());
        intent.putExtra("askedon",feedModel.getAskedOn());
        intent.putExtra("audiofileurl",feedModel.getAudioFileUrl());
        intent.putExtra("upvote",feedModel.getUpvote());
        intent.putExtra("downvote",feedModel.getDownvote());
        intent.putExtra("username",feedModel.getUser().getFullName());
        if(feedModel.isActive())
        {
            intent.putExtra("active","true");
        }
        else
        {
            intent.putExtra("active","false");
        }
        startActivity(intent);
    }
}
