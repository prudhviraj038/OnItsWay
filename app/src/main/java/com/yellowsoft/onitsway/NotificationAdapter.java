package com.yellowsoft.onitsway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class NotificationAdapter extends BaseAdapter{
    int price;
    ArrayList<Notifications> notificationses;
    String [] result;
    Context context;
    int [] imageId;
    NotificationFragment promotionsListFragment;
    private static LayoutInflater inflater=null;
    public NotificationAdapter(Context mainActivity, ArrayList<Notifications> notificationses) {
//        notificationses=new ArrayList<>();
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
        MyTextView offer_name,offer_descri,pro_price;
        ImageView com_logo,offer_background;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.notification_item_screen, null);
        holder.offer_name = (MyTextView) rowView.findViewById(R.id.promotion_title);
        holder.offer_descri = (MyTextView) rowView.findViewById(R.id.promotion_description);
        holder.pro_price = (MyTextView) rowView.findViewById(R.id.promotion_price);
        holder.offer_background = (ImageView) rowView.findViewById(R.id.offer_img_promtions);

        holder.offer_name.setText(notificationses.get(position).titlee);
        holder.offer_descri.setText(notificationses.get(position).msg);
        holder.pro_price.setText(notificationses.get(position).type);
//        Picasso.with(context).load(restaurants.get(position).image).fit().into(holder.offer_background);
            return rowView;
        }

    }
