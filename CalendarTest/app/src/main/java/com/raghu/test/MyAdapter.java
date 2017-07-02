package com.raghu.test;

/**
 * Created by raghu on 2/7/17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class MyAdapter extends ArrayAdapter<Event> {

    private  List<Event> list;
    private LayoutInflater mInflater;

    public MyAdapter(Context context, List<Event> list) {
        super(context, R.layout.row, list);
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    static class ViewHolder {
         TextView text;

    }

    public void addItems(List<Event> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.row, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.label);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.text.setText(list.get(position).getEvents());

        return convertView;
    }


}
