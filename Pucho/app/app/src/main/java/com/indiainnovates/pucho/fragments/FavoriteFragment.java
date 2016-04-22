package com.indiainnovates.pucho.fragments;

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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.indiainnovates.pucho.PuchoApplication;
import com.indiainnovates.pucho.R;
//import com.indiainnovates.pucho.adapters.CustomCursorAdapter;
import com.indiainnovates.pucho.adapters.CursorRecyclerViewAdapter;
//import com.indiainnovates.pucho.adapters.CustomCursorAdapter;
import com.indiainnovates.pucho.adapters.CustomCursorAdapter;
import com.indiainnovates.pucho.provider.FavoriteContract;
import com.indiainnovates.pucho.widgets.GridItemDecoration;

import javax.inject.Inject;

/**
 * Created by Raghunandan on 11-02-2016.
 */
public class FavoriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

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

    @Inject
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.favorite_row, container, false);
        ((PuchoApplication) getActivity().getApplication()).component().inject(this);

        mRecylerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        /*pb = (ProgressBar) view.findViewById(R.id.progressBar);
        tv = (TextView) view.findViewById(R.id.errorTextView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setVisibility(View.GONE);*/



        String userName = sharedPreferences.getString("user_name", "");
        String photoUri = sharedPreferences.getString("photo_uri", "");
       /* if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(photoUri)) {
            mAdapter.setUserName(userName);
            mAdapter.setPhotoUri(photoUri);
        }*/
        // 1 column in portrait mode and 2 columns in landscape mode


        //pb.setVisibility(View.VISIBLE);




        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), getActivity().getResources().getInteger(R.integer.grid_columns));
        mRecylerView.setLayoutManager(mGridLayoutManager);
        mRecylerView.setHasFixedSize(true);
        mRecylerView.addItemDecoration(new GridItemDecoration());
        mAdapter = new CustomCursorAdapter(getActivity(),this,mCursor);
        mRecylerView.setAdapter(mAdapter);
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
            Log.i("FavoriteFragment", "Cursor size"+data.getCount());
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mAdapter.changeCursor(null);
    }


}
