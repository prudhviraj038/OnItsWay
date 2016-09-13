package com.yellowsoft.onitsway;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Chinni on 13-04-2016.
 */
public class NavigationActivity extends FragmentActivity implements ContactUsFragment.FragmentTouchListner,
        SummaryFragment.FragmentTouchListner, CompaniesGridFragment.FragmentTouchListner, CompanieslistFragment.FragmentTouchListner,
        OrderStatusFragment.FragmentTouchListner, CompanyDetailsFragment.FragmentTouchListner, PickupDropoffAddressFragment.FragmentTouchListner,
        HomeFragment.FragmentTouchListner, LoginSignupFragment.FragmentTouchListner, SettingsFragment.FragmentTouchListner,
        SearchFragment.FragmentTouchListner, AboutUsfragment.FragmentTouchListner, WhatWeDoFragment.FragmentTouchListner,
        MyAccountFragment.FragmentTouchListner, NotificationFragment.FragmentTouchListner, Invoicefragment.FragmentTouchListner, TermsAndConditions.FragmentTouchListner, CompanyLoginFragment.FragmentTouchListner, MyAddressfragment.FragmentTouchListner {
    DrawerLayout drawerLayout;
    ImageView menu_img, settings,back_btn,logo_image;
    MyTextView header,home_tv,my_acc_tv,about_tv,what_tv,contact_tv,tc_tv;
    FrameLayout container;
    FragmentManager fragmentManager;
    public  static final long DURATION=500;
    boolean animation_direction=true;
    LinearLayout home, myaccount, about_us, what_we_do, contact_us,tc_ll;
    ArrayList<Integer> prgmImages;
    ArrayList<String> prgmTitles;
    private ListView mDrawerList1;
    int current_position=0;
    AlertDialogManager alert = new AlertDialogManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.forceRTLIfSupported(this);
        setContentView(R.layout.navigation_activity);
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        prgmImages = new ArrayList<>();
        prgmTitles = new ArrayList<>();
        prgmImages.add(R.drawable.home);
        prgmImages.add(R.drawable.person_img);
        prgmImages.add(R.drawable.aboutus_img);
        prgmImages.add(R.drawable.whatwe_img);
        prgmImages.add(R.drawable.mobile_img);
        prgmImages.add(R.drawable.book);
//        prgmImages.add(R.drawable.book);
        prgmTitles.add(Settings.getword(this, "home"));
        prgmTitles.add(Settings.getword(this, "my_account"));
        prgmTitles.add(Settings.getword(this, "about_us"));
        prgmTitles.add(Settings.getword(this, "what_we_do"));
        prgmTitles.add(Settings.getword(this, "contact_us"));
//        prgmTitles.add(Settings.getword(this, "promotions"));
        String abcd=Settings.getcomUserid(NavigationActivity.this);
        if (abcd.equals("-1")){
            prgmTitles.add(Settings.getword(this, "company"));
        }else{
            prgmTitles.add(Settings.getword(this, "company")+" "+ Settings.getcomName(this));
        }
        mDrawerList1 = (ListView) findViewById(R.id.mdrawerlist1);
        mDrawerList1.setAdapter(new NavigationListAdapter(this, prgmImages, prgmTitles));
        header = (MyTextView) findViewById(R.id.tv_header);
        home_tv = (MyTextView) findViewById(R.id.nav_home);
        home_tv.setText(Settings.getword(this, "home"));
        my_acc_tv = (MyTextView) findViewById(R.id.nav_myaccount);
        my_acc_tv.setText(Settings.getword(this, "my_account"));
        about_tv = (MyTextView) findViewById(R.id.nav_aboutus);
        about_tv.setText(Settings.getword(this, "about_us"));
        what_tv = (MyTextView) findViewById(R.id.nav_whatwedo);
        what_tv.setText(Settings.getword(this, "what_we_do"));
        contact_tv = (MyTextView) findViewById(R.id.nav_contactus);
        contact_tv.setText(Settings.getword(this, "contact_us"));
        tc_tv = (MyTextView) findViewById(R.id.nav_tc);
        tc_tv.setText(Settings.getword(this, "title_tc"));
        menu_img = (ImageView) findViewById(R.id.menu_image);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        logo_image = (ImageView) findViewById(R.id.logo_img);
        settings = (ImageView) findViewById(R.id.settings);
        home = (LinearLayout) findViewById(R.id.nav_hpme_ll);
        myaccount = (LinearLayout) findViewById(R.id.nav_myaccount_ll);
        about_us = (LinearLayout) findViewById(R.id.nav_aboutus_ll);
        what_we_do = (LinearLayout) findViewById(R.id.nav_whatwedo_ll);
        contact_us = (LinearLayout) findViewById(R.id.nav_contactus_ll);
        tc_ll = (LinearLayout) findViewById(R.id.nav_tc_ll);
        container = (FrameLayout) findViewById(R.id.container_main);
        fragmentManager = getSupportFragmentManager();
        HomeFragment fragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.container_main, fragment).commit();

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String abc = Settings.getUserid(NavigationActivity.this);
                if (abc.equals("-1")) {
                    LoginSignupFragment loginSignupFragment = new LoginSignupFragment();
                    fragmentManager.beginTransaction().replace(R.id.container_main, loginSignupFragment).addToBackStack(null).commit();
                } else {
                    SettingsFragment settingsFragment = new SettingsFragment();
                    fragmentManager.beginTransaction().replace(R.id.container_main, settingsFragment).addToBackStack(null).commit();
                }
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

        mDrawerList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                drawerLayout.closeDrawer(GravityCompat.START);

                    switch (position) {
                        case 0:
                            animation_direction = true;
                            drawerLayout.closeDrawer(GravityCompat.END);
                            HomeFragment fragment = new HomeFragment();
                            fragmentManager.beginTransaction().replace(R.id.container_main, fragment).addToBackStack(null).commit();
                            break;
                        case 1:
                            animation_direction = true;
                            drawerLayout.closeDrawer(GravityCompat.END);
                            String abc = Settings.getUserid(NavigationActivity.this);
                            if (abc.equals("-1")) {
                                LoginSignupFragment loginSignupFragment = new LoginSignupFragment();
                                fragmentManager.beginTransaction().replace(R.id.container_main, loginSignupFragment).addToBackStack(null).commit();
                            } else {
                                MyAccountFragment fragment1 = new MyAccountFragment();
                                fragmentManager.beginTransaction().replace(R.id.container_main, fragment1).addToBackStack(null).commit();
                            }
                            break;
                        case 2:
                            animation_direction = true;
                            drawerLayout.closeDrawer(GravityCompat.END);
                            AboutUsfragment fragment2 = new AboutUsfragment();
                            fragmentManager.beginTransaction().replace(R.id.container_main, fragment2).addToBackStack(null).commit();
                        case 3:
                            animation_direction = true;
                            drawerLayout.closeDrawer(GravityCompat.END);
                            WhatWeDoFragment fragment3 = new WhatWeDoFragment();
                            fragmentManager.beginTransaction().replace(R.id.container_main, fragment3).addToBackStack(null).commit();
                            break;
                        case 4:
                            animation_direction = true;
                            drawerLayout.closeDrawer(GravityCompat.END);
                            ContactUsFragment fragment4 = new ContactUsFragment();
                            fragmentManager.beginTransaction().replace(R.id.container_main, fragment4).addToBackStack(null).commit();
                            break;
//                        case 5:
//                            animation_direction = true;
//                            drawerLayout.closeDrawer(GravityCompat.END);
//                            TermsAndConditions fragment5 = new TermsAndConditions();
//                            fragmentManager.beginTransaction().replace(R.id.container_main, fragment5).addToBackStack(null).commit();
//                            break;
                        case 5
                                :
//                            animation_direction = true;
                            drawerLayout.closeDrawer(GravityCompat.END);
                            String abcd=Settings.getcomUserid(NavigationActivity.this);
                            if (abcd.equals("-1")){
                                CompanyLoginFragment fragment6 = new CompanyLoginFragment();
                                fragmentManager.beginTransaction().replace(R.id.container_main, fragment6).commit();
                            }else{
                                Intent mainIntent = new Intent(getApplicationContext(), CompanyActvity.class);
                                startActivity(mainIntent);
//                                finish();
                            }

                            break;
                    }
//                current_position = position;
            }
        });
        String intent_msg=getIntent().getStringExtra("order");
        if(intent_msg!=null){
            my_orders_page();
        }
    }
    @Override
    public void onBackPressed() {
        animation_direction=false;

        if(orderStatusFragment!=null){
            if(orderStatusFragment.cangoback()){
                //back_btn_press_used_by_fragment;
            }
            else
                super.onBackPressed();
        }
        else
        super.onBackPressed();
    }
    @Override
    public Animation get_animation(Boolean enter) {
//            return MoveAnimation.create(MoveAnimation.RIGHT, enter, DURATION);
        if (Settings.get_user_language(this).equals("ar")) {
            if (animation_direction)
                return MoveAnimation.create(MoveAnimation.LEFT, enter, DURATION);
            else
                return MoveAnimation.create(MoveAnimation.RIGHT, enter, DURATION);
        } else {
            if (animation_direction)
                return MoveAnimation.create(MoveAnimation.RIGHT, enter, DURATION);
            else
                return MoveAnimation.create(MoveAnimation.LEFT, enter, DURATION);
        }
    }
    @Override
    public void after_login() {
//         FragmentManager fragmentManager =getSupportFragmentManager();
//         PickUpFragment pickUpFragment = new PickUpFragment();
//         fragmentManager.beginTransaction().replace(R.id.container_main, pickUpFragment).commit();
        onBackPressed();
    }
    @Override
    public void after_logout() {
        animation_direction=true;
        FragmentManager fragmentManager =getSupportFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.container_main, homeFragment).commit();
        // onBackPressed();
    }
    @Override
    public void com_login() {
        animation_direction=true;
        Intent mainIntent = new Intent(getApplicationContext(), CompanyActvity.class);
        startActivity(mainIntent);
        finish();
        // onBackPressed();
    }

    @Override
    public void signup_to_login() {
        animation_direction=true;
        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.container_main, homeFragment).commit();

    }

    @Override
    public void display_text(String text) {

    }
    @Override
    public void lang() {
        animation_direction=true;
        Intent mainIntent = new Intent(getApplicationContext(), LanguageActvity.class);
        startActivity(mainIntent);
        finish();
    }
    @Override
    public void my_orders_page() {
        animation_direction=true;
//        FragmentManager fragmentManager = getSupportFragmentManager();
        OrderStatusFragment orderStatusFragment = new OrderStatusFragment();
        fragmentManager.beginTransaction().replace(R.id.container_main, orderStatusFragment).addToBackStack(null).commit();
    }
    @Override
    public void my_address_page() {
        animation_direction=true;
//        FragmentManager fragmentManager = getSupportFragmentManager();
        MyAddressfragment myAddressfragment = new MyAddressfragment();
        fragmentManager.beginTransaction().replace(R.id.container_main, myAddressfragment).addToBackStack(null).commit();
    }

    @Override
    public void pick_drop(String type){
        animation_direction=true;
        String abc= Settings.getUserid(this);
        if(abc.equals("-1")) {
//            FragmentManager fragmentManager = this.getSupportFragmentManager();
            LoginSignupFragment loginSignupFragment = new LoginSignupFragment();
            fragmentManager.beginTransaction().replace(R.id.container_main, loginSignupFragment).addToBackStack(null).commit();
        }
        else{
//            FragmentManager fragmentManager =this.getSupportFragmentManager();
            SearchFragment searchFragment = new SearchFragment();
            fragmentManager.beginTransaction().replace(R.id.container_main, searchFragment).addToBackStack(null).commit();
        }
    }



    @Override
    public void to_companies_list(String p_area,String d_area,String item,String weight){
        animation_direction=true;
//        FragmentManager fragmentManager = this.getSupportFragmentManager();
        CompanieslistFragment companieslistFragment = new CompanieslistFragment();
        final Bundle bundle = new Bundle();
        bundle.putSerializable("p_area", p_area);
        bundle.putSerializable("d_area", d_area);
        bundle.putSerializable("item", item);
        bundle.putSerializable("weight", weight);
        companieslistFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, companieslistFragment).addToBackStack(null).commit();
    }
    @Override
    public void courier_order(String type){
        animation_direction=true;
        String abc= Settings.getUserid(this);
        Log.e("u_id",abc);
        if(abc.equals("-1")) {
//            FragmentManager fragmentManager = this.getSupportFragmentManager();
            LoginSignupFragment loginSignupFragment = new LoginSignupFragment();
            fragmentManager.beginTransaction().replace(R.id.container_main, loginSignupFragment).commit();
        }
        else{
            if(type.equals("courier")){
//            FragmentManager fragmentManager =this.getSupportFragmentManager();
            CompaniesGridFragment companiesGridFragment = new CompaniesGridFragment();
            fragmentManager.beginTransaction().replace(R.id.container_main, companiesGridFragment).addToBackStack(null).commit();
            }else{
//                FragmentManager fragmentManager = this.getSupportFragmentManager();
                orderStatusFragment = new OrderStatusFragment();
                fragmentManager.beginTransaction().replace(R.id.container_main, orderStatusFragment).addToBackStack(null).commit();
            }
        }
    }
    OrderStatusFragment orderStatusFragment;

    @Override
    public void goto_com_details(CompanyDetails companyDetails){
        animation_direction=true;
//        FragmentManager fragmentManager = this.getSupportFragmentManager();
        CompanyDetailsFragment companyDetailsFragment = new CompanyDetailsFragment();
        final Bundle bundle = new Bundle();
        bundle.putSerializable("company", companyDetails);
        companyDetailsFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, companyDetailsFragment).addToBackStack(null).commit();
    }
    @Override
    public void to_pickup(CompanyDetails companyDetails){
        animation_direction=true;
//        FragmentManager fragmentManager = this.getSupportFragmentManager();
        PickupDropoffAddressFragment summaryFragment = new PickupDropoffAddressFragment();
        final Bundle bundle = new Bundle();
        bundle.putSerializable("company", companyDetails);
        summaryFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, summaryFragment).addToBackStack(null).commit();
    }
    @Override
    public void after_drop_off(CompanyDetails companyDetails) {
        animation_direction=true;
//        FragmentManager fragmentManager = this.getSupportFragmentManager();
        SummaryFragment summaryFragment = new SummaryFragment();
        final Bundle bundle = new Bundle();
        bundle.putSerializable("company", companyDetails);
        summaryFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_main, summaryFragment).addToBackStack(null).commit();
    }
    @Override
    public void to_payment(String user_id,String price) {
        animation_direction=true;
    }

    @Override
    public void back_butt() {
        settings.setVisibility(View.GONE);
        back_btn.setVisibility(View.VISIBLE);
        logo_image.setVisibility(View.GONE);
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

    }@Override
     public void home() {
        settings.setVisibility(View.VISIBLE);
        back_btn.setVisibility(View.GONE);
        logo_image.setVisibility(View.GONE);
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
//        header.setVisibility(View.GONE);
    }

    @Override
    public void search_back_butt(String head) {
        settings.setVisibility(View.GONE);
        back_btn.setVisibility(View.VISIBLE);
        logo_image.setVisibility(View.GONE);
        header.setVisibility(View.VISIBLE);
        header.setText(head);
    }

    @Override
    public void text_back_butt(String header1) {
        settings.setVisibility(View.GONE);
        back_btn.setVisibility(View.VISIBLE);
        logo_image.setVisibility(View.GONE);
        header.setVisibility(View.VISIBLE);
        header.setText(header1);
    }

    @Override
    public void notification_page() {
        animation_direction=true;
//        FragmentManager fragmentManager = this.getSupportFragmentManager();
        NotificationFragment notificationFragment = new NotificationFragment();
        fragmentManager.beginTransaction().replace(R.id.container_main, notificationFragment).addToBackStack(null).commit();
    }


}