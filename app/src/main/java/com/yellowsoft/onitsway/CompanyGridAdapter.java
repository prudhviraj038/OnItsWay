package com.yellowsoft.onitsway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CompanyGridAdapter extends BaseAdapter{
    String [] result;
    Context context;
    int [] imageId;
    ArrayList<CompanyDetails> companyDetailses;
    private static LayoutInflater inflater=null;
    public CompanyGridAdapter(Context mainActivity,ArrayList<CompanyDetails> companies) {
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
        TextView tv,status;
        ImageView img;
        LinearLayout rating;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.companies_grid_item, null);
        holder.tv=(TextView) rowView.findViewById(R.id.company_grid_name);
        holder.status=(TextView) rowView.findViewById(R.id.com_grid_status);
        holder.status.setText(companyDetailses.get(position).current_status);
        holder.img=(ImageView) rowView.findViewById(R.id.company_grid_logo);
        holder.rating=(LinearLayout)rowView.findViewById(R.id.rating_ll);
        Settings.set_grid_rating(context, companyDetailses.get(position).rating, holder.rating);
          holder.tv.setText(companyDetailses.get(position).title1);
//         holder.img.setImageResource(imageId[position]);
        Picasso.with(context).load(companyDetailses.get(position).logo).into(holder.img);

        return rowView;
    }

}