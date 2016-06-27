package com.yellowsoft.onitsway;

/**
 * Created by Chinni on 05-04-2016.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CompanyDetailsFragment extends Fragment {
    CompanyDetails companyDetails;
    String com_id;
    LinearLayout places_layout,items_layout;
    int a;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void com_details_bar(String logo);
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
        return inflater.inflate(R.layout.companies_details_screen, container, false);
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        Bundle args = getArguments();
        companyDetails = (CompanyDetails)args.getSerializable("company");
        TextView name = (TextView)view.findViewById(R.id.tv_company_name);
        TextView description = (TextView)view.findViewById(R.id.descri);
        name.setText(companyDetails.title1);
        mCallBack.com_details_bar(companyDetails.logo);
        description.setText(Html.fromHtml(companyDetails.description));
        places_layout = (LinearLayout)view.findViewById(R.id.places_layout);
        items_layout = (LinearLayout) view.findViewById(R.id.items_layout);
        for(int i=0;i<companyDetails.places_not.size();i++){
            TextView temp = new TextView(getActivity());
            if(Settings.get_user_language_words(getActivity()).equals("ar"))
            temp.setText(companyDetails.places_not.get(i).title_ar);
            else
                temp.setText(companyDetails.places_not.get(i).title);
            places_layout.addView(temp);
        }

        for(int i=0;i<companyDetails.items_not.size();i++){
            TextView temp = new TextView(getActivity());
            if(Settings.get_user_language_words(getActivity()).equals("ar"))
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