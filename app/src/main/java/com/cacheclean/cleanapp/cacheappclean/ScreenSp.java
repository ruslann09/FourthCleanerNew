package com.cacheclean.cleanapp.cacheappclean;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cacheclean.cleanapp.cacheappclean.UserI.MainActiv;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import net.grandcentrix.tray.AppPreferences;

import java.util.Timer;
import java.util.TimerTask;

import static com.cacheclean.cleanapp.cacheappclean.Frags.JunkClean.RC_HANDLE_PERMISSIONA_ALL;
import static com.cacheclean.cleanapp.cacheappclean.Frags.JunkClean.isPermission;
import static com.cacheclean.cleanapp.cacheappclean.Frags.JunkClean.cleanCacheAndTemp;

public class ScreenSp extends Activity {

//    private StartAppAd startAppAd = new StartAppAd(this);

    public static String TAG = "Cash";

    private InterstitialAd mInterstitialAd;

    public static boolean cleanEnabled = true;

    private boolean adIsLoaded = false, startAdStarted = false;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /** Called when the activity is first created. */
    Thread splashTread;

    Timer interstitialAsLoad;
    String packagename;
    boolean isAdmob, isStartApp;
    AppPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInterstitialAd = new InterstitialAd(ScreenSp.this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.on_start_admob));
        AdRequest adRequestInter = new AdRequest.Builder().build();

        mInterstitialAd.loadAd(adRequestInter);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
//                    if (!adIsLoaded) {
//                        adIsLoaded = true;
//
////                        startMenuActivity();
//
////                        if (interstitialAsLoad != null)
////                            interstitialAsLoad.cancel();
////
////                        SplashScreenActivityed.this.finish();
//                    }
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);

//                    if (!startAdStarted && !adIsLoaded) {
//                        startAdStarted = true;
//                        adIsLoaded = true;
//                        showStartAppStartingAds();
//                    }

//                    if (!startAppAd.isReady())
//                        startAppAd.loadAd();
            }
        });

        preferences = new AppPreferences(this);

        if (!isAdmob)
            isAdmob = true;

        preferences.put("admob",isAdmob);
        preferences.put("startapp",isStartApp);

        setContentView(R.layout.splash_screen);

//        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            isPermission = true;
//
//            if (cleanCacheAndTemp(getBaseContext(), false) > 0) {
//                cleanEnabled = true;
//            }

            StartAnimations();

//        } else {
//            requestAndExternalPermission();
//        }

        TextView privacy = (TextView) findViewById(R.id.privacy);

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri policy_privacy = Uri.parse("http://globalapp.info/privacy_policy_cache_remover.html");
                Intent link = new Intent (Intent.ACTION_VIEW, policy_privacy);
                startActivity (link);
            }
        });
    }

//    public void showStartAppStartingAds () {
//        if (!adIsLoaded && !startAppAd.isReady())
//            startAppAd.loadAd(new AdEventListener() {
//                @Override
//                public void onReceiveAd(Ad ad) {
//                    adIsLoaded = true;
//
//                    if (!adIsLoaded) {
//                        startMenuActivity();
//                        startAppAd.showAd();
//                    }
//                }
//
//                @Override
//                public void onFailedToReceiveAd(Ad ad) {
//                    startAppAd.loadAd();
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            for (int i = 0; i < 5; i++) {
//                                try {
//                                    Thread.sleep(500);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//
//                                if (!adIsLoaded && startAppAd.isReady()) {
//                                    adIsLoaded = true;
//
//                                    startMenuActivity();
//                                    startAppAd.showAd();
//
//                                    break;
//                                }
//                            }
//
//                            startAppAd.loadAd();
//                        }
//                    }, 3000);
//                }
//            });
//        else if (startAppAd.isReady()) {
//            startMenuActivity();
//            startAppAd.showAd();
//        }
//    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

//        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
//        anim.reset();
//        ImageView iv = (ImageView) findViewById(R.id.splash);
//        iv.clearAnimation();
//        iv.startAnimation(anim);

//        iv.setVisibility(View.VISIBLE);

        //wait load admob
        waitInterstitialAsLoad();
    }

    //Load adMob
    private void waitInterstitialAsLoad() {

        interstitialAsLoad = new Timer();
        final int[] timer = {0};

//        if (!startAdStarted) {
//            startAdStarted = true;
//            showStartAppStartingAds();
//        }

        interstitialAsLoad.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if ((mInterstitialAd!=null && mInterstitialAd.isLoaded()) && timer[0] >= 10) {
                            if (!adIsLoaded) {
                                adIsLoaded = true;

                                startMenuActivity();

                                if (isAdmob && mInterstitialAd != null && mInterstitialAd.isLoaded())
                                    mInterstitialAd.show();

                                interstitialAsLoad.cancel();

                                finish();
                            }
                        } else if (timer[0] >= 30) {
                            startMenuActivity();

//                            if (!startAdStarted) {
//                                startAdStarted = true;
//                                showStartAppStartingAds();
//                            }

                            if (!adIsLoaded && !startAdStarted) {
                                adIsLoaded = true;
                                startAdStarted = true;
//                                startAppAd.showAd();
//                                startAppAd.loadAd();
                            }

                            interstitialAsLoad.cancel();

                            finish();
                        }
                        timer[0]++;
                    }
                });
            }
        }, 0, 500);
    }

    public void startMenuActivity () {
        Intent intent = new Intent(ScreenSp.this, MainActiv.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != RC_HANDLE_PERMISSIONA_ALL) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            isPermission = true;

            if (cleanCacheAndTemp(getBaseContext(), false) > 0) {
                cleanEnabled = true;
            }

            StartAnimations();

            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void requestAndExternalPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String [] permission = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};

        if(!hasPermissions(this, permission)){
            ActivityCompat.requestPermissions(this, permission, RC_HANDLE_PERMISSIONA_ALL);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!hasPermissions(thisActivity, permission)){
                    ActivityCompat.requestPermissions(thisActivity, permission, RC_HANDLE_PERMISSIONA_ALL);
                }
            }
        };
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        startAppAd.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        startAppAd.onPause();
//    }
}