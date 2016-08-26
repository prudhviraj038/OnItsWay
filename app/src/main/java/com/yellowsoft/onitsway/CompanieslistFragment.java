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
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class CompanieslistFragment extends Fragment {
    ArrayList<CompanyDetails> companies;
    CompanylistAdapter adapter;
    AllApis allApis = new AllApis();
    TextView no_service;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void back_butt();
        public void text_back_butt(String head);
        public void to_pickup(CompanyDetails companyDetails);
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
       View rootview=inflater.inflate(R.layout.companies_list_screen, container, false);
               return rootview;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        GridView gridView=(GridView) view.findViewById(R.id.companies_list);
        String head=Settings.getword(getActivity(),"couriers");
        mCallBack.text_back_butt(head);
        mCallBack.back_butt();
        no_service = (TextView) view.findViewById(R.id.no_service);
        no_service.setText(Settings.getword(getActivity(),"no_service"));
        adapter=allApis.company_list(getActivity(),this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallBack.to_pickup(adapter.getItem(position));


            }
                    });
        mCallBack.text_back_butt(head);
    }
}
