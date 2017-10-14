package com.example.raghu.ecommerce;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.raghu.ecommerce.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghu on 14/10/17.
 */

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> mlist_products = new ArrayList<>();
    private Context mContext;


    public ProductAdapter(Context context) {
        this.mlist_products = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_items, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.txtViewTitle.setText(mlist_products.get(position).getContent());
        viewHolder.price.setText("Rs."+mlist_products.get(position).getPrice());
        viewHolder.ratingBar.setRating(mlist_products.get(position).getStars());


        RequestOptions cropOptions = new RequestOptions().centerCrop();

        Glide.with(mContext)
                 .asBitmap()
                .apply(cropOptions)
                .load(mlist_products.get(position).getImage_url())
                .into(new SimpleTarget<Bitmap>(100,100) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        viewHolder.imgViewIcon.setImageBitmap(bitmap);

                       /* Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                try {
                                    viewHolder.textBackground.setBackgroundColor(palette.getDarkVibrantSwatch().getRgb());
                                    viewHolder.txtViewTitle.setBackgroundColor(palette.getDarkVibrantSwatch().getTitleTextColor());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });*/
                    }

                });

    }

    public void addProducts(List<Product> products) {
        mlist_products.addAll(products);
        notifyDataSetChanged();
        
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtViewTitle,price;
        private ImageView imgViewIcon;
        private RatingBar ratingBar;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.title);
            price = (TextView) itemLayoutView.findViewById(R.id.price);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);
            ratingBar = (RatingBar) itemLayoutView.findViewById(R.id.ratingBar);
        }
    }


    @Override
    public int getItemCount() {
        return mlist_products.size();
    }
}