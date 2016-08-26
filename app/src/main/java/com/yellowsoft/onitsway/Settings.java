package com.yellowsoft.onitsway;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;


public class Settings {
    public static final String SERVERURL = "http://onitsway.net/api/";
    public static final String PAYMENT_URL    = "http://onitsway.net/Tap2.php?";
//    public static final String PAYMENT_URL    = "http://onitsway.net/Tap2-check.php?";
    public static final String USERID = "education_id";
    public static final String NAME = "education_name";
    public static final String PHONE = "education_phone";
    public static final String Com_USERID = "com_id";
    public static final String Com_NAME = "com_name";
    static String words_key = "danden_words";
    static String lan_key = "radio_lan";

    public static void setUserid(Context context, String member_id, String type, String mobile) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERID, member_id);
        editor.putString(NAME, type);
        editor.putString(PHONE, mobile);
        editor.commit();
    }

    public static String getUserid(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USERID, "-1");

    }
    public static String getUser_mobile(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PHONE, "-1");

    } public static String getUser_name(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(NAME, "-1");

    }
    public static void setcomUserid(Context context, String member_id, String type) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Com_USERID, member_id);
        editor.putString(Com_NAME, type);
        editor.commit();
    }
    public static String getcomUserid(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Com_USERID, "-1");

    }
    public static String getcomName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Com_NAME, "-1");

    }
    public static   void forceRTLIfSupported(Activity activity)
    {
        SharedPreferences sharedPref;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        Log.e("lan", sharedPref.getString(lan_key, "-1"));

        if (sharedPref.getString(lan_key, "-1").equals("en")) {
            Resources res = activity.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale("en".toLowerCase());
            res.updateConfiguration(conf, dm);
        }

        else if(sharedPref.getString(lan_key, "-1").equals("ar")){
            Resources res = activity.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale("ar".toLowerCase());
            res.updateConfiguration(conf, dm);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }

        else {
            Resources res = activity.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale("en".toLowerCase());
            res.updateConfiguration(conf, dm);
        }

    }
    static SharedPreferences sharedPreferences;
    public static void set_user_language(Context context,String user_id){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(lan_key,user_id);
        editor.commit();
    }
    public static String get_user_language(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(lan_key,"-1");
    }


    public static void set_user_language_words(Context context,String user_id){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(words_key,user_id);
        editor.commit();
    }

    public static String get_lan(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(sharedPreferences.getString(lan_key,"en").equals("ar"))
        {
            return "_ar";
        }
        return "";
    }
    public static JSONObject get_user_language_words(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(sharedPreferences.getString(words_key,"-1"));
            jsonObject = jsonObject.getJSONObject(get_user_language(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public static String getword(Context context,String word)
    {

        JSONObject words = get_user_language_words(context);

        try {
            return words.getString(word);
        } catch (JSONException e) {
            e.printStackTrace();
            return word;
        }
    }
    public static void setSettings(Context context, String setting) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("setting",setting);
        editor.commit();
    }

    public static String getSettings(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("setting", "-1");
    }
    public static void setAboutUs(Context context, String about_us) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("about_us",about_us);
        editor.commit();
    }

    public static String getAboutUs(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("about_us", "-1");
    }

    public static void setContactUs(Context context, String contact_us) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("contact_us",contact_us);
        editor.commit();
    }

    public static String getContactUs(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("contact_us", "-1");
    }
    public static void setWhatWeDo(Context context, String what_we_do) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("what_we_do",what_we_do);
        editor.commit();
    }

    public static String getWhatWeDo(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("what_we_do", "-1");
    }

    public static void set_order_json(Context context, String area_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("order",area_id);
        editor.commit();
    }
    public static  String get_order_json(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("order","-1");
    }
//    public static void set_check(Context context, String area_id) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("order",area_id);
//        editor.commit();
//    }
//    public static  String get_check(Context context) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        return sharedPreferences.getString("order","-1");
//    }
    public static void set_p_date(Context context, String th,String tm,String dy,String dm,String dd) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("th",th); editor.putString("tm",tm); editor.putString("dy",dy); editor.putString("dm",dm);
        editor.putString("dd",dd);
        editor.commit();
    }
    public static  String get_th(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("th","-1");
    }
    public static  String get_tm(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("tm","-1");
    }
    public static  String get_dy(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("dy","-1");
    }
    public static  String get_dm(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("dm","-1");
    }
    public static  String get_dd(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("dd","-1");
    }
    public static void set_type(Context context, String type) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("type",type);
        editor.commit();
    }

    public static  String get_type(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("type","-1");
    }
    public static void set_pickup_area(Context context, String area_id,String area_name) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("p_area",area_id);
        editor.putString("p_area_name",area_name);
        editor.commit();
    }
    public static  String get_pickup_area_id(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("p_area","-1");
    }
    public static  String get_pickup_area_name(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("p_area_name","-1");
    }
    public static void set_drop_off_area(Context context, String area_id,String area_name) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("d_area",area_id);
        editor.putString("d_area_name",area_name);
        editor.commit();
    }
    public static  String get_drop_off_area_id(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("d_area","-1");
    }
    public static  String get_drop_off_area_name(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("d_area_name","-1");
    }
    public static void set_item(Context context, String area_id ,String area_name) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Item",area_id);
        editor.putString("Item_name",area_name);
        editor.commit();
    }
    public static  String get_item_id(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("Item","-1");
    }
    public static  String get_item_name(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("Item_name","-1");
    }
    public static void set_weight(Context context, String area_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("weight",area_id);
        editor.commit();
    }
    public static  String get_weight(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("weight","-1");
    }
    public static  void set_rating(Context context,String value,LinearLayout rating_ll){

        rating_ll.removeAllViews();
        for(float i=1;i<=5;i++) {
            ImageView star = new ImageView(context);
            star.setMaxWidth(35);
            star.setMaxHeight(35);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(2,0,2,0);
            star.setLayoutParams(lp);
            star.setAdjustViewBounds(true);
            if(i<=Float.parseFloat(value))
                star.setImageResource(R.drawable.star_full);
            else if(i-Float.parseFloat(value)<1)
                star.setImageResource(R.drawable.star_half);
            else
                star.setImageResource(R.drawable.star_empty);
            rating_ll.addView(star);
        }
    }
    public static  void set_grid_rating(Context context,String value,LinearLayout rating_ll){

        rating_ll.removeAllViews();
        for(float i=1;i<=5;i++) {
            ImageView star = new ImageView(context);
            star.setMaxWidth(25);
            star.setMaxHeight(25);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(2,0,2,0);
            star.setLayoutParams(lp);
            star.setAdjustViewBounds(true);
            if(i<=Float.parseFloat(value))
                star.setImageResource(R.drawable.star_full);
            else if(i-Float.parseFloat(value)<1)
                star.setImageResource(R.drawable.star_half);
            else
                star.setImageResource(R.drawable.star_empty);
            rating_ll.addView(star);
        }
    }
    public static String get_isfirsttime(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("firsttime", "-1");
    }
    public static void set_isfirsttime(Context context, String gcm_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firsttime",gcm_id);
        editor.commit();
    }
    public static String get_gcmid(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("gcm_id", "-1");
    }
    public static void set_gcmid(Context context, String gcm_id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("gcm_id",gcm_id);
        editor.commit();
    }
}