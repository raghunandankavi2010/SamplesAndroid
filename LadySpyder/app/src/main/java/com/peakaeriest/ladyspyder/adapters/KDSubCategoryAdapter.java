package com.peakaeriest.ladyspyder.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peakaeriest.ladyspyder.R;
import com.peakaeriest.ladyspyder.models.KDProductsPojo;

import java.util.ArrayList;

public class KDSubCategoryAdapter extends RecyclerView.Adapter<KDSubCategoryAdapter.MyViewHolder> {

    public ArrayList<KDProductsPojo> mVehicleArrayList;
    public Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvCarName, tvCarnumber;
        ImageView ivProfile, tv_quantity;

        public MyViewHolder(View view) {
            super(view);
            tvCarName = (TextView) view.findViewById(R.id.tv_vechicle_name);
            tvName = (TextView) view.findViewById(R.id.tv_tenant_name);
            tvCarnumber = (TextView) view.findViewById(R.id.tv_quantity);
            ivProfile = (ImageView) view.findViewById(R.id.profile_image);

        }
    }

    /**
     * Vechicle adapter constrcutor
     *
     * @param mContext
     * @param moviesList
     */
    public KDSubCategoryAdapter(Context mContext, ArrayList<KDProductsPojo> moviesList) {
        this.mVehicleArrayList = moviesList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kd_adapter_product_sub_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            KDProductsPojo tenantObject = mVehicleArrayList.get(position);
            holder.tvCarName.setText(tenantObject.getCarname());
            holder.tvCarnumber.setText(tenantObject.getCarNUmber());
            holder.tvName.setText(tenantObject.getTenantname());
            String valueImage = "aaaa" + tenantObject.getCarimage();
            Glide.with(mContext).load(R.drawable.ic_product_image1).into(holder.ivProfile);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return
                mVehicleArrayList.size();
    }


}
