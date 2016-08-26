package com.yellowsoft.onitsway;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MyEditText extends android.widget.EditText {

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyEditText(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {

            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Regular.ttf");
            setTypeface(tf);
        }

    }


}