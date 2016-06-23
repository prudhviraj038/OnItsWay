package com.yellowsoft.onitsway;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


public class LoginSignupFragment extends Fragment {
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
        return inflater.inflate(R.layout.login_signup_screen, container, false);
}

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        mCallBack.setting_butt();
        final FragmentManager childFragMan = getChildFragmentManager();

        TextView login = (TextView) view.findViewById(R.id.login_main);
        TextView signup = (TextView) view.findViewById(R.id.signup);
        final FrameLayout log_container = (FrameLayout) view.findViewById(R.id.login_container);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment fragment = new LoginFragment();
                childFragMan.beginTransaction().add(R.id.login_container, fragment).commit();
            }
        });
        login.performClick();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment fragment = new SignUpFragment();
                childFragMan.beginTransaction().add(R.id.login_container, fragment).commit();

            }
        });

    }
}