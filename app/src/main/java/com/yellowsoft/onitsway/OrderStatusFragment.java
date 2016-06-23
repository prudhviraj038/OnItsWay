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
import android.widget.GridView;
import android.widget.TextView;

public class OrderStatusFragment extends Fragment {
    AllApis allApis=new AllApis();
    OrderStatusAdapter adapter;
    TextView no_ser;
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
        View rootview=inflater.inflate(R.layout.orders_status_screen, container, false);
        return rootview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        no_ser = (TextView) view.findViewById(R.id.no_services);
        no_ser.setText(Settings.getword(getActivity(), "no_service"));
        mCallBack.back_butt();
        GridView gridView=(GridView) view.findViewById(R.id.orders_status_list);
        adapter=allApis.order_list(getActivity(), this);
        gridView.setAdapter(adapter);

    }
}