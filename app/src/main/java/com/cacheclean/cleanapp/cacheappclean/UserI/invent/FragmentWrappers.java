package com.cacheclean.cleanapp.cacheappclean.UserI.invent;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import com.cacheclean.cleanapp.cacheappclean.Frags.BatterySave;
import com.cacheclean.cleanapp.cacheappclean.Frags.CPUCool;
import com.cacheclean.cleanapp.cacheappclean.Frags.JunkClean;
import com.cacheclean.cleanapp.cacheappclean.Frags.PhoneBoost;
import com.cacheclean.cleanapp.cacheappclean.R;
import com.cacheclean.cleanapp.cacheappclean.ScreenSp;

public class FragmentWrappers extends AppCompatActivity {

    public final static String REQUEST_ACTIVITY_CODE = "CODE_ACTIVITY";
    public final static String RUNTIME_MODE = "RUNTIME_MODE";

    public final static String JUNK_CLEANER_CODE = "JUNKCLEANER",
            BOOSTER_CODE = "PHONEBOOSTER",
            COOLER_CODE = "COOLER",
            BATTERY_SAVER_CODE = "BATTERYSAVER",
            NOTIFICATIONS_CLEANER_CODE = "NOTIFICATIONSCLEANER";

    private String requestStateCode;

    public static boolean notWaitJustRedirect = false;
    private boolean isExitingFromApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_wrapper);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().hide ();

        try {
            Intent intent = getIntent ();

            requestStateCode = intent.getStringExtra(REQUEST_ACTIVITY_CODE);
        } catch (Exception e) {

        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (requestStateCode) {
            case JUNK_CLEANER_CODE:
                JunkClean junkCleaner_fragment = new JunkClean();
                ft.replace(R.id.wrapper, junkCleaner_fragment);
                ft.commit();
                break;
            case BOOSTER_CODE:
                PhoneBoost speedBooster_fragment = new PhoneBoost();
                ft.replace(R.id.wrapper, speedBooster_fragment);
                ft.commit();
                break;
            case COOLER_CODE:
                CPUCool cpuCooler_fragment = new CPUCool();
                ft.replace(R.id.wrapper, cpuCooler_fragment);
                ft.commit();
                break;
            case BATTERY_SAVER_CODE:
                BatterySave batterySaver_fragment = new BatterySave();
                ft.replace(R.id.wrapper, batterySaver_fragment);
                ft.commit();
                break;
            case NOTIFICATIONS_CLEANER_CODE:
//                NotificationCleaner_Fragment notificationCleaner_fragment = new NotificationCleaner_Fragment();
//                ft.replace(R.id.wrapper, notificationCleaner_fragment);
//                ft.commit();
                break;
            default:
                Toast.makeText(this, "Wrong app query, try now!", Toast.LENGTH_SHORT).show();
                finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isExitingFromApp) {
//            mInterstitialAd.setAdListener(new AdListener() {
//                @Override
//                public void onAdLoaded() {
//                    mInterstitialAd.show ();
//                }
//
//                @Override
//                public void onAdFailedToLoad(int i) {
//                    super.onAdFailedToLoad(i);
//                }
//            });

            startActivity (new Intent (getApplicationContext (), ScreenSp.class));

            finish();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (requestStateCode.equals(NOTIFICATIONS_CLEANER_CODE)) {
                    notWaitJustRedirect = true;
                    redirectToSecureSettings();
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    public void redirectToSecureSettings() {
        Toast.makeText(getApplicationContext(), "You should allow this app to use notification control before using",
                Toast.LENGTH_LONG).show();

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                startActivity (new Intent (getApplicationContext(), MainMenuScreen.class));

                finish ();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @Override
//    protected void onUserLeaveHint() {
//        super.onUserLeaveHint();
//
//        isExitingFromApp = true;
//    }

    public static long TIME_INTERVAL_FOR_EXIT = 1500;
    private long lastTimeBackPressed;

    @Override
    public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
        if(pKeyCode == KeyEvent.KEYCODE_BACK && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
//            if(System.currentTimeMillis() - lastTimeBackPressed < TIME_INTERVAL_FOR_EXIT) {
//                finish();
//            }
//            else {}

//            Intent startHomescreen = new Intent(getApplicationContext(), MainMenuScreen.class);
//            startActivity(startHomescreen);

            finish();

            return true;
        } else {
            return super.onKeyDown(pKeyCode, pEvent);
        }
    }
}
