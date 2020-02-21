package com.cacheclean.cleanapp.cacheappclean;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public final class JunkAlar extends BroadcastReceiver {
    SharedPreferences.Editor editor;
    SharedPreferences sharedpreferences;

    public final static String PREFERENCES_RES = "waseem";

    @Override
    public void onReceive(Context context, Intent intent) {

        sharedpreferences = context.getSharedPreferences(PREFERENCES_RES, Context.MODE_PRIVATE);

        /// notify user to clean junk files that junk has been appeared

        editor = sharedpreferences.edit();
        editor.putString("junk", "1");
        editor.commit();

//        try {
//            JunkClean.mainbutton.setBackgroundResource(0);
//            JunkClean.mainbutton.setImageResource(0);
//            JunkClean.mainbutton.setImageResource(R.drawable.clean);
//        }
//        catch(Exception e)
//        {
//
//        }
    }
}
