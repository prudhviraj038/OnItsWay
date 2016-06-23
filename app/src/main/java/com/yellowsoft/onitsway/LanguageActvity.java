package com.yellowsoft.onitsway;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;


public class LanguageActvity extends Activity {
 TextView english,arabic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.langage_screen);
        english=(TextView)findViewById(R.id.lng_english);
        arabic=(TextView)findViewById(R.id.lag_arb);
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_language_words();
                Settings.set_user_language(LanguageActvity.this, "en");

            }
        });
        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_language_words();
                Settings.set_user_language(LanguageActvity.this, "ar");
            }
        });

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
    private void get_language_words(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(Settings.getword(this,"please_wait"));
        progressDialog.setCancelable(false);
        String url = Settings.SERVERURL+"words-json-android.php";
        Log.e("url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                Settings.set_user_language_words(LanguageActvity.this, jsonObject.toString());
                Intent intent = new Intent(LanguageActvity.this,NavigationActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                Log.e("error",error.toString());
                Toast.makeText(LanguageActvity.this, "Cannot reach our servers, Check your connection", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);


    }

}

