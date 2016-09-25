package example.raghunandan.databinding.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import example.raghunandan.databinding.FeedActivity;


import example.raghunandan.databinding.databinding.FeedRowBinding;
import example.raghunandan.databinding.databinding.OsItemsBinding;
import example.raghunandan.databinding.models.FeedModel;
import example.raghunandan.databinding.models.FeedResponse;

/**
 * Created by Raghunandan on 25-09-2016.
 */

public class FeedAdapter extends  RecyclerView.Adapter<FeedAdapter.FeedHolder>{

    List<FeedModel> mList;
    public FeedAdapter()
    {
        mList = new ArrayList<>();
    }
    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FeedRowBinding binding = FeedRowBinding.inflate(inflater, parent, false);
        return new FeedHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(FeedHolder holder, int position) {

        holder.binding.setRowvalues(mList.get(position));
    }

    public void addList(List<FeedModel> list)
    {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class FeedHolder extends RecyclerView.ViewHolder {

        FeedRowBinding binding;

        public FeedHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
