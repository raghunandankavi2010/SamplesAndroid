package com.india.innovates.pucho.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.india.innovates.pucho.AnswersActivity;
import com.india.innovates.pucho.PuchoApplication;
import com.india.innovates.pucho.R;
import com.india.innovates.pucho.adapters.FeedAdapter;
import com.india.innovates.pucho.events.FeedErrorEvent;
import com.india.innovates.pucho.events.FeedResponseEvent;
import com.india.innovates.pucho.listeners.EndlessScrollListener;
import com.india.innovates.pucho.listeners.OnFragmentCallback;
import com.india.innovates.pucho.listeners.ShareButtonClickListener;
import com.india.innovates.pucho.models.FeedModel;
import com.india.innovates.pucho.models.FeedResponse;
import com.india.innovates.pucho.presenters.FeedPresenter;
import com.india.innovates.pucho.screen_contracts.FeedFragmentScreen;
import com.india.innovates.pucho.widgets.EmptyRecyclerView;

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
    public static final String preference_key =  "listkey";

    private FeedAdapter feedAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private EmptyRecyclerView mRecylerView;
    private ProgressBar pb;
    private TextView tv;
    /*private int mActionBarAutoHideMinY = 0;
    private int mActionBarAutoHideSensivity = 0;
    private int mActionBarAutoHideSignal = 0;
    private boolean mActionBarShown = true;*/


    private static final String TAG = "FeedFragment";

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
    private String language;


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


    SharedPreferences prefs;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "ActivityCreated");


        pb = (ProgressBar) getView().findViewById(R.id.progressBar);
        tv = (TextView) getView().findViewById(R.id.errorTextView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.activity_main_swipe_refresh_layout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshContent();

            }
        });


        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        language = prefs.getString("listkey","");
        Log.d(TAG,language);

        if(!TextUtils.isEmpty(language))
        {
            if(feedAdapter!=null)
                feedAdapter.clearData();

        }


        feedPresenter.setInitialPageCount(pageCount);

        if (savedInstanceState != null) {
            Log.i(TAG, "Saved Instance stage");
            pageCount = savedInstanceState.getInt("count");

            boolean bool = savedInstanceState.getBoolean(REQUEST_PEDNING, false);
            totalcount = savedInstanceState.getInt("totalcount");
            if (bool) {
                Log.i("LoadMore", "Rotation");
                mLoadMore = savedInstanceState.getBoolean("loadMore", mLoadMore);
                feedPresenter.fetchFeed(language);

            } else if (savedInstanceState.containsKey(STATE_FEED)) {
                Log.i("LoadSame Data", "Rotation");


                    List<FeedModel> list = savedInstanceState.getParcelableArrayList(STATE_FEED);
                    feedAdapter.setData(list,language);

            } else {
                Log.i("Nothing", "Tryagain");

                mRequestPending = true;
                feedPresenter.hideRecyclerView();
                feedPresenter.displayProgressBar();
                feedPresenter.hideErrorText();
                feedPresenter.setInitialPageCount(pageCount);
                feedPresenter.fetchFeed(language);
            }

        } else if (savedInstanceState == null) {


            /*Toast.
                    makeText(getActivity().getApplicationContext(),"Loading data first time",
                            Toast.LENGTH_SHORT).show();*/
            Log.d(TAG, "Fetch Feed First Time");
            mRequestPending = true;
            feedPresenter.hideRecyclerView();
            feedPresenter.displayProgressBar();
            feedPresenter.hideErrorText();
            feedPresenter.setInitialPageCount(pageCount);
            feedPresenter.fetchFeed(language);
        }

        //mRequestPending = true;
        //feedPresenter.fetchFeed();

        mRecylerView.addOnScrollListener(new EndlessScrollListener(pageCount) {


            @Override
            public void onLoadMore(int current_page, int totalItemCount) {

                pageCount = current_page;
                Log.d("TAG", "PageCount " + pageCount);

                Log.d("TAG", " Total count: " + totalcount + "Item count: " + totalItemCount);
                if (totalcount != totalItemCount) {
                    feedPresenter.setInitialPageCount(pageCount);
                    feedPresenter.fetchFeed(language);
                    mLoadMore = true;
                    feedAdapter.add(null);
                } else {

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
        Log.i("FeedFragment", "Resumed");


        /*SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

                if(key.equals(SettingsFragment.preference_key))
                    language = prefs.getString("listkey", "");



            }
        };

        prefs.registerOnSharedPreferenceChangeListener(prefListener);*/

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

        //tv.setVisibility(View.VISIBLE);

    }


    @Override
    public void onHideRecyclerView() {
        mRecylerView.setVisibility(View.GONE);
    }

    @Override
    public void onHideErrorText() {

        //tv.setVisibility(View.GONE);
    }

    @Override
    public void onFeedFetched(FeedResponse feedResponse) {

        mRequestPending = false;

        if (mLoadMore == true) {
            Log.i("Load More", "Remove" + mLoadMore);
            feedAdapter.remove();
            mLoadMore = false;
        } else {
            Log.i("Load More", "Note Remove" + mLoadMore);
            mLoadMore = false;
        }
        totalcount = feedResponse.getTotal();
        if (feedResponse.getData() != null && feedResponse.getData().size() > 0) {
            feedPresenter.hideErrorText();
            feedPresenter.hideProgressBar();
            feedPresenter.displayRecyclerView();
            data = feedResponse.getData();
            feedAdapter.setData(data,language);
        }
    }

    @Override
    public void onError(Throwable e) {

        e.printStackTrace();
        mRequestPending = false;
        mError = true;


        if (mLoadMore) {
            Log.d(TAG, " Load Moree!.Yes");
            feedAdapter.remove();
            mLoadMore = false;
            feedPresenter.hideErrorText();
            feedPresenter.hideProgressBar();
        } else {
            Log.d(TAG, "Error!. Yes");
            feedPresenter.hideRecyclerView();
            feedPresenter.hideProgressBar();
            feedPresenter.displayErrorText();

        }
    }

    @Override
    public void onStart() {
        super.onStart();

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

        feedPresenter.onDestroyFragment();
        if (mLoadMore) {
            feedAdapter.remove();
            mLoadMore = false;
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
            Log.d(TAG, "Remove" + mLoadMore);
            feedAdapter.remove();
            mLoadMore = false;
        } else {

            mLoadMore = false;
        }
        totalcount = feedResponseEvent.getFeedResponse().getTotal();
        if (feedResponseEvent.getFeedResponse().getData() != null && feedResponseEvent.getFeedResponse().getData().size() > 0) {
            feedPresenter.hideErrorText();
            feedPresenter.hideProgressBar();
            feedPresenter.displayRecyclerView();
            data = feedResponseEvent.getFeedResponse().getData();
            feedAdapter.setData(feedResponseEvent.getFeedResponse().getData(),language);
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
        outState.putBoolean("loadMore", mLoadMore);
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

        if(language.equals("Hindi"))
        {
            intent.putExtra("question", feedAdapter.getmList().get(position).getLanguageContents().get(0).getContent());
        } else {
            intent.putExtra("question", feedAdapter.getmList().get(position).getTitle());
        }
        Log.d(TAG, "Question id: "  + feedAdapter.getmList().get(position).getId());
        intent.putExtra("questionid", feedAdapter.getmList().get(position).getId());
        intent.putExtra("askedon", feedAdapter.getmList().get(position).getAskedOn());
        intent.putExtra("audiofileurl", feedAdapter.getmList().get(position).getAudioFileUrl());
        intent.putExtra("upvote", feedAdapter.getmList().get(position).getUpvote());
        intent.putExtra("downvote", feedAdapter.getmList().get(position).getDownvote());
        intent.putExtra("username", feedAdapter.getmList().get(position).getUser().getFullName());
        intent.putExtra("userid", feedAdapter.getmList().get(position).getUser().getId());

        if (feedAdapter.getmList().get(position).isActive()) {
            intent.putExtra("active", "true");
        } else {
            intent.putExtra("active", "false");
        }
        startActivity(intent);
    }

    private void refreshContent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (getActivity() != null)


                    mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 5000);
    }



}
