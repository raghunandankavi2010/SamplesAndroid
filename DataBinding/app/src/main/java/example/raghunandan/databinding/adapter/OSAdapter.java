package example.raghunandan.databinding.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import java.util.List;

import example.raghunandan.databinding.databinding.OsItemsBinding;
import example.raghunandan.databinding.models.OperatingSystems;
import example.raghunandan.databinding.utils.Utils;


/**
 * Created by Raghunandan on 24-09-2016.
 */
public class OSAdapter extends RecyclerView.Adapter<OSAdapter.OSItemViewHolder> {


    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    private static final int ANIMATED_ITEMS_COUNT = 2;

    private Context context;
    private int lastAnimatedPosition = -1;
    private int itemsCount = 0;
    private boolean animateItems = false;
    private List<OperatingSystems> mList;
    public OSAdapter(List<OperatingSystems> mList) {
        this.mList = mList;
    }


    private void runEnterAnimation(View view, int position) {
      /*  if (!animateItems || position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }*/

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(Utils.getScreenHeight(view.getContext()));
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }

    @Override
    public OSItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        OsItemsBinding binding = OsItemsBinding.inflate(inflater, parent, false);
        return new OSItemViewHolder(binding.getRoot());

    }

    @Override
    public void onBindViewHolder(OSItemViewHolder holder, int position) {

        //runEnterAnimation(holder.itemView, position);
        holder.binding.setItem(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



    public static class OSItemViewHolder extends RecyclerView.ViewHolder {

        OsItemsBinding binding;
        public View itemView;

        public OSItemViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            binding = DataBindingUtil.bind(itemView);
        }
    }
}