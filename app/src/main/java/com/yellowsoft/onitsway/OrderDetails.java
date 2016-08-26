package com.yellowsoft.onitsway;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Chinni on 23-04-2016.
 */
public class OrderDetails {
    String order_id,order_time,payment,status,com_name,com_name_ar,logoo,item_name,item_name_ar,p_date,p_time,weight,pick_fname,p_house,p_block,p_build,
            p_street,p_area,p_area_ar,d_date,d_time,drop_fname,d_house,d_block,d_build,d_street,d_area,d_area_ar,d_mobile,cost,mem_id,
            mem_name,mem_email,mem_phone;
    JSONObject jsonObject;
     OrderDetails(String order_id,String order_time,String payment,String status,JSONObject jsonObject){
         this.order_id=order_id;
         this.order_time=order_time;
         this.payment=payment;
         this.status=status;
         this.jsonObject=jsonObject;
         try {
             com_name=jsonObject.getJSONObject("company").getString("name");
             com_name_ar=jsonObject.getJSONObject("company").getString("name_ar");
             logoo=jsonObject.getJSONObject("company").getString("image");
             item_name=jsonObject.getJSONObject("pick_item").getString("name");
             item_name_ar=jsonObject.getJSONObject("pick_item").getString("name_ar");
             p_date=jsonObject.getString("pick_date");
             p_time=jsonObject.getString("pick_time");
             weight=jsonObject.getString("pick_weight");
             pick_fname=jsonObject.getString("pick_fname");
             p_house=jsonObject.getString("pick_house");
             p_block=jsonObject.getString("pick_block");
             p_build=jsonObject.getString("pick_build");
             p_street=jsonObject.getString("pick_street");
             p_area=jsonObject.getJSONObject("pick_area").getString("name");
             p_area_ar=jsonObject.getJSONObject("pick_area").getString("name_ar");
             d_date=jsonObject.getString("drop_date");
             d_time=jsonObject.getString("drop_time");
             drop_fname=jsonObject.getString("drop_fname");
             d_house=jsonObject.getString("drop_house");
             d_block=jsonObject.getString("drop_block");
             d_build=jsonObject.getString("drop_building");
             d_street=jsonObject.getString("drop_street");
             d_area=jsonObject.getJSONObject("drop_area").getString("name");
             d_area_ar=jsonObject.getJSONObject("drop_area").getString("name_ar");
             d_mobile=jsonObject.getString("drop_mobile");
             cost=jsonObject.getString("total_price");
             mem_id=jsonObject.getJSONObject("member").getString("id");
             mem_name=jsonObject.getJSONObject("member").getString("name");
             mem_email=jsonObject.getJSONObject("member").getString("email");
             mem_phone=jsonObject.getJSONObject("member").getString("phone");
         } catch (JSONException e) {
             e.printStackTrace();
         }

     }
    public String get_com_name(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return com_name_ar;
        else
            return  com_name;
    }
    public String get_item_name(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return item_name_ar;
        else
            return  item_name;
    }
    public String get_p_area_name(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return p_area_ar;
        else
            return  p_area;
    }
    public String get_d_area_name(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return d_area_ar;
        else
            return  d_area;
    }
}
