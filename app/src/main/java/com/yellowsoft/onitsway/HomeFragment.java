package com.yellowsoft.onitsway;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;

public class HomeFragment extends Fragment {
    MyTextView orderss,couriers,pick_up,drop_off;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void home();
        public void setting_butt();
        public void pick_drop(String type);
        public void courier_order(String type);
        public Animation get_animation(Boolean enter);
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
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return mCallBack.get_animation(enter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_screen, container, false);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        mCallBack.home();
        orderss=(MyTextView)view.findViewById(R.id.orders);
        orderss.setText(Settings.getword(getActivity(), "orders"));
        couriers=(MyTextView)view.findViewById(R.id.courier);
        couriers.setText(Settings.getword(getActivity(),"couriers"));
        drop_off=(MyTextView)view.findViewById(R.id.drop_off_home);
        drop_off.setText(Settings.getword(getActivity(),"drop_delivery"));
        pick_up=(MyTextView)view.findViewById(R.id.pick_up_home);
        pick_up.setText(Settings.getword(getActivity(),"pickup_delivery"));
        LinearLayout order_ll = (LinearLayout) view.findViewById(R.id.orders_lll);
        LinearLayout courier_ll = (LinearLayout) view.findViewById(R.id.courier_lll);
        LinearLayout pickup_deliv_ll = (LinearLayout) view.findViewById(R.id.pickup_deliveryy);
        LinearLayout dropoff_deli_ll = (LinearLayout) view.findViewById(R.id.dropoff_deliveryy);
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
                Settings.set_type(getActivity(),"pick");
                Settings.set_order_json(getActivity(),"-1");
                mCallBack.pick_drop("pick");
            }
        });
        dropoff_deli_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.set_type(getActivity(),"drop");
//                Settings.set_order_json(getActivity(), "-1");
                mCallBack.pick_drop("drop");
            }
        });
    }
}