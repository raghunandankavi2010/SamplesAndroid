package com.example.raghu.contactsdashboard_raghunandan;

/**
 * Created by raghu on 10/5/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactsCallLogAdapter extends RecyclerView.Adapter<ContactsCallLogAdapter.MyViewHolder> {

    private List<Contacts> contactsList;
    private Context mContext;

    public void setContactsList(List<Contacts> list) {
         clear();
        this.contactsList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear()
    {
        contactsList.clear();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, number, email,total_duration,date,dob;
        public AvatarImageView photo;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.user_name);
            number = (TextView) view.findViewById(R.id.user_number);
            email = (TextView) view.findViewById(R.id.user_email);
            total_duration = (TextView) view.findViewById(R.id.total_duration);
            date = (TextView) view.findViewById(R.id.date);
            photo = (AvatarImageView) view.findViewById(R.id.user_photo);
            dob = (TextView) view.findViewById(R.id.dob);
        }
    }


    public ContactsCallLogAdapter(Context context) {
        this.contactsList = new ArrayList<>();
        Log.d("Adapter size",""+contactsList.size());
        mContext =context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contacts contacts = contactsList.get(position);
        holder.name.setText(contacts.getName());
        holder.number.setText("Mobile - "+contacts.getNumber());

       if(Utils.checkNotEmpty(contacts.getDob())) {
            holder.dob.setVisibility(View.VISIBLE);
            holder.dob.setText("DOB - " + contacts.getDob());
        }else
            holder.dob.setVisibility(View.GONE);


        if(Utils.checkNotEmpty(contacts.getEmail())) {
            holder.email.setVisibility(View.VISIBLE);
            holder.email.setText("DOB - " + contacts.getEmail());
        }
        else
            holder.email.setVisibility(View.GONE);
        holder.total_duration.setText(Utils.timeConversion(contacts.getDuration()));
        holder.date.setText(contacts.getCallDate());

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
      // generate random color
        int color = generator.getColor(contacts.getName());
        holder.photo.setUser(contacts,color);
    /*    if(Utils.checkNotEmpty(contacts.getPhoto()))
        {
            Glide.with(mContext)
                    .load(contacts.getPhoto())
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.photo);

        }else
        {
            // make sure Glide doesn't load anything into this view until told otherwise
            Glide.clear(holder.photo);
            *//*TextDrawable drawable = TextDrawable.builder()
                    .buildRoundRect(Character.toString(contacts.getName().charAt(0)), Color.RED,10);

            holder.photo.setImageDrawable(drawable);*//*


        }*/


    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }
}