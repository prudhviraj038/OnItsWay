package com.yellowsoft.onitsway;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DropOffFragment extends Fragment {
    EditText fname,block,building,street,house,mobile;
    LinearLayout area,pick_up_submit;
    String areas_id="0";
    ArrayList<String> area_id;
    ArrayList<String> area_title;
    TextView area_tv,done;
    String area_name;
    AllApis allApis=new AllApis();
    FragmentTouchListner mCallBack;
    Context context;
    public interface FragmentTouchListner {
        public void from_drop_off();
    }
    public void onAttachFragment(Fragment fragment)
    {
        try
        {
            mCallBack = (PickupDropoffAddressFragment)fragment;

        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(
                    fragment.toString() + " must implement OnPlayerSelectionSetListener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.drop_off_screen, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        onAttachFragment(getParentFragment());
        pick_up_submit = (LinearLayout)v.findViewById(R.id.pickup_submit);
        area_id= new ArrayList<String>();
        area_title=new ArrayList<String>();
        fname=(EditText)v.findViewById(R.id.drop_fname);
        block=(EditText)v.findViewById(R.id.dropoff_block);
        building=(EditText)v.findViewById(R.id.dropoff_building);
        street=(EditText)v.findViewById(R.id.dropoff_street);
        house=(EditText)v.findViewById(R.id.dropoff_house);
        mobile=(EditText)v.findViewById(R.id.dropoff_mobile);
        area=(LinearLayout)v.findViewById(R.id.drop_area);
        area_tv=(TextView)v.findViewById(R.id.drop_area_tv);
        done=(TextView)v.findViewById(R.id.done);
        if (!Settings.get_order_json(getActivity()).equals("-1")) {
        try {
            JSONObject jsonObject=new JSONObject(Settings.get_order_json(getActivity()));
            fname.setText(jsonObject.getString("drop_fname"));
            area_tv.setText(jsonObject.getString("drop_area_name"));
            block.setText(jsonObject.getString("drop_block"));
            building.setText(jsonObject.getString("drop_building"));
            street.setText(jsonObject.getString("drop_street"));
            house.setText(jsonObject.getString("drop_house"));
            mobile.setText(jsonObject.getString("drop_mobile"));
        } catch (JSONException e) {
            e.printStackTrace();
        }}
        area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("CHOOSE AREA");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, area_title);
                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(ChooseSubjectActivity.this, sub_title.get(which), Toast.LENGTH_SHORT).show();
                        areas_id = area_id.get(which);
                        area_name=area_title.get(which);
                        area_tv.setText(area_name);
                    }
                });

                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        get_area();
        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String full_name=fname.getText().toString();
                String block1=block.getText().toString();
                String build=building.getText().toString();
                String streetname=street.getText().toString();
                String house1=house.getText().toString();
                String mobile1=mobile.getText().toString();
                    if (full_name.equals(""))
                        Toast.makeText(getActivity(), "Please Enter FullName", Toast.LENGTH_SHORT).show();
                    else if (areas_id.equals("0"))
                        Toast.makeText(getActivity(), "Please Enter Area", Toast.LENGTH_SHORT).show();
                    else if (block1.equals(""))
                        Toast.makeText(getActivity(), "Please Enter Block", Toast.LENGTH_SHORT).show();
                    else if (build.equals(""))
                        Toast.makeText(getActivity(), "Please Enter Building Name", Toast.LENGTH_SHORT).show();
                    else if (streetname.equals(""))
                        Toast.makeText(getActivity(), "Please Enter Street Name", Toast.LENGTH_SHORT).show();
                    else if (house1.equals(""))
                        Toast.makeText(getActivity(), "Please Enter House Name", Toast.LENGTH_SHORT).show();
                    else if (mobile1.equals(""))
                        Toast.makeText(getActivity(), "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                    else {


                        try {
                            JSONObject jsonObject = new JSONObject(Settings.get_order_json(getActivity()));
                            jsonObject.put("to",areas_id);
                            jsonObject.put("drop_fname",full_name);
                            jsonObject.put("drop_area_name",area_name);
                            jsonObject.put("drop_block",block1);
                            jsonObject.put("drop_building",build);
                            jsonObject.put("drop_street",streetname);
                            jsonObject.put("drop_house",house1);
                            jsonObject.put("drop_mobile", mobile1);
                            fname.setText(jsonObject.getString("drop_fname"));
                            area_tv.setText(jsonObject.getString("drop_area_name"));
                            block.setText(jsonObject.getString("drop_block"));
                            building.setText(jsonObject.getString("drop_building"));
                            street.setText(jsonObject.getString("drop_street"));
                            house.setText(jsonObject.getString("drop_house"));
                            mobile.setText(jsonObject.getString("drop_mobile"));
                            Settings.set_order_json(getActivity(), jsonObject.toString());
                            mCallBack.from_drop_off();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
            }
        });
    }
    private void get_area(){
        String url=Settings.SERVERURL+"locations-json.php";
        Log.e("url--->", url);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait....");
        progressDialog.setCancelable(false);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                Log.e("response is: ", jsonObject.toString());
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("locations");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject sub = jsonArray.getJSONObject(i);
                        String area_name = sub.getString("title"+Settings.get_lan(context));
                        String ar_id = sub.getString("id");
                        area_id.add(ar_id);
                        area_title.add(area_name);
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
                Toast.makeText(getActivity(), "Server not connected", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        });

// Access the RequestQueue through your singleton class.
        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }
}