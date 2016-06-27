package com.yellowsoft.onitsway;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyAccountFragment extends Fragment {
    String head;
    FragmentTouchListner mCallBack;
    LinearLayout logout,language,my_orders;
    TextView logout_tv,lang_tv,my_orders_tv;
    public interface FragmentTouchListner {
        public void text_back_butt(String header);
        public void lang();
        public void my_orders_page();
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
        return inflater.inflate(R.layout.myaccount_screen, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        head=String.valueOf("My Account");
        mCallBack.text_back_butt(head);
        logout=(LinearLayout)v.findViewById(R.id.my_acc_logout_ll);
        language=(LinearLayout)v.findViewById(R.id.my_acc_lang_ll);
        my_orders=(LinearLayout)v.findViewById(R.id.my_acc_ord_ll);
        lang_tv=(TextView)v.findViewById(R.id.my_acc_lang_tv);
        logout_tv=(TextView)v.findViewById(R.id.my_acc_logout_tv);
        my_orders_tv=(TextView)v.findViewById(R.id.my_acc_ord_tv);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.setUserid(getActivity(),"-1","");
            }
        });
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.lang();
            }
        });
        my_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.my_orders_page();
            }
        });
    }
}