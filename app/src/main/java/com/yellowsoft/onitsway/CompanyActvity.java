package com.yellowsoft.onitsway;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;


public class CompanyActvity extends Activity {
    OrderStatusAdapter adapter;
    ArrayList<String> grade;
    MyTextView no_ser;
    ImageView back,filter;
    ListView status_lv;
    GridView gridView;
    int hour,minutes;
    ViewFlipper viewFlipper;
    ArrayList<OrderDetails> orders;
    ArrayList<OrderDetails> orders_ll;
    String area_name,time1="0",date1="0",th="0",tm="0",dy="0",dm="0",dd="0";
    private int mYear, mMonth, mDay, mHour, mMinute;
    CompanyStatusAdapter orderStatusAdapter;
    LinearLayout cancel_ll,logout,status_ll,res_ll,res_pop,res_time,res_date,res_title_ll;
    String ord_id,sta,wt_status;
    ArrayList<String> status_list;
    MyTextView sta_o_summary, sta_o_id, sta_o_time, sta_p_time, sta_d_time, sta_o_status, sta_com_details, sta_p_add, sta_d_add, sta_total, sta_total_cost,
            o_id, o_time, o_status, o_p_time, o_d_time, p_add4, p_add5, p_add6,p_add1, p_add2, p_add3, d_add4, d_add5, d_add6,d_add1, d_add2, d_add3, com_name, cost, o_item, sta_o_item, cancel_tv
            ,sta_user_details,sta_user_name,sta_user_phone,sta_user_email,user_name,user_email,user_phone,logout_tv,title_orders,res_tv,res_sta,res_time_tv,res_date_tv,res_pop_tv;
    MyTextView sta_o_p_name,sta_o_p_area,sta_o_p_house,sta_o_p_block,sta_o_p_building,sta_o_p_street,
            sta_o_d_name,sta_o_d_area,sta_o_d_house,sta_o_d_block,sta_o_d_building,sta_o_d_street;
    ImageView logoo;
    AlertDialogManager alert = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.forceRTLIfSupported(this);
        setContentView(R.layout.company_status_screen);
        get_orders();
        status_ll=(LinearLayout)findViewById(R.id.status_pop);
        filter = (ImageView) findViewById(R.id.filter_orders);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status_ll.getVisibility() == View.VISIBLE)
                    status_ll.setVisibility(View.GONE);
                else
                    status_ll.setVisibility(View.VISIBLE);
            }
        });
        orders = new ArrayList<>();
        orders_ll = new ArrayList<>();
        status_list=new ArrayList<>();
        status_list.add("Pending");
        status_list.add("In Process");
        status_list.add("Shipped");
        status_list.add("Delivered");
        status_list.add("Completed");
        status_list.add("Cancelled");
        status_list.add("Returned");
        status_list.add("All");
        status_lv=(ListView)findViewById(R.id.status_list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CompanyActvity.this,R.layout.status_list_item,R.id.status_item, status_list);
        status_lv.setAdapter(arrayAdapter);
        status_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                wt_status = status_list.get(i);
                Log.e("status,", wt_status);
                if (wt_status.equals("All")) {
                    wt_status = null;
                }
                orderStatusAdapter.getFilter().filter(wt_status);
                status_ll.setVisibility(View.GONE);
            }
        });

        no_ser = (MyTextView) findViewById(R.id.no_orders);
        no_ser.setText("No orders");
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper_com);
        orderStatusAdapter = new CompanyStatusAdapter(this, orders);
        gridView = (GridView) findViewById(R.id.c_orders_status_list);
        gridView.setAdapter(orderStatusAdapter);
        title_orders = (MyTextView) findViewById(R.id.order_com_tv);
        title_orders.setText(Settings.getword(this, "my_orders"));
        sta_o_summary = (MyTextView) findViewById(R.id.c_sta_order_summary);
        sta_o_summary.setText(Settings.getword(this, "order_summary"));
        sta_o_id = (MyTextView) findViewById(R.id.c_sta_o_id_or);
        sta_o_id.setText(Settings.getword(this, "order_id"));
        sta_o_time = (MyTextView) findViewById(R.id.c_sta_o_time_or);
        sta_o_time.setText(Settings.getword(this, "order_time"));
        sta_o_item = (MyTextView) findViewById(R.id.c_sta_item_or);
        sta_o_item.setText(Settings.getword(this, "item_type"));
        sta_p_time = (MyTextView) findViewById(R.id.c_sta_o_p_time_or);
        sta_p_time.setText(Settings.getword(this, "time_pickup"));
        sta_d_time = (MyTextView) findViewById(R.id.c_sta_o_d_time_or);
        sta_d_time.setText(Settings.getword(this, "time_dropoff"));
        sta_o_status = (MyTextView) findViewById(R.id.c_sta_status_or);
        sta_o_status.setText(Settings.getword(this, "status"));
        sta_com_details = (MyTextView) findViewById(R.id.c_sta_com_details);
        sta_com_details.setText(Settings.getword(this, "title_company_details"));
        sta_p_add = (MyTextView) findViewById(R.id.c_sta_p_add_or);
        sta_p_add.setText(Settings.getword(this, "pickup_address"));
        sta_d_add = (MyTextView) findViewById(R.id.c_sta_d_add_or);
        sta_d_add.setText(Settings.getword(this, "drop_address"));
        sta_total = (MyTextView) findViewById(R.id.c_sta_total_or);
        sta_total.setText(Settings.getword(this, "total_cost"));
        sta_total_cost = (MyTextView) findViewById(R.id.c_sta_total_cost_or);
        sta_total_cost.setText(Settings.getword(this, "total_cost"));
        cancel_tv = (MyTextView) findViewById(R.id.order_change_tv);
        cancel_tv.setText(Settings.getword(this, "change_status"));
        sta_user_details = (MyTextView) findViewById(R.id.sta_person_details);
        sta_user_details.setText(Settings.getword(this, "user_details"));
        sta_user_name = (MyTextView) findViewById(R.id.sta_user_name);
        sta_user_name.setText(Settings.getword(this, "user_name"));
        sta_user_email = (MyTextView) findViewById(R.id.sta_user_email);
        sta_user_email.setText(Settings.getword(this, "user_email"));
        sta_user_phone = (MyTextView) findViewById(R.id.sta_user_phone);
        sta_user_phone.setText(Settings.getword(this, "user_phone"));
        logout_tv = (MyTextView) findViewById(R.id.c_logout_tv);
        logout_tv.setText(Settings.getword(this, "logout"));

        sta_o_p_name = (MyTextView) findViewById(R.id.c_p_add_or1_sta);
        sta_o_p_name.setText(Settings.getword(this, "full_name"));
        sta_o_p_area = (MyTextView) findViewById(R.id.c_p_add_or2_sta);
        sta_o_p_area.setText(Settings.getword(this, "area"));
        sta_o_p_house = (MyTextView) findViewById(R.id.c_p_add_or3_sta);
        sta_o_p_house.setText(Settings.getword(this, "house"));
        sta_o_p_block = (MyTextView) findViewById(R.id.c_p_add_or4_sta);
        sta_o_p_block.setText(Settings.getword(this, "block"));
        sta_o_p_building = (MyTextView) findViewById(R.id.c_p_add_or5_sta);
        sta_o_p_building.setText(Settings.getword(this, "building"));
        sta_o_p_street = (MyTextView) findViewById(R.id.c_p_add_or6_sta);
        sta_o_p_street.setText(Settings.getword(this, "street_name"));

        sta_o_d_name = (MyTextView) findViewById(R.id.c_d_add_or1_sta);
        sta_o_d_name.setText(Settings.getword(this, "full_name"));
        sta_o_d_area = (MyTextView) findViewById(R.id.c_d_add_or2_sta);
        sta_o_d_area.setText(Settings.getword(this, "area"));
        sta_o_d_house = (MyTextView) findViewById(R.id.c_d_add_or3_sta);
        sta_o_d_house.setText(Settings.getword(this, "house"));
        sta_o_d_block = (MyTextView) findViewById(R.id.c_d_add_or4_sta);
        sta_o_d_block.setText(Settings.getword(this, "block"));
        sta_o_d_building = (MyTextView) findViewById(R.id.c_d_add_or5_sta);
        sta_o_d_building.setText(Settings.getword(this, "building"));
        sta_o_d_street = (MyTextView) findViewById(R.id.c_d_add_or6_sta);
        sta_o_d_street.setText(Settings.getword(this, "street_name"));

        o_id = (MyTextView) findViewById(R.id.c_oo_id);
        o_time = (MyTextView) findViewById(R.id.c_oo_time);
        o_item = (MyTextView) findViewById(R.id.c_item_or);
        o_p_time = (MyTextView) findViewById(R.id.c_oo_p_time);
        o_d_time = (MyTextView) findViewById(R.id.c_oo_d_time);
        o_status = (MyTextView) findViewById(R.id.c_status_order);
        p_add1 = (MyTextView) findViewById(R.id.c_p_add_or1);
        p_add2 = (MyTextView) findViewById(R.id.c_p_add_or2);
        p_add3 = (MyTextView) findViewById(R.id.c_p_add_or3);
        p_add4 = (MyTextView) findViewById(R.id.c_p_add_or4);
        p_add5 = (MyTextView) findViewById(R.id.c_p_add_or5);
        p_add6 = (MyTextView) findViewById(R.id.c_p_add_or6);
        d_add1 = (MyTextView) findViewById(R.id.c_d_add_or1);
        d_add2 = (MyTextView) findViewById(R.id.c_d_add_or2);
        d_add3 = (MyTextView) findViewById(R.id.c_d_add_or3);
        d_add4 = (MyTextView) findViewById(R.id.c_d_add_or4);
        d_add5 = (MyTextView) findViewById(R.id.c_d_add_or5);
        d_add6 = (MyTextView) findViewById(R.id.c_d_add_or6);
        com_name = (MyTextView) findViewById(R.id.c_com_name_or);
        cost = (MyTextView) findViewById(R.id.c_total_order);
        user_name = (MyTextView) findViewById(R.id.user_name);
        user_email = (MyTextView) findViewById(R.id.user_email);
        user_phone= (MyTextView) findViewById(R.id.user_phone);

        logoo = (ImageView) findViewById(R.id.c_com_logo_or);
        back = (ImageView) findViewById(R.id.c_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getApplicationContext(), NavigationActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });
        logout = (LinearLayout) findViewById(R.id.c_logout_ll);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.setcomUserid(CompanyActvity.this, "-1", "");
                Intent mainIntent = new Intent(getApplicationContext(), NavigationActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });
        res_pop = (LinearLayout) findViewById(R.id.res_pop);
        res_time = (LinearLayout) findViewById(R.id.res_time);
        res_date= (LinearLayout) findViewById(R.id.res_date);
        res_title_ll = (LinearLayout) findViewById(R.id.res_title_pop_ll);
        res_tv = (MyTextView) findViewById(R.id.resheduled_tv);
        res_tv.setText(Settings.getword(CompanyActvity.this, "reschedule"));
        res_sta = (MyTextView) findViewById(R.id.sta_res);
        res_sta.setText(Settings.getword(CompanyActvity.this, "select_res_time"));
        res_time_tv = (MyTextView) findViewById(R.id.res_time_tv);
        res_time_tv.setText(Settings.getword(CompanyActvity.this, "time"));
        res_date_tv = (MyTextView) findViewById(R.id.res_date_tv);
        res_date_tv.setText(Settings.getword(CompanyActvity.this, "date"));
        res_pop_tv = (MyTextView) findViewById(R.id.res_title_pop_tv);
        res_pop_tv.setText(Settings.getword(CompanyActvity.this, "reschedule"));
        res_ll = (LinearLayout) findViewById(R.id.resheduled_ll);
        res_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                res_pop.setVisibility(View.VISIBLE);
            }
        });

        res_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(CompanyActvity.this, new TimePickerDialog.OnTimeSetListener() {
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
                        String time1 = new StringBuilder().append(hour).append(':')
                                .append(min ).append(" ").append(timeSet).toString();
                        res_time_tv.setText(time1);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });
        res_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CompanyActvity.this, new DatePickerDialog.OnDateSetListener() {
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
                        res_date_tv.setText(date1);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        res_title_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (res_time_tv.getText().toString().equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_time"), Toast.LENGTH_SHORT).show();
                    alert.showAlertDialog(CompanyActvity.this, "Info", Settings.getword(CompanyActvity.this, "empty_time"), false);
                else if (res_date_tv.getText().toString().equals(""))
//                        Toast.makeText(getActivity(), Settings.getword(getActivity(),"empty_date"), Toast.LENGTH_SHORT).show();
                    alert.showAlertDialog(CompanyActvity.this, "Info", Settings.getword(CompanyActvity.this, "empty_date"), false);
                else{
                 get_cancel(ord_id,"7");
               }
            }
        });
        cancel_ll = (LinearLayout) findViewById(R.id.order_change_ll);
        cancel_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grade = new ArrayList<String>();
                grade.add("Pending");
                grade.add("InProcess");
                grade.add("Shipped");
                grade.add("Delivered");
                grade.add("Completed");
                grade.add("Cancelled");
                grade.add("Returned");
                grade.add("Refunded");
                AlertDialog.Builder builder = new AlertDialog.Builder(CompanyActvity.this);
                builder.setTitle(Settings.getword(CompanyActvity.this, "change_status"));
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CompanyActvity.this, android.R.layout.select_dialog_item, grade);
                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(ChooseSubjectActivity.this, grade.get(which), Toast.LENGTH_SHORT).show();
                        if(which==7){
                            which=9;
                        }
//                        sta=grade.get(which);
//                        Log.e("status_id",sta);
                        get_cancel(ord_id,String.valueOf(which));

                    }
                });

                final AlertDialog dialog = builder.create();
                dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                dialog.show();
            }

    });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewFlipper.setDisplayedChild(1);
                filter.setVisibility(View.GONE);
                title_orders.setText(Settings.getword(CompanyActvity.this, "order_summary"));
//                if (orders.get(i).status.equals("Pending")) {
//                    cancel_ll.setVisibility(View.VISIBLE);
//                } else {
//                    cancel_ll.setVisibility(View.GONE);
//                }
                ord_id = orders.get(i).order_id;
                o_id.setText(orders.get(i).order_id);
                o_time.setText(orders.get(i).order_time);
                o_item.setText(orders.get(i).get_item_name(CompanyActvity.this));
                o_p_time.setText(orders.get(i).p_date + "  " + orders.get(i).p_time);
                o_d_time.setText(orders.get(i).d_date + "  " + orders.get(i).d_time);
                o_status.setText(orders.get(i).status);
                p_add1.setText(orders.get(i).pick_fname);
                p_add2.setText(orders.get(i).get_p_area_name(CompanyActvity.this));
                p_add3.setText(orders.get(i).p_house);
                p_add4.setText(orders.get(i).p_block);
                p_add5.setText(orders.get(i).p_build);
                p_add6.setText(orders.get(i).p_street);
                d_add1.setText(orders.get(i).drop_fname);
                d_add2.setText(orders.get(i).get_d_area_name(CompanyActvity.this));
                d_add3.setText(orders.get(i).d_house);
                d_add4.setText(orders.get(i).d_block);
                d_add5.setText(orders.get(i).d_build);
                d_add6.setText(orders.get(i).d_street);
                com_name.setText(orders.get(i).get_com_name(CompanyActvity.this));
                cost.setText(orders.get(i).cost + " KD");
                Log.e("image", orders.get(i).logoo);
                Picasso.with(CompanyActvity.this).load(orders.get(i).logoo).into(logoo);
                user_name.setText(orders.get(i).mem_name);
                user_email.setText(orders.get(i).mem_email);
                user_phone.setText(orders.get(i).mem_phone);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (viewFlipper.getDisplayedChild() == 1) {
            res_pop.setVisibility(View.GONE);
            title_orders.setText(Settings.getword(this, "my_orders"));
            filter.setVisibility(View.VISIBLE);
            viewFlipper.setDisplayedChild(0);

        }else
            finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
    public void get_orders(){
        String url = null;
        try {
            url = Settings.SERVERURL + "invoice-json.php?"+"member_id="+ URLEncoder.encode(Settings.getcomUserid(this),
                    "utf-8")+"&type=admin";
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(this);
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
                    if(orders.size() == 0) {
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
                Toast.makeText(CompanyActvity.this, "Server not connected", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }
    public void get_cancel(String id, final String sta_id){
        String url = null;
        try {
            if(sta_id.equals("7")){
                url = Settings.SERVERURL + "order-status.php?"+"invoice_id="+ URLEncoder.encode(id,"utf-8")+"&status="+ URLEncoder.encode(sta_id,"utf-8")
                        +"&drop_time="+ URLEncoder.encode(res_time_tv.getText().toString(),"utf-8")+"&drop_date="+ URLEncoder.encode(res_date_tv.getText().toString(),"utf-8");
            }else {
                url = Settings.SERVERURL + "order-status.php?" + "invoice_id=" + URLEncoder.encode(id, "utf-8") + "&status=" + URLEncoder.encode(sta_id, "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(CompanyActvity.this);
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
                        alert.showAlertDialog(CompanyActvity.this, "Info",msg, false);
                    }
                    else {
                        String msg = jsonObject.getString("message");
//                        Toast.makeText(CompanyActvity.this, msg, Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(CompanyActvity.this, "Info", msg, false);
                        if(sta_id.equals("7")){
                            res_pop.setVisibility(View.GONE);
                            viewFlipper.setDisplayedChild(0);
                            filter.setVisibility(View.VISIBLE);
                            get_orders();
                        }else{
                            viewFlipper.setDisplayedChild(0);
                            filter.setVisibility(View.VISIBLE);
                            get_orders();
                        }
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
                Toast.makeText(CompanyActvity.this, "Server not connected", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }
}

