package com.demo.sticker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<MyViewHolder> {

  private List<Data> mList;

  CustomAdapter(ArrayList<Data> images) {
    mList = new ArrayList<>(images.size());
    this.mList.addAll(images);
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
    return new MyViewHolder(v);
  }

  @Override
  public void onBindViewHolder(MyViewHolder holder, final int position) {
    holder.bind(mList.get(position));
  }

  @Override
  public int getItemCount() {
    return mList.size();
  }
}
