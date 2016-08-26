package com.yellowsoft.onitsway;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
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

public class DropOffFragment extends Fragment {
    MyEditText fname,block,building,street,house,mobile,comments;
    LinearLayout area,pick_up_submit;
    String areas_id="0";
    ArrayList<String> area_id;
    ArrayList<String> area_title;
    TextView area_tv,done,time,date,title_comments;
    String full_name,block1,build,streetname,house1,mobile1,comment;
    String area_name,time1="0",date1="0",th="0",tm="0",dy="0",dm="0",dd="0";
    private int mYear, mMonth, mDay, mHour, mMinute;
    AllApis allApis=new AllApis();
    FragmentTouchListner mCallBack;
    AlertDialogManager alert = new AlertDialogManager();
    Context context;
    int hour,minutes;
    public interface FragmentTouchListner {
        public void from_drop_off();

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
        return inflater.inflate(R.layout.drop_off_screen, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        String head=Settings.getword(getActivity(), "drop_address");
//        mCallBack.text(head);
        onAttachFragment(getParentFragment());
        pick_up_submit = (LinearLayout)v.findViewById(R.id.pickup_submit);
        area_id= new ArrayList<String>();
        area_title=new ArrayList<String>();
        fname=(MyEditText)v.findViewById(R.id.drop_fname);
        fname.setHint(Settings.getword(getActivity(),"full_name"));
        block=(MyEditText)v.findViewById(R.id.dropoff_block);
        block.setHint(Settings.getword(getActivity(),"block"));
        building=(MyEditText)v.findViewById(R.id.dropoff_building);
        building.setHint(Settings.getword(getActivity(),"building"));
        street=(MyEditText)v.findViewById(R.id.dropoff_street);
        street.setHint(Settings.getword(getActivity(),"street_name"));
        house=(MyEditText)v.findViewById(R.id.dropoff_house);
        house.setHint(Settings.getword(getActivity(),"house"));
        mobile=(MyEditText)v.findViewById(R.id.dropoff_mobile);
        mobile.setHint(Settings.getword(getActivity(),"mobile_number"));
        time = (TextView) v.findViewById(R.id.drop_time);
        time.setText(Settings.getword(getActivity(), "time"));
        date = (TextView) v.findViewById(R.id.drop_date);
        date.setText(Settings.getword(getActivity(), "date"));
        title_comments = (TextView) v.findViewById(R.id.title_drop_comments);
        title_comments.setText(Settings.getword(getActivity(), "comments"));
        comments = (MyEditText) v.findViewById(R.id.drop_comments);
        comments.setHint(Settings.getword(getActivity(), "comments"));
        area=(LinearLayout)v.findViewById(R.id.drop_area);
        area_tv=(TextView)v.findViewById(R.id.drop_area_tv);
        area_tv.setText("Area :  "+Settings.get_drop_off_area_name(getActivity()));
        done=(TextView)v.findViewById(R.id.done);
        done.setText(Settings.getword(getActivity(), "done"));
        if (!Settings.get_order_json(getActivity()).equals("-1")) {
        try {
            JSONObject jsonObject=new JSONObject(Settings.get_order_json(getActivity()));
            fname.setText(jsonObject.getString("drop_fname"));
            area_tv.setText("Area :  "+Settings.get_drop_off_area_name(getActivity()));
            block.setText(jsonObject.getString("drop_block"));
            building.setText(jsonObject.getString("drop_building"));
            street.setText(jsonObject.getString("drop_street"));
            house.setText(jsonObject.getString("drop_house"));
            mobile.setText(jsonObject.getString("drop_mobile"));
//            time.setText(jsonObject.getString("drop_time"));
//            date.setText(jsonObject.getString("drop_date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }}

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
                        dy=String.valueOf(year);
                        String temp = String.valueOf(monthOfYear + 1);
                        if (temp.length() < 2)
                            temp = "0" + temp;
                        dm=temp;
                        String temp1 = String.valueOf(dayOfMonth);
                        if (temp1.length() < 2)
                            temp1 = "0" + temp1;
                        dd=temp1;
                        date1 = temp1 + "-" + temp + "-" +year;
                        date.setText(date1);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


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
//                    }
//                });
//
//                final AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });
//        get_area();

        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                full_name=fname.getText().toString();
                block1=block.getText().toString();
                build=building.getText().toString();
                streetname=street.getText().toString();
                house1=house.getText().toString();
                mobile1=mobile.getText().toString();
                comment=comments.getText().toString();
                    if (full_name.equals(""))
//                        Toast.makeText(getActivity(),Settings.getword(getActivity(),"empty_name"), Toast.LENGTH_SHORT).show();
                          alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_name"), false);
                    else if (area_tv.equals("0"))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_area"), Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_area"), false);
                    else if (block1.equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_block"), Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_block"), false);
                    else if (build.equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_building"), Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_building"), false);
                    else if (streetname.equals(""))
//                        Toast.makeText(getActivity(),  Settings.getword(getActivity(),"empty_street"), Toast.LENGTH_SHORT).show();
                            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_street"), false);
                    else if (house1.equals(""))
//                        Toast.makeText(getActivity(),Settings.getword(getActivity(),"empty_house"), Toast.LENGTH_SHORT).show();
                         alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_house"), false);
                    else if (time1.equals("0"))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_time"), Toast.LENGTH_SHORT).show();
                            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_time"), false);
                    else if (date1.equals("0"))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_date"), Toast.LENGTH_SHORT).show();
                         alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_date"), false);
                    else if (mobile1.equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_mobile"), Toast.LENGTH_SHORT).show();
                            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_mobile"), false);
//                    else if (comment.equals(""))
////                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_comment"), Toast.LENGTH_SHORT).show();
//                         alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "empty_comment"), false);
                    else {
                        if(Integer.parseInt(Settings.get_dy(getActivity()))==Integer.parseInt(dy)){
                            if(Integer.parseInt(Settings.get_dm(getActivity()))==Integer.parseInt(dm)) {
                                if(Integer.parseInt(Settings.get_dd(getActivity()))==Integer.parseInt(dd)){
                                    if(Integer.parseInt(Settings.get_th(getActivity()))==Integer.parseInt(th)) {
                                        if((Integer.parseInt(Settings.get_tm(getActivity()))-Integer.parseInt(th))>30){
                                            next();
                                        }else{
                                            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(),"error_drop_time"), false);
                                        }
                                    }else if(Integer.parseInt(Settings.get_th(getActivity()))>Integer.parseInt(th)){
                                        alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(),"error_drop_time"), false);
                                    }else{
                                        next();
                                    }
                                }else if(Integer.parseInt(Settings.get_dd(getActivity()))>Integer.parseInt(dd)){
                                    alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(),"error_drop_time"), false);
                                }else{
                                    next();
                                }
                            }else if(Integer.parseInt(Settings.get_dm(getActivity()))>Integer.parseInt(dm)){
                                alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(),"error_drop_time"), false);
                            }else{
                                next();
                            }
                        }else if(Integer.parseInt(Settings.get_dy(getActivity()))>Integer.parseInt(dy)){
                            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(),"error_drop_time"), false);
                        }else{
                            next();
                        }
                    }
            }
        });
    }
    public void next() {
        try {
            JSONObject jsonObject = new JSONObject(Settings.get_order_json(getActivity()));
            jsonObject.put("drop_area_name",Settings.get_drop_off_area_name(getActivity()));
            jsonObject.put("drop_fname",full_name);
            jsonObject.put("to",Settings.get_drop_off_area_id(getActivity()));
            jsonObject.put("drop_block",block1);
            jsonObject.put("drop_building",build);
            jsonObject.put("drop_street",streetname);
            jsonObject.put("drop_house",house1);
            jsonObject.put("drop_mobile", mobile1);
            jsonObject.put("drop_time",time1);
            jsonObject.put("drop_date",date1);
            jsonObject.put("drop_comments",comment);
            fname.setText(jsonObject.getString("drop_fname"));
            area_tv.setText("Area :  "+Settings.get_drop_off_area_name(getActivity()));
            block.setText(jsonObject.getString("drop_block"));
            building.setText(jsonObject.getString("drop_building"));
            street.setText(jsonObject.getString("drop_street"));
            house.setText(jsonObject.getString("drop_house"));
            mobile.setText(jsonObject.getString("drop_mobile"));
//                            time.setText(jsonObject.getString("drop_time"));
//                            date.setText(jsonObject.getString("drop_date"));
            Settings.set_order_json(getActivity(), jsonObject.toString());
            mCallBack.from_drop_off();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                        String area_name = sub.getString("title"+Settings.get_lan(context));
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