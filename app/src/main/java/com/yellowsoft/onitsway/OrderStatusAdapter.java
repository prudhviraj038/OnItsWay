package com.yellowsoft.onitsway;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderStatusAdapter extends BaseAdapter{
    String [] result;
    Context context;
    int [] imageId;
    ArrayList<OrderDetails> orderDetailses;
    private static LayoutInflater inflater=null;
    public OrderStatusAdapter(Context mainActivity,ArrayList<OrderDetails> orders) {
        // TODO Auto-generated constructor stub
        //  result=prgmNameList;
        context=mainActivity;
        orderDetailses=orders;
        //  imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return orderDetailses.size();
    }

    @Override
    public OrderDetails getItem(int position) {
        // TODO Auto-generated method stub
        return orderDetailses.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv,status_tv,pick_tim,pick_dat,drop_tim,drop_dat,compa_name;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.orderstatus_item_screen, null);
        holder.tv=(TextView) rowView.findViewById(R.id.order_number);
        holder.status_tv=(TextView) rowView.findViewById(R.id.order_status_dis);
        holder.pick_tim=(TextView) rowView.findViewById(R.id.order_pickup_time);
        holder.pick_dat=(TextView) rowView.findViewById(R.id.oeder_pickup_date);
        holder.drop_tim=(TextView) rowView.findViewById(R.id.order_dropoff_time);
        holder.drop_dat=(TextView) rowView.findViewById(R.id.order_dropoff_date);
        holder.compa_name=(TextView) rowView.findViewById(R.id.order_company_name);
           holder.tv.setText(orderDetailses.get(position).order_id);
        holder.status_tv.setText(orderDetailses.get(position).status);
        holder.pick_tim.setText(orderDetailses.get(position).pick_time);
        holder.pick_dat.setText(orderDetailses.get(position).pick_date);
        holder.drop_tim.setText(orderDetailses.get(position).drop_time);
        holder.drop_dat.setText(orderDetailses.get(position).drop_date);
        holder.compa_name.setText(orderDetailses.get(position).company_name);
        //  holder.tv.setText(result[position]);
        // holder.img.setImageResource(imageId[position]);

        return rowView;
    }

}