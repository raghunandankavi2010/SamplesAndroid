package com.example.raghu.specbeeassignment;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghu on 16/11/17.
 */

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<File> mlist_images = new ArrayList<>();
    private Context mContext;

    private OnItemClickListener onItemClickListener;


    public ImageAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.mlist_images = new ArrayList<>();
        this.mContext = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_items, parent, false);
        // create ViewHolder
        RecyclerView.ViewHolder viewHolder = new ViewHolder(itemLayoutView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {


        final ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.bind(mlist_images.get(position), onItemClickListener);


    }


    public void add(File images) {
        mlist_images.add(images);
        notifyItemInserted(getItemCount());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private CardView root;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            root = itemLayoutView.findViewById(R.id.root);


            image = (ImageView) itemLayoutView.findViewById(R.id.item_icon);

        }

        public void bind(final File item, final OnItemClickListener listener) {


            RequestOptions cropOptions = new RequestOptions().placeholder(R.mipmap.ic_launcher);


            RequestManager requestManager = Glide.with(root.getContext());

            try {
                if(item.getAbsolutePath()!=null) {

                    requestManager.asBitmap()
                            .apply(cropOptions)
                            .load(item.getAbsolutePath())
                            .into(image);

                }else {
                    requestManager.clear(image);
                    // remove the placeholder (optional); read comments below
                    image.setImageDrawable(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item.getAbsolutePath());
                }
            });

        }


    }

    @Override
    public int getItemCount() {

        return mlist_images.size();
    }
}

