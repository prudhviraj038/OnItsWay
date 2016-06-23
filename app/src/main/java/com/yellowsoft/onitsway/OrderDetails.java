package com.yellowsoft.onitsway;

/**
 * Created by Chinni on 23-04-2016.
 */
public class OrderDetails {
    String order_id,status,pick_time,pick_date,drop_time,drop_date,company_name;
     OrderDetails(String order_id,String status,String pick_time,String pick_date,String drop_time,String drop_date,String company_name){
        this.order_id=order_id;
        this.status=status;
        this.pick_time=pick_time;
        this.pick_date=pick_date;
        this.drop_time=drop_time;
        this.drop_date=drop_date;
        this.company_name=company_name;
    }
}
