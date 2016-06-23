package com.yellowsoft.onitsway;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LoginFragment extends Fragment {
    EditText et_email,et_password;
    AllApis allApis=new AllApis();
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
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
        return inflater.inflate(R.layout.login_screen, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        TextView login = (TextView)view.findViewById(R.id.login);
        TextView fpassword = (TextView) view.findViewById(R.id.forgot_pass);
        et_email=(EditText)view.findViewById(R.id.email_login);
        et_password=(EditText)view.findViewById(R.id.pass_login);

        login.setOnClickListener(new View.OnClickListener() {
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
                    allApis.login(getActivity(),email,password,mCallBack);
                }
            }
        });


        fpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}