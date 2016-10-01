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
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PickUpFragment extends Fragment {
    boolean from_saved = false;
    MyEditText fname,block,building,street,house,mobile,weight,comments,add_title_et;
    MyTextView time,date,pick_submit,title_comments,address_tv,sta_address_tv,save_tv,dntsave_tv;
    LinearLayout area,item,pick_up_submit,address_ll,address_pop,save_pop,save_ll,dntsave_ll,p_ll;
    String addr_id="0",addr_title="0";
    ImageView close;
    ArrayList<String> area_id;
    ArrayList<String> area_title;
    ArrayList<String> item_id;
    ArrayList<String> address_id;
    ArrayList<String> address_title;
    ArrayList<String> item_title;
    ArrayList<Area> address;
    MyTextView area_tv,item_tv;
    ListView address_list;
    String full_name,block1,build,streetname,weight1,house1,mobile1,comment;
    AreaAdapter ad;
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
//        get_address();
        area_id = new ArrayList<String>();
        area_title = new ArrayList<String>();
        item_id = new ArrayList<String>();
        item_title = new ArrayList<String>();
        address_id=new ArrayList<>();
        address=new ArrayList<>();
        address_title=new ArrayList<>();
        fname = (MyEditText) v.findViewById(R.id.pickup_fname);
        fname.setText(Settings.getUser_name(getActivity()));
        add_title_et = (MyEditText) v.findViewById(R.id.p_address_title);
        add_title_et.setHint(Settings.getword(getActivity(), "empty_address_title"));
        block = (MyEditText) v.findViewById(R.id.pickup_block);
        block.setHint(Settings.getword(getActivity(), "block"));
        building = (MyEditText) v.findViewById(R.id.pickup_building);
        building.setHint(Settings.getword(getActivity(),"building"));
        street = (MyEditText) v.findViewById(R.id.pickup_streetname);
        street.setHint(Settings.getword(getActivity(),"street_name"));
        house = (MyEditText) v.findViewById(R.id.pickup_house);
        house.setHint(Settings.getword(getActivity(), "house"));
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
        pick_submit.setText(Settings.getword(getActivity(), "go_to_delivery_details"));
        address_tv = (MyTextView) v.findViewById(R.id.p_addr_list_tv);
        address_tv.setText(Settings.getword(getActivity(),"my_addresses"));
        sta_address_tv = (MyTextView) v.findViewById(R.id.p_sta_my_addre);
        sta_address_tv.setText(Settings.getword(getActivity(),"my_addresses"));
        save_tv = (MyTextView) v.findViewById(R.id.p_save_tv);
        save_tv.setText(Settings.getword(getActivity(),"save"));
        dntsave_tv = (MyTextView) v.findViewById(R.id.p_dnt_save_tv);
        dntsave_tv.setText(Settings.getword(getActivity(),"dont_save"));
        close=(ImageView)v.findViewById(R.id.close_p);
        save_pop=(LinearLayout)v.findViewById(R.id.p_save_pop);
        save_ll=(LinearLayout)v.findViewById(R.id.p_save_ll);
        dntsave_ll=(LinearLayout)v.findViewById(R.id.p_dnt_save_ll);
        p_ll=(LinearLayout)v.findViewById(R.id.p_ll);
        address_ll=(LinearLayout)v.findViewById(R.id.p_addr_list_ll);
        address_pop=(LinearLayout)v.findViewById(R.id.p_my_address_pop);
        address_list=(ListView)v.findViewById(R.id.p_a_lv);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address_pop.setVisibility(View.GONE);
            }
        });
        address_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_address();
//                address_pop.setVisibility(View.VISIBLE);
//                ad = new AreaAdapter(getActivity(), address_title);
//                address_list.setAdapter(ad);
            }
        });
        address_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                address_pop.setVisibility(View.GONE);
                addr_id = address.get(position).id;
                add_title_et.setText(address.get(position).title);
                addr_title = address.get(position).title;
                fname.setText(address.get(position).name);
                area_tv.setText("Area :  " + address.get(position).get_area_title(getActivity()));
//                item_tv.setText(Settings.get_item_name(getActivity()));
                block.setText(address.get(position).block);
                building.setText(address.get(position).building);
                street.setText(address.get(position).street);
                house.setText(address.get(position).flat);
//                time.setText(jsonObject.getString("pick_time"));
//                date.setText(jsonObject.getString("pick_date"));
                mobile.setText(address.get(position).phone);
//                weight.setText(Settings.get_weight(getActivity()));
                from_saved = true;
            }
        });
        weight = (MyEditText) v.findViewById(R.id.pickup_weight);
        weight.setHint(Settings.getword(getActivity(),"weight"));
        Log.e("weight", Settings.get_weight(getActivity()));
//        weight.setText(Settings.get_weight(getActivity()));
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
                time1 = jsonObject.getString("pick_time");
                date1 = jsonObject.getString("pick_date");

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
                             full_name=fname.getText().toString();
                             block1=block.getText().toString();
                            build=building.getText().toString();
                             streetname=street.getText().toString();
                            weight1=weight.getText().toString();
                             house1=house.getText().toString();
                             mobile1=mobile.getText().toString();
                             comment=comments.getText().toString();


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
                    if(!from_saved)
                    save_pop.setVisibility(View.VISIBLE);
                    else {
                        save_pop.setVisibility(View.GONE);
                        dntsave_ll.performClick();
                    }

                }
                        }
        });
        save_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (add_title_et.getText().toString().equals("")) {
//                    Toast.makeText(getActivity(),Settings.getword(getActivity(),"empty_name"), Toast.LENGTH_SHORT).show();
                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_name"), false);
                }else {
                    save();
                }
            }
        });
    dntsave_ll.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        save_pop.setVisibility(View.GONE);
            Settings.set_p_date(getActivity(), th, tm, dy, dm, dd);
            mCallBack.select_drop_off();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("pick_fname", full_name);
//                        jsonObject.put("pick_area_name",Settings.get_pickup_area_name(getActivity()));
                jsonObject.put("from", Settings.get_pickup_area_id(getActivity()));
                jsonObject.put("pick_block", block1);
                jsonObject.put("pick_build", build);
                jsonObject.put("pick_street", streetname);
                jsonObject.put("pick_house", house1);
//                        jsonObject.put("pick_item_name",Settings.get_item_name(getActivity()));
                jsonObject.put("pick_item", Settings.get_item_id(getActivity()));
                jsonObject.put("pick_time", time1);
                jsonObject.put("pick_date", date1);
                jsonObject.put("pick_weight", Settings.get_weight(getActivity()));
                jsonObject.put("pick_phone", mobile1);
                jsonObject.put("pick_comments", comment);
                fname.setText(jsonObject.getString("pick_fname"));
                area_tv.setText("Area :  " + Settings.get_pickup_area_name(getActivity()));
                item_tv.setText(Settings.get_item_name(getActivity()));
                block.setText(jsonObject.getString("pick_block"));
                building.setText(jsonObject.getString("pick_build"));
                street.setText(jsonObject.getString("pick_street"));
                house.setText(jsonObject.getString("pick_house"));
                time.setText(jsonObject.getString("pick_time"));
                date.setText(jsonObject.getString("pick_date"));
                mobile.setText(jsonObject.getString("pickup_mobile"));
                weight.setText(Settings.get_weight(getActivity()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Settings.set_order_json(getActivity(), jsonObject.toString());
//                    Settings.set_check(getActivity(),"-1");
        }
    });
        save_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


}
    private void save(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = Settings.SERVERURL+"add-address.php?";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        try {
                            JSONObject jsonObject1=new JSONObject(response);
                            String reply=jsonObject1.getString("status");
                            if(reply.equals("Failed")) {
                                String msg = jsonObject1.getString("message");
//                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                alert.showAlertDialog(getActivity(), "Info", msg, false);

                            }
                            else {
                                String msg = jsonObject1.getString("message");
                                alert.showAlertDialog(getActivity(), "Info", msg, false);
                                save_pop.setVisibility(View.GONE);

                                {
                                    Settings.set_p_date(getActivity(), th, tm, dy, dm, dd);
                                    mCallBack.select_drop_off();
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("pick_fname", full_name);
//                        jsonObject.put("pick_area_name",Settings.get_pickup_area_name(getActivity()));
                                        jsonObject.put("from", Settings.get_pickup_area_id(getActivity()));
                                        jsonObject.put("pick_block", block1);
                                        jsonObject.put("pick_build", build);
                                        jsonObject.put("pick_street", streetname);
                                        jsonObject.put("pick_house", house1);
//                        jsonObject.put("pick_item_name",Settings.get_item_name(getActivity()));
                                        jsonObject.put("pick_item", Settings.get_item_id(getActivity()));
                                        jsonObject.put("pick_time", time1);
                                        jsonObject.put("pick_date", date1);
                                        jsonObject.put("pick_weight", Settings.get_weight(getActivity()));
                                        jsonObject.put("pick_phone", mobile1);
                                        jsonObject.put("pick_comments", comment);
                                        fname.setText(jsonObject.getString("pick_fname"));
                                        area_tv.setText("Area :  " + Settings.get_pickup_area_name(getActivity()));
                                        item_tv.setText(Settings.get_item_name(getActivity()));
                                        block.setText(jsonObject.getString("pick_block"));
                                        building.setText(jsonObject.getString("pick_build"));
                                        street.setText(jsonObject.getString("pick_street"));
                                        house.setText(jsonObject.getString("pick_house"));
                                        time.setText(jsonObject.getString("pick_time"));
                                        date.setText(jsonObject.getString("pick_date"));
                                        mobile.setText(jsonObject.getString("pickup_mobile"));
                                        weight.setText(Settings.get_weight(getActivity()));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Settings.set_order_json(getActivity(), jsonObject.toString());
//                    Settings.set_check(getActivity(),"-1");

                                }
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
                params.put("member_id",Settings.getUserid(getActivity()));
                params.put("address_id",addr_id);
                params.put("title",add_title_et.getText().toString());
                params.put("name",full_name);
                params.put("area",Settings.get_pickup_area_id(getActivity()));
                params.put("block",block1);
                params.put("building",build);
                params.put("street",streetname);
                params.put("house",house1);
                params.put("phone",mobile1);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    private void get_address(){
        String url=Settings.SERVERURL+"addresses.php?member_id="+Settings.getUserid(getActivity())+
                "&area="+Settings.get_pickup_area_id(getActivity());
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                progressDialog.dismiss();
                Log.e("response is: ", jsonArray.toString());
                try {
                    address_id.clear();
                    address_title.clear();
                    address.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject sub = jsonArray.getJSONObject(i);
                        String area_name = sub.getString("title");
                        String ar_id = sub.getString("id");
                        address_id.add(ar_id);
                        address_title.add(area_name);
                        Area area=new Area(sub);
                        address.add(area);
                    }
                    if(address.size()==0){
//                        p_ll.setVisibility(View.GONE);
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_address"), false);
                    }else{
//                        p_ll.setVisibility(View.VISIBLE);
                        address_pop.setVisibility(View.VISIBLE);
                        ad = new AreaAdapter(getActivity(), address_title);
                        address_list.setAdapter(ad);
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
                if(progressDialog!=null)
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