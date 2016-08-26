package com.yellowsoft.onitsway;

/**
 * Created by Chinni on 05-04-2016.
 */
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
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
    MyEditText fname,block,building,street,house,mobile,weight,comments;
    MyTextView time,date,pick_submit,title_comments;
    LinearLayout area,item,pick_up_submit;
    String areas_id="0",items_id="0";
    ArrayList<String> area_id;
    ArrayList<String> area_title;
    ArrayList<String> item_id;
    ArrayList<String> item_title;
    MyTextView area_tv,item_tv;
    AlertDialogManager alert = new AlertDialogManager();
    private int mYear, mMonth, mDay, mHour, mMinute;
    FragmentTouchListner mCallBack;
    int hour,minutes;
    String area_name,item_name,time1="0",date1="0",th="0",tm="0",dy="0",dm="0",dd="0";
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
        String head=Settings.getword(getActivity(), "pickup_address");
//        mCallBack.text(head);
        onAttachFragment(getParentFragment());
        pick_up_submit = (LinearLayout) v.findViewById(R.id.pickup_submit);
        area_id = new ArrayList<String>();
        area_title = new ArrayList<String>();
        item_id = new ArrayList<String>();
        item_title = new ArrayList<String>();
        fname = (MyEditText) v.findViewById(R.id.pickup_fname);
        fname.setText(Settings.getUser_name(getActivity()));
        block = (MyEditText) v.findViewById(R.id.pickup_block);
        block.setHint(Settings.getword(getActivity(), "block"));
        building = (MyEditText) v.findViewById(R.id.pickup_building);
        building.setHint(Settings.getword(getActivity(),"building"));
        street = (MyEditText) v.findViewById(R.id.pickup_streetname);
        street.setHint(Settings.getword(getActivity(),"street_name"));
        house = (MyEditText) v.findViewById(R.id.pickup_house);
        house.setHint(Settings.getword(getActivity(),"house"));
        time = (MyTextView) v.findViewById(R.id.pickup_time);
        time.setText(Settings.getword(getActivity(), "time"));
        date = (MyTextView) v.findViewById(R.id.pickup_date);
        date.setText(Settings.getword(getActivity(),"date"));
        title_comments = (MyTextView) v.findViewById(R.id.title_comments);
        title_comments.setText(Settings.getword(getActivity(), "comments"));
        comments = (MyEditText) v.findViewById(R.id.pick_comments);
        comments.setHint(Settings.getword(getActivity(), "comments"));
        mobile=(MyEditText)v.findViewById(R.id.pick_mobile);
        mobile.setHint(Settings.getword(getActivity(), "mobile_number"));
        mobile.setText(Settings.getUser_mobile(getActivity()));
        pick_submit = (MyTextView) v.findViewById(R.id.picku_submit);
        pick_submit.setText(Settings.getword(getActivity(),"go_to_delivery_details"));
        weight = (MyEditText) v.findViewById(R.id.pickup_weight);
        Log.e("weight", Settings.get_weight(getActivity()));
        weight.setText(Settings.get_weight(getActivity()));
        item = (LinearLayout) v.findViewById(R.id.item_type);
        area = (LinearLayout) v.findViewById(R.id.pickup_area);
        area_tv = (MyTextView) v.findViewById(R.id.pickup_area_tv);
        area_tv.setText("Area :  "+Settings.get_pickup_area_name(getActivity()));
        item_tv = (MyTextView) v.findViewById(R.id.item_tv);
        item_tv.setText(Settings.get_item_name(getActivity()));
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String temp = String.valueOf(hourOfDay);
                        if (temp.length() < 2)
                            temp = "0" + temp;
                        th=temp;
                        String temp2 = String.valueOf(minute);
                        if (temp2.length() < 2)
                            temp2 = "0" + temp2;
                        tm=temp2;
                        hour = hourOfDay;
                        minutes = minute;
                        String timeSet = "";
                        if (hour > 12) {
                            hour -= 12;
                            timeSet = "PM";
                        } else if (hour == 0) {
                            hour += 12;
                            timeSet = "AM";
                        } else if (hour == 12)
                            timeSet = "PM";
                        else
                            timeSet = "AM";

                        String min = "";
                        if (minutes < 10)
                            min = "0" + minutes ;
                        else
                            min = String.valueOf(minutes);

                        // Append in a StringBuilder
                         time1 = new StringBuilder().append(hour).append(':')
                                .append(min ).append(" ").append(timeSet).toString();
                        time.setText(time1);;
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
                        dy=String.valueOf(year);
                        String temp=String.valueOf(monthOfYear+1);
                        if(temp.length()<2)
                            temp="0"+temp;
                            dm=temp;
                        String temp1=String.valueOf(dayOfMonth);
                        if(temp1.length()<2)
                            temp1="0"+temp1;
                        dd=temp1;
                        date1 = temp1 + "-" + temp + "-" +year;
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
                area_tv.setText("Area :  "+Settings.get_pickup_area_name(getActivity()));
                item_tv.setText(Settings.get_item_name(getActivity()));
                block.setText(jsonObject.getString("pick_block"));
                building.setText(jsonObject.getString("pick_build"));
                street.setText(jsonObject.getString("pick_street"));
                house.setText(jsonObject.getString("pick_house"));
                time.setText(jsonObject.getString("pick_time"));
                date.setText(jsonObject.getString("pick_date"));
                mobile.setText(jsonObject.getString("pick_phone"));
                weight.setText(Settings.get_weight(getActivity()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }




//        area.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle(Settings.getword(getActivity(),"title_area"));
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, area_title);
//                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //Toast.makeText(ChooseSubjectActivity.this, sub_title.get(which), Toast.LENGTH_SHORT).show();
//                        areas_id = area_id.get(which);
//                        area_name=area_title.get(which);
//                        area_tv.setText(area_name);
//
//
//                    }
//                });
//
//                final AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });
//        get_area();

//    item.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setTitle(Settings.getword(getActivity(),"select_items"));
//            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, item_title);
//            builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    //Toast.makeText(ChooseSubjectActivity.this, sub_title.get(which), Toast.LENGTH_SHORT).show();
//                    items_id = item_id.get(which);
//                    item_name=item_title.get(which);
//                    item_tv.setText(item_name);
//
//
//                }
//            });
//
//            final AlertDialog dialog = builder.create();
//            dialog.show();
//        }
//    });
//        get_item();
        pick_up_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
            public void onClick(View v) {
                            String full_name=fname.getText().toString();
                            String block1=block.getText().toString();
                            String build=building.getText().toString();
                            String streetname=street.getText().toString();
                            String weight1=weight.getText().toString();
                            String house1=house.getText().toString();
                            String mobile1=mobile.getText().toString();
                            String comment=comments.getText().toString();
                            Settings.set_weight(getActivity(), weight.getText().toString());
                if (full_name.equals(""))
//                    Toast.makeText(getActivity(),Settings.getword(getActivity(),"empty_name"), Toast.LENGTH_SHORT).show();
                            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_name"), false);
                else if (area_tv.equals("0"))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_area"), Toast.LENGTH_SHORT).show();
                            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_area"), false);
                else if (block1.equals(""))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_block"), Toast.LENGTH_SHORT).show();
                            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_block"), false);
                else if (build.equals(""))
//                    Toast.makeText(getActivity(),Settings.getword(getActivity(),"empty_building"), Toast.LENGTH_SHORT).show();
                            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_building"), false);

                else if (streetname.equals(""))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_street"), Toast.LENGTH_SHORT).show();
                            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_street"), false);
                else if (house1.equals(""))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_house"), Toast.LENGTH_SHORT).show();
                            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_house"), false);
//                else if (item_tv.equals("0"))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_item"), Toast.LENGTH_SHORT).show();
                else if (time1.equals("0"))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_time"), Toast.LENGTH_SHORT).show();
                            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_time"), false);
                else if (date1.equals("0"))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_date"), Toast.LENGTH_SHORT).show();
                            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_date"), false);
//                else if (weight1.equals(""))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_weight"), Toast.LENGTH_SHORT).show();
                else if (mobile1.equals(""))
//                    Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_mobile"), Toast.LENGTH_SHORT).show();
                            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_mobile"), false);
//                else if (comment.equals(""))
////                    Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_comment"), Toast.LENGTH_SHORT).show();
//                            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_comment"), false);
                else {
                    Settings.set_p_date(getActivity(),th,tm,dy,dm,dd);
                    mCallBack.select_drop_off();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("pick_fname",full_name);
//                        jsonObject.put("pick_area_name",Settings.get_pickup_area_name(getActivity()));
                        jsonObject.put("from",Settings.get_pickup_area_id(getActivity()));
                        jsonObject.put("pick_block",block1);
                        jsonObject.put("pick_build",build);
                        jsonObject.put("pick_street",streetname);
                        jsonObject.put("pick_house",house1);
//                        jsonObject.put("pick_item_name",Settings.get_item_name(getActivity()));
                        jsonObject.put("pick_item",Settings.get_item_id(getActivity()));
                        jsonObject.put("pick_time",time1);
                        jsonObject.put("pick_date",date1);
                        jsonObject.put("pick_weight", Settings.get_weight(getActivity()));
                        jsonObject.put("pick_phone",mobile1);
                        jsonObject.put("pick_comments",comment);
                        fname.setText(jsonObject.getString("pick_fname"));
                        area_tv.setText("Area :  "+Settings.get_pickup_area_name(getActivity()));
                        item_tv.setText(Settings.get_item_name(getActivity()));
                        block.setText(jsonObject.getString("pick_block"));
                        building.setText(jsonObject.getString("pick_build"));
                        street.setText(jsonObject.getString("pick_street"));
                        house.setText(jsonObject.getString("pick_house"));
                        time.setText(jsonObject.getString("pick_time"));
                        date.setText(jsonObject.getString("pick_date"));
                        mobile.setText(jsonObject.getString("pickup_mobile"));
                        weight.setText(Settings.get_weight(getActivity()));
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Settings.set_order_json(getActivity(),jsonObject.toString());
//                    Settings.set_check(getActivity(),"-1");
                }
            }
        });
}
    private void get_area(){
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
                Toast.makeText(getActivity(), Settings.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(),  Settings.getword(getActivity(),"server_not_connected"), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
}