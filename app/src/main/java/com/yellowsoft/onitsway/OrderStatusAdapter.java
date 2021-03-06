package com.yellowsoft.onitsway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

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
        MyTextView tv,status_tv,pick_tim,pick_dat,drop_tim,drop_dat,compa_name,sta_ord_id,sta_status,
                sta_pick_time,sta_drop_time,sta_company,sta_p_area,p_area,sta_d_area,d_area;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.orderstatus_item_screen, null);
        holder.sta_ord_id=(MyTextView) rowView.findViewById(R.id.order_id);
        holder.sta_ord_id.setText(Settings.getword(context,"order_id"));
        holder.sta_status=(MyTextView) rowView.findViewById(R.id.status);
        holder.sta_status.setText(Settings.getword(context,"status"));
        holder.sta_pick_time=(MyTextView) rowView.findViewById(R.id.time_pick);
        holder.sta_pick_time.setText(Settings.getword(context,"time_pickup"));
        holder.sta_drop_time=(MyTextView) rowView.findViewById(R.id.date_dropoff);
        holder.sta_drop_time.setText(Settings.getword(context,"time_dropoff"));
        holder.sta_company=(MyTextView) rowView.findViewById(R.id.company);
        holder.sta_company.setText(Settings.getword(context,"company"));
        holder.sta_p_area=(MyTextView) rowView.findViewById(R.id.sta_o_p_area);
        holder.sta_p_area.setText(Settings.getword(context,"pickup_area"));
        holder.sta_d_area=(MyTextView) rowView.findViewById(R.id.sta_o_d_area);
        holder.sta_d_area.setText(Settings.getword(context,"drop_area"));
        holder.tv=(MyTextView) rowView.findViewById(R.id.order_number);
        holder.status_tv=(MyTextView) rowView.findViewById(R.id.order_status_dis);
        holder.pick_tim=(MyTextView) rowView.findViewById(R.id.order_pickup_time);
        holder.pick_dat=(MyTextView) rowView.findViewById(R.id.oeder_pickup_date);
        holder.drop_tim=(MyTextView) rowView.findViewById(R.id.order_dropoff_time);
        holder.drop_dat=(MyTextView) rowView.findViewById(R.id.order_dropoff_date);
        holder.compa_name=(MyTextView) rowView.findViewById(R.id.order_company_name);
        holder.p_area=(MyTextView) rowView.findViewById(R.id.o_p_area);
        holder.d_area=(MyTextView) rowView.findViewById(R.id.o_d_area);
           holder.tv.setText(orderDetailses.get(position).order_id);
        holder.status_tv.setText(orderDetailses.get(position).status);
        holder.pick_tim.setText(orderDetailses.get(position).p_time);
        holder.pick_dat.setText(orderDetailses.get(position).p_date);
        holder.drop_tim.setText(orderDetailses.get(position).d_time);
        holder.drop_dat.setText(orderDetailses.get(position).d_date);
        holder.compa_name.setText(orderDetailses.get(position).get_com_name(context));
        holder.p_area.setText(orderDetailses.get(position).p_area);
        holder.d_area.setText(orderDetailses.get(position).d_area);
        //  holder.tv.setText(result[position]);
        // holder.img.setImageResource(imageId[position]);

        return rowView;
    }

}