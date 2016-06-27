package com.yellowsoft.onitsway;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class HomeFragment extends Fragment {
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void setting_butt();
        public void pick_drop(String type);
        public void courier_order(String type);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBack = (NavigationActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Listner");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_screen, container, false);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        mCallBack.setting_butt();
        LinearLayout order_ll = (LinearLayout) view.findViewById(R.id.orders_ll);
        LinearLayout courier_ll = (LinearLayout) view.findViewById(R.id.courier_ll);
        LinearLayout pickup_deliv_ll = (LinearLayout) view.findViewById(R.id.pickup_delivery);
        LinearLayout dropoff_deli_ll = (LinearLayout) view.findViewById(R.id.dropoff_delivery);
        courier_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {mCallBack.courier_order("courier");
            }
        });
        order_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.courier_order("order");
            }
        });
        pickup_deliv_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.set_order_json(getActivity(),"-1");
                mCallBack.pick_drop("pick");
            }
        });
        dropoff_deli_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.pick_drop("drop");
            }
        });
    }
}