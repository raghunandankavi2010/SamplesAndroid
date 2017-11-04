package com.ladyspyd.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.ladyspyd.R;
import com.ladyspyd.activities.DetailActivity;
import com.ladyspyd.activities.FilterActivity;
import com.ladyspyd.activities.GridItemDecoration;
import com.ladyspyd.adapters.ProductAdapter;
import com.ladyspyd.helpers.LSApp;
import com.ladyspyd.listener.EndlessRecyclerViewScrollListener;
import com.ladyspyd.listener.OnItemClickListener;
import com.ladyspyd.models.Product;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class DetailFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter mAdapter;
    private boolean loadmore = false;
    private ProgressBar pb;
    private int page_count = 1;
    private String title;
    private TextView error;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment getInstance(String mTitle) {

        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",mTitle);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private boolean check =false;
    /**
     * Update fragment contents
     *
     * @param mTitle title
     */
    public void updateFragmentContents(String mTitle) {

        this.title = mTitle;
        page_count=1;
        getData(page_count);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.kd_fragment_categories, container, false);

        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getArguments();
        if(extras!=null) {
            title = getArguments().getString("title");
        }
        page_count = 1;
        error = view.findViewById(R.id.error);


        pb = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);
        mAdapter = new ProductAdapter(getActivity(), new OnItemClickListener() {
            @Override
            public void onItemClick(Product item) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("title_url", item.getTitle_url());
                intent.putExtra("title", item.getContent());
                Log.i("DetailFragment", item.getTitle_url());
                startActivity(intent);
            }
        });
        recyclerView.setHasFixedSize(true);
        int span_count=0;
        if(getResources().getBoolean(R.bool.isTablet)) {
            span_count=4;
        }else{
            span_count=2;
        }
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), span_count);
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
                page_count = current_page;
                Log.i("Loading Page", "" + page_count);
                loadmore = true;
                getData(page_count);
            }

        });

        getData(page_count);
        return view;
    }



    /**
     * Should make inner class static
     * it hold reference to outer class
     * So on rotation activity should be destroyed-recreated
     * any reference to the outer class may lead to
     * mem leaks. For now orientation is fixed
     */
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

                if (page_count == 1) {
                    pb.setVisibility(View.VISIBLE);
                }

            }

            @Override
            protected List<Product> doInBackground(Integer... params) {

                List<Product> mList_products;
                try {

                    String url = "https://ladyspyder.com/" + title + "/page/" + params[0];
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
                        /**
                         * Discount
                         */
                        product.setDiscount(span.select("div.discount").text());

                        /**
                         * Url for webview
                         */
                        String murl = span.first().getElementsByTag("a").attr("href");
                        Log.i("ImageUrl", murl);
                        product.setTitle_url(murl);
                        Elements thum = span.select("div.thumb-wrap");

                        Element img = thum.select("img").first();
                        /**
                         * Image url
                         */

                        product.setImage_url(img.absUrl("src"));
                        /**
                         * Header h4
                         */
                        product.setContent(span.select("h4").first().text());
                        Element pricetag = span.select("div.price").first();
                        /**
                         * Price
                         */
                        product.setPrice(pricetag.getElementsByTag("meta").attr("content"));
                        /**
                         * Stars
                         */
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

                if (page_count == 1) {
                    pb.setVisibility(View.GONE);
                }
                if (loadmore) {
                    mAdapter.remove();
                    loadmore = false;
                }

                if (products != null)
                    mAdapter.addProducts(products);

                if (products != null && products.size() == 0) {
                    error.setVisibility(View.VISIBLE);
                    error.setText("No items to display");
                }
            }
        }.execute(page);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filter, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getActivity(), FilterActivity.class);
        String title = LSApp.getInstance().getPrefs().getTitle();
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.menu_low:
                intent.putExtra("menu", "price_low");
                intent.putExtra("title", title);
                startActivity(intent);
                return true;
            case R.id.menu_high:
                intent.putExtra("menu", "price_high");
                intent.putExtra("title", title);
                startActivity(intent);
                return true;
            case R.id.menu_discount:
                intent.putExtra("menu", "discount");
                intent.putExtra("title", title);
                startActivity(intent);
                return true;
            case R.id.menu_popularity:
                intent.putExtra("menu", "popularity");
                intent.putExtra("title", title);
                startActivity(intent);
                return true;
            case R.id.menu_newest:
                intent.putExtra("menu", "newest");
                intent.putExtra("title", title);
                startActivity(intent);
                return true;
            case android.R.id.home :

                return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
