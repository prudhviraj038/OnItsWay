package com.yellowsoft.onitsway;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MyTextViewArabic extends android.widget.TextView {
    boolean loadedfromjson = false;
    public MyTextViewArabic(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyTextViewArabic(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyTextViewArabic(Context context) {
        super(context);
        init(context);
    }

    private void init(final Context context) {

        if (!isInEditMode()) {
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Hacen-Tunisia.ttf");
                setTypeface(tf);

        }

    }
}