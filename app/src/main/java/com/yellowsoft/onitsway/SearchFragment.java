package com.yellowsoft.onitsway;

/**
 * Created by Chinni on 05-04-2016.
 */
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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    LinearLayout pick_area,drop_area,item;
    String pick_areas_id="0",drop_areas_id="0",items_id="0";
    ArrayList<String> pick_area_id;
    ArrayList<String> pick_area_title;
    ArrayList<String> drop_area_id;
    ArrayList<String> drop_area_title;
    ArrayList<String> item_id;
    ArrayList<String> item_title;
    TextView pick_area_tv,drop_area_tv,item_tv;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void search_back_butt();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_companies_screen, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        mCallBack.search_back_butt();
        pick_area_id= new ArrayList<String>();
        pick_area_title=new ArrayList<String>();
        drop_area_id= new ArrayList<String>();
        drop_area_title=new ArrayList<String>();
        item_id= new ArrayList<String>();
        item_title=new ArrayList<String>();
        item=(LinearLayout)v.findViewById(R.id.item_type);
        drop_area = (LinearLayout)v.findViewById(R.id.pickup_submit);
        pick_area=(LinearLayout)v.findViewById(R.id.pickup_area);
        pick_area_tv=(TextView)v.findViewById(R.id.pickup_area_tv);
        drop_area_tv=(TextView)v.findViewById(R.id.pickup_area_tv);
        item_tv=(TextView)v.findViewById(R.id.item_tv);

        pick_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("CHOOSE PICK UP AREA");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, pick_area_title);
                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(ChooseSubjectActivity.this, sub_title.get(which), Toast.LENGTH_SHORT).show();
                        drop_areas_id = pick_area_id.get(which);
                        drop_area_tv.setText(pick_area_title.get(which));

                    }
                });

                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        get_pick_area();

        drop_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("CHOOSE DROP OFF AREA");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, drop_area_title);
                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(ChooseSubjectActivity.this, sub_title.get(which), Toast.LENGTH_SHORT).show();
                        pick_areas_id = drop_area_id.get(which);
                        pick_area_tv.setText(drop_area_title.get(which));

                    }
                });

                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        get_drop_area();


        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("CHOOSE ITEM");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, item_title);
                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(ChooseSubjectActivity.this, sub_title.get(which), Toast.LENGTH_SHORT).show();
                        items_id = item_id.get(which);
                        item_tv.setText(item_title.get(which));

                    }
                });

                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        get_item();
    }
    private void get_pick_area(){
        String url=Settings.SERVERURL+"locations-json.php";
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait....");
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
                        String area_name = sub.getString("title"+Settings.get_lan(getActivity()));
                        String ar_id = sub.getString("id");
                        pick_area_id.add(ar_id);
                        pick_area_title.add(area_name);
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
    private void get_drop_area(){
        String url=Settings.SERVERURL+"locations-json.php";
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait....");
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
                        String area_name = sub.getString("title"+Settings.get_lan(getActivity()));
                        String ar_id = sub.getString("id");
                        drop_area_id.add(ar_id);
                        drop_area_title.add(area_name);
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
    private void get_item(){
        String url=Settings.SERVERURL+"items-json.php";
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject sub = jsonArray.getJSONObject(i);
                        String item_name = sub.getString("title"+Settings.get_lan(getActivity()));
                        String itm_id = sub.getString("id");
                        item_id.add(itm_id);
                        item_title.add(item_name);
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