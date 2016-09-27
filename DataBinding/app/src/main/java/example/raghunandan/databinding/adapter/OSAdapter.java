package example.raghunandan.databinding.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import example.raghunandan.databinding.databinding.OsItemsBinding;
import example.raghunandan.databinding.models.OperatingSystems;


/**
 * Created by Raghunandan on 24-09-2016.
 */
public class OSAdapter extends RecyclerView.Adapter<OSAdapter.OSItemViewHolder> {

    private List<OperatingSystems> mList;
    public OSAdapter(List<OperatingSystems> mList) {
        this.mList = mList;
    }



    @Override
    public OSItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        OsItemsBinding binding = OsItemsBinding.inflate(inflater, parent, false);
        return new OSItemViewHolder(binding.getRoot());

    }

    @Override
    public void onBindViewHolder(OSItemViewHolder holder, int position) {

        holder.binding.setItem(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



    static class OSItemViewHolder extends RecyclerView.ViewHolder {

        OsItemsBinding binding;

        public OSItemViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}