package com.yellowsoft.onitsway;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Chinni on 04-05-2016.
 */
public class NotificationFragment extends Fragment {
    ArrayList<Notifications> notificationses;
    String id;
    NotificationAdapter promotionAdapter;
    FragmentTouchListner mCallBack;
//    AlertDialogManager alert = new AlertDialogManager();
    public interface FragmentTouchListner {
//        public void five_items();
//        public void songselected(Restaurants restaurant);
        public void text_back_butt(String head);
        public void my_orders_page();
          public Animation get_animation(Boolean enter);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBack = (NavigationActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Listner");
        }

    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return mCallBack.get_animation(enter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.notification_fragment_screen, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        String head=Settings.getword(getActivity(), "notifications");
        mCallBack.text_back_butt(head);
        GridView offerss=(GridView)view.findViewById(R.id.offer_list);
        notificationses = new ArrayList<>();
        getRestaurants();
        promotionAdapter=new NotificationAdapter(getActivity(),notificationses);
        offerss.setAdapter(promotionAdapter);
       offerss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               if(notificationses.get(position).type.equals("order"))
                   mCallBack.my_orders_page();
                   //getRestaurant("4");
//               else
//                   getRestaurant(typesid.get(position));
           }
       });
//        getRestaurants();
    }



    private void getRestaurants(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(Settings.getword(getActivity(),"please_wait"));
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url;
//        notificationses.clear();
        url = Settings.SERVERURL + "notifications.php?member_id="+Settings.getUserid(getActivity());
        Log.e("url", url);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jsonArray) {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                notificationses.clear();
                Log.e("reponse", jsonArray.toString());
                try {
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject tmp_json = jsonArray.getJSONObject(i);
                        Notifications temp = new Notifications(
                                tmp_json.getString("id"),
                                tmp_json.getString("title"),
                                tmp_json.getString("message"),
                                tmp_json.getString("type"),
                                tmp_json.getString("type_id"));
                        notificationses.add(temp);
                    }
                    promotionAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }}
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("response is:", error.toString());
                if(progressDialog!=null)
                    progressDialog.dismiss();

            }
        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
}