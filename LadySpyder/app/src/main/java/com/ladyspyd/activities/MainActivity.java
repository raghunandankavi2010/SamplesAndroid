package com.ladyspyd.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.ladyspyd.R;
import com.ladyspyd.adapters.ProductAdapter;
import com.ladyspyd.fragments.DetailFragment;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter mAdapter;
    private boolean loadmore = false;
    private ProgressBar pb;
    private int page_count = 1;
    private String title;
    private TextView error;
    //private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getBoolean(R.bool.isTablet)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_host_fragment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String title = getIntent().getStringExtra("title");

        if(savedInstanceState==null) {
            DetailFragment detailFragment = DetailFragment.getInstance(title);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.container, detailFragment, "frag_detail");

            //transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }else {
            DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentByTag("frag_detail");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.container, detailFragment, "frag_detail");

            //transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

/*
        Log.i("MainActivity", "onCreate");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = getIntent().getStringExtra("title");
        getSupportActionBar().setTitle(title.toUpperCase());
        error = findViewById(R.id.error);

        pb = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new ProductAdapter(this, new OnItemClickListener() {
            @Override
            public void onItemClick(Product item) {

                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("title_url",item.getTitle_url());
                intent.putExtra("title",item.getContent());
                Log.i("MainActivity",item.getTitle_url());
                startActivity(intent);
            }
        });
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int spanCount = gridLayoutManager.getSpanCount();
                switch (mAdapter.getItemViewType(position)) {
                    case ProductAdapter.VIEW_ITEM:
                        return 1;
                    case ProductAdapter.VIEW_PROG:
                        return spanCount;
                    default:
                        return -1;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new GridItemDecoration());
        recyclerView.setAdapter(mAdapter);


        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(page_count) {
            @Override
            public void onLoadMore(int current_page, int totalItemCount) {
                if(page_count==1){
                    loadmore =false;
                }else {
                    page_count = current_page;
                    Log.i("Loading Page", "" + page_count);
                    loadmore = true;
                    getData(page_count);
                }
            }

        });

        getData(page_count);
    }

    *//**
     *
     * Should make inner class static
     * it hold reference to outer class
     * So on rotation activity should be destroyed-recreated
     * any reference to the outer class may lead to
     * mem leaks. For now orientation is fixed
     *//*
    @SuppressLint("StaticFieldLeak")
    public void getData(int page) {

        new AsyncTask<Integer, Void, List<Product>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (loadmore) {
                    recyclerView.post(new Runnable() {
                        public void run() {
                            mAdapter.add(null);
                        }
                    });

                }

                if(page_count==1){
                    pb.setVisibility(View.VISIBLE);
                }

            }

            @Override
            protected List<Product> doInBackground(Integer... params) {

                List<Product> mList_products;
                try {

                    String url = "https://ladyspyder.com/"+title+"/page/"+params[0];
                    Log.i("test", url);
                    Document doc = Jsoup.connect(url).get();

                    Elements list_product = doc.select("div.list_product");

                    Elements col = list_product.select("[class=col-lg-15 col-sm-20 col-xs-30]");

                    mList_products = new ArrayList<>(col.size());
                    for (Element cols : col) {

                        Product product = new Product();
                        float star_val = 0;

                        Elements cat = cols.select("div.item-cat");
                        Elements product_item = cat.select("div.product-item");
                        Elements span = product_item.tagName("span");
                        *//**
                         * Discount
                         *//*
                        product.setDiscount(span.select("div.discount").text());

                        *//**
                         * Url for webview
                         *//*
                        String murl =  span.first().getElementsByTag("a").attr("href");
                        Log.i("ImageUrl",murl);
                        product.setTitle_url(murl);
                        Elements thum = span.select("div.thumb-wrap");

                        Element img = thum.select("img").first();
                        *//**
                         * Image url
                         *//*

                        product.setImage_url(img.absUrl("src"));
                        *//**
                         * Header h4
                         *//*
                        product.setContent(span.select("h4").first().text());
                        Element pricetag = span.select("div.price").first();
                        *//**
                         * Price
                         *//*
                        product.setPrice(pricetag.getElementsByTag("meta").attr("content"));
                        *//**
                         * Stars
                         *//*
                        Elements stardiv = span.select("div.stars");
                        Elements stars = stardiv.select("span");
                        for (Element star : stars) {
                            String star_name = star.attr("class");
                            if (star_name.equals("star star-full")) {
                                star_val = star_val + 1f;
                            } else if (star_name.equals("star star-half")) {
                                star_val = star_val + 0.5f;
                            }
                        }
                        product.setStars(star_val);
                        mList_products.add(product);
                    }


                    return mList_products;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<Product> products) {
                super.onPostExecute(products);

                if(page_count==1) {
                    pb.setVisibility(View.GONE);
                }
                if (loadmore) {
                    mAdapter.remove();
                    loadmore = false;
                }

                if (products != null)
                    mAdapter.addProducts(products);

                if(products!=null && products.size()==0) {
                    error.setVisibility(View.VISIBLE);
                    error.setText("No items to display");
                }
            }
        }.execute(page);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(MainActivity.this,FilterActivity.class);
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button

            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_low:
                intent.putExtra("menu","price_low");
                intent.putExtra("title",title);
                startActivity(intent);
                return true;
            case R.id.menu_high:
                intent.putExtra("menu","price_high");
                intent.putExtra("title",title);
                startActivity(intent);
                return true;
            case R.id.menu_discount:
                intent.putExtra("menu","discount");
                intent.putExtra("title",title);
                startActivity(intent);
                return true;
            case R.id.menu_popularity:
                intent.putExtra("menu","popularity");
                intent.putExtra("title",title);
                startActivity(intent);
                return true;
            case R.id.menu_newest:
                intent.putExtra("menu","newest");
                intent.putExtra("title",title);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.filter, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "Destroyed");
    }
}*/
