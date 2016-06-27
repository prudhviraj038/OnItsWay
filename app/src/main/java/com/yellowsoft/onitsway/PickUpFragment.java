package com.yellowsoft.onitsway;

/**
 * Created by Chinni on 05-04-2016.
 */
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class PickUpFragment extends Fragment {
    EditText fname,block,building,street,house,weight;
    TextView time,date;
    LinearLayout area,item,pick_up_submit;
    String areas_id="0",items_id="0";
    ArrayList<String> area_id;
    ArrayList<String> area_title;
    ArrayList<String> item_id;
    ArrayList<String> item_title;
    TextView area_tv,item_tv;
    private int mYear, mMonth, mDay, mHour, mMinute;
    FragmentTouchListner mCallBack;
    String area_name,item_name,time1,date1;
    public interface FragmentTouchListner {
        public void select_drop_off();
    }
    public void onAttachFragment(Fragment fragment)
    {
        try
        {
            mCallBack = (PickupDropoffAddressFragment)fragment;

        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(
                    fragment.toString() + " must implement OnPlayerSelectionSetListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pickup_screen, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        onAttachFragment(getParentFragment());
        pick_up_submit = (LinearLayout) v.findViewById(R.id.pickup_submit);
        area_id = new ArrayList<String>();
        area_title = new ArrayList<String>();
        item_id = new ArrayList<String>();
        item_title = new ArrayList<String>();
        fname = (EditText) v.findViewById(R.id.pickup_fname);
        block = (EditText) v.findViewById(R.id.pickup_block);
        building = (EditText) v.findViewById(R.id.pickup_building);
        street = (EditText) v.findViewById(R.id.pickup_streetname);
        house = (EditText) v.findViewById(R.id.pickup_house);
        time = (TextView) v.findViewById(R.id.pickup_time);
        date = (TextView) v.findViewById(R.id.pickup_date);
        weight = (EditText) v.findViewById(R.id.pickup_weight);
        item = (LinearLayout) v.findViewById(R.id.item_type);
        area = (LinearLayout) v.findViewById(R.id.pickup_area);
        area_tv = (TextView) v.findViewById(R.id.pickup_area_tv);
        item_tv = (TextView) v.findViewById(R.id.item_tv);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String temp=String.valueOf(hourOfDay);
                        if(temp.length()<2)
                            temp="0"+temp;
                        String temp1=String.valueOf(minute);
                        if(temp1.length()<2)
                            temp1="0"+temp1;
                        time1 = temp+":"+temp1;
                        time.setText(time1);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String temp=String.valueOf(monthOfYear+1);
                        if(temp.length()<2)
                            temp="0"+temp;
                        String temp1=String.valueOf(dayOfMonth);
                        if(temp1.length()<2)
                            temp1="0"+temp1;
                        date1 = year + "-" + temp + "-" +temp1;
                        date.setText(date1);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        if (!Settings.get_order_json(getActivity()).equals("-1")) {
            try {
                JSONObject jsonObject = new JSONObject(Settings.get_order_json(getActivity()));
                fname.setText(jsonObject.getString("pick_fname"));
                area_tv.setText(jsonObject.getString("pick_area_name"));
                item_tv.setText(jsonObject.getString("pick_item_name"));
                block.setText(jsonObject.getString("pick_block"));
                building.setText(jsonObject.getString("pick_build"));
                street.setText(jsonObject.getString("pick_street"));
                house.setText(jsonObject.getString("pick_house"));
                time.setText(jsonObject.getString("pick_time"));
                date.setText(jsonObject.getString("pick_date"));
                weight.setText(jsonObject.getString("pick_weight"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }




        area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("CHOOSE AREA");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, area_title);
                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(ChooseSubjectActivity.this, sub_title.get(which), Toast.LENGTH_SHORT).show();
                        areas_id = area_id.get(which);
                        area_name=area_title.get(which);
                        area_tv.setText(area_name);


                    }
                });

                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        get_area();

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
                    item_name=item_title.get(which);
                    item_tv.setText(item_name);


                }
            });

            final AlertDialog dialog = builder.create();
            dialog.show();
        }
    });
        get_item();
        pick_up_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
            public void onClick(View v) {
                            String full_name=fname.getText().toString();
                            String block1=block.getText().toString();
                            String build=building.getText().toString();
                            String streetname=street.getText().toString();
                            String weight1=weight.getText().toString();
                            String house1=house.getText().toString();
                if (full_name.equals(""))
                    Toast.makeText(getActivity(), "Please Enter FullName  "+full_name, Toast.LENGTH_SHORT).show();
                else if (areas_id.equals("0"))
                    Toast.makeText(getActivity(), "Please Enter Area", Toast.LENGTH_SHORT).show();
                else if (block1.equals(""))
                    Toast.makeText(getActivity(), "Please Enter Block", Toast.LENGTH_SHORT).show();
                else if (build.equals(""))
                    Toast.makeText(getActivity(), "Please Enter Building Name", Toast.LENGTH_SHORT).show();
                else if (streetname.equals(""))
                    Toast.makeText(getActivity(), "Please Enter Street Name", Toast.LENGTH_SHORT).show();
                else if (house1.equals(""))
                    Toast.makeText(getActivity(), "Please Enter House Name", Toast.LENGTH_SHORT).show();
                else if (items_id.equals("0"))
                    Toast.makeText(getActivity(), "Please Enter Item Type", Toast.LENGTH_SHORT).show();
                else if (time1.equals(""))
                    Toast.makeText(getActivity(), "Please Enter Time", Toast.LENGTH_SHORT).show();
                else if (date1.equals(""))
                    Toast.makeText(getActivity(), "Please Enter Date", Toast.LENGTH_SHORT).show();
                else if (weight1.equals(""))
                    Toast.makeText(getActivity(), "Please Enter Weight", Toast.LENGTH_SHORT).show();
                else {
                    mCallBack.select_drop_off();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("pick_fname",full_name);
                        jsonObject.put("pick_area_name",area_name);
                        jsonObject.put("from",areas_id);
                        jsonObject.put("pick_block",block1);
                        jsonObject.put("pick_build",build);
                        jsonObject.put("pick_street",streetname);
                        jsonObject.put("pick_house",house1);
                        jsonObject.put("pick_item_name",item_name);
                        jsonObject.put("pick_item",items_id);
                        jsonObject.put("pick_time",time1);
                        jsonObject.put("pick_date",date1);
                        jsonObject.put("pick_weight", weight1);
                        fname.setText(jsonObject.getString("pick_fname"));
                        area_tv.setText(jsonObject.getString("pick_area_name"));
                        item_tv.setText(jsonObject.getString("pick_item_name"));
                        block.setText(jsonObject.getString("pick_block"));
                        building.setText(jsonObject.getString("pick_build"));
                        street.setText(jsonObject.getString("pick_street"));
                        house.setText(jsonObject.getString("pick_house"));
                        time.setText(jsonObject.getString("pick_time"));
                        date.setText(jsonObject.getString("pick_date"));
                        weight.setText(jsonObject.getString("pick_weight"));
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Settings.set_order_json(getActivity(),jsonObject.toString());
                }
            }
        });
}
    private void get_area(){
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