package com.example.raghu.ecommerce;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class ProductAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product> mlist_products = new ArrayList<>();
    private Context mContext;

    public static final int VIEW_ITEM = 1;
    public static final int VIEW_PROG = 0;


    public ProductAdapter(Context context) {
        this.mlist_products = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        RecyclerView.ViewHolder  viewHolder;
        if(viewType==VIEW_ITEM) {
            View itemLayoutView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_items, parent, false);
            // create ViewHolder
            viewHolder = new ViewHolder(itemLayoutView);
        }else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progress_item, parent, false);

            viewHolder = new ProgressViewHolder(v);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if(viewHolder instanceof ViewHolder) {

            final ViewHolder mHolder = (ViewHolder) viewHolder;
            mHolder.txtViewTitle.setText(mlist_products.get(position).getContent());
            mHolder.price.setText("Rs."+mlist_products.get(position).getPrice());
            mHolder.ratingBar.setRating(mlist_products.get(position).getStars());


            RequestOptions cropOptions = new RequestOptions().centerCrop();

            Glide.with(mContext)
                    .asBitmap()
                    .apply(cropOptions)
                    .load(mlist_products.get(position).getImage_url())
                    .into(new SimpleTarget<Bitmap>(100,100) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            mHolder.imgViewIcon.setImageBitmap(bitmap);

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
        }else {
            ((ProgressViewHolder)viewHolder).progressBar.setIndeterminate(true);
        }
    }

    public void remove() {

        int pos = mlist_products.size() - 1;
        mlist_products.remove(pos);
        notifyItemRemoved(pos);
    }

    public void addProducts(List<Product> products) {

        mlist_products.addAll(products);
        notifyItemRangeInserted(getItemCount(),products.size());
        
    }


    public void add(Product product) {
        mlist_products.add(product);
        notifyItemInserted(getItemCount()+1);
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


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {


        ProgressBar progressBar;
        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }


    @Override
    public int getItemCount() {

        return mlist_products.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mlist_products.get(position)!=null? VIEW_ITEM: VIEW_PROG;
    }
}