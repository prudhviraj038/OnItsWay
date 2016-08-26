package com.yellowsoft.onitsway;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class CompanyLoginFragment extends Fragment {
    MyEditText et_fname, et_lname, et_phone, et_email, et_password,etl_email,etl_password;
    LinearLayout login_ll,signup_ll,submit_ll,bar_ll;
    MyTextView signup,login,fpassword,sign_up_tv,login_tv,title_tc,title_descrp,submit;
    String write,fname,lname,phone,email,password;
    ImageView logo_login;
    AlertDialogManager alert = new AlertDialogManager();
    CheckBox checkBox;
    AllApis allApis = new AllApis();
    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void setting_butt();
        public void com_login();
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
        return inflater.inflate(R.layout.company_login_screen, container, false);
}

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        mCallBack.setting_butt();
        login = (MyTextView) view.findViewById(R.id.login_mainn);
        login.setText(Settings.getword(getActivity(), "login"));
        login_tv = (MyTextView) view.findViewById(R.id.com_login_tv);
        login_tv.setText(Settings.getword(getActivity(),"login"));
        et_email = (MyEditText) view.findViewById(R.id.com_email_login);
        et_email.setHint(Settings.getword(getActivity(), "user_name"));
        et_password = (MyEditText) view.findViewById(R.id.com_pass_login);
        et_password.setHint(Settings.getword(getActivity(), "password"));
        login_ll = (LinearLayout)view.findViewById(R.id.com_login_ll);
        fpassword = (MyTextView) view.findViewById(R.id.forgot_passs);
        fpassword.setText(Settings.getword(getActivity(), "forgot_password"));
        fpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert1 = new AlertDialog.Builder(getActivity());
                alert1.setTitle(Settings.getword(getActivity(), "forgot_password_sent"));
                final EditText input = new EditText(getActivity());
                input.setMinLines(5);
                input.setVerticalScrollBarEnabled(true);
                input.setPadding(10, 10, 10, 10);
                alert1.setView(input);
                alert1.setPositiveButton(Settings.getword(getActivity(), "submit"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        write = input.getText().toString();
                        if (write.equals(""))
//                            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_email"), Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_email"), false);
                        else if (!write.matches(emailPattern))
//                            Toast.makeText(getActivity(), Settings.getword(getActivity(), "valid_email_id"), Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "valid_email_id"), false);
                        else {
                            forgot_pass();
                        }
                    }
                });
                alert1.show();
            }
        });
        login_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emaill = et_email.getText().toString();
                String passwordd = et_password.getText().toString();
                Log.e("email",et_email.getText().toString());
                String type="member";
                if (emaill.equals(""))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_user_name"), Toast.LENGTH_SHORT).show();
                alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_user_name"), false);
//                else if (!emaill.matches(emailPattern))
//                    Toast.makeText(getActivity(),Settings.getword(getActivity(), "valid_email_id"), Toast.LENGTH_SHORT).show();
                else if (passwordd.equals(""))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_password"), Toast.LENGTH_SHORT).show();
                alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_password"), false);
                else if (passwordd.length() < 5)
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(),"password_lenth"), Toast.LENGTH_SHORT).show();
                alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "password_lenth"), false);
                else {
                    String url = Settings.SERVERURL+"login.php?";
                    try {
                        url = url +"type=admin&email="+ URLEncoder.encode(emaill, "utf-8")+
                                "&password="+URLEncoder.encode(passwordd,"utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Log.e("url--->", url);
                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Please wait....");
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            progressDialog.dismiss();
                            Log.e("response is: ", jsonObject.toString());
                            try {
                                String reply=jsonObject.getString("status");
                                if(reply.equals("Failure")) {
                                    String msg = jsonObject.getString("message");
//                                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                    alert.showAlertDialog(getActivity(), "Info",msg, false);
                                }
                                else {
                                    String mem_id=jsonObject.getString("member_id");
                                    String name=jsonObject.getString("name");
                                    Settings.setcomUserid(getActivity(), mem_id, name);
                                    Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
//                                    alert.showAlertDialog(getActivity(), "Info",name, false);
//                        update_gcm(mem_id, context);
                                    mCallBack.com_login();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.e("response is:", error.toString());
                            Toast.makeText(getActivity(), "Server not connected", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    });

// Access the RequestQueue through your singleton class.
                    AppController.getInstance().addToRequestQueue(jsObjRequest);

                }
            }

        });
    }




    public void forgot_pass(){
        String url = Settings.SERVERURL+"forget-password.php?email="+write;
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                try {
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Failed")) {
                        String msg = jsonObject.getString("message");
//                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(getActivity(), "Info",msg, false);
                    }
                    else {
                        String msg = jsonObject.getString("message");
//                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(getActivity(), "Info",msg, false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(getActivity(),Settings.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }

}