package com.yellowsoft.onitsway;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import org.json.JSONException;
import org.json.JSONObject;

public class WhatWeDoFragment extends Fragment {
    String head;
    FragmentTouchListner mCallBack;
    public interface FragmentTouchListner {
        public void text_back_butt(String header);
        public Animation get_animation(Boolean enter);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBack = (NavigationActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Listner");
        }
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return mCallBack.get_animation(enter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.what_we_do_screen, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();

        MyTextView what_we_do_title=(MyTextView)v.findViewById(R.id.title_what_we_do);
        MyTextView what_we_do_descri=(MyTextView)v.findViewById(R.id.descri_what_we_do);
        try {
            JSONObject jsonObject = new JSONObject(Settings.getSettings(getActivity()));
            head=String.valueOf(Html.fromHtml(jsonObject.getJSONObject("whatwedo").getString("title" + Settings.get_lan(getActivity()))));
            mCallBack.text_back_butt(head);
            what_we_do_title.setText(jsonObject.getJSONObject("whatwedo").getString("title" + Settings.get_lan(getActivity())));
            what_we_do_descri.setText(Html.fromHtml(jsonObject.getJSONObject("whatwedo").getString("description" + Settings.get_lan(getActivity()))));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}