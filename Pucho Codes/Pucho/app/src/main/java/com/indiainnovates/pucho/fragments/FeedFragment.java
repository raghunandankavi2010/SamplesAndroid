package com.indiainnovates.pucho.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.indiainnovates.pucho.AnswersActivity;
import com.indiainnovates.pucho.PuchoApplication;
import com.indiainnovates.pucho.R;
import com.indiainnovates.pucho.adapters.FeedAdapter;
import com.indiainnovates.pucho.animations.FeedItemAnimator;
import com.indiainnovates.pucho.events.FeedErrorEvent;
import com.indiainnovates.pucho.events.FeedResponseEvent;
import com.indiainnovates.pucho.listeners.EndlessScrollListener;
import com.indiainnovates.pucho.listeners.OnFragmentCallback;
import com.indiainnovates.pucho.listeners.ShareButtonClickListener;
import com.indiainnovates.pucho.models.FeedModel;
import com.indiainnovates.pucho.models.FeedResponse;
import com.indiainnovates.pucho.presenters.FeedPresenter;
import com.indiainnovates.pucho.screen_contracts.FeedFragmentScreen;
import com.indiainnovates.pucho.widgets.EmptyRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Raghunandan on 31-01-2016.
 */
public class FeedFragment extends Fragment implements FeedFragmentScreen, ShareButtonClickListener {


    private int pageCount = 1, totalcount;

    private boolean mLoadMore, mRequestPending, mError;

    private FeedAdapter feedAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private EmptyRecyclerView mRecylerView;
    private ProgressBar pb;
    private TextView tv;
    /*private int mActionBarAutoHideMinY = 0;
    private int mActionBarAutoHideSensivity = 0;
    private int mActionBarAutoHideSignal = 0;
    private boolean mActionBarShown = true;*/

    private static final String TAG ="FeedFragment";

    @Inject
    FeedPresenter feedPresenter;

    private static final String STATE_FEED = "state_feed";
    private static final String REQUEST_PEDNING = "request_pending";
    private static final String ERROR = "error";

    private LinearLayoutManager linearLayoutManager;


    private OnFragmentCallback onFragmentCallback;

    @Inject
    SharedPreferences sharedPreferences;

    List<FeedModel> data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.feed_fragment, container, false);

        ((PuchoApplication) getActivity().getApplication()).component().inject(this);

        mRecylerView = (EmptyRecyclerView) view.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        // 1 column in portrait mode and 2 columns in landscape mode
        // getActivity().getResources().getInteger(R.integer.grid_columns));
        mRecylerView.setLayoutManager(linearLayoutManager);
        mRecylerView.setHasFixedSize(true);
        feedAdapter = new FeedAdapter(this);
        mRecylerView.setAdapter(feedAdapter);
        //mRecylerView.setItemAnimator(new FeedItemAnimator());


        feedPresenter.setPresenter(this);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i("FeedFragment","ActivityCreated");


        pb = (ProgressBar) getView().findViewById(R.id.progressBar);
        tv = (TextView) getView().findViewById(R.id.errorTextView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.activity_main_swipe_refresh_layout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshContent();

            }
        });


        feedPresenter.setInitialPageCount(pageCount);

        if (savedInstanceState != null) {
            Log.i(TAG,"Saved Instance stage");
            pageCount = savedInstanceState.getInt("count");

            boolean bool = savedInstanceState.getBoolean(REQUEST_PEDNING, false);
            totalcount = savedInstanceState.getInt("totalcount");
            if (bool) {
                Log.i("LoadMore", "Rotation");
                mLoadMore = savedInstanceState.getBoolean("loadMore", mLoadMore);
                feedPresenter.fetchFeed();

            } else if (savedInstanceState.containsKey(STATE_FEED)) {
                Log.i("LoadSame Data", "Rotation");
                List<FeedModel> list = savedInstanceState.getParcelableArrayList(STATE_FEED);
                feedAdapter.setData(list);
            } else {
                Log.i("Nothing", "Tryagain");
                mRequestPending = true;
                feedPresenter.hideRecyclerView();
                feedPresenter.displayProgressBar();
                feedPresenter.hideErrorText();
                feedPresenter.setInitialPageCount(pageCount);
                feedPresenter.fetchFeed();
            }

        } else  if(savedInstanceState==null){


            /*Toast.
                    makeText(getActivity().getApplicationContext(),"Loading data first time",
                            Toast.LENGTH_SHORT).show();*/
            Log.i("First Time", "fetch Feed");
            mRequestPending = true;
            feedPresenter.hideRecyclerView();
            feedPresenter.displayProgressBar();
            feedPresenter.hideErrorText();
            feedPresenter.setInitialPageCount(pageCount);
            feedPresenter.fetchFeed();
        }

        //mRequestPending = true;
        //feedPresenter.fetchFeed();

        mRecylerView.addOnScrollListener(new EndlessScrollListener(pageCount) {


            @Override
            public void onLoadMore(int current_page, int totalItemCount) {

               pageCount = current_page;
                Log.i("PageCount",""+pageCount);

                Log.i("Total count",""+totalcount+"Item count"+totalItemCount);
                if ( totalcount != totalItemCount) {
                    feedPresenter.setInitialPageCount(pageCount);
                    feedPresenter.fetchFeed();
                    mLoadMore = true;
                    feedAdapter.add(null);
                }else
                {

                }
            }


        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("FeedFragment","Resumed");
    }

    @Override
    public void onDisplayProgressBar() {
        pb.setVisibility(View.VISIBLE);
    }
    @Override
    public void onHideProgressBar() {
       pb.setVisibility(View.GONE);
    }

    @Override
    public void onDisplayRecyclerView() {
        mRecylerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDisplayErrorText() {

        tv.setVisibility(View.VISIBLE);
    }



    @Override
    public void onHideRecyclerView() {
        mRecylerView.setVisibility(View.GONE);
    }

    @Override
    public void onHideErrorText() {

        tv.setVisibility(View.GONE);
    }

    @Override
    public void onFeedFetched(FeedResponse feedResponse) {

        mRequestPending = false;

        if (mLoadMore == true) {
            Log.i("Load More", "Remove"+mLoadMore);
            feedAdapter.remove();
            mLoadMore = false;
        }else
        {
            Log.i("Load More", "Note Remove"+mLoadMore);
            mLoadMore =false;
        }
        totalcount = feedResponse.getTotal();
        if (feedResponse.getData() != null && feedResponse.getData().size() > 0) {
            feedPresenter.hideErrorText();
            feedPresenter.hideProgressBar();
            feedPresenter.displayRecyclerView();
            data = feedResponse.getData();
            feedAdapter.setData(data);
        }
    }

    @Override
    public void onError(Throwable e) {

       e.printStackTrace();
        mRequestPending = false;
        mError = true;


        if (mLoadMore) {
            Log.i("Load more", "Yes");
            feedAdapter.remove();
            mLoadMore = false;
            feedPresenter.hideErrorText();
            feedPresenter.hideProgressBar();
        } else {
            Log.i("Displaying error text", "Yes");
            feedPresenter.hideRecyclerView();
            feedPresenter.hideProgressBar();
            feedPresenter.displayErrorText();

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("FeedFragment","Fragment Started");
        EventBus.getDefault().register(this);
    }



    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
        mSwipeRefreshLayout.setRefreshing(false);



    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Fragment Destroyed","Destroyed");

        feedPresenter.onDestroyFragment();
        if (mLoadMore) {
            feedAdapter.remove();
            mLoadMore =false;
        }


    }

   @Subscribe
    public void onEvent(FeedErrorEvent e) {
        e.getErrorEvent().printStackTrace();

        mRequestPending = false;
        mError = true;


        if (mLoadMore) {
            feedAdapter.remove();
            mLoadMore = false;
            feedPresenter.hideErrorText();
            feedPresenter.hideProgressBar();
        } else {

            feedPresenter.hideRecyclerView();
            feedPresenter.hideProgressBar();
            feedPresenter.displayErrorText();
            //mProgress.setVisibility(View.INVISIBLE);
            //mErrorText.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe
    public void onEvent(FeedResponseEvent feedResponseEvent) {

        mRequestPending = false;

        if (mLoadMore) {
            Log.i("Load More", "Remove"+mLoadMore);
            feedAdapter.remove();
            mLoadMore = false;
        }else
        {
            Log.i("Load More", "Note Remove"+mLoadMore);
            mLoadMore =false;
        }
        totalcount = feedResponseEvent.getFeedResponse().getTotal();
        if (feedResponseEvent.getFeedResponse().getData() != null && feedResponseEvent.getFeedResponse().getData().size() > 0) {
            feedPresenter.hideErrorText();
            feedPresenter.hideProgressBar();
            feedPresenter.displayRecyclerView();
            data = feedResponseEvent.getFeedResponse().getData();
            feedAdapter.setData(feedResponseEvent.getFeedResponse().getData());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        onFragmentCallback = (OnFragmentCallback) context;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (feedAdapter.getmList().size() > 0) {
            outState.putParcelableArrayList(STATE_FEED, (ArrayList) feedAdapter.getmList());
        }
        outState.putBoolean("loadMore",mLoadMore);
        outState.putBoolean(REQUEST_PEDNING, mRequestPending);
        outState.putBoolean(ERROR, mError);
        outState.putInt("count", pageCount);
        outState.putInt("totalcount", totalcount);
    }


    /* Got to implement direct share later
    Sample available @ https://github.com/googlesamples/android-DirectShare
     */
    @Override
    public void share(int position) {

        onFragmentCallback.onFragmentCallback(feedAdapter.getmList().get(position).getTitle());

    }


    @Override
    public void onCardClick(int position) {
        //Intent intent = new Intent(getActivity(), DummyActivity.class);
        Intent intent = new Intent(getActivity(), AnswersActivity.class);

        intent.putExtra("question",feedAdapter.getmList().get(position).getTitle());
        Log.i("Question id", "" + feedAdapter.getmList().get(position).getId());
        intent.putExtra("questionid", feedAdapter.getmList().get(position).getId());
        intent.putExtra("askedon",feedAdapter.getmList().get(position).getAskedOn());
        intent.putExtra("audiofileurl",feedAdapter.getmList().get(position).getAudioFileUrl());
        intent.putExtra("upvote",feedAdapter.getmList().get(position).getUpvote());
        intent.putExtra("downvote",feedAdapter.getmList().get(position).getDownvote());
        intent.putExtra("username",feedAdapter.getmList().get(position).getUser().getFullName());
        if(feedAdapter.getmList().get(position).isActive())
        {
            intent.putExtra("active","true");
        }
        else
        {
            intent.putExtra("active","false");
        }
        startActivity(intent);
    }

    private void refreshContent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(getActivity()!=null)
                //Toast.makeText(getActivity().getApplicationContext(),"Dummy refresh content",Toast.LENGTH_SHORT).show();

                mSwipeRefreshLayout.setRefreshing(false);
            }
        },5000);
    }


}
