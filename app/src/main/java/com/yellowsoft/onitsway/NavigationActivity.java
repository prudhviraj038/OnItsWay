package com.yellowsoft.onitsway;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by Chinni on 13-04-2016.
 */
public class NavigationActivity extends FragmentActivity implements ContactUsFragment.FragmentTouchListner,
        SummaryFragment.FragmentTouchListner, CompaniesGridFragment.FragmentTouchListner, CompanieslistFragment.FragmentTouchListner,
        OrderStatusFragment.FragmentTouchListner, CompanyDetailsFragment.FragmentTouchListner, PickupDropoffAddressFragment.FragmentTouchListner,
        HomeFragment.FragmentTouchListner, LoginSignupFragment.FragmentTouchListner, SettingsFragment.FragmentTouchListner,
        SearchFragment.FragmentTouchListner, AboutUsfragment.FragmentTouchListner, WhatWeDoFragment.FragmentTouchListner,
        MyAccountFragment.FragmentTouchListner {
    DrawerLayout drawerLayout;
    ImageView menu_img, settings,back_btn,logo_image;
    TextView header;
    FrameLayout container;
    FragmentManager fragmentManager;
    LinearLayout home, myaccount, about_us, what_we_do, contact_us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity);
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        header=(TextView)findViewById(R.id.tv_header);
        menu_img = (ImageView) findViewById(R.id.menu_image);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        logo_image = (ImageView) findViewById(R.id.logo_img);
        settings = (ImageView) findViewById(R.id.settings);
        home = (LinearLayout) findViewById(R.id.nav_hpme_ll);
        myaccount = (LinearLayout) findViewById(R.id.nav_myaccount_ll);
        about_us = (LinearLayout) findViewById(R.id.nav_aboutus_ll);
        what_we_do = (LinearLayout) findViewById(R.id.nav_whatwedo_ll);
        contact_us = (LinearLayout) findViewById(R.id.nav_contactus_ll);
        container = (FrameLayout) findViewById(R.id.container_main);
        fragmentManager = getSupportFragmentManager();
        HomeFragment fragment = new HomeFragment();
        fragmentManager.beginTransaction().add(R.id.container_main, fragment).commit();

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment settingsFragment = new SettingsFragment();
                fragmentManager.beginTransaction().replace(R.id.container_main, settingsFragment).commit();
            }
        });
        menu_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.END);
                HomeFragment fragment = new HomeFragment();
                fragmentManager.beginTransaction().replace(R.id.container_main, fragment).commit();
            }
        });
        myaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.END);
                MyAccountFragment fragment = new MyAccountFragment();
                fragmentManager.beginTransaction().replace(R.id.container_main, fragment).commit();
            }
        });
        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.END);
                AboutUsfragment fragment = new AboutUsfragment();
                fragmentManager.beginTransaction().replace(R.id.container_main, fragment).commit();
            }
        });
        what_we_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.END);
                WhatWeDoFragment fragment = new WhatWeDoFragment();
                fragmentManager.beginTransaction().replace(R.id.container_main, fragment).commit();
            }
        });
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.END);
                ContactUsFragment fragment = new ContactUsFragment();
                fragmentManager.beginTransaction().replace(R.id.container_main, fragment).commit();
            }
        });
    }

    @Override
    public void after_login() {
         FragmentManager fragmentManager =getSupportFragmentManager();
         PickUpFragment pickUpFragment = new PickUpFragment();
         fragmentManager.beginTransaction().replace(R.id.container_main, pickUpFragment).commit();
       // onBackPressed();
    }

    @Override
    public void signup_to_login() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        LoginSignupFragment loginSignupFragment = new LoginSignupFragment();
        fragmentManager.beginTransaction().replace(R.id.container_main, loginSignupFragment).commit();

    }

    @Override
    public void display_text(String text) {

    }
    @Override
    public void lang() {
        Intent mainIntent = new Intent(getApplicationContext(), LanguageActvity.class);
        startActivity(mainIntent);
        finish();
    }
    @Override
    public void my_orders_page() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        OrderStatusFragment orderStatusFragment = new OrderStatusFragment();
        fragmentManager.beginTransaction().replace(R.id.container_main, orderStatusFragment).commit();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 7) {
            if (data.getStringExtra("msg").equals("OK")) {
                Toast.makeText(NavigationActivity.this, "Payment success", Toast.LENGTH_SHORT).show();
//                kartFragment.place_order();
            } else
                Toast.makeText(NavigationActivity.this, Settings.getword(NavigationActivity.this, "pay_failed"), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void pick_drop(String type){
        String abc= Settings.getUserid(this);
        if(abc.equals("-1")) {
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            LoginSignupFragment loginSignupFragment = new LoginSignupFragment();
            fragmentManager.beginTransaction().replace(R.id.container_main, loginSignupFragment).addToBackStack(null).commit();
        }
        else{
            FragmentManager fragmentManager =this.getSupportFragmentManager();
            PickupDropoffAddressFragment pickupDropoffAddressFragment = new PickupDropoffAddressFragment();
            final Bundle bundle = new Bundle();
            bundle.putSerializable("type",type);
            pickupDropoffAddressFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.container_main, pickupDropoffAddressFragment).addToBackStack(null).commit();
        }
    }
    @Override
    public void courier_order(String type){
        String abc= Settings.getUserid(this);
        if(abc.equals("-1")) {
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            LoginSignupFragment loginSignupFragment = new LoginSignupFragment();
            fragmentManager.beginTransaction().replace(R.id.container_main, loginSignupFragment).addToBackStack(null).commit();
        }
        else{
            if(type.equals("courier")){
            FragmentManager fragmentManager =this.getSupportFragmentManager();
            CompaniesGridFragment companiesGridFragment = new CompaniesGridFragment();
            fragmentManager.beginTransaction().replace(R.id.container_main, companiesGridFragment).addToBackStack(null).commit();
            }else{
                FragmentManager fragmentManager = this.getSupportFragmentManager();
                OrderStatusFragment orderStatusFragment = new OrderStatusFragment();
                fragmentManager.beginTransaction().replace(R.id.container_main, orderStatusFragment).addToBackStack(null).commit();
            }
        }
    }
    @Override
    public void goto_com_details(CompanyDetails companyDetails){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        CompanyDetailsFragment companyDetailsFragment = new CompanyDetailsFragment();
        final Bundle bundle = new Bundle();
        bundle.putSerializable("company", companyDetails);
        companyDetailsFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, companyDetailsFragment).addToBackStack(null).commit();
    }
    @Override
    public void to_summery(CompanyDetails companyDetails){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        SummaryFragment summaryFragment = new SummaryFragment();
        final Bundle bundle = new Bundle();
        bundle.putSerializable("company", companyDetails);
        summaryFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, summaryFragment).addToBackStack(null).commit();
    }
    @Override
    public void after_drop_off() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        CompanieslistFragment companieslistFragment = new CompanieslistFragment();
        fragmentManager.beginTransaction().replace(R.id.container_main, companieslistFragment).addToBackStack(null).commit();
    }
    @Override
    public void to_payment(String user_id,String price) {
        Intent payment = new Intent(this,PaymentActivity.class);
        payment.putExtra("cust_id", user_id);
        payment.putExtra("total_price", price);
        startActivityForResult(payment, 7);
    }

    @Override
    public void back_butt() {
        settings.setVisibility(View.GONE);
        back_btn.setVisibility(View.VISIBLE);
        logo_image.setVisibility(View.VISIBLE);
        Picasso.with(this).load(R.drawable.logo).into(logo_image);
        header.setVisibility(View.GONE);
    }

    @Override
    public void com_details_bar(String logo) {
        settings.setVisibility(View.GONE);
        back_btn.setVisibility(View.VISIBLE);
        logo_image.setVisibility(View.VISIBLE);
        Picasso.with(this).load(logo).into(logo_image);
        header.setVisibility(View.GONE);
    }

    @Override
    public void pick_drop_bar() {
        settings.setVisibility(View.GONE);
        back_btn.setVisibility(View.VISIBLE);
        logo_image.setVisibility(View.VISIBLE);
        header.setVisibility(View.GONE);

    }

    @Override
    public void setting_butt() {
        settings.setVisibility(View.VISIBLE);
        back_btn.setVisibility(View.GONE);
        logo_image.setVisibility(View.GONE);
        header.setVisibility(View.GONE);
    }
    @Override
    public void setting_page_back() {
        settings.setVisibility(View.GONE);
        back_btn.setVisibility(View.VISIBLE);
        logo_image.setVisibility(View.GONE);
        header.setVisibility(View.GONE);
    }

    @Override
    public void search_back_butt() {
        settings.setVisibility(View.GONE);
        back_btn.setVisibility(View.VISIBLE);
        logo_image.setVisibility(View.GONE);
        header.setVisibility(View.GONE);
    }

    @Override
    public void text_back_butt(String header1) {
        settings.setVisibility(View.GONE);
        back_btn.setVisibility(View.VISIBLE);
        logo_image.setVisibility(View.GONE);
        header.setVisibility(View.VISIBLE);
        header.setText(header1);
    }
}