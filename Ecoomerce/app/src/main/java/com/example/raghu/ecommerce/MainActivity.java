package com.example.raghu.ecommerce;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.raghu.ecommerce.models.Product;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Product>  mList_products = new ArrayList<>();
    private ProductAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new ProductAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.addItemDecoration(new GridItemDecoration());
        recyclerView.setAdapter(mAdapter);

        getData();
    }

    @SuppressLint("StaticFieldLeak")
    public void getData(){

        new AsyncTask<Void,Void,List<Product>>(){

            @Override
            protected List<Product> doInBackground(Void... voids) {
                try {
                    Document doc = Jsoup.connect("https://ladyspyder.com/footwear/").get();
                    Elements list_product = doc.select("div.list_product");

                    Elements col = list_product.select("[class=col-lg-15 col-sm-20 col-xs-30]");

                    for (Element cols : col) {

                        Product product = new Product();
                        float star_val=0;

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
                            if(star_name.equals("star star-full")) {
                                star_val = star_val+1f;
                            }else if(star_name.equals("star star-half")) {
                                star_val = star_val+0.5f;
                            }
                        }
                        product.setStars(star_val);
                        mList_products.add(product);
                    }

                    return  mList_products;


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<Product> products) {
                super.onPostExecute(products);
                if(products!=null)
                mAdapter.addProducts(mList_products);
            }
        }.execute();

    }


}
