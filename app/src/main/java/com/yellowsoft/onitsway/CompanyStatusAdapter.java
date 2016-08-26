package com.yellowsoft.onitsway;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import java.util.ArrayList;

public class CompanyStatusAdapter extends BaseAdapter  implements Filterable {
    String [] result;
    Context context;
    int [] imageId;
    ArrayList<OrderDetails> orderDetailses;
    ArrayList<OrderDetails> orderDetailses_all;
    PlanetFilter planetFilter;
    AlertDialogManager alert = new AlertDialogManager();
    private static LayoutInflater inflater=null;
    public CompanyStatusAdapter(Context mainActivity, ArrayList<OrderDetails> orders) {
        // TODO Auto-generated constructor stub
        //  result=prgmNameList;
        context=mainActivity;
        orderDetailses=orders;
        orderDetailses_all=orders;
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

    @Override
    public Filter getFilter() {
        if(planetFilter==null)
            planetFilter=new PlanetFilter();
        return planetFilter;
    }

    public class Holder
    {
        MyTextView tv,status_tv,pick_tim,pick_dat,drop_tim,drop_dat,compa_name,sta_ord_id,sta_status,sta_pick_time,sta_drop_time,sta_company;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.company_item_screen, null);
        holder.sta_ord_id=(MyTextView) rowView.findViewById(R.id.c_order_id);
        holder.sta_ord_id.setText(Settings.getword(context,"order_id"));
        holder.sta_status=(MyTextView) rowView.findViewById(R.id.c_status);
        holder.sta_status.setText(Settings.getword(context,"status"));
        holder.sta_pick_time=(MyTextView) rowView.findViewById(R.id.c_time_pick);
        holder.sta_pick_time.setText(Settings.getword(context,"time_pickup"));
        holder.sta_drop_time=(MyTextView) rowView.findViewById(R.id.c_date_dropoff);
        holder.sta_drop_time.setText(Settings.getword(context,"time_dropoff"));
        holder.sta_company=(MyTextView) rowView.findViewById(R.id.c_company);
        holder.sta_company.setText(Settings.getword(context,"company"));
        holder.tv=(MyTextView) rowView.findViewById(R.id.c_order_number);
        holder.status_tv=(MyTextView) rowView.findViewById(R.id.c_order_status_dis);
        holder.pick_tim=(MyTextView) rowView.findViewById(R.id.c_order_pickup_time);
        holder.pick_dat=(MyTextView) rowView.findViewById(R.id.c_oeder_pickup_date);
        holder.drop_tim=(MyTextView) rowView.findViewById(R.id.c_order_dropoff_time);
        holder.drop_dat=(MyTextView) rowView.findViewById(R.id.c_order_dropoff_date);
        holder.compa_name=(MyTextView) rowView.findViewById(R.id.c_order_company_name);
           holder.tv.setText(orderDetailses.get(position).order_id);
        holder.status_tv.setText(orderDetailses.get(position).status);
        holder.pick_tim.setText(orderDetailses.get(position).p_time);
        holder.pick_dat.setText(orderDetailses.get(position).p_date);
        holder.drop_tim.setText(orderDetailses.get(position).d_time);
        holder.drop_dat.setText(orderDetailses.get(position).d_date);
        holder.compa_name.setText(orderDetailses.get(position).get_com_name(context));
        //  holder.tv.setText(result[position]);
        // holder.img.setImageResource(imageId[position]);

        return rowView;
    }
    private class PlanetFilter extends Filter {
        Boolean clear_all=false;
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.e("status",String.valueOf(constraint));
            FilterResults results = new FilterResults();
// We implement here the filter logic
            clear_all=false;
            if (constraint == null || constraint.length() == 0) {
                clear_all=true;
// No filter implemented we return all the list
                results.values = orderDetailses;
                results.count = orderDetailses.size();
            }
            else {
// We perform filtering operation
                ArrayList<OrderDetails> nPlanetList = new ArrayList<OrderDetails>();

                for (OrderDetails p : orderDetailses_all) {
                    if (p.status.toUpperCase().equals(constraint.toString().toUpperCase()))
                        nPlanetList.add(p);
                }

                results.values = nPlanetList;
                results.count = nPlanetList.size();

            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint,FilterResults results) {
            if (results.count == 0) {
                alert.showAlertDialog(context, "Info",Settings.getword(context,"no_orders_related_status"), false);
                orderDetailses = orderDetailses_all;
                notifyDataSetChanged();
            }
            else if(clear_all){
                orderDetailses = orderDetailses_all;
                 notifyDataSetChanged();
            }
            else {
                orderDetailses = (ArrayList<OrderDetails>) results.values;
                notifyDataSetChanged();
            }
        }

    }
}