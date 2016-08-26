package com.yellowsoft.onitsway;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class NavigationListAdapter extends BaseAdapter{
    Context context;
    ArrayList<Integer> images;
    ArrayList<String> titles;
    private static LayoutInflater inflater=null;
    public NavigationListAdapter(Activity mainActivity, ArrayList<Integer> images, ArrayList<String> titles) {
        // TODO Auto-generated constructor stu
        context=mainActivity;
        this.images=images;
        this.titles=titles;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return images.size();
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
        MyTextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        if(convertView==null)
        rowView = inflater.inflate(R.layout.menu_item, parent,false);
        else
        rowView = convertView;
        holder.tv=(MyTextView)rowView.findViewById(R.id.title);
        holder.img=(ImageView) rowView.findViewById(R.id.icon);
        holder.tv.setText(titles.get(position));
        holder.img.setImageResource(images.get(position));
        return rowView;
    }

}