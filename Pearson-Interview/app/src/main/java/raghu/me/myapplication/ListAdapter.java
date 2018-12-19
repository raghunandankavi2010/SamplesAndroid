package raghu.me.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import raghu.me.myapplication.model.Users;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private List<Users> mList = new ArrayList<>();
    private OnClickListener onClickListener;
    public ListAdapter(Context context) {
        onClickListener = (OnClickListener) context;
    }

    public interface OnClickListener {
        public void onClick(Users user);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(mList.get(position).getName());
        holder.emailId.setText(mList.get(position).getEmail());
        holder.rootView.setTag(position);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (Integer) v.getTag();
                onClickListener.onClick(mList.get(pos));
            }
        });
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<Users> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name,emailId;
        private View rootView;
        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            emailId = v.findViewById(R.id.email);
            rootView = v.findViewById(R.id.rootLayout);


        }
    }


}
