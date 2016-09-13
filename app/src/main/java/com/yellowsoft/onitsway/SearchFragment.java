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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
    LinearLayout pick_area,drop_area,item,weight_ll,search_ll,area_pop1,area_pop2,area_pop3;
    String pick_areas_id="0",drop_areas_id="0",items_id="0",weightt="0";
    ArrayList<String> pick_area_id;
    ArrayList<String> pick_area_title;
    AreaAdapter ad;
    ListView lv1,lv2,lv3;
    ArrayList<String> drop_area_id;
    ArrayList<String> drop_area_title;
    ArrayList<String> item_id;
    ArrayList<String> item_title;
    MyTextView pick_area_tv,drop_area_tv,item_tv,search,title,sta_area1,sta_area2,sta_area3;
    MyEditText weight;
    AlertDialogManager alert = new AlertDialogManager();
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
//        public void search_back_butt();
        public void search_back_butt(String head);
        public void to_companies_list(String p_area,String d_area,String item,String weight);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_companies_screen, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        String head=Settings.getword(getActivity(), "search");
//        mCallBack.text_back_butt(head);
        Log.e("p_area1", pick_areas_id);
        mCallBack.search_back_butt(head);
        pick_area_id= new ArrayList<String>();
        pick_area_title=new ArrayList<String>();
        drop_area_id= new ArrayList<String>();
        drop_area_title=new ArrayList<String>();
        item_id= new ArrayList<String>();
        item_title=new ArrayList<String>();
        item=(LinearLayout)v.findViewById(R.id.search_ll_item);
        title=(MyTextView)v.findViewById(R.id.tv_ser_dlr_comp);
        title.setText(Settings.getword(getActivity(),"title_search_companies"));
        lv1=(ListView)v.findViewById(R.id.a_lv1);
        lv2=(ListView)v.findViewById(R.id.a_lv2);
        lv3=(ListView)v.findViewById(R.id.a_lv3);
        search_ll=(LinearLayout)v.findViewById(R.id.search_ll);
        area_pop1=(LinearLayout)v.findViewById(R.id.area_pop1);
        area_pop2=(LinearLayout)v.findViewById(R.id.area_pop2);
        area_pop3=(LinearLayout)v.findViewById(R.id.area_pop3);
        drop_area = (LinearLayout)v.findViewById(R.id.search_ll_drop);
        pick_area=(LinearLayout)v.findViewById(R.id.search_ll_pick);
        pick_area_tv=(MyTextView)v.findViewById(R.id.pickup_area_tv_search);
        drop_area_tv=(MyTextView)v.findViewById(R.id.drop_area_tv_search);
        item_tv=(MyTextView)v.findViewById(R.id.item_tv_search);
        sta_area1=(MyTextView)v.findViewById(R.id.sta_area1);
        sta_area2=(MyTextView)v.findViewById(R.id.sta_area2);
        sta_area3=(MyTextView)v.findViewById(R.id.sta_area3);
        weight=(MyEditText)v.findViewById(R.id.search_weight);
        if(Settings.get_drop_off_area_id(getActivity()).equals("-1")) {
            pick_area_tv.setText(Settings.getword(getActivity(), "pickup_area"));
            drop_area_tv.setText(Settings.getword(getActivity(), "drop_area"));
            item_tv.setText(Settings.getword(getActivity(), "select_items"));
            weight.setHint(Settings.getword(getActivity(), "weight"));



        }else{
            pick_area_tv .setText(Settings.get_pickup_area_name(getActivity()));
            drop_area_tv .setText(Settings.get_drop_off_area_name(getActivity()));
            item_tv .setText(Settings.get_item_name(getActivity()));
            weight.setText(Settings.get_weight(getActivity()));
            pick_areas_id = Settings.get_pickup_area_id(getActivity());
            drop_areas_id = Settings.get_drop_off_area_id(getActivity());
            items_id = Settings.get_item_id(getActivity());

        }
        search=(MyTextView)v.findViewById(R.id.search);
        search.setText(Settings.getword(getActivity(), "search"));

        pick_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                area_pop1.setVisibility(View.VISIBLE);
                sta_area1.setText(Settings.getword(getActivity(), "pickup_area"));
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle(Settings.getword(getActivity(), "pickup_area"));
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, pick_area_title);
//                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //Toast.makeText(ChooseSubjectActivity.this, sub_title.get(which), Toast.LENGTH_SHORT).show();
//                pick_areas_id = pick_area_id.get(which);
//                        Log.e("p_area2",pick_areas_id);
//                        Settings.set_pickup_area(getActivity(), pick_area_id.get(which), pick_area_title.get(which));
//                        pick_area_tv.setText(pick_area_title.get(which));
//
//                    }
//                });
//
//                final AlertDialog dialog = builder.create();
//                dialog.show();
                ad = new AreaAdapter(getActivity(), pick_area_title);
                lv1.setAdapter(ad);
            }
        });
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pick_areas_id = pick_area_id.get(i);
                Log.e("p_area2", pick_areas_id);
                Settings.set_pickup_area(getActivity(), pick_area_id.get(i), pick_area_title.get(i));
                pick_area_tv.setText(pick_area_title.get(i));
                area_pop1.setVisibility(View.GONE);
            }
        });
        get_pick_area();

        drop_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                area_pop2.setVisibility(View.VISIBLE);
                sta_area2.setText(Settings.getword(getActivity(), "drop_area"));
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle(Settings.getword(getActivity(), "drop_area"));
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, drop_area_title);
//                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //Toast.makeText(ChooseSubjectActivity.this, sub_title.get(which), Toast.LENGTH_SHORT).show();
//                        drop_areas_id = drop_area_id.get(which);
//                        Settings.set_drop_off_area(getActivity(), drop_area_id.get(which), drop_area_title.get(which));
//                        drop_area_tv.setText(drop_area_title.get(which));
//
//                    }
//                });
//
//                final AlertDialog dialog = builder.create();
//                dialog.show();
                ad = new AreaAdapter(getActivity(), drop_area_title);
                lv2.setAdapter(ad);
            }
        });
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                drop_areas_id = drop_area_id.get(i);
                Settings.set_drop_off_area(getActivity(), drop_area_id.get(i), drop_area_title.get(i));
                drop_area_tv.setText(drop_area_title.get(i));
                area_pop2.setVisibility(View.GONE);
            }
        });
        get_drop_area();


        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                area_pop3.setVisibility(View.VISIBLE);
                sta_area3.setText(Settings.getword(getActivity(), "select_items"));
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle(Settings.getword(getActivity(), "select_items"));
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, item_title);
//                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //Toast.makeText(ChooseSubjectActivity.this, sub_title.get(which), Toast.LENGTH_SHORT).show();
//                        items_id = item_id.get(which);
//                        Settings.set_item(getActivity(), item_id.get(which), item_title.get(which));
//                        item_tv.setText(item_title.get(which));
//
//                    }
//                });
//
//                final AlertDialog dialog = builder.create();
//                dialog.show();
                ad = new AreaAdapter(getActivity(), item_title);
                lv3.setAdapter(ad);
            }
        });
        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    items_id = item_id.get(i);
                    Settings.set_item(getActivity(), item_id.get(i), item_title.get(i));
                    item_tv.setText(item_title.get(i));
                area_pop3.setVisibility(View.GONE);
            }
        });
        get_item();
        search_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("wwwwww", weight.getText().toString());
                Settings.set_weight(getActivity(), weight.getText().toString());
                Log.e("p_area3", pick_areas_id);
                if((pick_areas_id.equals("-1"))||pick_areas_id.equals("0")) {
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "empty_pickup_area"), Toast.LENGTH_SHORT).show();
                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_pickup_area"), false);
                }
                else if (drop_areas_id.equals("-1")||drop_areas_id.equals("0"))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "empty_dropoff_area"), Toast.LENGTH_SHORT).show();
                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_dropoff_area"), false);
                else if (items_id.equals("-1")||items_id.equals("0"))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "empty_item"), Toast.LENGTH_SHORT).show();
                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_item"), false);
//                else if (weight.getText().toString().equals(""))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(), "empty_weight"), Toast.LENGTH_SHORT).show();
                 else
                    mCallBack.to_companies_list(pick_areas_id, drop_areas_id, items_id, weight.getText().toString());
            }
        });

    }
    private void get_pick_area(){
        String url=Settings.SERVERURL+"locations-json.php";
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
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
                Toast.makeText(getActivity(),Settings.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
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
        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
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
                Toast.makeText(getActivity(),Settings.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
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
        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
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
                Toast.makeText(getActivity(),Settings.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
}