package com.yellowsoft.onitsway;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class AreaAdapter extends BaseAdapter{
    int price;
    ArrayList<String> notificationses;
    String [] result;
    Context context;
    int [] imageId;
    NotificationFragment promotionsListFragment;
    private static LayoutInflater inflater=null;
    public AreaAdapter(Context mainActivity, ArrayList<String> notificationses) {
//        notificationses=    ArrayList<String.> notificationses;

        // TODO Auto-generated constructor stub
        //  result=prgmNameList;
        context=mainActivity;
        this.notificationses=notificationses;
//          imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return notificationses.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        MyTextView offer_nam;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.area_list_item, null);
        holder.offer_nam = (MyTextView) rowView.findViewById(R.id.area_list_name_search);
        Log.e("name111",notificationses.get(position));
        holder.offer_nam.setText(notificationses.get(position));
            return rowView;
        }

    }
