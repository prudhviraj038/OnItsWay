package com.yellowsoft.onitsway;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by HP on 08-Sep-16.
 */
public class Area {
    String id,title,area_id,area_title,area_title_ar,name,street,block,building,flat,phone;
    Area(JSONObject jsonObject){
        try {
            id=jsonObject.getString("id");
            title=jsonObject.getString("title");
            area_id=jsonObject.getJSONObject("area").getString("id");
            area_title=jsonObject.getJSONObject("area").getString("title");
            area_title_ar=jsonObject.getJSONObject("area").getString("title_ar");
            name=jsonObject.getString("name");
            street=jsonObject.getString("street");
            block=jsonObject.getString("block");
            building=jsonObject.getString("building");
            flat=jsonObject.getString("house");
            phone=jsonObject.getString("phone");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String get_area_title(Context context) {
        if(Settings.get_user_language(context).equals("ar"))
            return area_title_ar;
        else
            return  area_title;
    }
}
