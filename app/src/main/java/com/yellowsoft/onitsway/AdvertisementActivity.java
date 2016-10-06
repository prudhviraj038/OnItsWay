package com.yellowsoft.onitsway;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

public class AdvertisementActivity extends Activity {
    ImageView img;
    MyTextView button;
    private final Handler handler = new Handler();

    private final Runnable startActivityRunnable = new Runnable() {
        @Override
        public void run() {
                newone();        }
    };

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advertisement_screen);
        img = (ImageView) findViewById (R.id.splash_image);
        LinearLayout skip_btn = (LinearLayout) findViewById(R.id.skip_btn);
        getAdvertisements();
        button=(MyTextView)findViewById(R.id.enter_but);
        button.setText(Settings.getword(AdvertisementActivity.this,"enter_word"));
        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handler.removeCallbacks(startActivityRunnable);
                newone();
            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onPause()
    {
        super.onPause();
        handler.removeCallbacks(startActivityRunnable);
    }
    public  void newone(){
                Intent mainIntent = new Intent(getApplicationContext(), NavigationActivity.class);
                mainIntent.putExtra("type","1");
                startActivity(mainIntent);
                finish();
        }
    private void getAdvertisements(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = Settings.SERVERURL+"advertisements.php";
        Log.e("url", url);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest( url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonObject) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                try {
                    String img_path = jsonObject.getJSONObject(0).getString("image");
                    Picasso.with(AdvertisementActivity.this).load(img_path).into(img);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                handler.postDelayed(startActivityRunnable, 5000);


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("response is:", error.toString());
                handler.postDelayed(startActivityRunnable, 1500);
            }
        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }

}
