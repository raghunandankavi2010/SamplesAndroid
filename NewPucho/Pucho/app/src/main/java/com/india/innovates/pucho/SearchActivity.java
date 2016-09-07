package com.india.innovates.pucho;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.india.innovates.pucho.adapters.SearchAdapter;
import com.india.innovates.pucho.listeners.EndlessScrollListener;
import com.india.innovates.pucho.listeners.ShareButtonClickListener;
import com.india.innovates.pucho.models.SearchQueryResponse;
import com.india.innovates.pucho.models.SearchQuestions;
import com.india.innovates.pucho.presenters.SearchPresenter;
import com.india.innovates.pucho.screen_contracts.SearchScreen;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Raghunandan on 27-02-2016.
 */
public class SearchActivity extends AppCompatActivity implements SearchScreen, ShareButtonClickListener {

    private String mQuery = "";
    private SearchView mSearchView;
    private ProgressBar pb;
    private RecyclerView recyclerView;
    private SearchAdapter feedAdapter;
    private GridLayoutManager mGridLayoutManager;
    private int fromCount = 0, totalcount;
    private boolean mLoadMore;
    private TextView textView;

    private static final String TAG = SearchActivity.class.getSimpleName();

    @Inject
    SearchPresenter searchPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        PuchoApplication.component().inject(this);
        searchPresenter.setContext(this);

        pb = (ProgressBar) this.findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) this.findViewById(R.id.recyclerview);
        textView =(TextView) this.findViewById(R.id.errorTextView);

        mGridLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.grid_columns));
        recyclerView.setLayoutManager(mGridLayoutManager);
        //recyclerView.addItemDecoration(new GridItemDecoration());

        feedAdapter = new SearchAdapter(this);
        recyclerView.setAdapter(feedAdapter);
        recyclerView.setVisibility(View.GONE);

        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int spanCount = mGridLayoutManager.getSpanCount();
                switch (feedAdapter.getItemViewType(position)) {
                    case SearchAdapter.VIEW_ITEM:
                        return 1;
                    case SearchAdapter.VIEW_PROG:
                        return spanCount;
                    default:
                        return -1;
                }
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        toolbar.setTitle(getResources().getString(R.string.search));
        toolbar.setNavigationIcon(R.drawable.ic_up);
        this.setSupportActionBar(toolbar);

        recyclerView.addOnScrollListener(new EndlessScrollListener(fromCount) {

            @Override
            public void onLoadMore(int current_page, int totalItemCount) {

                fromCount = feedAdapter.getmList().size();

                Log.d(TAG,"Total Count: "+totalcount+"Item count"+totalItemCount);
                if (totalcount != totalItemCount) {
                    searchPresenter.setInitialFromCount(fromCount);
                    searchPresenter.getSearchResults();
                    mLoadMore = true;
                    feedAdapter.add(null);
                }
            }


        });

        if(savedInstanceState!=null && savedInstanceState.containsKey("list" ))
        {
            if(savedInstanceState.getParcelableArrayList("list")!=null && savedInstanceState.getParcelableArrayList("list").size()>0) {
                List<SearchQuestions> list = savedInstanceState.getParcelableArrayList("list");
                feedAdapter.setData(list);
            }
        }

        //handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }


    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            String query = intent.getStringExtra(SearchManager.QUERY);
            if (!TextUtils.isEmpty(query)) {
                //searchFor(query);
                mSearchView.setQuery(query, false);
            }
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.search, menu);
        final MenuItem searchItem = menu.findItem(R.id.menu_search);
        if (searchItem != null) {
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            final SearchView view = (SearchView) searchItem.getActionView();
            mSearchView = view;
            //mSearchView.setIconifiedByDefault(false);
            if (view == null) {
                Log.d("SearchActivity", "Could not set up search view, view is null.");
            } else {
                view.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                view.setIconified(false);
                view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        view.clearFocus();
                          if(!TextUtils.isEmpty(s)) {
                              if(feedAdapter!=null)
                                  feedAdapter.clearList();
                              fromCount=0;
                              pb.setVisibility(View.VISIBLE);
                              searchPresenter.setInitialFromCount(fromCount);
                              searchPresenter.setSearchString(s);
                              searchPresenter.getSearchResults();
                          }else {
                              Toast.makeText(SearchActivity.this,"Enter search query to search",Toast.LENGTH_SHORT).show();
                          }
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {

                        return true;
                    }
                });
                view.setOnCloseListener(new SearchView.OnCloseListener() {
                    @Override
                    public boolean onClose() {
                        //finish();
                        view.onActionViewCollapsed();
                        MenuItemCompat.collapseActionView(searchItem);
                        return false;
                    }
                });
            }

            if (!TextUtils.isEmpty(mQuery)) {
                if(view!=null)
                view.setQuery(mQuery, false);
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchPresenter.onActivityDestroy();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if(pb!=null )
        {
            pb.setVisibility(View.GONE);
            textView.setText("Something went wrong!. Try again later");
        }
        if (mLoadMore) {
            Log.d(TAG, " Load More!.Yes");
            feedAdapter.remove();
            mLoadMore = false;
        } else {
            Log.d(TAG, "Display Error Tesxt");

        }
    }

    @Override
    public void onSearchResponse(SearchQueryResponse searchQueryResponse) {

        if(pb!=null  )
        {
            pb.setVisibility(View.GONE);
        }
        if (mLoadMore) {
            Log.d(TAG, "Load More!.Remove"+true);
            feedAdapter.remove();
            mLoadMore = false;
        }else
        {
            mLoadMore =false;
            Log.d(TAG, "Note Remove"+false);
        }

        totalcount = searchQueryResponse.getSuccess_response().getSuccess().getTotal();
        if ( searchQueryResponse.getSuccess_response().getSuccess().getQuestions_data() != null && searchQueryResponse.getSuccess_response().getSuccess().getQuestions_data().size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);

            feedAdapter.setData(searchQueryResponse.getSuccess_response().getSuccess().getQuestions_data());
        }else
        {
           Toast.makeText(getApplicationContext(),"No search results. Try again!.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void share(int position) {

    }

    @Override
    @SuppressWarnings("unchecked")
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if(feedAdapter.getmList()!=null && feedAdapter.getmList().size()>0)
        {
            outState.putParcelableArrayList("list",(ArrayList)feedAdapter.getmList());
        }
    }

    @Override
    public void onCardClick(int position) {

        Intent intent = new Intent(this, SearchQuestionDetailActivity.class);

        intent.putExtra("question",feedAdapter.getmList().get(position).getTitle());
        Log.d(TAG, "Question ID" + feedAdapter.getmList().get(position).getId());
        intent.putExtra("questionid", feedAdapter.getmList().get(position).getId());
        intent.putExtra("askedon",feedAdapter.getmList().get(position).getAsked_on());
        intent.putExtra("audiofileurl","");
        intent.putExtra("upvote",feedAdapter.getmList().get(position).getUpvote());
        intent.putExtra("downvote",feedAdapter.getmList().get(position).getDownvote());
        intent.putExtra("username",feedAdapter.getmList().get(position).getUser().getFull_name());
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
}
