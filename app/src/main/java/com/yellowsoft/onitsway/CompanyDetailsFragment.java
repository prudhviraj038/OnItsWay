package com.yellowsoft.onitsway;

/**
 * Created by Chinni on 05-04-2016.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CompanyDetailsFragment extends Fragment {
    CompanyDetails companyDetails;
    String com_id;
    LinearLayout places_layout,items_layout,rating;
    int a;
    MyTextView description,name,establish,sta_status,status,p_not,i_not;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void com_details_bar(String logo);
        public void text_back_butt(String head);
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
        return inflater.inflate(R.layout.companies_details_screen, container, false);
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        String head=Settings.getword(getActivity(), "title_company_details");
        Bundle args = getArguments();
        companyDetails = (CompanyDetails)args.getSerializable("company");
        name = (MyTextView)view.findViewById(R.id.tv_company_name);
        name.setText(companyDetails.title1);
        mCallBack.com_details_bar(companyDetails.logo);
        description = (MyTextView)view.findViewById(R.id.descri);
        description.setText(Html.fromHtml(companyDetails.description));
        establish = (MyTextView)view.findViewById(R.id.establish);
        establish.setText(Settings.getword(getActivity(),"established")+" "+companyDetails.established);
        sta_status = (MyTextView)view.findViewById(R.id.statuss);
        sta_status.setText(Settings.getword(getActivity(),"status"));
        status = (MyTextView)view.findViewById(R.id.status_tv);
        Log.e("status", companyDetails.current_status);
        status.setText(companyDetails.current_status);
        p_not = (MyTextView)view.findViewById(R.id.tv_places_not);
        p_not.setText(Settings.getword(getActivity(),"palce_dnt_deliver"));
        i_not = (MyTextView)view.findViewById(R.id.tv_items_not);
        i_not.setText(Settings.getword(getActivity(),"item_dnt_deliver"));
        rating = (LinearLayout)view.findViewById(R.id.rating_details_page_ll);
        Log.e("rating",companyDetails.rating);
        mCallBack.text_back_butt(head);
        Settings.set_rating(getActivity(), companyDetails.rating, rating);
        places_layout = (LinearLayout)view.findViewById(R.id.places_layout);
        items_layout = (LinearLayout) view.findViewById(R.id.items_layout);
        for(int i=0;i<companyDetails.places_not.size();i++){
            TextView temp = new TextView(getActivity());
            if(Settings.get_user_language(getActivity()).equals("ar"))
            temp.setText(companyDetails.places_not.get(i).title_ar);
            else
                temp.setText(companyDetails.places_not.get(i).title);
            places_layout.addView(temp);
        }

        for(int i=0;i<companyDetails.items_not.size();i++){
            TextView temp = new TextView(getActivity());
            if(Settings.get_user_language(getActivity()).equals("ar"))
                temp.setText(companyDetails.items_not.get(i).title_ar);
            else
                temp.setText(companyDetails.items_not.get(i).title);
            items_layout.addView(temp);
        }

        if(companyDetails.places_not.size()>companyDetails.items_not.size()) {
            a = companyDetails.places_not.size() - companyDetails.items_not.size();
            for (int i = 0; i < a; i++) {
                TextView temp = new TextView(getActivity());
                temp.setText("");
                items_layout.addView(temp);
            }
        }
            else if(companyDetails.places_not.size()<companyDetails.items_not.size()){
                a=companyDetails.items_not.size()-companyDetails.places_not.size();
                for (int i=0;i<a;i++){
                    TextView temp = new TextView(getActivity());
                    temp.setText("");
                    places_layout.addView(temp);
                }

            }

    }

}