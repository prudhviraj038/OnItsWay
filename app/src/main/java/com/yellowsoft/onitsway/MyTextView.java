package com.yellowsoft.onitsway;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

public class MyTextView extends android.widget.TextView {
    boolean loadedfromjson = false;
    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyTextView(Context context) {
        super(context);
        init(context);
    }

    private void init(final Context context) {

        if (!isInEditMode()) {
            if (Settings.get_user_language(context).equals("ar")) {
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Hacen-Tunisia.ttf");
                setTypeface(tf);
            } else {
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Regular.ttf");
                setTypeface(tf);
            }

        }

    }
}