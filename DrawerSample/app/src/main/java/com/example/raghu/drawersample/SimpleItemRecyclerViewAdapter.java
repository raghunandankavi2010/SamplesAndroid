package com.example.raghu.drawersample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by raghu on 26/10/17.
 */

public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private final List<Items> mValues;
    private OnItemClickListener onItemClickListener;
    private int selectedPosition = -1;

    private static int View_Header =0;
    private static int View_Item = 1;
    private static int View_Space = 2;
    private static int View_Divider = 3;


    SimpleItemRecyclerViewAdapter(Context parent,
                                  List<Items> items, OnItemClickListener onItemClickListener
    ) {
        mValues = items;
        mContext = parent;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;
        if(viewType == View_Item) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.primary_item, parent, false);
            holder = new ViewHolder_Item(view);
         
        }else if(viewType == View_Header) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.drawer_header, parent, false);
            holder = new ViewHolder_header(view);
            
        }else if(viewType == View_Space) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.space, parent, false);
            holder = new ViewHolder_Space(view);

        }else if(viewType == View_Divider) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.divider_item, parent, false);
            holder = new ViewHolder_Space(view);

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder_Item) {
            ViewHolder_Item viewHolderitem = (ViewHolder_Item) holder;
               viewHolderitem.bind(mValues.get(position ), onItemClickListener, getSelectedPosition());

        }else if( holder instanceof ViewHolder_header){
            ViewHolder_header viewHolder = (ViewHolder_header) holder;
            viewHolder.bind();
        }else if( holder instanceof ViewHolder_Space){
            ViewHolder_Space viewHolder = (ViewHolder_Space) holder;

        }else if( holder instanceof ViewHolder_Divider){
            ViewHolder_Divider viewHolder = (ViewHolder_Divider) holder;

        }



    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType (int position) {
        if(mValues.get(position).getHeader()==position) {
            return View_Header;
        } else if(mValues.get(position).getSpace()==position) {
            return View_Space;
        }  else if(mValues.get(position).isDivider()) {
            return View_Divider;
        } else  {
            return View_Item;
        }

    }
    private boolean isPositionDivider (int position) {

        return position == 3;
    }

    private boolean isPositionHeader (int position) {
        return position == 0;
    }

    private boolean isPositionSpace (int position) {
        return position == 1;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    static class ViewHolder_Item extends RecyclerView.ViewHolder {

        final TextView mContentView;
        final LinearLayout ll;
        final ImageView imageView;

        ViewHolder_Item(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.material_drawer_icon);
            mContentView = (TextView) view.findViewById(R.id.material_drawer_name);
            ll = view.findViewById(R.id.ll);
        }


        public void bind(final Items item, final OnItemClickListener listener,int pos) {

            imageView.setImageResource(item.getDrawable());
            mContentView.setText(item.getName());

            if (getAdapterPosition() == pos) {
                imageView.setColorFilter(mContentView.getContext().getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                mContentView.setTextColor(
                        mContentView.getContext().getResources().getColor(R.color.colorAccent));

                ll.setBackgroundResource(R.drawable.item_background);
            }
            else {
                imageView.setColorFilter(mContentView.getContext().getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                mContentView.setTextColor(Color.BLACK);
                ll.setBackgroundResource(android.R.color.white);

            }

            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if (pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(pos);
                    }

                }
            });

        }
    }


    static class ViewHolder_header extends RecyclerView.ViewHolder {

        final TextView mContentView;

        ViewHolder_header(View view) {
            super(view);

            mContentView = (TextView) view.findViewById(R.id.navheader_txt);

        }

        public void bind() {
            mContentView.setText("Raghunandan");

        }
    }

    static class ViewHolder_Space extends RecyclerView.ViewHolder {

        final View space;

        ViewHolder_Space(View view) {
            super(view);

            space = (View) view.findViewById(R.id.space);

        }
    }

    static class ViewHolder_Divider extends RecyclerView.ViewHolder {

        final View divider;

        ViewHolder_Divider(View view) {
            super(view);

            divider = (View) view.findViewById(R.id.material_drawer_divider);

        }
    }
}