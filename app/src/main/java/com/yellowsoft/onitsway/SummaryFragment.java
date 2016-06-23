package com.yellowsoft.onitsway;

/**
 * Created by Chinni on 05-04-2016.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.JsonArrayRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class SummaryFragment extends Fragment {
    AllApis allApis=new AllApis();
    int price;
    CompanyDetails companyDetails;
    TextView pick_address1,pick_address2,pick_address3,drop_address1,drop_address2,drop_address3,pick_price,drop_price,total_price,pay;
    ImageView image,star1,star2,star3,star4,star5;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void to_payment(String user_id,String price);
        public void setting_butt();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.summary_screen, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        mCallBack.setting_butt();
        Bundle args = getArguments();
        companyDetails = (CompanyDetails)args.getSerializable("company");
        pay=(TextView)v.findViewById(R.id.pay);
        image=(ImageView)v.findViewById(R.id.order_logo);
        pick_address1=(TextView)v.findViewById(R.id.tv_pickup_add1);
        pick_address2=(TextView)v.findViewById(R.id.tv_pickup_add2);
        pick_address3=(TextView)v.findViewById(R.id.tv_pickup_add3);
        drop_address1=(TextView)v.findViewById(R.id.tv_drop_add1);
        drop_address2=(TextView)v.findViewById(R.id.tv_drop_add2);
        drop_address3=(TextView)v.findViewById(R.id.tv_drop_add3);
        pick_price=(TextView)v.findViewById(R.id.pick_amount);
        drop_price=(TextView)v.findViewById(R.id.delivery_amount);
        total_price=(TextView)v.findViewById(R.id.total_amount);
        try {
            JSONObject jsonObject=new JSONObject(Settings.get_order_json(getActivity()));
            pick_address1.setText(jsonObject.getString("pick_block")+","+jsonObject.getString("pick_house")+","+jsonObject.getString("pick_build"));
            pick_address2.setText(jsonObject.getString("pick_street"));
            pick_address3.setText(jsonObject.getString("pick_area_name"));
            drop_address1.setText(jsonObject.getString("drop_block")+","+jsonObject.getString("drop_house")+","+jsonObject.getString("drop_building"));
            pick_address2.setText(jsonObject.getString("drop_street"));
            pick_address3.setText(jsonObject.getString("drop_area_name"));
            pick_price.setText(companyDetails.price_pickup+"KD");
            drop_price.setText(companyDetails.price_drop_off+"KD");
            price=Integer.parseInt(companyDetails.price_pickup)+Integer.parseInt(companyDetails.price_drop_off);
            total_price.setText(String.valueOf(price)+"KD");
            Picasso.with(getActivity()).load(companyDetails.logo).into(image);
            jsonObject.put("company_id", companyDetails.company_id);
            jsonObject.put("company_name", companyDetails.title1);
            jsonObject.put("pick_price",companyDetails.price_pickup);
            jsonObject.put("pick_price",companyDetails.price_drop_off);
            jsonObject.put("total_price",String.valueOf(price));
            Settings.set_order_json(getActivity(),jsonObject.toString());
//            total_price.setText();
        } catch (JSONException e) {
            e.printStackTrace();
        }
      pay.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            //  mCallBack.to_payment(Settings.getUserid(getActivity()),String.valueOf(price));
              allApis.place_order(getActivity());
          }
      });
    }
}