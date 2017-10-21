package com.peakaeriest.ladyspyder.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

import com.peakaeriest.ladyspyder.R;
import com.peakaeriest.ladyspyder.listener.OnItemClickListener;
import com.peakaeriest.ladyspyder.models.Product;

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
    private OnItemClickListener onItemClickListener;


    public ProductAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.mlist_products = new ArrayList<>();
        this.mContext = context;
        this.onItemClickListener = onItemClickListener;
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
            mHolder.bind(mlist_products.get(position), onItemClickListener);

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

        private TextView txtViewTitle,price,label;
        private ImageView imgViewIcon;
        private RatingBar ratingBar;
        private CardView root;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            root = itemLayoutView.findViewById(R.id.root);
            label = itemLayoutView.findViewById(R.id.label);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.title);
            price = (TextView) itemLayoutView.findViewById(R.id.price);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);
            ratingBar = (RatingBar) itemLayoutView.findViewById(R.id.ratingBar);
        }

        public void bind(final Product item, final OnItemClickListener listener) {

            if(!TextUtils.isEmpty(item.getDiscount())) {
                label.setVisibility(View.VISIBLE);
                label.setText(item.getDiscount());
            }else{
                label.setVisibility(View.GONE);
            }
           txtViewTitle.setText(item.getContent());
           price.setText("Rs."+item.getPrice());
           ratingBar.setRating(item.getStars());


            RequestOptions cropOptions = new RequestOptions().override(150,150);


            Glide.with(root.getContext())
                    .asBitmap()
                    .apply(cropOptions)
                    .load(item.getImage_url())
                    .into(new SimpleTarget<Bitmap>(150,150) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            imgViewIcon.setImageBitmap(bitmap);

                    }

        });
          root.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  listener.onItemClick(item);
              }
          });

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