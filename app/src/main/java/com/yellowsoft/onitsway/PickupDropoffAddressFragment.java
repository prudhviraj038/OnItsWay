package com.yellowsoft.onitsway;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


public class PickupDropoffAddressFragment extends Fragment implements PickUpFragment.FragmentTouchListner,
        DropOffFragment.FragmentTouchListner {
    TextView d_address,p_address;
    FragmentTouchListner mCallBack;
    String type;
    public interface FragmentTouchListner {
        public void pick_drop_bar();
        public void after_drop_off();
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
        return inflater.inflate(R.layout.pickup_dropoff_screen, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        final FragmentManager childFragMan = getChildFragmentManager();
        Bundle args = getArguments();
        type = (String)args.getSerializable("type");
        mCallBack.pick_drop_bar();
         p_address = (TextView) view.findViewById(R.id.pickup);
         d_address = (TextView) view.findViewById(R.id.dropoff);
        final FrameLayout pick_container = (FrameLayout) view.findViewById(R.id.pickup_container);
        Log.e("type", type);

        p_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickUpFragment fragment = new PickUpFragment();
                childFragMan.beginTransaction().replace(R.id.pickup_container, fragment).commit();
            }
        });
//        p_address.performClick();
        d_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DropOffFragment fragment = new DropOffFragment();
                fragment.context=getActivity();
                childFragMan.beginTransaction().replace(R.id.pickup_container, fragment).commit();

            }
        });
        if (type.equals("drop"))
            d_address.performClick();
        else
            p_address.performClick();
    }
    @Override
    public void  from_drop_off(){
        mCallBack.after_drop_off();
    }
    @Override
    public void select_drop_off() {
        d_address.performClick();
    }
}
