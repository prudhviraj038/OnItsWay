package com.yellowsoft.onitsway;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CompanylistAdapter extends BaseAdapter{
    int price;
    String [] result;
    Context context;
    int [] imageId;
    ArrayList<CompanyDetails> companyDetailses;
    private static LayoutInflater inflater=null;
    public CompanylistAdapter(Context mainActivity,ArrayList<CompanyDetails> companies) {
        // TODO Auto-generated constructor stub
        //  result=prgmNameList;
        context=mainActivity;
        this.companyDetailses = companies;
        //  imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return companyDetailses.size();
    }

    @Override
    public CompanyDetails getItem(int position) {
        // TODO Auto-generated method stub
        return companyDetailses.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        MyTextView tv,tv1,tv2;
        ImageView img;
        LinearLayout rating;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.company_list_item, null);
        holder.tv=(MyTextView) rowView.findViewById(R.id.list_com_name);
        holder.tv1=(MyTextView) rowView.findViewById(R.id.list_com_status);
        holder.tv2=(MyTextView) rowView.findViewById(R.id.list_cost);
        holder.img=(ImageView) rowView.findViewById(R.id.company_logo_list);
         holder.tv.setText(companyDetailses.get(position).title1);
        holder.tv1.setText(companyDetailses.get(position).current_status);
        holder.rating=(LinearLayout)rowView.findViewById(R.id.list_rating_ll);
        Settings.set_rating(context, companyDetailses.get(position).rating, holder.rating);
        Log.e("price",companyDetailses.get(position).price_pickup+","+companyDetailses.get(position).price_drop_off);
//        price=Integer.parseInt(companyDetailses.get(position).price_pickup)+Integer.parseInt(companyDetailses.get(position).price_drop_off);
        if(Settings.get_type(context).equals("pick")){
            holder.tv2.setText(companyDetailses.get(position).price_pickup+"  KD");
        }else{
            holder.tv2.setText(companyDetailses.get(position).price_drop_off+"  KD");
        }
        // holder.img.setImageResource(imageId[position]);
        Picasso.with(context).load(companyDetailses.get(position).logo).into(holder.img);

        return rowView;
    }

}