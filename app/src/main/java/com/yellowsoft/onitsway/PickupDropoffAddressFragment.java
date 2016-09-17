package com.yellowsoft.onitsway;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class PickupDropoffAddressFragment extends Fragment implements PickUpFragment.FragmentTouchListner,
        DropOffFragment.FragmentTouchListner {
    MyTextView d_address,p_address;
    ImageView p_img,d_img;
    CompanyDetails companyDetails;
    FragmentTouchListner mCallBack;
    String type;
    public interface FragmentTouchListner {
        public void pick_drop_bar();
        public void text_back_butt(String head);
        public void after_drop_off(CompanyDetails companyDetails);
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
        return inflater.inflate(R.layout.pickup_dropoff_screen, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        String head=Settings.getword(getActivity(), "pickup_address");
        mCallBack.text_back_butt(head);
        final FragmentManager childFragMan = getChildFragmentManager();
        Bundle args = getArguments();
        companyDetails = (CompanyDetails)args.getSerializable("company");
        mCallBack.pick_drop_bar();
        p_img = (ImageView) view.findViewById(R.id.p_img);
        d_img = (ImageView) view.findViewById(R.id.d_img);
         p_address = (MyTextView) view.findViewById(R.id.pickup);
        p_address.setText(Settings.getword(getActivity(),"pickup_address"));
         d_address = (MyTextView) view.findViewById(R.id.dropoff);
        d_address.setText(Settings.getword(getActivity(), "drop_address"));
        final FrameLayout pick_container = (FrameLayout) view.findViewById(R.id.pickup_container);

        p_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String head=Settings.getword(getActivity(), "pickup_address");
                mCallBack.text_back_butt(head);
                p_img.setVisibility(View.VISIBLE);
                d_img.setVisibility(View.GONE);
                p_address.setBackgroundColor(Color.parseColor("#b469aa"));
                p_address.setTextColor(Color.parseColor("#ffffff"));
                d_address.setBackgroundColor(Color.parseColor("#e4e3e4"));
                d_address.setTextColor(Color.parseColor("#b0b0b0"));
                PickUpFragment fragment = new PickUpFragment();
                childFragMan.beginTransaction().replace(R.id.pickup_container, fragment).commit();
            }
        });
        p_address.performClick();
        p_address.setBackgroundColor(Color.parseColor("#b469aa"));
        p_address.setTextColor(Color.parseColor("#ffffff"));
        p_img.setVisibility(View.VISIBLE);
        d_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String head=Settings.getword(getActivity(), "drop_address");
                mCallBack.text_back_butt(head);
                d_img.setVisibility(View.VISIBLE);
                p_img.setVisibility(View.GONE);
                p_address.setBackgroundColor(Color.parseColor("#e4e3e4"));
                p_address.setTextColor(Color.parseColor("#b0b0b0"));
                d_address.setBackgroundColor(Color.parseColor("#b469aa"));
                d_address.setTextColor(Color.parseColor("#ffffff"));
                DropOffFragment fragment = new DropOffFragment();
                fragment.context=getActivity();
                childFragMan.beginTransaction().replace(R.id.pickup_container, fragment).commit();

            }
        });
     }
    @Override
    public void  from_drop_off(){
        mCallBack.after_drop_off(companyDetails);
    }
    @Override
    public void select_drop_off() {
        d_address.performClick();
    }
//    @Override
//    public void text(String head) {
//        mCallBack.text_back_butt(head);
//    }

}

