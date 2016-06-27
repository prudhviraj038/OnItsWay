package com.yellowsoft.onitsway;

/**
 * Created by Chinni on 05-04-2016.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SettingsFragment extends Fragment {
    EditText n_pass,c_pass;
    Animation animation,animation2;
    LinearLayout change_pass,notification,name,submit,cancel,main_ll;
    TextView name_tv,change_pass_tv,notification_tv,title_change_pass,submit_tv,cancel_tv;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void setting_page_back();
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
        return inflater.inflate(R.layout.settings_screen, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        mCallBack.setting_page_back();
        name_tv=(TextView)view.findViewById(R.id.name_sett_tv);
        change_pass_tv=(TextView)view.findViewById(R.id.change_pass_tv);
        notification_tv=(TextView)view.findViewById(R.id.notification_tv);
        title_change_pass=(TextView)view.findViewById(R.id.title_c_pass);
        submit_tv=(TextView)view.findViewById(R.id.pass_submit);
        cancel_tv=(TextView)view.findViewById(R.id.pass_cancel_tv);
        name=(LinearLayout)view.findViewById(R.id.name_sett_ll);
        change_pass=(LinearLayout)view.findViewById(R.id.change_pass_ll);
        notification=(LinearLayout)view.findViewById(R.id.notification_ll);
        main_ll=(LinearLayout)view.findViewById(R.id.change_password_main);
        submit=(LinearLayout)view.findViewById(R.id.pass_submit_ll);
        cancel=(LinearLayout)view.findViewById(R.id.pass_cancel_ll);
        n_pass=(EditText)view.findViewById(R.id.n_pass);
        c_pass=(EditText)view.findViewById(R.id.c_pass);
        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                main_ll.setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_move);
                animation.reset();
                main_ll.setAnimation(animation);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_ll.setVisibility(View.GONE);
                animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_move2);
                animation2.reset();
                main_ll.setAnimation(animation2);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_password();
            }
        });
    }
    public  void change_password(){
        final String new_pass = n_pass.getText().toString();
        final String confirm_pass = c_pass.getText().toString();
        if (new_pass.length() < 6)
//            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "password_lenth"), false);
            Toast.makeText(getActivity(), Settings.getword(getActivity(), "password_lenth"), Toast.LENGTH_SHORT).show();
        else if (!new_pass.equals(confirm_pass)){
//            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_same_password"), false);
            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_same_password"), Toast.LENGTH_SHORT).show();
            c_pass.setText("");
        }else{
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
            progressDialog.show();
            progressDialog.setCancelable(false);
            String url = Settings.SERVERURL+"change-password.php?";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(progressDialog!=null)
                        progressDialog.dismiss();
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        String reply=jsonObject.getString("status");
                        if(reply.equals("Success")) {
                            String msg = jsonObject.getString("message");
                            Log.e("msg", msg);
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            main_ll.setVisibility(View.GONE);
                            n_pass.setText("");
                            c_pass.setText("");
                        }
                        else {
                            String msg=jsonObject.getString("message");
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if(progressDialog!=null)
                                progressDialog.dismiss();
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("member_id",Settings.getUserid(getActivity()));
                    params.put("password",new_pass);
                    params.put("cpassword",confirm_pass);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }
}