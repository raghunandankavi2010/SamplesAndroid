package com.example.raghu.ecommerce;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.raghu.ecommerce.listener.EndlessRecyclerViewScrollListener;
import com.example.raghu.ecommerce.models.Product;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter mAdapter;
    private boolean loadmore = false;
    private ProgressBar pb;
    private int page_count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new ProductAdapter(this);
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
                page_count = current_page;
                Log.i("Loading Page", "" + page_count);
                loadmore = true;
                getData(page_count);
            }

        });

        getData(page_count);
    }

    /**
     *
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

                if(page_count==1){
                    pb.setVisibility(View.VISIBLE);
                }

            }

            @Override
            protected List<Product> doInBackground(Integer... params) {

                List<Product> mList_products;
                try {

                    Log.i("test", "" + "https://ladyspyder.com/footwear/page/" + params[0]);
                    Document doc = Jsoup.connect("https://ladyspyder.com/footwear/page/" + params[0]).get();

                    Elements list_product = doc.select("div.list_product");

                    Elements col = list_product.select("[class=col-lg-15 col-sm-20 col-xs-30]");

                    mList_products = new ArrayList<>(col.size());
                    for (Element cols : col) {

                        Product product = new Product();
                        float star_val = 0;

                        Elements cat = cols.select("div.item-cat");
                        Elements product_item = cat.select("div.product-item");
                        Elements span = product_item.tagName("span");
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

                if(page_count==1){
                    pb.setVisibility(View.GONE);
                }
                if (loadmore) {
                    mAdapter.remove();
                    loadmore = false;
                }

                if (products != null)
                    mAdapter.addProducts(products);
            }
        }.execute(page);

    }


}
