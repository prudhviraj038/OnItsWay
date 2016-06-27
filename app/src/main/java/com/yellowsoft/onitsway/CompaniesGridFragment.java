package com.yellowsoft.onitsway;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class CompaniesGridFragment extends Fragment {
    ArrayList<CompanyDetails> companies;
    CompanyGridAdapter adapter;
    AllApis allApis = new AllApis();
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void back_butt();
        public void goto_com_details(CompanyDetails companyDetails);
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
        View rootview = inflater.inflate(R.layout.companies_grid_screen, container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        GridView gridView = (GridView) view.findViewById(R.id.gridView_companies);
        adapter=allApis.company_grid(getActivity(),this);
        gridView.setAdapter(adapter);
        mCallBack.back_butt();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallBack.goto_com_details(adapter.getItem(position));
            }
        });


    }
}