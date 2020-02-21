package com.cacheclean.cleanapp.cacheappclean;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.fragment.app.FragmentActivity;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cacheclean.cleanapp.cacheappclean.PagesAdaptes.SimplePager;
import com.cacheclean.cleanapp.cacheappclean.Serve.Alarm_Notifs;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ActivMain extends FragmentActivity {

    private final static String PREFERENCES_RES = "waseembest";

    public static TextView name;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;


    private ImageView ivTab1,ivTab2,ivTab3,ivTab4;
    private ViewPager viewPager;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        final Thread.UncaughtExceptionHandler oldHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                        //Do your own error handling here

                        if (oldHandler != null)
                            oldHandler.uncaughtException(paramThread, paramThrowable); //Delegates to Android's error handling
                        else
                            System.exit(2); //Prevents the service/app from freezing
                    }
                });

//        setTheme(R.style.AppTheme1);
        name=(TextView) findViewById(R.id.name);
        sharedpreferences = getSharedPreferences(PREFERENCES_RES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

//        ImageView img_animation = (ImageView) findViewById(R.id.backbar);
//
//        TranslateAnimation animation = new TranslateAnimation(0.0f, 1000.0f, 0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
//        animation.setDuration(10000);  // animation duration
//        animation.setRepeatCount(0);
//        animation.setInterpolator(new LinearInterpolator());// animation repeat count
////        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
//        animation.setFillAfter(true);
//
//        img_animation.startAnimation(animation);



//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);




        ////// Create Tabs Layout.

//       final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tab_charge_booster));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tab_junk_cleaner));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tab_battery_saver));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tab_cpu_cooler));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        getIds();

        viewPager= (ViewPager) findViewById(R.id.pager);

        final SimplePager adapter = new SimplePager(getSupportFragmentManager(), 4/*tabLayout.getTabCount()*/);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);

        setTabListeners();

//        if (getIntent() != null && getIntent().hasExtra("MODE")) {
//            try {
//                if (getIntent().getStringExtra("MODE").equals("MODE_NOTIFICATION_CLEANER"))
//                    setSelected(2);
//            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), e.toString() + getClass(), Toast.LENGTH_LONG).show ();
//            }
//        } else
            setSelected(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        viewPager.setCurrentItem(4);

//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//
//                viewPager.setCurrentItem(tab.getPosition());
////                tab.setIcon(tabicons[tab.getPosition()]);
////                tab.getIcon().setColorFilter(Color.parseColor("#31B2FB"), PorterDuff.Mode.SRC_IN);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
////                tab.setIcon(tabblackicons[tab.getPosition()]);
//
////                tabLayout.getTabAt(tab.getPosition()).setIcon(tabblackicons[tab.getPosition()]);
////                tab.getIcon().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//
//            }
//        });

//        scheduleNotification(getNotification(), AlarmManager.INTERVAL_HALF_DAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        editor.putString("button1", "0");
        editor.putString("button2", "0");
        editor.putString("button3", "0");
        editor.putString("button4", "0");
        editor.commit();
    }

    private void scheduleNotification(Notification notification, long delay) {
        Intent notificationIntent = new Intent(this, Alarm_Notifs.class);
        notificationIntent.putExtra(Alarm_Notifs.NOTIFICATION_ID, 2);
        notificationIntent.putExtra(Alarm_Notifs.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, delay, pendingIntent);
        }
    }

    private Notification getNotification() {
        Intent resultIntent = new Intent(this, ScreenSp.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext());

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "my_channel_01";// The id of the channel.
            CharSequence name = getString(R.string.channel_name);// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);

            notificationManager.createNotificationChannel(mChannel);

            notificationBuilder.setChannelId(CHANNEL_ID);
        }

        notificationBuilder.setContentIntent(resultPendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                // обязательные настройки
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("It's time to clean the cache!") // Текст уведомления
                .setShowWhen(false)
                .setTicker("It's time to clean the cache!")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Master Clean 2018")
                .setDefaults(Notification.FLAG_SHOW_LIGHTS
                        | Notification.DEFAULT_VIBRATE
                        | Notification.FLAG_NO_CLEAR
                        | Notification.FLAG_FOREGROUND_SERVICE)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        return notificationBuilder.build();
    }


    private void getIds(){
        ivTab1= (ImageView) findViewById(R.id.iv_tab1);
        ivTab2= (ImageView) findViewById(R.id.iv_tab2);
        ivTab3= (ImageView) findViewById(R.id.iv_tab3);
        ivTab4= (ImageView) findViewById(R.id.iv_tab4);
//        ivTab5= (ImageView) findViewById(R.id.iv_tab5);
    }


    private void setTabListeners(){
        ivTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        ivTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        ivTab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

        ivTab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });

//        ivTab5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewPager.setCurrentItem(4);
//            }
//        });

    }

    private void setSelected(int pos){
        if(pos==0){
            ivTab1.setBackground(getResources().getDrawable(R.drawable.bottom_active_xml_btn));

            ivTab2.setBackground(getResources().getDrawable(R.drawable.bottom_xml_btn));
            ivTab3.setBackground(getResources().getDrawable(R.drawable.bottom_xml_btn));
            ivTab4.setBackground(getResources().getDrawable(R.drawable.bottom_xml_btn));
        }else if(pos==1){
            ivTab1.setBackground(getResources().getDrawable(R.drawable.bottom_xml_btn));
            ivTab2.setBackground(getResources().getDrawable(R.drawable.bottom_active_xml_btn));
            ivTab3.setBackground(getResources().getDrawable(R.drawable.bottom_xml_btn));
            ivTab4.setBackground(getResources().getDrawable(R.drawable.bottom_xml_btn));
        }else if(pos==2){
            ivTab3.setBackground(getResources().getDrawable(R.drawable.bottom_active_xml_btn));

            ivTab1.setBackground(getResources().getDrawable(R.drawable.bottom_xml_btn));
            ivTab2.setBackground(getResources().getDrawable(R.drawable.bottom_xml_btn));
            ivTab4.setBackground(getResources().getDrawable(R.drawable.bottom_xml_btn));
        }else if(pos==3){
            ivTab4.setBackground(getResources().getDrawable(R.drawable.bottom_active_xml_btn));

            ivTab1.setBackground(getResources().getDrawable(R.drawable.bottom_xml_btn));
            ivTab2.setBackground(getResources().getDrawable(R.drawable.bottom_xml_btn));
            ivTab3.setBackground(getResources().getDrawable(R.drawable.bottom_xml_btn));
        }
//        else if(pos==4){
//            ivTab5.setBackground(getResources().getDrawable(R.drawable.bottom_active_xml_btn));
//
//            ivTab1.setBackground(getResources().getDrawable(R.drawable.bottom_xml_btn));
//            ivTab2.setBackground(getResources().getDrawable(R.drawable.bottom_xml_btn));
//            ivTab3.setBackground(getResources().getDrawable(R.drawable.bottom_xml_btn));
//            ivTab4.setBackground(getResources().getDrawable(R.drawable.bottom_xml_btn));
//        }
    }

    public static long TIME_INTERVAL_FOR_EXIT = 1500;
    private long lastTimeBackPressed;

    @Override
    public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
        if(pKeyCode == KeyEvent.KEYCODE_BACK && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
//            if(System.currentTimeMillis() - lastTimeBackPressed < TIME_INTERVAL_FOR_EXIT) {
//                finish();
//            }
//            else {}

            Intent startHomescreen = new Intent(Intent.ACTION_MAIN);
            startHomescreen.addCategory(Intent.CATEGORY_HOME);
            startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(startHomescreen);

            return true;
        } else {
            return super.onKeyDown(pKeyCode, pEvent);
        }
    }
}
