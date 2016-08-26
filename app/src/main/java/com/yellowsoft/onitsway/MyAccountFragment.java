package com.yellowsoft.onitsway;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;

public class MyAccountFragment extends Fragment {
    String head;
    FragmentTouchListner mCallBack;
    LinearLayout logout,language,my_orders;
    MyTextView logout_tv,lang_tv,my_orders_tv;
    public interface FragmentTouchListner {
        public void text_back_butt(String header);
        public void lang();
        public void after_logout();
        public void my_orders_page();
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
        lang_tv=(MyTextView)v.findViewById(R.id.my_acc_lang_tv);
        lang_tv.setText(Settings.getword(getActivity(),"language"));
        logout_tv=(MyTextView)v.findViewById(R.id.my_acc_logout_tv);
        logout_tv.setText(Settings.getword(getActivity(),"logout"));
        my_orders_tv=(MyTextView)v.findViewById(R.id.my_acc_ord_tv);
        my_orders_tv.setText(Settings.getword(getActivity(),"my_orders"));
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.setUserid(getActivity(), "-1", "","");
                Log.e("abc",Settings.getUserid(getActivity()));
                mCallBack.after_logout();
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