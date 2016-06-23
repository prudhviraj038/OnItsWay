package com.yellowsoft.onitsway;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Chinni on 15-04-2016.
 */
public class CompanyDetails  implements java.io.Serializable {
    String company_id, logo, title1, description,current_status,rating,established,price_pickup,price_drop_off;
    ArrayList<Place> places_not;
     ArrayList<ItemNot> items_not;

    CompanyDetails(String company_id, String title, String description, JSONArray places_not, JSONArray items_not, String logo) {
        this.logo = logo;
        this.title1 = title;
        this.description = description;
        this.places_not = new ArrayList<>();
        this.items_not = new ArrayList<>();
        this.company_id = company_id;
        for (int i = 0; i < places_not.length(); i++) {
            try {
                this.places_not.add(new Place(places_not.getJSONObject(i).getString("title"),
                        places_not.getJSONObject(i).getString("title_ar")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < items_not.length(); i++) {
            try {
                this.items_not.add(new ItemNot(items_not.getJSONObject(i).getString("title"),
                        items_not.getJSONObject(i).getString("title_ar")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class Place implements java.io.Serializable {
       public  String title, title_ar;

        Place(String title, String title_ar) {
            this.title = title;
            this.title_ar = title_ar;
        }
    }

    public class ItemNot implements java.io.Serializable {
      public   String title, title_ar;

        ItemNot(String title, String title_ar) {
            this.title = title;
            this.title_ar = title_ar;
        }
    }
    CompanyDetails(String company_id, String title, String description,String logo,String current_status,String rating,String established,String price_pickup,String price_drop_off){
        this.company_id=company_id;
        this.title1=title;
        this.description=description;
        this.logo=logo;
        this.current_status=current_status;
        this.rating=rating;
        this.established=established;
        this.price_pickup=price_pickup;
        this.price_drop_off=price_drop_off;

    }
}
