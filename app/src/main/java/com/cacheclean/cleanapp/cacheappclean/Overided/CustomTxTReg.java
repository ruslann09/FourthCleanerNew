package com.cacheclean.cleanapp.cacheappclean.Overided;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by 123 on 25-Apr-18.
 */

public class CustomTxTReg extends androidx.appcompat.widget.AppCompatTextView {

    public CustomTxTReg(Context context, AttributeSet attributeSet, int defstyle)
    {
        super(context,attributeSet,defstyle);
        init();
    }

    public CustomTxTReg(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
        init();
    }

    public CustomTxTReg(Context context)
    {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()){
            Typeface normalTypeface = Typeface.createFromAsset(getContext().getAssets(), "Montserrat-Regular.ttf");
            setTypeface(normalTypeface);
        }
    }
}
