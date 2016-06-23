package com.yellowsoft.onitsway;

/**
 * Created by Chinni on 05-04-2016.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {
    EditText et_fname, et_lname, et_phone, et_email, et_password;
    TextView signup;
    AllApis allApis = new AllApis();
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void signup_to_login();
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
        return inflater.inflate(R.layout.signup_screen, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        signup = (TextView) view.findViewById(R.id.register);
        et_fname = (EditText) view.findViewById(R.id.firstname);
        et_lname = (EditText) view.findViewById(R.id.lastname);
        et_phone = (EditText) view.findViewById(R.id.phone);
        et_email = (EditText) view.findViewById(R.id.email_signup);
        et_password = (EditText) view.findViewById(R.id.pass_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
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