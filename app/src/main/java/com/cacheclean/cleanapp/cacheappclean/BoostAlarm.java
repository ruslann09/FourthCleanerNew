package com.cacheclean.cleanapp.cacheappclean;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.cacheclean.cleanapp.cacheappclean.Frags.PhoneBoost;

public final class BoostAlarm extends BroadcastReceiver {

    public final static String PREFERENCES_RES_BOOSTER = "waseem";

    SharedPreferences.Editor editor;
    SharedPreferences sharedpreferences;

    @Override
    public void onReceive(Context context, Intent intent) {

        sharedpreferences = context.getSharedPreferences(PREFERENCES_RES_BOOSTER, Context.MODE_PRIVATE);
//        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();

        /// when memory is orveloaded or increased

        editor = sharedpreferences.edit();
        editor.putString("booster", "1");
        editor.commit();

        try {
            PhoneBoost.optimizebutton.setBackgroundResource(0);
            PhoneBoost.optimizebutton.setImageResource(0);
            PhoneBoost.optimizebutton.setImageResource(R.drawable.clear_btn);
        }
        catch(Exception e)
        {

        }

    }
}
