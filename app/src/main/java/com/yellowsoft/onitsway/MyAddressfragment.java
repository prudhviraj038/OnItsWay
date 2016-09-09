package com.yellowsoft.onitsway;


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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAddressfragment extends Fragment {
    String head,adds_id,a_id="";
    FragmentTouchListner mCallBack;
    boolean loaded=false;
    ImageView close;
    AlertDialogManager alert = new AlertDialogManager();
    MyTextView et_my_area, my_alias, my_area, my_block, my_street, my_building, my_floor, my_flat, my_mobile, update,
            delete_tv,edit_email, edit_tv, cp_submit_tv, add_new_address_tv,sta_area_tv,name;
    MyEditText et_my_alias, et_my_block, et_my_street, et_my_building, et_my_floor, et_my_flat, et_my_mobile,et_my_name;
    LinearLayout my_order_ll, my_address_ll, edit_address, change_pass, settings, logout, edit, cp_submit, add_new_address_ll,
            update_ll,delete, area_list_my_acc, language,area_list_ll,area_pop;
    ViewFlipper viewFlipper;
    AreaAdapter personAdapter;
    ArrayList<Area> area_list;
    ListView my_address_list;
    ArrayList<Area> address_list;
    ArrayList<String> address_id;
    ArrayList<String> address_title;
    ArrayList<String> area_id;
    ArrayList<String> area_title;
    AreaAdapter ad;
    boolean isadd = false;
    ProgressBar progressBar;
    MyAccountAddressAdapter myAccountAddressAdapter;
    public interface FragmentTouchListner {
        public void text_back_butt(String header);
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
                    + " must implement Listneddr");
        }
    }
//    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        return mCallBack.get_animation(enter,loaded);
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_address_page, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        head=String.valueOf("my_address");
        mCallBack.text_back_butt(head);
//        loaded=true;
        address_list = new ArrayList<>();
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar4);
        get_address_list();
        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewFlipper8);
        viewFlipper.setDisplayedChild(0);
        area_id= new ArrayList<String>();
        area_title=new ArrayList<String>();
        address_id=new ArrayList<>();
        address_title=new ArrayList<>();
        get_pick_area();
        my_alias = (MyTextView) view.findViewById(R.id.sta_my_alias_tv);
        my_alias.setText(Settings.getword(getActivity(), "empty_address_title"));
        my_area = (MyTextView) view.findViewById(R.id.sta_my_area_tv);
        my_area.setText(Settings.getword(getActivity(), "area"));
        name = (MyTextView) view.findViewById(R.id.sta_my_name_tv);
        name.setText(Settings.getword(getActivity(), "full_name"));
        my_block = (MyTextView) view.findViewById(R.id.sta_my_block_tv);
        my_block.setText(Settings.getword(getActivity(), "block"));
        my_street = (MyTextView) view.findViewById(R.id.sta_my_street_tv);
        my_street.setText(Settings.getword(getActivity(), "street_name"));
        my_building = (MyTextView) view.findViewById(R.id.sta_my_building_tv);
        my_building.setText(Settings.getword(getActivity(), "building"));
        my_floor = (MyTextView) view.findViewById(R.id.sta_my_floor_tv);
//        my_floor.setText(Settings.getword(getActivity(), "floor"));
        my_flat = (MyTextView) view.findViewById(R.id.sta_my_flat_tv);
        my_flat.setText(Settings.getword(getActivity(), "house"));
        my_mobile = (MyTextView) view.findViewById(R.id.sta_my_mobile_tv);
        my_mobile.setText(Settings.getword(getActivity(), "mobile_number"));
        update = (MyTextView) view.findViewById(R.id.update_tv);
        update.setText(Settings.getword(getActivity(), "update"));
        delete_tv = (MyTextView) view.findViewById(R.id.delete_my_address);
        delete_tv.setText(Settings.getword(getActivity(), "delete_address"));
        et_my_alias = (MyEditText) view.findViewById(R.id.my_alias_et);
        et_my_name = (MyEditText) view.findViewById(R.id.my_name_et);
        et_my_area = (MyTextView) view.findViewById(R.id.my_area_et);
        et_my_block = (MyEditText) view.findViewById(R.id.my_block_et);
        et_my_street = (MyEditText) view.findViewById(R.id.my_street_et);
        et_my_building = (MyEditText) view.findViewById(R.id.my_buiding_et);
        et_my_floor = (MyEditText) view.findViewById(R.id.my_floor_et);
        et_my_flat = (MyEditText) view.findViewById(R.id.my_flat_et);
        et_my_mobile = (MyEditText) view.findViewById(R.id.my_mobile_et);
        area_list_my_acc = (LinearLayout) view.findViewById(R.id.area_list_my_acc);
        update_ll = (LinearLayout) view.findViewById(R.id.update_ll);
        delete = (LinearLayout) view.findViewById(R.id.delete_my_address_ll);
//            area_list_ll = (LinearLayout) view.findViewById(R.id.area_list_ll);
        add_new_address_ll = (LinearLayout) view.findViewById(R.id.add_new_address_ll);
        add_new_address_tv = (MyTextView) view.findViewById(R.id.add_new_address_tv);
        add_new_address_tv.setText(Settings.getword(getActivity(), "add_address"));
        my_address_list = (ListView) view.findViewById(R.id.my_address_list);
        myAccountAddressAdapter = new MyAccountAddressAdapter(getActivity(), address_list);
        my_address_list.setAdapter(myAccountAddressAdapter);
        my_address_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewFlipper.setDisplayedChild(1);
                isadd = false;
                delete.setVisibility(View.VISIBLE);
                adds_id = address_list.get(i).id;
                et_my_alias.setText(address_list.get(i).title);
                et_my_name.setText(address_list.get(i).name);
                et_my_area.setText(address_list.get(i).get_area_title(getActivity()));
                et_my_block.setText(address_list.get(i).block);
                et_my_street.setText(address_list.get(i).street);
                et_my_building.setText(address_list.get(i).building);
//                et_my_floor.setText(address_list.get(i).floor);
                et_my_flat.setText(address_list.get(i).flat);
                et_my_mobile.setText(address_list.get(i).phone);

            }
        });

        add_new_address_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.setDisplayedChild(1);
                isadd = true;
                delete.setVisibility(View.GONE);
                update.setText(Settings.getword(getActivity(), "add"));
                et_my_alias.setText("");
                et_my_name.setText("");
                et_my_area.setText(Settings.getword(getActivity(), "area"));
                et_my_block.setText("");
                et_my_street.setText("");
                et_my_building.setText("");
//                et_my_floor.setText("");
                et_my_flat.setText("");
                et_my_mobile.setText("");

            }
        });
        sta_area_tv = (MyTextView) view.findViewById(R.id.sta_my_addre_se);
        sta_area_tv.setText(Settings.getword(getActivity(), "area"));
        close=(ImageView)view.findViewById(R.id.close_address);
        area_pop=(LinearLayout)view.findViewById(R.id.area_address_pop);
        area_list = new ArrayList<>();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                area_pop.setVisibility(View.GONE);
            }
        });
        ListView area_listView = (ListView) view.findViewById(R.id.a_a_lv1);
        ad = new AreaAdapter(getActivity(), area_title);
        area_listView.setAdapter(ad);
        area_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                area_pop.setVisibility(View.GONE);
                a_id=area_id.get(position);
                et_my_area.setText(area_title.get(position));
            }
        });
        update_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isadd) {
                    if (et_my_alias.getText().toString().equals(""))
                        Toast.makeText(getActivity(), "Please enter address name", Toast.LENGTH_SHORT).show();
                    else if (et_my_name.getText().toString().equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_area"), Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_name"), false);
                    else if (et_my_area.getText().toString().equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_area"), Toast.LENGTH_SHORT).show();
                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_area"), false);
                    else if (et_my_block.getText().toString().equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_block"), Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_block"), false);
                    else if (et_my_street.getText().toString().equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_street"), Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_street"), false);
                    else if (et_my_building.getText().toString().equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_building"), Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_building"), false);
                    else if (et_my_flat.getText().toString().equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_flat"), Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_house"), false);
                    else if (et_my_mobile.getText().toString().equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_floor"), Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_mobile"), false);
                    else {
                        add_address("0");
                    }
                } else {
                     if (et_my_name.getText().toString().equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_area"), Toast.LENGTH_SHORT).show();
                         alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_name"), false);
                     else if (et_my_area.getText().toString().equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_area"), Toast.LENGTH_SHORT).show();
                         alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_area"), false);
                     else if (et_my_block.getText().toString().equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_block"), Toast.LENGTH_SHORT).show();
                         alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_block"), false);
                     else if (et_my_street.getText().toString().equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_street"), Toast.LENGTH_SHORT).show();
                         alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_street"), false);
                     else if (et_my_building.getText().toString().equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_building"), Toast.LENGTH_SHORT).show();
                         alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_building"), false);
                     else if (et_my_flat.getText().toString().equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_flat"), Toast.LENGTH_SHORT).show();
                         alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_house"), false);
                     else if (et_my_mobile.getText().toString().equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_floor"), Toast.LENGTH_SHORT).show();
                         alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_mobile"), false);
                    else {
                        add_address(adds_id);
                    }
                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_address();
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
                    } else if (viewFlipper.getDisplayedChild() == 2) {
                        viewFlipper.setDisplayedChild(0);
                        return true;
                    } else {
                        return false;
                    }
                }

                return false;
            }
    });
        area_list_my_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_pick_area();
                area_pop.setVisibility(View.VISIBLE);
//                viewFlipper.setDisplayedChild(2);
//                    mCallBack.area_list("-1");
            }
        });
    }
    private void get_address_list(){
        String url=Settings.SERVERURL+"addresses.php?"+"member_id="+Settings.getUserid(getActivity())+ "&area=1";
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
        progressDialog.setCancelable(false);
//        progressDialog.show();
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                Log.e("response is: ", jsonArray.toString());
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject sub = jsonArray.getJSONObject(i);
                        String area_name = sub.getString("title");
                        String ar_id = sub.getString("id");
                        address_id.add(ar_id);
                        address_title.add(area_name);
                        Area area=new Area(sub);
                        address_list.add(area);
                    }
                    myAccountAddressAdapter.notifyDataSetChanged();
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
                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }
    private void add_address(final String id){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = Settings.SERVERURL+"add-address.php";
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
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        get_address_list();
                        viewFlipper.setDisplayedChild(0);
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
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("member_id", Settings.getUserid(getActivity()));
                params.put("address_id", adds_id);
                params.put("title", et_my_alias.getText().toString());
                params.put("name", et_my_name.getText().toString());
                params.put("area", a_id);
                params.put("block", et_my_block.getText().toString());
                params.put("building", et_my_building.getText().toString());
                params.put("street", et_my_street.getText().toString());
                params.put("house", et_my_flat.getText().toString());
                params.put("phone", et_my_mobile.getText().toString());
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    public void  delete_address(){
        String url = Settings.SERVERURL+"del-address.php?address_id="+adds_id+"&member_id="+Settings.getUserid(getActivity());
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
                    if(reply.equals("Success")) {
                        String msg = jsonObject.getString("message");
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        get_address_list();
                        viewFlipper.setDisplayedChild(0);
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
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
    private void get_pick_area(){
        String url=Settings.SERVERURL+"locations-json.php";
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(), "please_wait"));
        progressDialog.setCancelable(false);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("locations");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject sub = jsonArray.getJSONObject(i);
                        String area_name = sub.getString("title" + Settings.get_lan(getActivity()));
                        String ar_id = sub.getString("id");
                        area_id.add(ar_id);
                        area_title.add(area_name);
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
