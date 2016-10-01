package com.yellowsoft.onitsway;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chinni on 19-04-2016.
 */
public class AllApis {
    AlertDialogManager alert = new AlertDialogManager();
      String fname,lname,phone,email,password,name,message,drop_area_id;
      Context context;
     public void signup(final Context context,final String fname,final String lname,final String phone,final String email,final String password,final LoginSignupFragment.FragmentTouchListner mcallback){
         this.context=context;
         this.fname=fname;
         this.lname=lname;
         this.phone=phone;
         this.email=email;
         this.password=password;
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(Settings.getword(context, "please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url = Settings.SERVERURL+"add-member.php";
        Log.e("url",url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        Log.e("response",response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String reply=jsonObject.getString("status");
                            if(reply.equals("Failed")) {
                                String msg = jsonObject.getString("message");
//                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                alert.showAlertDialog(context, "Info", msg, false);
//                                LoginSignupFragment.reg();

                            }
                            else {
                                String mem_id=jsonObject.getString("member_id");
                                String msg = jsonObject.getString("message");
//                                String name=jsonObject.getString("name");
//                                Settings.setUserid(context, mem_id, "",msg);
//                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                alert.showAlertDialog(context, "Info", msg, false);
//                                update_gcm(mem_id, context);;
                                mcallback.signup_to_login();
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
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("fname",fname);
                params.put("lname",lname);
                params.put("phone",phone);
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    public void login(final Context context,String email,String password,final LoginSignupFragment.FragmentTouchListner mCallback){
        this.context=context;
        this.email=email;
        this.password=password;
        String url = Settings.SERVERURL+"login.php?";
        try {
            url = url +"type=member&email="+ URLEncoder.encode(email, "utf-8")+
                    "&password="+URLEncoder.encode(password,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(Settings.getword(context,"please_wait"));
        progressDialog.show();
        progressDialog.setCancelable(false);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                try {
                    String reply=jsonObject.getString("status");
                    if(reply.equals("Failure")) {
                        String msg = jsonObject.getString("message");
//                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(context, "Info", msg, false);
                    }
                    else {
                        String mem_id=jsonObject.getString("member_id");
                        String name=jsonObject.getString("name");
                        String phone=jsonObject.getString("phone");
                        Settings.setUserid(context, mem_id, name, phone);
                        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
//                        alert.showAlertDialog(context, "Info", name, false);
//                        update_gcm(mem_id, context);
                        mCallback.after_login();
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
                Toast.makeText(context, "Server not connected", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
    public void contact_us(final Context context,String name,String email,String phone,String message ){
        this.context=context;
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.message=message;
        final ProgressDialog progressDialog= new ProgressDialog(context);
        progressDialog.setMessage(Settings.getword(context,"please_wait"));
        progressDialog.show();
        String url = null;

        try {
            url = Settings.SERVERURL+"contact-form.php?phone=+965"+phone+
                    "&name="+ URLEncoder.encode(name, "utf-8")+
                    "&email="+ URLEncoder.encode(email, "utf-8")
                    +"&message="+ URLEncoder.encode(message, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("register url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Log.e("response is",jsonObject.toString());
                try{
                    Log.e("response is",jsonObject.getString("response"));
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String result = jsonObject1.getString("status");
                    if(result.equals("Success")) {
                        //finish();
//                        Toast.makeText(context, Settings.getword(context,"message_sent_successfully"), Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(context, "Info", Settings.getword(context,"message_sent_successfully"), false);
                    }
                    else{
//                        Toast.makeText(context, Settings.getword(context,"please_try_again"), Toast.LENGTH_SHORT).show();
                        alert.showAlertDialog(context, "Info", Settings.getword(context,"please_try_again"), false);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("error response is:", error.toString());
                if(progressDialog!=null)
                    progressDialog.dismiss();
//                Toast.makeText(context, Settings.getword(context,"please_try_again"), Toast.LENGTH_SHORT).show();
                alert.showAlertDialog(context, "Info", Settings.getword(context, "please_try_again"), false);

            }
        });

        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    CompanyGridAdapter adapter1;
    public CompanyGridAdapter company_grid(final Context context, final CompaniesGridFragment companiesGridFragment){
        this.context=context;
        final ArrayList<CompanyDetails> companies = new ArrayList<CompanyDetails>();
        adapter1= new CompanyGridAdapter(context,companies);;
        String url = Settings.SERVERURL + "services-json.php";
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonArray.toString());
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject sub = jsonArray.getJSONObject(i);
                        String company_id = sub.getString("id");
                        String title = sub.getString("title" + Settings.get_lan(context));
                        String description = sub.getString("description"+Settings.get_lan(context));
                        String cur_status=sub.getString("current_status");
                        String image= sub.getString("image");
                        String rating=sub.getString("rating");
                        String established=sub.getString("established");
                        JSONArray places_not=sub.getJSONArray("places_not");
                        JSONArray items_not=sub.getJSONArray("items_not");
                        CompanyDetails companyDetails = new CompanyDetails(company_id,title, description,cur_status,established,rating,places_not,items_not,image);
                        companies.add(companyDetails);

                    }
                    adapter1.notifyDataSetChanged();
                    companiesGridFragment.adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(context, "Server not connected", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
      return adapter1;
    }

    CompanylistAdapter adapter2;
    public CompanylistAdapter company_list(final Context context, final CompanieslistFragment companieslistFragment){
        this.context=context;
        final ArrayList<CompanyDetails> companies = new ArrayList<CompanyDetails>();
        adapter2= new CompanylistAdapter(context,companies);;
        String url = null;
        try {
            url = Settings.SERVERURL + "prices-json.php?"+"&from="+ URLEncoder.encode(Settings.get_pickup_area_id(context), "utf-8")+
                    "&to="+URLEncoder.encode(Settings.get_drop_off_area_id(context), "utf-8")
                    +"&item="+URLEncoder.encode(Settings.get_item_id(context), "utf-8");
            Log.e("url--->", url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonArray.toString());
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject sub = jsonArray.getJSONObject(i);
                        String company_id = sub.getString("id");
                        String title = sub.getString("title"+Settings.get_lan(context));
                        String description = sub.getString("description" + Settings.get_lan(context));
                        String image= sub.getString("image");
                        String cur_status=sub.getString("current_status");
                        String rating=sub.getString("rating");
                        String established=sub.getString("established");
                        String price_pick=sub.getString("pick");
                        String price_drop=sub.getString("drop");


                        CompanyDetails companyDetails = new CompanyDetails(company_id,title, description,image,cur_status,rating,established,price_pick,price_drop);
                        companies.add(companyDetails);

                    }
                    adapter2.notifyDataSetChanged();
                    companieslistFragment.adapter.notifyDataSetChanged();
                    if(companies.size()==0){
                        companieslistFragment.no_service.setVisibility(View.VISIBLE);
                    }
                    else
                        companieslistFragment.no_service.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(context, "Server not connected", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        return adapter2;
    }
    public  void place_order(final Context context, final SummaryFragment.FragmentTouchListner mcallback){

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("please wait.. we are processing your order");
        progressDialog.show();
        progressDialog.setCancelable(false);

        String url = Settings.SERVERURL+"place-order.php";
        Log.e("response is:", url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        Log.e("response is:", response.toString());
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String reply=jsonObject.getString("status");
                            if(reply.equals("Failure")) {
                                String msg = jsonObject.getString("message");
//                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                alert.showAlertDialog(context, "Info", msg, false);
                            }
                            else {
                                String msg = jsonObject.getString("message");
                                String id = jsonObject.getString("order_id");
                                Settings.setOrderid(context,id);
//                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                                alert.showAlertDialog(context, "Info",msg, false);
                                mcallback.invoice();
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
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("member_id",Settings.getUserid(context));
                params.put("content",Settings.get_order_json(context));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    OrderStatusAdapter adapter3;
    public OrderStatusAdapter order_list(final Context context, final OrderStatusFragment orderStatusFragment){
        this.context=context;
        final ArrayList<OrderDetails> orders = new ArrayList<OrderDetails>();
        adapter3= new OrderStatusAdapter(context,orders);;
        String url = null;
        try {
            url = Settings.SERVERURL + "invoice-json.php?"+"member_id="+ URLEncoder.encode(Settings.getUserid(context), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(context);
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
                        OrderDetails orderDetails = new OrderDetails(order_id,order_time,payment,status,sub);
                        orders.add(orderDetails);
                    }
                    adapter3.notifyDataSetChanged();
                    orderStatusFragment.adapter.notifyDataSetChanged();
                    if(orders.size()==0){
                        orderStatusFragment.no_ser.setVisibility(View.VISIBLE);
                    }
                    else
                        orderStatusFragment.no_ser.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                Toast.makeText(context, "Server not connected", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        return adapter3;
    }
    public void update_gcm(String memberid , final Context context) {
        this.context = context;
        String url = null;
//        url = CommonUtilities.SERVER_URL+"?member_id="+Settings.getUserid(context)+ "&device_token=" + Settings.get_gcmid(context);
        Log.e("register url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.e("response is", jsonObject.toString());
                try {
                    Log.e("response is", jsonObject.getString("response"));
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String result = jsonObject1.getString("status");
                    if (result.equals("Success")) {
                        //finish();
                        //  Toast.makeText(context, Settings.getword(context, "contact_us_message_sent"), Toast.LENGTH_SHORT).show();

                    } else {
                        //  Toast.makeText(context, Settings.getword(context, "please_try_again"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("error response is:", error.toString());
                //  Toast.makeText(context, Settings.getword(context, "please_try_again"), Toast.LENGTH_SHORT).show();

            }
        });

        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
}




