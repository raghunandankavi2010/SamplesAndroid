package com.demo.sticker;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

class MyViewHolder extends RecyclerView.ViewHolder {

  private ImageView image;

  MyViewHolder(View itemView) {
    super(itemView);
    image = itemView.findViewById(R.id.image);
  }

  void bind(Data data) {

    Glide.with(image.getContext()).load(data.imageId).centerCrop().into(image);
  }
}
