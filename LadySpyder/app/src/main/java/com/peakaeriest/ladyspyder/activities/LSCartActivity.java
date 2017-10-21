package com.peakaeriest.ladyspyder.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.peakaeriest.ladyspyder.R;
import com.peakaeriest.ladyspyder.models.KDCartPojo;

import java.util.ArrayList;

public class LSCartActivity extends LSBaseActivity {
    private RecyclerView recyclerView;
    private KDCartAdapter mAdapter;
    Button btn_checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lscart);

        updateFragmentTitle("My Cart", true, false);
        btn_checkout = (Button) findViewById(R.id.btn_checkout);

        recyclerView = (RecyclerView) findViewById(R.id.rvChallenge);
        getLocalArray();
        recyclerView.setLayoutManager(new LinearLayoutManager(LSCartActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new LSCartActivity.RecyclerTouchListener(LSCartActivity.this, recyclerView, new LSCartActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LSCartActivity.this, LSAddAddressActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getLocalArray() {

        final ArrayList<KDCartPojo> examples1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            KDCartPojo example = new KDCartPojo();
            example.setProductName("Medjool Dates Large Sizet" + i);
            example.setHalfKg("125 KG" + i);
            example.setCarMake("KA05M2837" + i);
            example.setCarimage(i);
            example.setPriceDetails("Rs 0.12 " + i);
            examples1.add(example);
        }

        mAdapter = new KDCartAdapter(LSCartActivity.this, examples1);
        recyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_album, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private LSCartActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final LSCartActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(LSCartActivity.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {


        }
    }


    public class KDCartAdapter extends RecyclerView.Adapter<KDCartAdapter.MyViewHolder> {

        public ArrayList<KDCartPojo> mVehicleArrayList;
        public Context mContext;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tvName, tvCarName, tvCarnumber;
            ImageView ivProfile;

            public MyViewHolder(View view) {
                super(view);
                tvCarName = (TextView) view.findViewById(R.id.tv_vechicle_name);
                tvName = (TextView) view.findViewById(R.id.tv_tenant_name);
                tvCarnumber = (TextView) view.findViewById(R.id.tv_quantity);
                ivProfile = (ImageView) view.findViewById(R.id.profile_image);
            }
        }

        /**
         * Vechicle adapter constrcutor
         *
         * @param mContext
         * @param moviesList
         */
        public KDCartAdapter(Context mContext, ArrayList<KDCartPojo> moviesList) {
            this.mVehicleArrayList = moviesList;
            this.mContext = mContext;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cart_adapter, parent, false);

            return new MyViewHolder(itemView);
        }

        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            try {
                KDCartPojo tenantObject = mVehicleArrayList.get(position);
                holder.tvCarName.setText(tenantObject.getCarname());
                holder.tvCarnumber.setText(tenantObject.getCarNUmber());
                holder.tvName.setText(tenantObject.getTenantname());
                String valueImage = "aaaa" + tenantObject.getCarimage();
                int drawableResourceId = mContext.getResources().getIdentifier(valueImage, "drawable", mContext.getPackageName());
             /*   Glide.with(mContext).load(drawableResourceId).into(holder.ivProfile);
*/
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return
                    mVehicleArrayList.size();
        }

    }

}
