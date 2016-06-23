package com.yellowsoft.onitsway;


import android.app.Activity;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class HomeFragment extends Fragment {
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void setting_butt();
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
            public void onClick(View v) {
                String abc= Settings.getUserid(getActivity());
                if(abc.equals("-1")) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    LoginSignupFragment loginSignupFragment = new LoginSignupFragment();
                    fragmentManager.beginTransaction().replace(R.id.container_main, loginSignupFragment).addToBackStack(null).commit();
                }
                else{
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    CompaniesGridFragment companiesGridFragment = new CompaniesGridFragment();
                    fragmentManager.beginTransaction().replace(R.id.container_main, companiesGridFragment).addToBackStack(null).commit();
                }
            }
        });
        order_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String abc= Settings.getUserid(getActivity());
                if(abc.equals("-1")) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    LoginSignupFragment loginSignupFragment = new LoginSignupFragment();
                    fragmentManager.beginTransaction().replace(R.id.container_main, loginSignupFragment).addToBackStack(null).commit();
                }
                else{
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    OrderStatusFragment orderStatusFragment = new OrderStatusFragment();
                    fragmentManager.beginTransaction().replace(R.id.container_main, orderStatusFragment).addToBackStack(null).commit();
                }
            }
        });
        pickup_deliv_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.set_order_json(getActivity(),"-1");
                String abc= Settings.getUserid(getActivity());
                if(abc.equals("-1")) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    LoginSignupFragment loginSignupFragment = new LoginSignupFragment();
                    fragmentManager.beginTransaction().replace(R.id.container_main, loginSignupFragment).addToBackStack(null).commit();
                }
                else{
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    PickupDropoffAddressFragment pickupDropoffAddressFragment = new PickupDropoffAddressFragment();
                    fragmentManager.beginTransaction().replace(R.id.container_main, pickupDropoffAddressFragment).addToBackStack(null).commit();
                }
            }
        });
        dropoff_deli_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String abc= Settings.getUserid(getActivity());
                if(abc.equals("-1")) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    LoginSignupFragment loginSignupFragment = new LoginSignupFragment();
                    fragmentManager.beginTransaction().replace(R.id.container_main, loginSignupFragment).addToBackStack(null).commit();
                }
                else{
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    PickupDropoffAddressFragment pickupDropoffAddressFragment = new PickupDropoffAddressFragment();
                    fragmentManager.beginTransaction().replace(R.id.container_main, pickupDropoffAddressFragment).addToBackStack(null).commit();
                }
            }
        });
    }
}