package com.peakaeriest.ladyspyder.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.peakaeriest.ladyspyder.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.peakaeriest.ladyspyder.activities.LSMainActivity;
import com.peakaeriest.ladyspyder.activities.LSMenuDetailListActivity;
import com.peakaeriest.ladyspyder.models.LSMenuPojo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LSHomeAdapter extends RecyclerView.Adapter<LSHomeAdapter.MyViewHolder> {

    private ArrayList<LSMenuPojo> stringList;
    private Context mContext;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtView;
        RoundedImageView rvImageView;

        MyViewHolder(View view) {
            super(view);
            txtView = (TextView) view.findViewById(R.id.txtView);
            rvImageView = (RoundedImageView) view.findViewById(R.id.imageView1);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, LSMenuDetailListActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }


    public LSHomeAdapter(LSMainActivity lsMainActivity, ArrayList<LSMenuPojo> horizontalList) {
        this.stringList = horizontalList;
        this.mContext = lsMainActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ls_adapter_menu_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtView.setText(stringList.get(position).getmTitle());
        Picasso.with(mContext).load(stringList.get(position).getmPhoto()).into(holder.rvImageView);
        holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, holder.txtView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }
}