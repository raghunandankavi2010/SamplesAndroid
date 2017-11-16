package com.example.raghu.specbeeassignment;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghu on 16/11/17.
 */

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Items> mItems = new ArrayList<>();
    private Context mContext;

    public static final int VIEW_IMAGE = 1;
    public static final int VIEW_TEXT = 0;

    private OnItemClickListener onItemClickListener;


    public ImageAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.mItems = new ArrayList<>();
        this.mContext = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,

                                                      int viewType) {


        RecyclerView.ViewHolder viewHolder;
        if (viewType == VIEW_IMAGE) {
            View itemLayoutView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_items, parent, false);
            // create ViewHolder
            viewHolder = new ViewHolder(itemLayoutView);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_text, parent, false);

            viewHolder = new ViewHolder_Text(v);
        }

        return viewHolder;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ViewHolder) {

            final ViewHolder mHolder = (ViewHolder) viewHolder;
            mHolder.bind(mItems.get(position), onItemClickListener);

        } else {
            final ViewHolder_Text mHolder = (ViewHolder_Text) viewHolder;
            mHolder.bind(mItems.get(position), onItemClickListener);
        }




    }


    public void add(Items items) {

            mItems.add(items);

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

        public void bind(final Items item, final OnItemClickListener listener) {


            RequestOptions cropOptions = new RequestOptions().placeholder(R.mipmap.ic_launcher);


            RequestManager requestManager = Glide.with(root.getContext());

            try {
                if(item.getUrl()!=null) {

                    requestManager.asBitmap()
                            .apply(cropOptions)
                            .load(item.getUrl())
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
                    listener.onItemClick(item.getUrl());
                }
            });

        }
    }

    public static class ViewHolder_Text extends RecyclerView.ViewHolder {

        private TextView text;

        private CardView root;


        public ViewHolder_Text(View itemLayoutView) {
            super(itemLayoutView);
            root = itemLayoutView.findViewById(R.id.root);

            text = (TextView) itemLayoutView.findViewById(R.id.textView);

        }

        public void bind(final Items item, final OnItemClickListener listener) {

            if (!TextUtils.isEmpty(item.getText())) {
                text.setText(item.getText());
            }


            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickText(item.getText());
                }
            });

        }

    }


    @Override
    public int getItemCount() {

        return mItems.size();
    }


    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getUrl() != null ? VIEW_IMAGE : VIEW_TEXT;
    }
}

