package com.yellowsoft.onitsway;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        ImageView imageView=(ImageView)findViewById(R.id.splash_img);
        imageView.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                newone();
            }
        }, 2000);

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
    public  void newone(){
        about_us();
        contact_us();
        what_we_do();
        String abc= Settings.get_user_language(MainActivity.this);
        Log.e("lng", abc);
        if(abc.equals("-1")) {
            Intent mainIntent = new Intent(getApplicationContext(), LanguageActvity.class);
            startActivity(mainIntent);
            finish();
        }
        else {
            Intent mainIntent = new Intent(getApplicationContext(), NavigationActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }

    private void about_us() {
        final String[] about = new String[1];
        String url = Settings.SERVERURL + "aboutus-json.php";
        Log.e("url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                Log.e("response is: ", jsonObject.toString());
                Settings.setAboutUs(MainActivity.this, jsonObject.toString());


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
                Toast.makeText(MainActivity.this, "Cannot reach our servers, Check your connection", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
    private void contact_us() {
        final String[] about = new String[1];
        String url = Settings.SERVERURL + "contactus-json.php";
        Log.e("url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                Log.e("response is: ", jsonObject.toString());
                Settings.setContactUs(MainActivity.this, jsonObject.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
                Toast.makeText(MainActivity.this, "Cannot reach our servers, Check your connection", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
    private void what_we_do() {
        final String[] about = new String[1];
        String url = Settings.SERVERURL + "whatwedo-json.php";
        Log.e("url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                Log.e("response is: ", jsonObject.toString());
                Settings.setWhatWeDo(MainActivity.this, jsonObject.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
                Toast.makeText(MainActivity.this, "Cannot reach our servers, Check your connection", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
}

