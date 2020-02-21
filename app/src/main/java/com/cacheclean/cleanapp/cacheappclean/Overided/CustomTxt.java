package com.cacheclean.cleanapp.cacheappclean.Overided;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by abc on 2/23/2018.
 */

public class CustomTxt extends androidx.appcompat.widget.AppCompatTextView {

    public CustomTxt(Context context, AttributeSet attributeSet, int defstyle)
    {
        super(context,attributeSet,defstyle);
        init();
    }

    public CustomTxt(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
        init();
    }

    public CustomTxt(Context context  )
    {
        super(context);
        init();
    }

    private void init() {

        if (!isInEditMode()){
            Typeface normalTypeface = Typeface.createFromAsset(getContext().getAssets(), "Montserrat-Light.ttf");
            setTypeface(normalTypeface);
        }
    }
}
