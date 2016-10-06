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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
    MyTextView orderss,couriers,pick_up,drop_off,pro_tv;
    LinearLayout pro_pop,ok;
    ImageView pro_img;
    String image,id,title;
    String type;
    int temp=0;
    int t=0;
    AlertDialogManager alert = new AlertDialogManager();
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void home();
        public void setting_butt();
        public void pick_drop(String type);
        public void courier_order(String type);
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
        return inflater.inflate(R.layout.home_screen, container, false);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        mCallBack.home();
        Bundle args = getArguments();
        type = (String)args.getSerializable("type");
        Log.e("type",type);
//        if(temp==0) {
//            get_promo();
//        }
        if(type.equals("1")&&t==0){
            get_promo();
        }
        Settings.set_order_json(getActivity(), "-1");
        pro_pop=(LinearLayout)view.findViewById(R.id.pro_pop);
        ok=(LinearLayout)view.findViewById(R.id.ok_ll);
        pro_tv=(MyTextView)view.findViewById(R.id.pro_tv_home);

        pro_img=(ImageView)view.findViewById(R.id.pro_img_home);
        Picasso.with(getActivity()).load(image).into(pro_img);
        orderss=(MyTextView)view.findViewById(R.id.orders);
        orderss.setText(Settings.getword(getActivity(), "orders"));
        couriers=(MyTextView)view.findViewById(R.id.courier);
        couriers.setText(Settings.getword(getActivity(),"couriers"));
        drop_off=(MyTextView)view.findViewById(R.id.drop_off_home);
        drop_off.setText(Settings.getword(getActivity(),"drop_delivery"));
        pick_up=(MyTextView)view.findViewById(R.id.pick_up_home);
        pick_up.setText(Settings.getword(getActivity(),"pickup_delivery"));
        LinearLayout order_ll = (LinearLayout) view.findViewById(R.id.orders_lll);
        LinearLayout courier_ll = (LinearLayout) view.findViewById(R.id.courier_lll);
        LinearLayout pickup_deliv_ll = (LinearLayout) view.findViewById(R.id.pickup_deliveryy);
        LinearLayout dropoff_deli_ll = (LinearLayout) view.findViewById(R.id.dropoff_deliveryy);
        courier_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCallBack.courier_order("courier");
            }
        });
        order_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCallBack.courier_order("order");
            }
        });

        pickup_deliv_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Settings.set_type(getActivity(),"pick");
                Settings.set_order_json(getActivity(),"-1");
                mCallBack.pick_drop("pick");
            }
        });
        dropoff_deli_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Settings.set_type(getActivity(), "drop");
//                Settings.set_order_json(getActivity(), "-1");
                mCallBack.pick_drop("drop");
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro_pop.setVisibility(View.GONE);
            }
        });
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
                        alert.showAlertDialog(getActivity(), "Info", msg, false);
//                        pay_type=sta;
//                        free_status_ll.setVisibility(View.GONE);

                    }
                    else {
                        String msg = jsonObject.getString("message");
//                        pro_pop.setVisibility(View.GONE);
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
    public void get_promo(){
        temp=1;t=1;
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

                        pro_pop.setVisibility(View.VISIBLE);

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