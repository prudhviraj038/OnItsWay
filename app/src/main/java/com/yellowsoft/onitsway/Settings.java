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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;


public class Settings {
    public static final String SERVERURL = "http://www.clients.yellowsoft.in/onitsway/api/";
    public static final String PAYMENT_URL    = "http://clients.yellowsoft.in/onitsway/api/checkout-json.php";
    public static final String USERID = "education_id";
    public static final String NAME = "education_name";
    static String words_key = "danden_words";
    static String lan_key = "radio_lan";

    public static void setUserid(Context context, String member_id, String name) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERID, member_id);
        editor.putString(NAME, name);
        editor.commit();
    }

    public static String getUserid(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USERID, "-1");

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

}