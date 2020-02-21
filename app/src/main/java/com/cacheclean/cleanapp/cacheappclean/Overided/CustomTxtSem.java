package com.cacheclean.cleanapp.cacheappclean.Overided;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by 123 on 25-Apr-18.
 */

public class CustomTxtSem extends androidx.appcompat.widget.AppCompatTextView {

    public CustomTxtSem(Context context) {
        super(context);
        init();
    }

    public CustomTxtSem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTxtSem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        if (!isInEditMode()){
            Typeface normalTypeface = Typeface.createFromAsset(getContext().getAssets(), "Montserrat-SemiBold.ttf");
            setTypeface(normalTypeface);
        }
    }
}
