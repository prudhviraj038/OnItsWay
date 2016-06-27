package com.yellowsoft.onitsway;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;


public class LoginSignupFragment extends Fragment {
    EditText et_fname, et_lname, et_phone, et_email, et_password,etl_email,etl_password;
    LinearLayout login_ll,signup_ll;
    TextView signup,login,fpassword,sign_up_tv,login_tv;
    ViewFlipper viewFlipper;
    AllApis allApis = new AllApis();
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void setting_butt();
        public void signup_to_login();
        public void after_login();
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
        viewFlipper=(ViewFlipper)view.findViewById(R.id.viewFlipper);
        login = (TextView) view.findViewById(R.id.login_main);
        signup = (TextView) view.findViewById(R.id.signup);
        login_tv = (TextView) view.findViewById(R.id.login_tv);
        sign_up_tv = (TextView) view.findViewById(R.id.register_tv);
        et_fname = (EditText) view.findViewById(R.id.firstname);
        et_lname = (EditText) view.findViewById(R.id.lastname);
        et_phone = (EditText) view.findViewById(R.id.phone);
        et_email = (EditText) view.findViewById(R.id.email_signup);
        et_password = (EditText) view.findViewById(R.id.pass_signup);
        login_ll = (LinearLayout)view.findViewById(R.id.login_ll);
        signup_ll = (LinearLayout) view.findViewById(R.id.register_ll);
        fpassword = (TextView) view.findViewById(R.id.forgot_pass);
        etl_email=(EditText)view.findViewById(R.id.email_login);
        etl_password=(EditText)view.findViewById(R.id.pass_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setDisplayedChild(0);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setDisplayedChild(1);

            }
        });
        signup_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        fpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        login_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                if (email.equals(""))
                    Toast.makeText(getActivity(), "Please Enter UserName", Toast.LENGTH_SHORT).show();
                else if (password.equals(""))
                    Toast.makeText(getActivity(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                else if (password.length() < 6)
                    Toast.makeText(getActivity(), "Password Lenth should be grether than 6 charcters", Toast.LENGTH_SHORT).show();
                else {
                    allApis.login(getActivity(), email, password, mCallBack);
                }
            }
        });
    }

    private void register() {
        final String fname = et_fname.getText().toString();
        final String lname = et_lname.getText().toString();
        final String phone = et_phone.getText().toString();
        final String email = et_email.getText().toString();
        final String password = et_password.getText().toString();

        if (fname.equals(""))
            Toast.makeText(getActivity(), "Please Enter UserName", Toast.LENGTH_SHORT).show();
        else if (lname.equals(""))
            Toast.makeText(getActivity(), "Please Enter Fullname", Toast.LENGTH_SHORT).show();
        else if (phone.equals(""))
            Toast.makeText(getActivity(), "Please Enter Mobile", Toast.LENGTH_SHORT).show();
        else if (email.equals(""))
            Toast.makeText(getActivity(), "Please Enter Email", Toast.LENGTH_SHORT).show();
        else if (password.equals(""))
            Toast.makeText(getActivity(), "Please Enter Password", Toast.LENGTH_SHORT).show();
        else if (password.length() < 6)
            Toast.makeText(getActivity(), "Password Lenth should be grether than 6 charcters", Toast.LENGTH_SHORT).show();
        else {
            allApis.signup(getActivity(),fname,lname,phone,email,password,mCallBack);

        }
    }

}