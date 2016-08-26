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

public class ContactUsFragment extends Fragment {
    MyEditText name,email_address,phone_number,message;
    AllApis allApis = new AllApis();
    FragmentTouchListner mCallBack;
    String head;
    AlertDialogManager alert = new AlertDialogManager();
    MyTextView title_comments;
    public interface FragmentTouchListner {
        public void text_back_butt(String header);
        public void display_text(String text);
        public Animation get_animation(Boolean enter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contct_us_screen, container, false);

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        MyTextView contact_us=(MyTextView)v.findViewById(R.id.contact_us);
        contact_us.setText(Settings.getword(getActivity(),"contact_us"));
        MyTextView address_contact=(MyTextView)v.findViewById(R.id.address_contact);
        try {
            JSONObject jsonObject = new JSONObject(Settings.getSettings(getActivity()));
            head=String.valueOf(Html.fromHtml(jsonObject.getJSONObject("contactus").getString("title" + Settings.get_lan(getActivity()))));
            mCallBack.text_back_butt(head);
            address_contact.setText(Html.fromHtml(jsonObject.getJSONObject("contactus").getString("description" + Settings.get_lan(getActivity()))));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatedata();
            }
        });
        name = (MyEditText) v.findViewById(R.id.et_contctus_name);
        email_address = (MyEditText) v.findViewById(R.id.et_contactus_email);
        phone_number = (MyEditText) v.findViewById(R.id.et_contactus_mobile);
        message = (MyEditText) v.findViewById(R.id.et_contact_msg);
        name.setHint(Settings.getword(getActivity(),"name"));
        email_address.setHint(Settings.getword(getActivity(),"email_id"));
        phone_number.setHint(Settings.getword(getActivity(),"mobile_number"));
        message.setHint(Settings.getword(getActivity(),"comments"));
        title_comments = (MyTextView) v.findViewById(R.id.contact_comments);
        title_comments.setText(Settings.getword(getActivity(), "comments"));
        mCallBack.display_text(Settings.getword(getActivity(), "contact_us"));

        setupwords();
    }
    private void setupwords(){
        try {
            JSONObject words = Settings.get_user_language_words(getActivity());
            mCallBack.display_text(words.getString("contact_us"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

  private void validatedata(){
      final String namee = name.getText().toString();
      final String email = email_address.getText().toString();
      final String phone = phone_number.getText().toString();
      final String msg = message.getText().toString();
        if(namee.equals("")){
//            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_name"), Toast.LENGTH_SHORT).show();
            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_name"), false);
        }else if(email.equals("")){
//            Toast.makeText(getActivity(),Settings.getword(getActivity(), "please_enter_email"),Toast.LENGTH_SHORT).show();
            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_email"), false);
        }else if(phone.equals("")){
//            Toast.makeText(getActivity(),Settings.getword(getActivity(), "please_enter_mobile"),Toast.LENGTH_SHORT).show();
            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_mobile"), false);
        }else if(msg.equals("")){
//            Toast.makeText(getActivity(),Settings.getword(getActivity(), "please_enter_message"),Toast.LENGTH_SHORT).show();
            alert.showAlertDialog(getActivity(), "Info", Settings.getword(getActivity(), "please_enter_message"), false);
        }else{
            allApis.contact_us(getActivity(), namee, email, phone, msg);
        }
    }
}

