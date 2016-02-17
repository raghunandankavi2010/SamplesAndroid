package com.eimmer.recyclerviewheightanimations.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eimmer.recyclerviewheightanimations.dataprovider.ColorProvider;
import com.eimmer.recyclerviewheightanimations.R;

import java.util.ArrayList;
import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ColorViewHolder> {

    private List<ColorDisplay> colorDisplayList = new ArrayList<>();

    public SimpleAdapter() {
        for (Integer color : ColorProvider.COLORS) {
            colorDisplayList.add(new ColorDisplay(color));
        }
    }

    @Override
    public SimpleAdapter.ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_simple_color, parent, false);
        final ColorViewHolder viewHolder = new ColorViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SimpleAdapter.ColorViewHolder holder, int position) {
        final ColorDisplay colorDisplay = colorDisplayList.get(position);
        holder.colorTitle.setText(Integer.toHexString(colorDisplay.color));
        holder.colorTitle.setBackgroundColor(colorDisplay.color);
        holder.colorBody.setBackgroundColor(colorDisplay.color);
        holder.dataModel = colorDisplay;

        holder.colorBody.setVisibility(colorDisplay.expanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return colorDisplayList.size();
    }

    public class ColorViewHolder extends RecyclerView.ViewHolder {

        TextView colorTitle;
        View colorBody;
        ColorDisplay dataModel;

        public ColorViewHolder(View itemView) {
            super(itemView);
            colorTitle = (TextView) itemView.findViewById(R.id.colorTitle);
            colorTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataModel.expanded = !dataModel.expanded;
                    notifyItemChanged(getAdapterPosition());
                }
            });

            colorBody = itemView.findViewById(R.id.colorBody);
        }


    }

    public class ColorDisplay {
        int color;
        boolean expanded = true;

        public ColorDisplay(int color) {
            this.color = color;
        }
    }
}
