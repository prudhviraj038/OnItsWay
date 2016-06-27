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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompanieslistFragment extends Fragment {
    ArrayList<CompanyDetails> companies;
    CompanylistAdapter adapter;
    AllApis allApis = new AllApis();
    TextView no_service;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void back_butt();
        public void to_summery(CompanyDetails companyDetails);
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
       View rootview=inflater.inflate(R.layout.companies_list_screen, container, false);
               return rootview;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        GridView gridView=(GridView) view.findViewById(R.id.companies_list);
        mCallBack.back_butt();
        no_service = (TextView) view.findViewById(R.id.no_service);
        no_service.setText(Settings.getword(getActivity(),"no_service"));
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(Settings.get_order_json(getActivity()));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter=allApis.company_list(getActivity(),this,jsonObject);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallBack.to_summery(adapter.getItem(position));


            }
                    });
    }
}
