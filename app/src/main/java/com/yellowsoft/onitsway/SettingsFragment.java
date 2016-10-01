package com.yellowsoft.onitsway;

/**
 * Created by Chinni on 05-04-2016.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SettingsFragment extends Fragment {
    MyEditText n_pass,c_pass;
    MyEditText et_fname, et_lname, et_phone, et_email, et_password;
    Animation animation,animation2;
    LinearLayout change_pass,notification,name,submit,cancel,main_ll,edit_ll,language;
    MyTextView name_tv,change_pass_tv,notification_tv,title_change_pass,submit_tv,cancel_tv,edit_tv,edit_title,lang_tv;
    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    String write,fname,lname,phone,email,password,first_name, last_name, mobile, email_id;
    ViewFlipper viewFlipper;
    String head;
    ProgressBar progressBar;
    AlertDialogManager alert = new AlertDialogManager();
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void setting_page_back(String head);
        public void lang();
        public void notification_page();
        public  Animation get_animation(Boolean enter);
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
        return inflater.inflate(R.layout.settings_screen, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        head=Settings.getword(getActivity(),"settings");
        mCallBack.setting_page_back(head);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar2);
        get_user_details();
        viewFlipper=(ViewFlipper)view.findViewById(R.id.viewFlipper4);
        language=(LinearLayout)view.findViewById(R.id.sett_lang_ll);
        lang_tv=(MyTextView)view.findViewById(R.id.sett_lang_tv);
        lang_tv.setText(Settings.getword(getActivity(),"change_language"));
        et_fname = (MyEditText) view.findViewById(R.id.edit_firstname);
        et_fname.setHint(Settings.getword(getActivity(), "first_name"));
        et_lname = (MyEditText) view.findViewById(R.id.edit_lastname);
        et_lname.setHint(Settings.getword(getActivity(), "last_name"));
        et_phone = (MyEditText) view.findViewById(R.id.edit_phone);
        et_phone.setHint(Settings.getword(getActivity(), "mobile_number"));
        et_email = (MyEditText) view.findViewById(R.id.edit_email);
        et_email.setHint(Settings.getword(getActivity(), "email_id"));
//        et_password = (MyEditText) view.findViewById(R.id.pass_signup);
//        et_password.setHint(Settings.getword(getActivity(), "password"));
        edit_title=(MyTextView)view.findViewById(R.id.edit_profile_tv);
        edit_title.setText(Settings.getword(getActivity(),"edit_profile"));
        edit_tv=(MyTextView)view.findViewById(R.id.edit_tv);
        edit_tv.setText(Settings.getword(getActivity(),"edit"));
        name_tv=(MyTextView)view.findViewById(R.id.name_sett_tv);
        change_pass_tv=(MyTextView)view.findViewById(R.id.change_pass_tv);
        change_pass_tv.setText(Settings.getword(getActivity(),"change_password"));
        notification_tv=(MyTextView)view.findViewById(R.id.notification_tv);
        notification_tv.setText(Settings.getword(getActivity(),"notifications"));
        title_change_pass=(MyTextView)view.findViewById(R.id.title_c_pass);
        title_change_pass.setText(Settings.getword(getActivity(),"change_password"));
        submit_tv=(MyTextView)view.findViewById(R.id.pass_submit);
        submit_tv.setText(Settings.getword(getActivity(),"submit"));
        cancel_tv=(MyTextView)view.findViewById(R.id.pass_cancel_tv);
        cancel_tv.setText(Settings.getword(getActivity(),"cancel"));
        name=(LinearLayout)view.findViewById(R.id.name_sett_ll);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    viewFlipper.setDisplayedChild(1);
            }
        });
        change_pass=(LinearLayout)view.findViewById(R.id.change_pass_ll);
        notification=(LinearLayout)view.findViewById(R.id.notification_ll);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mCallBack.notification_page();
            }
        });
        main_ll=(LinearLayout)view.findViewById(R.id.change_password_main);
        submit=(LinearLayout)view.findViewById(R.id.pass_submit_ll);
        cancel=(LinearLayout)view.findViewById(R.id.pass_cancel_ll);
        n_pass=(MyEditText)view.findViewById(R.id.n_pass);
        n_pass.setHint(Settings.getword(getActivity(),"new_password"));
        c_pass=(MyEditText)view.findViewById(R.id.c_pass);
        c_pass.setHint(Settings.getword(getActivity(),"confirm_password"));
        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_ll.setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_move);
                animation.reset();
                main_ll.setAnimation(animation);
            }

        });
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.lang();
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
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (viewFlipper.getDisplayedChild() == 1) {
                        viewFlipper.setDisplayedChild(0);
                        return true;
                    }
                }
                return false;
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_password();
            }
        });
        edit_ll=(LinearLayout)view.findViewById(R.id.edit_profile_ll);
        edit_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fname = et_fname.getText().toString();
                lname = et_lname.getText().toString();
                phone = et_phone.getText().toString();
//                email = et_email.getText().toString();
//                password = et_password.getText().toString();

                if (fname.equals(""))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "empty_fname"), Toast.LENGTH_SHORT).show();
                alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_fname"), false);
                else if (lname.equals(""))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "empty_lname"), Toast.LENGTH_SHORT).show();
                alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_lname"), false);
                else if (phone.equals(""))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "empty_mobile"), Toast.LENGTH_SHORT).show();
                alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_mobile"), false);
//                else if (email.equals(""))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "empty_email"), Toast.LENGTH_SHORT).show();
//                else if (!email.matches(emailPattern))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "valid_email_id"), Toast.LENGTH_SHORT).show();
//                else if (password.equals(""))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "empty_password"), Toast.LENGTH_SHORT).show();
//                else if (password.length() < 5)
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "password_lenth"), Toast.LENGTH_SHORT).show();
                else {
                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    String url = Settings.SERVERURL+"edit-member.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(progressDialog!=null)
                                        progressDialog.dismiss();
                                    try {
                                        JSONObject jsonObject=new JSONObject(response);
                                        String reply=jsonObject.getString("status");
                                        if(reply.equals("Failed")) {
                                            String msg = jsonObject.getString("message");
                                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

                                        }
                                        else {
//                                            String mem_id=jsonObject.getString("member_id");
                                            String msg = jsonObject.getString("message");
//                                String name=jsonObject.getString("name");
                                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                            viewFlipper.setDisplayedChild(0);
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
                            params.put("fname",fname);
                            params.put("lname",lname);
                            params.put("phone",phone);
//                            params.put("password",password);
                            return params;
                        }
                    };
                    AppController.getInstance().addToRequestQueue(stringRequest);
                }

            }
        });
    }
    public  void change_password(){
        final String new_pass = n_pass.getText().toString();
        final String confirm_pass = c_pass.getText().toString();
        if (new_pass.length() < 5)
            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "password_lenth"), false);
//            Toast.makeText(getActivity(), Settings.getword(getActivity(), "password_lenth"), Toast.LENGTH_SHORT).show();
        else if (!new_pass.equals(confirm_pass)){
            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_same_password"), false);
//            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_same_password"), Toast.LENGTH_SHORT).show();
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
                            alert.showAlertDialog(getActivity(), "Info",msg, false);
                            main_ll.setVisibility(View.GONE);
                            n_pass.setText("");
                            c_pass.setText("");
                        }
                        else {
                            String msg=jsonObject.getString("message");
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            alert.showAlertDialog(getActivity(), "Info", msg, false);

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
    public void  get_user_details(){
        String url = Settings.SERVERURL+"members-list.php?member_id="+Settings.getUserid(getActivity());
        Log.e("url--->", url);
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
//        progressDialog.show();
//        progressDialog.setCancelable(false);
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
//                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                Log.e("response is: ", jsonArray.toString());
                try {
                    first_name=jsonArray.getJSONObject(0).getString("fname");
                    last_name=jsonArray.getJSONObject(0).getString("lname");
                    mobile=jsonArray.getJSONObject(0).getString("phone");
                    email_id=jsonArray.getJSONObject(0).getString("email");

                    name_tv.setText(first_name+" "+last_name);
                    et_fname.setText(first_name);
                    et_lname.setText(last_name);
                    et_phone.setText(mobile);
                    et_email.setText(email_id);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
}