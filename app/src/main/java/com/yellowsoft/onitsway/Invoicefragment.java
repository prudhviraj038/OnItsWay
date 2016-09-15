package com.yellowsoft.onitsway;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class Invoicefragment extends Fragment {
    MyTextView sta_o_summary,sta_o_id,sta_o_time,sta_p_time,sta_d_time,sta_o_status,sta_com_details,sta_p_add,sta_d_add,sta_total,sta_total_cost,
            o_id,o_time,o_status,o_p_time,o_d_time,p_add1,p_add2,p_add3,p_add4,p_add5,p_add6,d_add1,d_add2,d_add3,d_add4,d_add5,d_add6,com_name,cost,sta_o_item,o_item;
    ImageView logo;
    MyTextView sta_o_p_name,sta_o_p_area,sta_o_p_house,sta_o_p_block,sta_o_p_building,sta_o_p_street,
            sta_o_d_name,sta_o_d_area,sta_o_d_house,sta_o_d_block,sta_o_d_building,sta_o_d_street,thank_message,back_to_home;
    String head;
    LinearLayout back_to_home_ll;
    OrderDetails orders;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void text_back_invoice(String header);
        public void back_home();
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
        return inflater.inflate(R.layout.invoice_screen, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        get_orders();
        head=Settings.getword(getActivity(),"invoice");
        mCallBack.text_back_invoice(head);
        back_to_home_ll=(LinearLayout)v.findViewById(R.id.back_to_home_ll);
        back_to_home=(MyTextView)v.findViewById(R.id.back_to_home);
        back_to_home.setText(Settings.getword(getActivity(), "back_to_home"));
        thank_message=(MyTextView)v.findViewById(R.id.thank_message);
        thank_message.setText(Settings.getword(getActivity(), "thank_message"));
        sta_o_summary=(MyTextView)v.findViewById(R.id.invoice_sta_order_summary);
        sta_o_summary.setText(Settings.getword(getActivity(), "order_summary"));
        sta_o_id=(MyTextView)v.findViewById(R.id.sta_o_id);
        sta_o_id.setText(Settings.getword(getActivity(),"order_id"));
        sta_o_time=(MyTextView)v.findViewById(R.id.sta_o_time);
        sta_o_time.setText(Settings.getword(getActivity(),"order_time"));
        sta_o_item=(MyTextView)v.findViewById(R.id.sta_item_in);
        sta_o_item.setText(Settings.getword(getActivity(),"item_type"));
        sta_p_time=(MyTextView)v.findViewById(R.id.sta_o_p_time);
        sta_p_time.setText(Settings.getword(getActivity(),"time_pickup"));
        sta_d_time=(MyTextView)v.findViewById(R.id.sta_o_d_time);
        sta_d_time.setText(Settings.getword(getActivity(),"time_dropoff"));
        sta_o_status=(MyTextView)v.findViewById(R.id.sta_status);
        sta_o_status.setText(Settings.getword(getActivity(),"status"));
        sta_com_details=(MyTextView)v.findViewById(R.id.invoice_sta_com_details);
        sta_com_details.setText(Settings.getword(getActivity(),"title_company_details"));
        sta_p_add=(MyTextView)v.findViewById(R.id.sta_p_add_invoice);
        sta_p_add.setText(Settings.getword(getActivity(),"pickup_address"));
        sta_d_add=(MyTextView)v.findViewById(R.id.sta_d_add_invoice);
        sta_d_add.setText(Settings.getword(getActivity(),"drop_address"));
        sta_total=(MyTextView)v.findViewById(R.id.sta_total_invoice);
        sta_total.setText(Settings.getword(getActivity(),"total_cost"));
        sta_total_cost=(MyTextView)v.findViewById(R.id.sta_total_cost_invoice);
        sta_total_cost.setText(Settings.getword(getActivity(),"total_cost"));


        sta_o_p_name = (MyTextView)v. findViewById(R.id.sta_p_add_invoice1);
        sta_o_p_name.setText(Settings.getword(getActivity(), "full_name"));
        sta_o_p_area = (MyTextView)v.findViewById(R.id.sta_p_add_invoice2);
        sta_o_p_area.setText(Settings.getword(getActivity(), "area"));
        sta_o_p_house = (MyTextView)v. findViewById(R.id.sta_p_add_invoice3);
        sta_o_p_house.setText(Settings.getword(getActivity(), "house"));
        sta_o_p_block = (MyTextView)v.findViewById(R.id.sta_p_add_invoice4);
        sta_o_p_block.setText(Settings.getword(getActivity(), "block"));
        sta_o_p_building = (MyTextView)v.findViewById(R.id.sta_p_add_invoice5);
        sta_o_p_building.setText(Settings.getword(getActivity(), "building"));
        sta_o_p_street = (MyTextView)v.findViewById(R.id.sta_p_add_invoice6);
        sta_o_p_street.setText(Settings.getword(getActivity(), "street_name"));

        sta_o_d_name = (MyTextView)v.findViewById(R.id.sta_d_add_invoice1);
        sta_o_d_name.setText(Settings.getword(getActivity(), "full_name"));
        sta_o_d_area = (MyTextView)v.findViewById(R.id.sta_d_add_invoice2);
        sta_o_d_area.setText(Settings.getword(getActivity(), "area"));
        sta_o_d_house = (MyTextView)v.findViewById(R.id.sta_d_add_invoice3);
        sta_o_d_house.setText(Settings.getword(getActivity(), "house"));
        sta_o_d_block = (MyTextView)v.findViewById(R.id.sta_d_add_invoice4);
        sta_o_d_block.setText(Settings.getword(getActivity(), "block"));
        sta_o_d_building = (MyTextView)v.findViewById(R.id.sta_d_add_invoice5);
        sta_o_d_building.setText(Settings.getword(getActivity(), "building"));
        sta_o_d_street = (MyTextView)v.findViewById(R.id.sta_d_add_invoice6);
        sta_o_d_street.setText(Settings.getword(getActivity(), "street_name"));


        o_id=(MyTextView)v.findViewById(R.id.o_id);
        o_time=(MyTextView)v.findViewById(R.id.o_time);
        o_item=(MyTextView)v.findViewById(R.id.item_in);
        o_p_time=(MyTextView)v.findViewById(R.id.o_p_time);
        o_d_time=(MyTextView)v.findViewById(R.id.o_d_time);
        o_status=(MyTextView)v.findViewById(R.id.status_invoice);
        p_add1=(MyTextView)v.findViewById(R.id.p_add_invoice1);
        p_add2=(MyTextView)v.findViewById(R.id.p_add_invoice2);
        p_add3=(MyTextView)v.findViewById(R.id.p_add_invoice3);
        p_add4=(MyTextView)v.findViewById(R.id.p_add_invoice4);
        p_add5=(MyTextView)v.findViewById(R.id.p_add_invoice5);
        p_add6=(MyTextView)v.findViewById(R.id.p_add_invoice6);
        d_add1=(MyTextView)v.findViewById(R.id.d_add_invoice1);
        d_add2=(MyTextView)v.findViewById(R.id.d_add_invoice2);
        d_add3=(MyTextView)v.findViewById(R.id.d_add_invoice3);
        d_add4=(MyTextView)v.findViewById(R.id.d_add_invoice4);
        d_add5=(MyTextView)v.findViewById(R.id.d_add_invoice5);
        d_add6=(MyTextView)v.findViewById(R.id.d_add_invoice6);
        com_name=(MyTextView)v.findViewById(R.id.com_name_invoice);
        cost=(MyTextView)v.findViewById(R.id.total_invoice);
        logo=(ImageView)v.findViewById(R.id.com_logo_invoice);
        back_to_home_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.back_home();
            }
        });

    }
    public void get_orders(){
        String url = null;
        try {
            url = Settings.SERVERURL + "invoice-json.php?"+"member_id="+ URLEncoder.encode(Settings.getUserid(getActivity()), "utf-8")+"&type=member&order_id="+URLEncoder.encode(Settings.getOrderid(getActivity()));
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
                   
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String order_id = jsonArray.getJSONObject(i).getString("id");
                        String order_time = jsonArray.getJSONObject(i).getString("order_placed");
                        String payment = jsonArray.getJSONObject(i).getString("payment");
                        String status = jsonArray.getJSONObject(i).getString("delivery");
                        JSONObject sub = jsonArray.getJSONObject(i).getJSONObject("content");
                        orders = new OrderDetails(order_id,order_time,payment,status,sub);
                        
                    }
                    o_id.setText(orders.order_id);
                    o_time.setText(orders.order_time);
                    o_item.setText(orders.get_item_name(getActivity()));
                    o_p_time.setText(orders.p_date + "  " + orders.p_time);
                    o_d_time.setText(orders.d_date + "  " + orders.d_time);
                    o_status.setText(orders.status);
                    p_add1.setText(orders.pick_fname);
                    p_add2.setText(orders.get_p_area_name(getActivity()));
                    p_add3.setText(orders.p_house);
                    p_add4.setText(orders.p_block);
                    p_add5.setText(orders.p_build);
                    p_add6.setText(orders.p_street);
                    d_add1.setText(orders.drop_fname);
                    d_add2.setText(orders.get_d_area_name(getActivity()));
                    d_add3.setText(orders.d_house);
                    d_add4.setText(orders.d_block);
                    d_add5.setText(orders.d_build);
                    d_add6.setText(orders.d_street);
                    com_name.setText(orders.get_com_name(getActivity()));
                    cost.setText(orders.cost + " KD");
                    Log.e("image", orders.logoo);
                    Picasso.with(getActivity()).load(orders.logoo).into(logo);
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