package com.yellowsoft.onitsway;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompaniesGridFragment extends Fragment {
    ArrayList<CompanyDetails> companies;
    CompanyGridAdapter adapter;
    AllApis allApis = new AllApis();
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void back_butt();
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
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                CompanyDetailsFragment companyDetailsFragment = new CompanyDetailsFragment();
                final Bundle bundle = new Bundle();
                bundle.putSerializable("company", adapter.getItem(position));
                companyDetailsFragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.container_main, companyDetailsFragment).addToBackStack(null).commit();
            }
        });


    }
}