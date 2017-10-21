package com.peakaeriest.ladyspyder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peakaeriest.ladyspyder.R;
import com.peakaeriest.ladyspyder.models.LSFAQModel;

import java.util.List;

public class APFaqListAdapter extends RecyclerView.Adapter<APFaqListAdapter.ViewHolderAttendees> {

    public Context mContext;
    public List<LSFAQModel.FAQ> mFaqDatas;
    public LayoutInflater mLayoutInflater;


    public APFaqListAdapter(Context mContext,
                            List<LSFAQModel.FAQ> mAttendeesDetailPojos) {
        super();
        this.mContext = mContext;
        this.mFaqDatas = mAttendeesDetailPojos;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public class ViewHolderAttendees extends RecyclerView.ViewHolder {
        public TextView tvTiltle;
        public TextView tvDesc;
        public TextView tvParty;

        public ViewHolderAttendees(View arg0) {
            super(arg0);
            tvTiltle = (TextView) arg0.findViewById(R.id.tvName);
            tvDesc = (TextView) arg0.findViewById(R.id.tv_desc);
            tvParty = (TextView) arg0.findViewById(R.id.tvDate);

        }

    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return mFaqDatas.size();
    }

    @Override
    public void onBindViewHolder(ViewHolderAttendees holder, int position) {
        holder.tvTiltle.setText("Q:" + mFaqDatas.get(position).getQuestion());
        holder.tvDesc.setText(mFaqDatas.get(position).getAnswer());
    }

    @Override
    public ViewHolderAttendees onCreateViewHolder(ViewGroup arg0, int arg1) {
        View view = mLayoutInflater.inflate(R.layout.ap_adapter_faq_list_item, arg0, false);
        ViewHolderAttendees attendees = new ViewHolderAttendees(view);
        return attendees;
    }
}