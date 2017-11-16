package com.example.raghu.specbeeassignment;

/**
 * Created by raghu on 16/11/17.
 */

import android.support.v7.widget.RecyclerView;

import java.io.File;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghu on 16/11/17.
 */

public class TextAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TextItem> mlist_itext= new ArrayList<>();
    private Context mContext;

    private OnItemClickListener onItemClickListener;


    public TextAdapter(Context context) {
        this.mlist_itext = new ArrayList<>();
        this.mContext = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_text, parent, false);
        // create ViewHolder
        RecyclerView.ViewHolder viewHolder = new ViewHolder(itemLayoutView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {


        final ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.bind(mlist_itext.get(position), onItemClickListener);


    }


    public void add(TextItem text) {
        mlist_itext.add(text);
        notifyItemInserted(getItemCount());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            textView = (TextView) itemLayoutView.findViewById(R.id.textView);


        }

        public void bind(final TextItem item, final OnItemClickListener listener) {

            textView.setText(item.getText());

        }


    }

    @Override
    public int getItemCount() {

        return mlist_itext.size();
    }
}

