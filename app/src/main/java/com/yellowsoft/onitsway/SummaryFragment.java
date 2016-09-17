package com.yellowsoft.onitsway;

/**
 * Created by Chinni on 05-04-2016.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class SummaryFragment extends Fragment {
    AllApis allApis=new AllApis();
    int price,cnt1=1,cnt2=1;
    String pay_type="0", temp="0",cash_pay="pick";
    AlertDialogManager alert = new AlertDialogManager();
    LinearLayout rating,cash_ll,knet_ll,submit_ll,free_status_ll,pay_pop,pick_ll,drop_ll;
    CompanyDetails companyDetails;
    TextView p_add1,p_add2,p_add3,p_add4, p_add5, p_add6,d_add1,d_add2,d_add3, d_add4, d_add5, d_add6,pick_price,drop_price,
            total_price,pay,order_summary,pick_up_address,drop_off_address,tot_cost,pick_cost,drop_cost,total_cost,cash,
            knet,title_tc,title_descrp,submit,free_tv;
    ImageView image,cash_img,knet_img,pick_img,drop_img;
    ViewFlipper viewFlipper;
    MyTextView sta_o_p_name,sta_o_p_area,sta_o_p_house,sta_o_p_block,sta_o_p_building,sta_o_p_street,
            sta_o_d_name,sta_o_d_area,sta_o_d_house,sta_o_d_block,sta_o_d_building,sta_o_d_street,pick_tv,drop_tv,cash_pop_heading;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void to_payment(String user_id,String price);
        public void setting_butt();
        public void text_back_butt(String head);
        public void invoice();
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
        return inflater.inflate(R.layout.summary_screen, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        String head=Settings.getword(getActivity(), "order_summary");
        mCallBack.text_back_butt(head);
        mCallBack.setting_butt();
        mCallBack.text_back_butt(head);
        Bundle args = getArguments();
        companyDetails = (CompanyDetails)args.getSerializable("company");
        Log.e("com_det", companyDetails.toString());
        free_status();
        viewFlipper=(ViewFlipper)v.findViewById(R.id.viewFlipper3);
        order_summary=(TextView)v.findViewById(R.id.order_summary);
        order_summary.setText(Settings.getword(getActivity(), "order_summary"));
        pick_up_address=(TextView)v.findViewById(R.id.tv_pick_add);
        pick_up_address.setText(Settings.getword(getActivity(), "pickup_address"));
        drop_off_address=(TextView)v.findViewById(R.id.tv_drop_add);
        drop_off_address.setText(Settings.getword(getActivity(),"drop_address"));
        tot_cost=(TextView)v.findViewById(R.id.totalcost);
        tot_cost.setText(Settings.getword(getActivity(),"total_cost"));
        pick_cost=(TextView)v.findViewById(R.id.pickup_cost);
        pick_cost.setText(Settings.getword(getActivity(),"pick_up_cost"));
        drop_cost=(TextView)v.findViewById(R.id.delivery_cost);
        drop_cost.setText(Settings.getword(getActivity(),"drop_off_cost"));
        total_cost=(TextView)v.findViewById(R.id.total);
        total_cost.setText(Settings.getword(getActivity(),"total_cost"));
        free_tv=(TextView)v.findViewById(R.id.free_tv);
        free_tv.setText(Settings.getword(getActivity(),"free_order"));
        pay=(TextView)v.findViewById(R.id.pay);
        pay.setText(Settings.getword(getActivity(),"pay"));
        cash=(TextView)v.findViewById(R.id.cash_tv);
        cash.setText(Settings.getword(getActivity(),"cash"));
        knet=(TextView)v.findViewById(R.id.knet_tv);
        knet.setText(Settings.getword(getActivity(),"knet"));

        sta_o_p_name = (MyTextView)v. findViewById(R.id.tv_pickup_add1_sta);
        sta_o_p_name.setText(Settings.getword(getActivity(), "full_name"));
        sta_o_p_area = (MyTextView)v.findViewById(R.id.tv_pickup_add2_sta);
        sta_o_p_area.setText(Settings.getword(getActivity(), "area"));
        sta_o_p_house = (MyTextView)v. findViewById(R.id.tv_pickup_add3_sta);
        sta_o_p_house.setText(Settings.getword(getActivity(), "house"));
        sta_o_p_block = (MyTextView)v.findViewById(R.id.tv_pickup_add4_sta);
        sta_o_p_block.setText(Settings.getword(getActivity(), "block"));
        sta_o_p_building = (MyTextView)v.findViewById(R.id.tv_pickup_add5_sta);
        sta_o_p_building.setText(Settings.getword(getActivity(), "building"));
        sta_o_p_street = (MyTextView)v.findViewById(R.id.tv_pickup_add6_sta);
        sta_o_p_street.setText(Settings.getword(getActivity(), "street_name"));

        sta_o_d_name = (MyTextView)v.findViewById(R.id.tv_drop_add1_sta);
        sta_o_d_name.setText(Settings.getword(getActivity(), "full_name"));
        sta_o_d_area = (MyTextView)v.findViewById(R.id.tv_drop_add2_sta);
        sta_o_d_area.setText(Settings.getword(getActivity(), "area"));
        sta_o_d_house = (MyTextView)v.findViewById(R.id.tv_drop_add3_sta);
        sta_o_d_house.setText(Settings.getword(getActivity(), "house"));
        sta_o_d_block = (MyTextView)v.findViewById(R.id.tv_drop_add4_sta);
        sta_o_d_block.setText(Settings.getword(getActivity(), "block"));
        sta_o_d_building = (MyTextView)v.findViewById(R.id.tv_drop_add5_sta);
        sta_o_d_building.setText(Settings.getword(getActivity(), "building"));
        sta_o_d_street = (MyTextView)v.findViewById(R.id.tv_drop_add6_sta);
        sta_o_d_street.setText(Settings.getword(getActivity(), "street_name"));

        p_add1=(MyTextView)v.findViewById(R.id.tv_pickup_add1);
        p_add2=(MyTextView)v.findViewById(R.id.tv_pickup_add2);
        p_add3=(MyTextView)v.findViewById(R.id.tv_pickup_add3);
        p_add4 = (MyTextView)v.findViewById(R.id.tv_pickup_add4);
        p_add5 = (MyTextView)v.findViewById(R.id.tv_pickup_add5);
        p_add6 = (MyTextView)v.findViewById(R.id.tv_pickup_add6);
        d_add1=(MyTextView)v.findViewById(R.id.tv_drop_add1);
        d_add2=(MyTextView)v.findViewById(R.id.tv_drop_add2);
        d_add3=(MyTextView)v.findViewById(R.id.tv_drop_add3);
        d_add4 = (MyTextView)v.findViewById(R.id.tv_drop_add4);
        d_add5 = (MyTextView)v.findViewById(R.id.tv_drop_add5);
        d_add6 = (MyTextView)v.findViewById(R.id.tv_drop_add6);

        pick_tv = (MyTextView)v.findViewById(R.id.pick_fin_tv);
        pick_tv.setText(Settings.getword(getActivity(), "pick_up"));
        drop_tv = (MyTextView)v.findViewById(R.id.drop_fin_tv);
        drop_tv.setText(Settings.getword(getActivity(), "drop_off"));

        cash_pop_heading = (MyTextView) v.findViewById(R.id.cod_pop_heading);
        cash_pop_heading.setText(Settings.getword(getActivity(),"when_will_you_pay"));

        pick_img=(ImageView)v.findViewById(R.id.pick_img);
        drop_img=(ImageView)v.findViewById(R.id.drop_img);
        pay_pop=(LinearLayout)v.findViewById(R.id.pay_pop);
        pay_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        pick_ll=(LinearLayout)v.findViewById(R.id.pick_fin_ll);
        pick_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick_img.setImageResource(R.drawable.pay_selected);
                drop_img.setImageResource(R.drawable.pay_selection);
                cash_pay="Pick up";
                cash.setText(Settings.getword(getActivity(),"cash")+"("+Settings.getword(getActivity(), "pick_up")+")");
                pay_pop.setVisibility(View.GONE);
                pick_tv.setTextColor(getResources().getColor(R.color.pink));
                drop_tv.setTextColor(getResources().getColor(R.color.login_signup_text));
            }
        });
        drop_ll=(LinearLayout)v.findViewById(R.id.drop_fin_ll);
        drop_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick_img.setImageResource(R.drawable.pay_selection);
                drop_img.setImageResource(R.drawable.pay_selected);
                cash_pay="Drop off";
                cash.setText(Settings.getword(getActivity(),"cash")+"("+Settings.getword(getActivity(), "drop_off")+")");
                pay_pop.setVisibility(View.GONE);
                drop_tv.setTextColor(getResources().getColor(R.color.pink));
                pick_tv.setTextColor(getResources().getColor(R.color.login_signup_text));
            }
        });
        free_status_ll=(LinearLayout)v.findViewById(R.id.free_status_ll);
        cash_img=(ImageView)v.findViewById(R.id.cash_img);
        knet_img=(ImageView)v.findViewById(R.id.knet_img);
        cash_ll=(LinearLayout)v.findViewById(R.id.cash_ll);
        knet_ll=(LinearLayout)v.findViewById(R.id.knet_ll);
        cash_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("type", pay_type);
                pay_type = "Cash";
                pay_pop.setVisibility(View.VISIBLE);
                cash_img.setImageResource(R.drawable.pay_selected);
                knet_img.setImageResource(R.drawable.pay_selection);
                cash.setTextColor(getResources().getColor(R.color.pink));
                knet.setTextColor(getResources().getColor(R.color.login_signup_text));
            }
        });

        knet_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drop_img.setImageResource(R.drawable.pay_selection);
                pick_img.setImageResource(R.drawable.pay_selection);
                Log.e("type", pay_type);
                pay_type = "K-net";
                cash_img.setImageResource(R.drawable.pay_selection);
                knet_img.setImageResource(R.drawable.pay_selected);
                cash.setTextColor(getResources().getColor(R.color.login_signup_text));
                knet.setTextColor(getResources().getColor(R.color.pink));
            }
        });
        title_tc = (TextView) v.findViewById(R.id.pay_tc_title);
        title_descrp = (TextView) v.findViewById(R.id.pay_tc_descrption);
        try {
            JSONObject jsonObject = new JSONObject(Settings.getSettings(getActivity()));
            title_tc.setText(Html.fromHtml(jsonObject.getJSONObject("termsconditions").getString("title" + Settings.get_lan(getActivity()))));
            title_descrp.setText(Html.fromHtml(jsonObject.getJSONObject("termsconditions").getString("description" + Settings.get_lan(getActivity()))));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        submit = (TextView) v.findViewById(R.id.pay_tc_submit_tv);
        submit.setText(Settings.getword(getActivity(), "submit"));

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (viewFlipper.getDisplayedChild() == 1) {
                        viewFlipper.setDisplayedChild(0);
                    } else
                        return false;
//                            viewFlipper.setDisplayedChild(0);
                    return true;
                }
                return false;
            }
        });

        rating=(LinearLayout)v.findViewById(R.id.rating_summary);
        Settings.set_rating(getActivity(), companyDetails.rating, rating);
        image=(ImageView)v.findViewById(R.id.order_logo);
        Picasso.with(getActivity()).load(companyDetails.logo).into(image);
        pick_price=(TextView)v.findViewById(R.id.pick_amount);
        drop_price=(TextView)v.findViewById(R.id.delivery_amount);
        total_price=(TextView)v.findViewById(R.id.total_amount);
        try {
            JSONObject jsonObject=new JSONObject(Settings.get_order_json(getActivity()));
            p_add1.setText(jsonObject.getString("pick_fname"));
            p_add2.setText(Settings.get_pickup_area_name(getActivity()));
            p_add3.setText(jsonObject.getString("pick_house"));
            p_add4.setText(jsonObject.getString("pick_block"));
            p_add5.setText(jsonObject.getString("pick_build"));
            p_add6.setText(jsonObject.getString("pick_street"));
            d_add1.setText(jsonObject.getString("drop_fname"));
            d_add2.setText(Settings.get_drop_off_area_name(getActivity()));
            d_add3.setText(jsonObject.getString("drop_house"));
            d_add4.setText(jsonObject.getString("drop_block"));
            d_add5.setText(jsonObject.getString("drop_building"));
            d_add6.setText(jsonObject.getString("drop_street"));
            if(Settings.get_type(getActivity()).equals("pick")) {
                total_price.setText(companyDetails.price_pickup + "KD");
                temp=companyDetails.price_pickup;
                jsonObject.put("total_price", companyDetails.price_pickup );
            }
            else{
                total_price.setText(companyDetails.price_drop_off + "KD");
                temp=companyDetails.price_drop_off;
                jsonObject.put("total_price", companyDetails.price_drop_off);
            }
//            pick_price.setText(companyDetails.price_pickup+"KD");
//            drop_price.setText(companyDetails.price_drop_off + "KD");
//            price=Float.parseFloat(companyDetails.price_pickup)+Float.parseFloat(companyDetails.price_drop_off);

            jsonObject.put("company_id", companyDetails.company_id);
            jsonObject.put("company_name", companyDetails.title1);
            jsonObject.put("pick_price",companyDetails.price_pickup);
            jsonObject.put("payment_method",pay_type);
            if(pay_type.equals("Cash"))
            {
                jsonObject.put("cash_at",cash_pay);
            //            jsonObject.put("pick_price",companyDetails.price_drop_off);
            }

            Settings.set_order_json(getActivity(),jsonObject.toString());
//            total_price.setText();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject=new JSONObject(Settings.get_order_json(getActivity()));
            Log.e("place_ordre",jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        submit_ll = (LinearLayout) v.findViewById(R.id.pay_tc_submit_ll);
        submit_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pay_type.equals("K-net")){
//                    mCallBack.to_payment(Settings.getUserid(getActivity()),String.valueOf(price));
                    Intent payment = new Intent(getActivity(),PaymentActivity.class);
                    payment.putExtra("cust_id", Settings.getUserid(getActivity()));
                    payment.putExtra("total_price", temp);
                    startActivityForResult(payment, 7);
                }else
                    allApis.place_order(getActivity(),mCallBack);
            }
        });
      pay.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              if(pay_type.equals("0")){
                  Toast.makeText(getActivity(),Settings.getword(getActivity(),"empty_payment"),Toast.LENGTH_SHORT).show();
              }else{
                  viewFlipper.setDisplayedChild(1);
              }

          }
      });
        free_status_ll.setVisibility(View.VISIBLE);
        free_tv.setVisibility(View.GONE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 7) {
            if (data.getStringExtra("msg").equals("OK")) {
//                Toast.makeText(NavigationActivity.this, "Payment success", Toast.LENGTH_SHORT).show();
                alert.showAlertDialog(getActivity(), "Info","Payment success", false);
                allApis.place_order(getActivity(),mCallBack);
            } else
//                Toast.makeText(NavigationActivity.this, Settings.getword(NavigationActivity.this, "pay_failed"), Toast.LENGTH_SHORT).show();
                alert.showAlertDialog(getActivity(), "Info",Settings.getword(getActivity(), "pay_failed"), false);
        }
    }
    public void free_status(){
            String url = Settings.SERVERURL+"free-delivery.php?member_id="+Settings.getUserid(getActivity());
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
                        String sta=jsonObject.getString("status");
                        if(sta.equals("Free")) {
                            String msg = jsonObject.getString("message");
//                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            alert.showAlertDialog(getActivity(), "Info",msg, false);
                            pay_type=sta;
                            free_status_ll.setVisibility(View.GONE);
                            free_tv.setVisibility(View.VISIBLE);
                        }
                        else {
                            String msg = jsonObject.getString("message");
//                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
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