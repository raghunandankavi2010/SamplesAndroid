package com.peakaeriest.ladyspyder.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peakaeriest.ladyspyder.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.peakaeriest.ladyspyder.LSProductDetailActivity;
import com.peakaeriest.ladyspyder.models.LSProductPojo;

import java.util.ArrayList;

public class LSProductListAdapter extends RecyclerView.Adapter<LSProductListAdapter.MyViewHolder> {

    private ArrayList<LSProductPojo> stringList;
    private Context mContext;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtView;
        RoundedImageView rvImageView;

        MyViewHolder(View view) {
            super(view);
           // txtView = (TextView) view.findViewById(R.id.txtView);
           // rvImageView = (RoundedImageView) view.findViewById(R.id.imageView1);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, LSProductDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }


    public LSProductListAdapter(Context lsMainActivity, ArrayList<LSProductPojo> horizontalList) {
        this.stringList = horizontalList;
        this.mContext = lsMainActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ls_adapter_product_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //  holder.txtView.setText(stringList.get(position).getProductName());
       // Picasso.with(mContext).load(stringList.get(position).getProductPrice()).into(holder.rvImageView);
       /* holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, holder.txtView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }
}