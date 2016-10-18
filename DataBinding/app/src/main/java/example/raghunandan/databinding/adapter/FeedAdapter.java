package example.raghunandan.databinding.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import example.raghunandan.databinding.R;
import example.raghunandan.databinding.databinding.FeedRowBinding;
import example.raghunandan.databinding.models.FeedModel;
import example.raghunandan.databinding.viewmodel.FeedItemViewModel;

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

        FeedRowBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.feed_row,
                parent,
                false);
        return new FeedHolder(binding);

    }

    @Override
    public void onViewRecycled(FeedHolder holder) {
        if(holder!=null)
        {
            holder.binding.setRowvalues(null);
        }
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(FeedHolder holder, int position) {

        holder.binding.setRowvalues(new FeedItemViewModel(mList.get(position)));

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

    public static class FeedHolder extends RecyclerView.ViewHolder {
        private FeedRowBinding binding;

        public FeedHolder(FeedRowBinding binding) {
            super(binding.cardView);
            this.binding = binding;
        }
    }
}
