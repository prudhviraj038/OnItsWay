package com.yellowsoft.onitsway;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

public class Invoicefragment extends Fragment {
    MyTextView sta_o_summary,sta_o_id,sta_o_time,sta_p_time,sta_d_time,sta_o_status,sta_com_details,sta_p_add,sta_d_add,sta_total,sta_total_cost,
            o_id,o_time,o_status,o_p_time,o_d_time,p_add1,p_add2,p_add3,d_add1,d_add2,d_add3,com_name,cost,sta_o_item,o_item;
    ImageView logo;
    String head;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void text_back_butt(String header);
        public  Animation get_animation(Boolean enter);
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
        return inflater.inflate(R.layout.invoice_screen, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        sta_o_summary=(MyTextView)v.findViewById(R.id.invoice_sta_order_summary);
        sta_o_summary.setText(Settings.getword(getActivity(), "order_summary"));
        sta_o_id=(MyTextView)v.findViewById(R.id.sta_o_id);
        sta_o_id.setText(Settings.getword(getActivity(),"order_id"));
        sta_o_time=(MyTextView)v.findViewById(R.id.sta_o_time);
        sta_o_time.setText(Settings.getword(getActivity(),"order_time"));
        sta_o_item=(MyTextView)v.findViewById(R.id.sta_item_in);
        sta_o_item.setText(Settings.getword(getActivity(),"item_type"));
        sta_p_time=(MyTextView)v.findViewById(R.id.sta_o_p_time);
        sta_p_time.setText(Settings.getword(getActivity(),"time_pickup"));
        sta_d_time=(MyTextView)v.findViewById(R.id.sta_o_d_time);
        sta_d_time.setText(Settings.getword(getActivity(),"time_dropoff"));
        sta_o_status=(MyTextView)v.findViewById(R.id.sta_status);
        sta_o_status.setText(Settings.getword(getActivity(),"status"));
        sta_com_details=(MyTextView)v.findViewById(R.id.invoice_sta_com_details);
        sta_com_details.setText(Settings.getword(getActivity(),"title_company_details"));
        sta_p_add=(MyTextView)v.findViewById(R.id.sta_p_add_invoice);
        sta_p_add.setText(Settings.getword(getActivity(),"pickup_address"));
        sta_d_add=(MyTextView)v.findViewById(R.id.sta_d_add_invoice);
        sta_d_add.setText(Settings.getword(getActivity(),"drop_address"));
        sta_total=(MyTextView)v.findViewById(R.id.sta_total_invoice);
        sta_total.setText(Settings.getword(getActivity(),"total_cost"));
        sta_total_cost=(MyTextView)v.findViewById(R.id.sta_total_cost_invoice);
        sta_total_cost.setText(Settings.getword(getActivity(),"total_cost"));

        o_id=(MyTextView)v.findViewById(R.id.o_id);
        o_time=(MyTextView)v.findViewById(R.id.o_time);
        o_item=(MyTextView)v.findViewById(R.id.item_in);
        o_p_time=(MyTextView)v.findViewById(R.id.o_p_time);
        o_d_time=(MyTextView)v.findViewById(R.id.o_d_time);
        o_status=(MyTextView)v.findViewById(R.id.status_invoice);
        p_add1=(MyTextView)v.findViewById(R.id.p_add_invoice1);
        p_add2=(MyTextView)v.findViewById(R.id.p_add_invoice2);
        p_add3=(MyTextView)v.findViewById(R.id.p_add_invoice3);
        d_add1=(MyTextView)v.findViewById(R.id.d_add_invoice1);
        d_add2=(MyTextView)v.findViewById(R.id.d_add_invoice2);
        d_add3=(MyTextView)v.findViewById(R.id.d_add_invoice3);
        com_name=(MyTextView)v.findViewById(R.id.com_name_invoice);
        cost=(MyTextView)v.findViewById(R.id.total_invoice);

        logo=(ImageView)v.findViewById(R.id.com_logo_invoice);
    }

}