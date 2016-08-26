package com.yellowsoft.onitsway;

/**
 * Created by Chinni on 05-04-2016.
 */

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderStatusFragment extends Fragment {
    AllApis allApis=new AllApis();
    OrderStatusAdapter adapter;
    MyTextView no_ser;
    GridView gridView;
    Dialog alert1;
    double rate=0;
    int pos;
    int temp=0;
    ImageView i1,i2,i3,i4,i5;
    MyEditText comments;
    String write;
    ViewFlipper viewFlipper;
    ArrayList<OrderDetails> orders;
    OrderStatusAdapter orderStatusAdapter;
    LinearLayout cancel_ll,accept_ll,di_ll,rate_ll,submit,rate_pop,rating_ll;
    String ord_id;
    AlertDialogManager alert = new AlertDialogManager();
    MyTextView sta_o_summary,sta_o_id,sta_o_time,sta_p_time,sta_d_time,sta_o_status,sta_com_details,sta_p_add,sta_d_add,sta_total,sta_total_cost,
            o_id,o_time,o_status,o_p_time,o_d_time,p_add1,p_add2,p_add3,p_add4, p_add5, p_add6,d_add1,d_add2,d_add3, d_add4, d_add5, d_add6,com_name,cost,o_item,sta_o_item,cancel_tv,accept_tv,rate_tv;
    MyTextView sta_o_p_name,sta_o_p_area,sta_o_p_house,sta_o_p_block,sta_o_p_building,sta_o_p_street,
            sta_o_d_name,sta_o_d_area,sta_o_d_house,sta_o_d_block,sta_o_d_building,sta_o_d_street;
    MyTextView rateing_tv,write_comments_tv,submit_tv;
    ImageView logoo;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void back_butt();
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
        View rootview=inflater.inflate(R.layout.orders_status_screen, container, false);
        return rootview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View v = getView();
        get_orders();
        String head=Settings.getword(getActivity(), "my_orders");
        mCallBack.text_back_butt(head);
        orders= new ArrayList<>();
        no_ser = (MyTextView) v.findViewById(R.id.no_services);
        no_ser.setText(Settings.getword(getActivity(), "no_service"));
        mCallBack.back_butt();
        viewFlipper=(ViewFlipper)v.findViewById(R.id.viewFlipper2);
        orderStatusAdapter=new OrderStatusAdapter(getActivity(),orders);
        gridView=(GridView) v.findViewById(R.id.orders_status_list);
        gridView.setAdapter(orderStatusAdapter);

        comments=(MyEditText)v.findViewById(R.id.write_comm_et);
        comments.setHint(Settings.getword(getActivity(), "comments"));
        rateing_tv=(MyTextView)v.findViewById(R.id.rate_tv);
        rateing_tv.setText(Settings.getword(getActivity(), "rate"));
        write_comments_tv=(MyTextView)v.findViewById(R.id.comm_tv_alert);
        write_comments_tv.setText(Settings.getword(getActivity(), "comments"));
        submit_tv=(MyTextView)v.findViewById(R.id.alert_submit);
        submit_tv.setText(Settings.getword(getActivity(), "submit"));
        submit=(LinearLayout)v.findViewById(R.id.alert_submit_ll);
        rating_ll=(LinearLayout)v.findViewById(R.id.rating_alert_ll);
        rate_pop=(LinearLayout)v.findViewById(R.id.rate_pop);
        i1=(ImageView)v.findViewById(R.id.i1);
        i2=(ImageView)v.findViewById(R.id.i2);
        i3=(ImageView)v.findViewById(R.id.i3);
        i4=(ImageView)v.findViewById(R.id.i4);
        i5=(ImageView)v.findViewById(R.id.i5);

        sta_o_summary=(MyTextView)v.findViewById(R.id.sta_order_summary);
        sta_o_summary.setText(Settings.getword(getActivity(), "order_summary"));
        sta_o_id=(MyTextView)v.findViewById(R.id.sta_o_id_or);
        sta_o_id.setText(Settings.getword(getActivity(),"order_id"));
        sta_o_time=(MyTextView)v.findViewById(R.id.sta_o_time_or);
        sta_o_time.setText(Settings.getword(getActivity(),"order_time"));
        sta_o_item=(MyTextView)v.findViewById(R.id.sta_item_or);
        sta_o_item.setText(Settings.getword(getActivity(),"item_type"));
        sta_p_time=(MyTextView)v.findViewById(R.id.sta_o_p_time_or);
        sta_p_time.setText(Settings.getword(getActivity(),"time_pickup"));
        sta_d_time=(MyTextView)v.findViewById(R.id.sta_o_d_time_or);
        sta_d_time.setText(Settings.getword(getActivity(),"time_dropoff"));
        sta_o_status=(MyTextView)v.findViewById(R.id.sta_status_or);
        sta_o_status.setText(Settings.getword(getActivity(),"status"));
        sta_com_details=(MyTextView)v.findViewById(R.id.sta_com_details);
        sta_com_details.setText(Settings.getword(getActivity(),"title_company_details"));
        sta_p_add=(MyTextView)v.findViewById(R.id.sta_p_add_or);
        sta_p_add.setText(Settings.getword(getActivity(),"pickup_address"));
        sta_d_add=(MyTextView)v.findViewById(R.id.sta_d_add_or);
        sta_d_add.setText(Settings.getword(getActivity(),"drop_address"));
        sta_total=(MyTextView)v.findViewById(R.id.sta_total_or);
        sta_total.setText(Settings.getword(getActivity(),"total_cost"));
        sta_total_cost=(MyTextView)v.findViewById(R.id.sta_total_cost_or);
        sta_total_cost.setText(Settings.getword(getActivity(),"total_cost"));
        cancel_tv=(MyTextView)v.findViewById(R.id.order_cancel_tv);
        cancel_tv.setText(Settings.getword(getActivity(),"cancel"));
        accept_tv=(MyTextView)v.findViewById(R.id.order_accept_tv);
        accept_tv.setText(Settings.getword(getActivity(),"accepted"));
        rate_tv=(MyTextView)v.findViewById(R.id.order_rate_tv);
        rate_tv.setText(Settings.getword(getActivity(),"rate"));

        sta_o_p_name = (MyTextView)v. findViewById(R.id.p_add_or1_sta);
        sta_o_p_name.setText(Settings.getword(getActivity(), "full_name"));
        sta_o_p_area = (MyTextView)v.findViewById(R.id.p_add_or2_sta);
        sta_o_p_area.setText(Settings.getword(getActivity(), "area"));
        sta_o_p_house = (MyTextView)v. findViewById(R.id.p_add_or3_sta);
        sta_o_p_house.setText(Settings.getword(getActivity(), "house"));
        sta_o_p_block = (MyTextView)v.findViewById(R.id.p_add_or4_sta);
        sta_o_p_block.setText(Settings.getword(getActivity(), "block"));
        sta_o_p_building = (MyTextView)v.findViewById(R.id.p_add_or5_sta);
        sta_o_p_building.setText(Settings.getword(getActivity(), "building"));
        sta_o_p_street = (MyTextView)v.findViewById(R.id.p_add_or6_sta);
        sta_o_p_street.setText(Settings.getword(getActivity(), "street_name"));

        sta_o_d_name = (MyTextView)v.findViewById(R.id.d_add_or1_sta);
        sta_o_d_name.setText(Settings.getword(getActivity(), "full_name"));
        sta_o_d_area = (MyTextView)v.findViewById(R.id.d_add_or2_sta);
        sta_o_d_area.setText(Settings.getword(getActivity(), "area"));
        sta_o_d_house = (MyTextView)v.findViewById(R.id.d_add_or3_sta);
        sta_o_d_house.setText(Settings.getword(getActivity(), "house"));
        sta_o_d_block = (MyTextView)v.findViewById(R.id.d_add_or4_sta);
        sta_o_d_block.setText(Settings.getword(getActivity(), "block"));
        sta_o_d_building = (MyTextView)v.findViewById(R.id.d_add_or5_sta);
        sta_o_d_building.setText(Settings.getword(getActivity(), "building"));
        sta_o_d_street = (MyTextView)v.findViewById(R.id.d_add_or6_sta);
        sta_o_d_street.setText(Settings.getword(getActivity(), "street_name"));

        o_id=(MyTextView)v.findViewById(R.id.oo_id);
        o_time=(MyTextView)v.findViewById(R.id.oo_time);
        o_item=(MyTextView)v.findViewById(R.id.item_or);
        o_p_time=(MyTextView)v.findViewById(R.id.oo_p_time);
        o_d_time=(MyTextView)v.findViewById(R.id.oo_d_time);
        o_status=(MyTextView)v.findViewById(R.id.status_order);
        p_add1=(MyTextView)v.findViewById(R.id.p_add_or1);
        p_add2=(MyTextView)v.findViewById(R.id.p_add_or2);
        p_add3=(MyTextView)v.findViewById(R.id.p_add_or3);
        p_add4 = (MyTextView)v.findViewById(R.id.p_add_or4);
        p_add5 = (MyTextView)v.findViewById(R.id.p_add_or5);
        p_add6 = (MyTextView)v.findViewById(R.id.p_add_or6);
        d_add1=(MyTextView)v.findViewById(R.id.d_add_or1);
        d_add2=(MyTextView)v.findViewById(R.id.d_add_or2);
        d_add3=(MyTextView)v.findViewById(R.id.d_add_or3);
        d_add4 = (MyTextView)v.findViewById(R.id.d_add_or4);
        d_add5 = (MyTextView)v.findViewById(R.id.d_add_or5);
        d_add6 = (MyTextView)v.findViewById(R.id.d_add_or6);
        com_name=(MyTextView)v.findViewById(R.id.com_name_or);
        cost=(MyTextView)v.findViewById(R.id.total_order);

        logoo=(ImageView)v.findViewById(R.id.com_logo_or);
        di_ll=(LinearLayout)v.findViewById(R.id.di_ll);
        accept_ll=(LinearLayout)v.findViewById(R.id.order_accept_ll);
        accept_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_accepted(ord_id, "8");
            }
        });
        cancel_ll=(LinearLayout)v.findViewById(R.id.order_cancel_ll);
        cancel_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_cancel(ord_id);
            }
        });
        rate_ll=(LinearLayout)v.findViewById(R.id.order_rate_ll);
        rate_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_rating(getActivity(),"0",rating_ll);
                rate_pop.setVisibility(View.VISIBLE);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_rating(pos);

            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pos=i;
                viewFlipper.setDisplayedChild(1);
                String head = Settings.getword(getActivity(), "order_summary");
                mCallBack.text_back_butt(head);
                if (orders.get(i).status.equals("Pending")) {
                    cancel_ll.setVisibility(View.VISIBLE);
                    rate_ll.setVisibility(View.GONE);
                    accept_ll.setVisibility(View.GONE);
                    di_ll.setVisibility(View.GONE);
                } else if (orders.get(i).status.equals("Reshceduled")) {
                    cancel_ll.setVisibility(View.VISIBLE);
                    accept_ll.setVisibility(View.VISIBLE);
                    di_ll.setVisibility(View.VISIBLE);
                    rate_ll.setVisibility(View.GONE);
                } else if (orders.get(i).status.equals("Delivered")||orders.get(i).status.equals("Completed")) {
                    if(temp==0) {
                        rate_ll.setVisibility(View.VISIBLE);
                        cancel_ll.setVisibility(View.GONE);
                        accept_ll.setVisibility(View.GONE);
                        di_ll.setVisibility(View.GONE);
                    }else{
                        rate_ll.setVisibility(View.GONE);
                        cancel_ll.setVisibility(View.GONE);
                        accept_ll.setVisibility(View.GONE);
                        di_ll.setVisibility(View.GONE);
                    }
                } else {
                    cancel_ll.setVisibility(View.GONE);
                    accept_ll.setVisibility(View.GONE);
                    di_ll.setVisibility(View.GONE);
                    rate_ll.setVisibility(View.GONE);
                }
                ord_id = orders.get(i).order_id;
                o_id.setText(orders.get(i).order_id);
                o_time.setText(orders.get(i).order_time);
                o_item.setText(orders.get(i).get_item_name(getActivity()));
                o_p_time.setText(orders.get(i).p_date + "  " + orders.get(i).p_time);
                o_d_time.setText(orders.get(i).d_date + "  " + orders.get(i).d_time);
                o_status.setText(orders.get(i).status);
                p_add1.setText(orders.get(i).pick_fname);
                p_add2.setText(orders.get(i).get_p_area_name(getActivity()));
                p_add3.setText(orders.get(i).p_house);
                p_add4.setText(orders.get(i).p_block);
                p_add5.setText(orders.get(i).p_build);
                p_add6.setText(orders.get(i).p_street);
                d_add1.setText(orders.get(i).drop_fname);
                d_add2.setText(orders.get(i).get_d_area_name(getActivity()));
                d_add3.setText(orders.get(i).d_house);
                d_add4.setText(orders.get(i).d_block);
                d_add5.setText(orders.get(i).d_build);
                d_add6.setText(orders.get(i).d_street);
                com_name.setText(orders.get(i).get_com_name(getActivity()));
                cost.setText(orders.get(i).cost + " KD");
                Log.e("image", orders.get(i).logoo);
                Picasso.with(getActivity()).load(orders.get(i).logoo).into(logoo);
            }
        });
        get_back(v);
        mCallBack.text_back_butt(head);
    }

    public void get_back(View v){
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (viewFlipper.getDisplayedChild() == 1) {
                        String head=Settings.getword(getActivity(), "my_orders");
                        mCallBack.text_back_butt(head);
                        viewFlipper.setDisplayedChild(0);
                        return true;
                    }
                }
                return false;
            }
        });
    }
    String rating_user;
    public  void set_rating(final Context context,String value, final LinearLayout rating_ll){

        rating_ll.removeAllViews();
        for(float i=1;i<=5;i++) {
            ImageView star = new ImageView(context);
            star.setMaxWidth(50);
            star.setMaxHeight(50);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(2,0,2,0);
            star.setLayoutParams(lp);
            star.setAdjustViewBounds(true);
            final float finalI = i;
            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    set_rating(context,String.valueOf(finalI),rating_ll);
                    rating_user=String.valueOf(finalI);
                }
            });
            if(i<=Float.parseFloat(value))
                star.setImageResource(R.drawable.star_full);
            else if(i-Float.parseFloat(value)<1)
                star.setImageResource(R.drawable.star_half);
            else
                star.setImageResource(R.drawable.star_empty);
            rating_ll.addView(star);
        }
    }
    public  void send_rating(final int position){
        write=comments.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(), "please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = Settings.SERVERURL+"add-rating.php?";
        Log.e("ratingggg",rating_user);
        Log.e("review", write);
//        Log.e("id", orderses.get(position).id);
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
                        String address_id = jsonObject.getString("address_id");
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        rate_pop.setVisibility(View.GONE);
                        temp=1;


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
                params.put("order_id", orders.get(position).order_id);
                params.put("rating", rating_user);
                params.put("review",write);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    public void get_orders(){
        String url = null;
        try {
            url = Settings.SERVERURL + "invoice-json.php?"+"member_id="+ URLEncoder.encode(Settings.getUserid(getActivity()), "utf-8")+"&type=member";
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                progressDialog.dismiss();
                Log.e("orders response is: ", jsonArray.toString());
                try {
                    orders.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String order_id = jsonArray.getJSONObject(i).getString("id");
                        String order_time = jsonArray.getJSONObject(i).getString("order_placed");
                        String payment = jsonArray.getJSONObject(i).getString("payment");
                        String status = jsonArray.getJSONObject(i).getString("delivery");
                        JSONObject sub = jsonArray.getJSONObject(i).getJSONObject("content");
                        OrderDetails orderDetails = new OrderDetails(order_id,order_time,payment,status,sub);
                        orders.add(orderDetails);
                    }
                    orderStatusAdapter.notifyDataSetChanged();
                    if(orders.size()==0){
                        no_ser.setVisibility(View.VISIBLE);
                    }
                    else
                        no_ser.setVisibility(View.GONE);

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
    public void get_cancel(String id){
        String url = null;
        try {
            url = Settings.SERVERURL + "order-cancel.php?"+"invoice_id="+ URLEncoder.encode(id,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("response is", jsonObject.toString());
                progressDialog.dismiss();
                try {
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Failed")) {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(getActivity(), "Info", msg, false);
                    }
                    else {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(getActivity(), "Info",msg, false);
                        viewFlipper.setDisplayedChild(0);
                        get_orders();
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
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }
    public void get_accepted(String id, final String sta_id){
        String url = null;
        try {
            url = Settings.SERVERURL + "order-status.php?"+"invoice_id="+ URLEncoder.encode(id,"utf-8")+"&status="+ URLEncoder.encode(sta_id,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("response is", jsonObject.toString());
                progressDialog.dismiss();
                try {
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Failed")) {
                        String msg = jsonObject.getString("message");
//                        Toast.makeText(CompanyActvity.this, msg, Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(getActivity(), "Info",msg, false);
                    }
                    else {
                        String msg = jsonObject.getString("message");
//                        Toast.makeText(CompanyActvity.this, msg, Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(getActivity(), "Info", msg, false);
                            viewFlipper.setDisplayedChild(0);
                            get_orders();

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
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }
}