package com.yellowsoft.onitsway;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginSignupFragment extends Fragment {
    MyEditText et_fname, et_lname, et_phone, et_email, et_password,etl_email,etl_password,et_re_password;
    LinearLayout login_ll,signup_ll,submit_ll,bar_ll;
    MyTextView signup,login,fpassword,sign_up_tv,login_tv,title_tc,title_descrp,submit,pro_tv;
    ViewFlipper viewFlipper;
    String write,fname,lname,phone,email,password,re_password;
    ImageView logo_login,pro_img;
    LinearLayout pro_pop,ok;
    CheckBox checkBox;
    String image,id,title;
    AllApis allApis = new AllApis();
    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void setting_butt();
        public void signup_to_login();
        public void after_login();
        public void com_login();
        public void text_back_butt(String head);
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
        return inflater.inflate(R.layout.login_signup_screen, container, false);
}

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        mCallBack.setting_butt();
        String head=Settings.getword(getActivity(), "login");
        mCallBack.text_back_butt(head);
        get_promo();
        pro_pop=(LinearLayout)view.findViewById(R.id.pro_pop_login);
        ok=(LinearLayout)view.findViewById(R.id.ok_ll_login);
        pro_tv=(MyTextView)view.findViewById(R.id.pro_tv_home_login);
        pro_img=(ImageView)view.findViewById(R.id.pro_img_home_login);
        viewFlipper=(ViewFlipper)view.findViewById(R.id.viewFlipper);
        login = (MyTextView) view.findViewById(R.id.login_main);
        login.setText(Settings.getword(getActivity(), "login"));
        signup = (MyTextView) view.findViewById(R.id.signup);
        signup.setText(Settings.getword(getActivity(),"signup"));
        login_tv = (MyTextView) view.findViewById(R.id.login_tv);
        login_tv.setText(Settings.getword(getActivity(),"login"));
        sign_up_tv = (MyTextView) view.findViewById(R.id.register_tv);
        sign_up_tv.setText(Settings.getword(getActivity(),"register"));
        et_fname = (MyEditText) view.findViewById(R.id.firstname);
        et_fname.setHint(Settings.getword(getActivity(), "first_name"));
        et_lname = (MyEditText) view.findViewById(R.id.lastname);
        et_lname.setHint(Settings.getword(getActivity(), "last_name"));
        et_phone = (MyEditText) view.findViewById(R.id.phone);
        et_phone.setHint(Settings.getword(getActivity(), "mobile_number"));
        et_email = (MyEditText) view.findViewById(R.id.email_signup);
        et_email.setHint(Settings.getword(getActivity(), "email_id"));
        et_password = (MyEditText) view.findViewById(R.id.pass_signup);
        et_password.setHint(Settings.getword(getActivity(), "password"));
        et_re_password = (MyEditText) view.findViewById(R.id.re_pass_signup);
        et_re_password.setHint(Settings.getword(getActivity(), "confirm_password"));
        login_ll = (LinearLayout)view.findViewById(R.id.login_ll);
        signup_ll = (LinearLayout) view.findViewById(R.id.register_ll);
        fpassword = (MyTextView) view.findViewById(R.id.forgot_pass);
        fpassword.setText(Settings.getword(getActivity(),"forgot_password"));
        etl_email=(MyEditText)view.findViewById(R.id.email_login);
        etl_email.setHint(Settings.getword(getActivity(), "email_id"));
        etl_password=(MyEditText)view.findViewById(R.id.pass_login);
        etl_password.setHint(Settings.getword(getActivity(), "password"));
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
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro_pop.setVisibility(View.GONE);
            }
        });
        title_tc = (MyTextView) view.findViewById(R.id.title_tc_signup);
        title_descrp = (MyTextView) view.findViewById(R.id.title_descr_signup);
        try {
            JSONObject jsonObject = new JSONObject(Settings.getSettings(getActivity()));
            title_tc.setText(Html.fromHtml(jsonObject.getJSONObject("termsconditions").getString("title" + Settings.get_lan(getActivity()))));
            title_descrp.setText(Html.fromHtml(jsonObject.getJSONObject("termsconditions").getString("description" + Settings.get_lan(getActivity()))));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        logo_login=(ImageView)view.findViewById(R.id.login_logo);
        bar_ll = (LinearLayout) view.findViewById(R.id.login_bar_ll);
        submit = (MyTextView) view.findViewById(R.id.tc_submit);
        submit.setText(Settings.getword(getActivity(),"submit"));
        submit_ll = (LinearLayout) view.findViewById(R.id.tc_submit_ll);
        submit_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allApis.signup(getActivity(),fname,lname,phone,email,password,mCallBack);
            }
        });

        fpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle(Settings.getword(getActivity(), "forgot_password_sent"));
                final EditText input = new EditText(getActivity());
                input.setMinLines(5);
                input.setVerticalScrollBarEnabled(true);
                input.setPadding(10, 10, 10, 10);
                alert.setView(input);
                alert.setPositiveButton(Settings.getword(getActivity(), "submit"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        write = input.getText().toString();
                        if (write.equals(""))
                            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_email"), Toast.LENGTH_SHORT).show();
                        else if (!write.matches(emailPattern))
                            Toast.makeText(getActivity(), Settings.getword(getActivity(), "valid_email_id"), Toast.LENGTH_SHORT).show();
                        else {
                            forgot_pass();
                        }
                    }
                });
                alert.show();
            }
        });
        login_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emaill = etl_email.getText().toString();
                String passwordd = etl_password.getText().toString();
                Log.e("email",et_email.getText().toString());
                String type="member";
                if (emaill.equals(""))
                    Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_email"), Toast.LENGTH_SHORT).show();
                else if (!emaill.matches(emailPattern))
                    Toast.makeText(getActivity(),Settings.getword(getActivity(), "valid_email_id"), Toast.LENGTH_SHORT).show();
                else if (passwordd.equals(""))
                    Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_password"), Toast.LENGTH_SHORT).show();
                else if (passwordd.length() < 5)
                    Toast.makeText(getActivity(), Settings.getword(getActivity(),"password_lenth"), Toast.LENGTH_SHORT).show();
                else {

                    allApis.login(getActivity(), emaill, passwordd, mCallBack);
                }
            }
        });
        if(viewFlipper.getDisplayedChild()==2){
            logo_login.setVisibility(View.GONE);
            bar_ll.setVisibility(View.GONE);
        }else {
            logo_login.setVisibility(View.VISIBLE);
            bar_ll.setVisibility(View.VISIBLE);
        }


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (viewFlipper.getDisplayedChild() == 2) {
                        viewFlipper.setDisplayedChild(1);
                        logo_login.setVisibility(View.VISIBLE);
                        bar_ll.setVisibility(View.VISIBLE);
                    } else if (viewFlipper.getDisplayedChild() == 1) {
                        viewFlipper.setDisplayedChild(0);
                    } else
                        return false;
//                            viewFlipper.setDisplayedChild(0);
                    return true;
                }
                return false;
            }
        });
    }
    public void get_promo1(){
        pro_pop.setVisibility(View.VISIBLE);
    }

    public void get_promo(){

        String url = Settings.SERVERURL+"promotions.php";
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonArray.toString());
                try {

                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    id=jsonObject.getString("id");
                    title=jsonObject.getString("title"+Settings.get_lan(getActivity()));
                    image=jsonObject.getString("image");
                    pro_tv.setText(title);
                    Picasso.with(getActivity()).load(image).into(pro_img);
//                    free_status();



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
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }

    public  void reg() {viewFlipper.setDisplayedChild(1);}
    private void register() {
         fname = et_fname.getText().toString();
         lname = et_lname.getText().toString();
         phone = et_phone.getText().toString();
         email = et_email.getText().toString();
        password = et_password.getText().toString();
       re_password = et_re_password.getText().toString();

        if (fname.equals(""))
            Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_fname"), Toast.LENGTH_SHORT).show();
        else if (lname.equals(""))
            Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_lname"), Toast.LENGTH_SHORT).show();
        else if (phone.equals(""))
            Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_mobile"), Toast.LENGTH_SHORT).show();
        else if (email.equals(""))
            Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_email"), Toast.LENGTH_SHORT).show();
        else if (!email.matches(emailPattern))
            Toast.makeText(getActivity(),Settings.getword(getActivity(), "valid_email_id"), Toast.LENGTH_SHORT).show();
        else if (password.equals(""))
            Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_password"), Toast.LENGTH_SHORT).show();
        else if (password.length() < 5)
            Toast.makeText(getActivity(), Settings.getword(getActivity(),"password_lenth"), Toast.LENGTH_SHORT).show();
        else if (!re_password.equals(password))
            Toast.makeText(getActivity(), Settings.getword(getActivity(),"same_password"), Toast.LENGTH_SHORT).show();
        else {
            viewFlipper.setDisplayedChild(2);
            logo_login.setVisibility(View.GONE);
            bar_ll.setVisibility(View.GONE);
        }
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
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
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