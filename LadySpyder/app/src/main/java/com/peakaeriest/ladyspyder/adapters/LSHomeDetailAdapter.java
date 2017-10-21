package com.peakaeriest.ladyspyder.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.peakaeriest.ladyspyder.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.peakaeriest.ladyspyder.activities.LSProductListActivity;
import com.peakaeriest.ladyspyder.models.LSMenuPojo;

import java.util.ArrayList;

public class LSHomeDetailAdapter extends RecyclerView.Adapter<LSHomeDetailAdapter.MyViewHolder> {

    private ArrayList<LSMenuPojo> stringList;
    private Context mContext;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtView;
        RoundedImageView rvImageView;

        MyViewHolder(View view) {
            super(view);
            txtView = (TextView) view.findViewById(R.id.txtView);
            rvImageView = (RoundedImageView) view.findViewById(R.id.iv_product_image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, LSProductListActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }


    public LSHomeDetailAdapter(Context lsMainActivity, ArrayList<LSMenuPojo> horizontalList) {
        this.stringList = horizontalList;
        this.mContext = lsMainActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ls_adapter_menu_items_detail, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            holder.txtView.setText(stringList.get(position).getmTitle());
            Glide.with(mContext).load(stringList.get(position).getmPhoto()).into(holder.rvImageView);
            holder.txtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, holder.txtView.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }
}