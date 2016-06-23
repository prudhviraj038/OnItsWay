package com.yellowsoft.onitsway;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ContactUsFragment extends Fragment {
    EditText name,email_address,phone_number,message;
    AllApis allApis = new AllApis();
    FragmentTouchListner mCallback;
    String head;
    public interface FragmentTouchListner {
        public void text_back_butt(String header);
        public void display_text(String text);
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
            mCallback = (NavigationActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Listner");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        TextView contact_us=(TextView)v.findViewById(R.id.contact_us);
        TextView address_contact=(TextView)v.findViewById(R.id.address_contact);
        try {
            JSONObject jsonObject = new JSONObject(Settings.getContactUs(getActivity()));
            head=String.valueOf(Html.fromHtml(jsonObject.getString("title"+Settings.get_lan(getActivity()))));
            mCallback.text_back_butt(head);
            address_contact.setText(Html.fromHtml(jsonObject.getString("description" + Settings.get_lan(getActivity()))));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatedata();
            }
        });
        name = (EditText) v.findViewById(R.id.et_contctus_name);
        email_address = (EditText) v.findViewById(R.id.et_contactus_email);
        phone_number = (EditText) v.findViewById(R.id.et_contactus_mobile);
        message = (EditText) v.findViewById(R.id.et_contact_msg);
        name.setHint(Settings.getword(getActivity(),"name"));
        email_address.setHint(Settings.getword(getActivity(),"email_id"));
        phone_number.setHint(Settings.getword(getActivity(),"mobile_number"));
        message.setHint(Settings.getword(getActivity(), "message"));
        mCallback.display_text(Settings.getword(getActivity(), "contact_us"));

        setupwords();
    }
    private void setupwords(){
        try {
            JSONObject words = Settings.get_user_language_words(getActivity());
            mCallback.display_text(words.getString("contact_us"));
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
            Toast.makeText(getActivity(), Settings.getword(getActivity(), "please_enter_name"), Toast.LENGTH_SHORT).show();
        }else if(email.equals("")){
            Toast.makeText(getActivity(),Settings.getword(getActivity(), "please_enter_email"),Toast.LENGTH_SHORT).show();
        }else if(phone.equals("")){
            Toast.makeText(getActivity(),Settings.getword(getActivity(), "please_enter_mobile"),Toast.LENGTH_SHORT).show();
        }else if(msg.equals("")){
            Toast.makeText(getActivity(),Settings.getword(getActivity(), "please_enter_message"),Toast.LENGTH_SHORT).show();
        }else{
            allApis.contact_us(getActivity(), namee, email, phone, msg);
        }
    }
}

